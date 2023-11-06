package me.hongbin.point.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import me.hongbin.barcode.domain.BarcodeRepository;
import me.hongbin.barcode.service.InMemoryBarcodeRepository;
import me.hongbin.generic.exception.NotFoundException;
import me.hongbin.generic.exception.PointException;
import me.hongbin.partner.domain.Category;
import me.hongbin.partner.domain.Partner;
import me.hongbin.partner.infra.PartnerRepository;
import me.hongbin.point.domain.Point;
import me.hongbin.point.domain.PointHistoryRepository;
import me.hongbin.point.domain.PointRepository;

@DataJpaTest
class PointService2Test {

    private PointService pointService;

    private BarcodeRepository barcodeRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    @Autowired
    private PointRepository pointRepository;

    @BeforeEach
    void setUp() {
        barcodeRepository = new InMemoryBarcodeRepository();
        pointService = new PointService(barcodeRepository, partnerRepository, pointHistoryRepository, pointRepository);
    }

    @Test
    @DisplayName("포인트를 추가할 수 있다")
    void save() {
        barcodeRepository.save(1L, "0123456789");
        var partner = partnerRepository.save(new Partner(Category.A, "partner"));

        pointService.save("0123456789", partner.getId(), 5000L);

        var point = pointRepository.findByWithLock(1L, Category.A)
            .get();
        assertThat(point.getAmount()).isEqualTo(5000L);
    }

    @Test
    @DisplayName("포인트 저장시 바코드가 존재하지 않으면 예외가 발생한다.")
    void save2() {
        partnerRepository.save(new Partner(Category.A, "partner"));

        assertThatThrownBy(
            () -> pointService.save("0123456789", 1L, 5000L))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("0123456789 바코드를 찾을 수 없습니다");
    }

    @Test
    @DisplayName("포인트 저장시 유효하지 않는 파트너 입력시 예외가 발생한다.")
    void save3() {
        barcodeRepository.save(1L, "0123456789");

        assertThatThrownBy(
            () -> pointService.save("0123456789", 1L, 5000L))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("[1] 존재하지 않는 파트너입니다");
    }

    @Test
    @DisplayName("포인트를 사용할 수 있다")
    void use() {
        barcodeRepository.save(1L, "0123456789");
        pointRepository.save(new Point(5000L, Category.A, 1L));
        partnerRepository.save(new Partner(Category.A, "partner"));

        pointService.use("0123456789", 1L, 5000L);

        var point = pointRepository.findByWithLock(1L, Category.A)
            .get();
        assertThat(point.getAmount()).isEqualTo(0);
    }

    @Test
    @DisplayName("포인트를 사용시 바코드가 존재하지 않으면 예외가 발생한다.")
    void use2() {
        pointRepository.save(new Point(5000L, Category.A, 1L));
        partnerRepository.save(new Partner(Category.A, "partner"));

        assertThatThrownBy(
            () -> pointService.use("0123456789", 1L, 5000L))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("0123456789 바코드를 찾을 수 없습니다");
    }

    @Test
    @DisplayName("포인트를 사용시 바코드가 존재하지 않으면 예외가 발생한다.")
    void use3() {
        barcodeRepository.save(1L, "0123456789");
        pointRepository.save(new Point(5000L, Category.A, 1L));

        assertThatThrownBy(
            () -> pointService.use("0123456789", 1L, 5000L))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("[1] 존재하지 않는 파트너입니다");
    }

    @Test
    @DisplayName("포인트를 사용시 포인트가 부족하면 예외가 발생한다..")
    void use4() {
        barcodeRepository.save(1L, "0123456789");
        var partner = partnerRepository.save(new Partner(Category.A, "partner"));
        pointRepository.save(new Point(4999L, Category.A, 1L));

        assertThatThrownBy(
            () -> pointService.use("0123456789", partner.getId(), 5000L))
            .isInstanceOf(PointException.class)
            .hasMessage("포인트 사용량을 초과했습니다. total=4999, request=5000");
    }
}