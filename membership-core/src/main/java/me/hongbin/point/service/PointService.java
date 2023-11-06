package me.hongbin.point.service;

import static me.hongbin.generic.NumberUtils.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.hongbin.barcode.domain.BarcodeRepository;
import me.hongbin.generic.exception.NotFoundException;
import me.hongbin.generic.type.CustomPageRequest;
import me.hongbin.generic.type.PageResponse;
import me.hongbin.partner.domain.Partner;
import me.hongbin.partner.infra.PartnerRepository;
import me.hongbin.point.domain.Point;
import me.hongbin.point.domain.PointHistory;
import me.hongbin.point.domain.PointHistoryRepository;
import me.hongbin.point.domain.PointRepository;
import me.hongbin.point.domain.TransactionType;
import me.hongbin.point.dto.PointReadResponse;

@Service
@Transactional(readOnly = true)
public class PointService {

    private final BarcodeRepository barcodeRepository;
    private final PartnerRepository partnerRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PointRepository pointRepository;

    public PointService(BarcodeRepository barcodeRepository, PartnerRepository partnerRepository,
        PointHistoryRepository pointHistoryRepository, PointRepository pointRepository) {
        this.barcodeRepository = barcodeRepository;
        this.partnerRepository = partnerRepository;
        this.pointHistoryRepository = pointHistoryRepository;
        this.pointRepository = pointRepository;
    }

    @Transactional
    public void save(String barcode, Long partnerId, Long amount) {
        var userId = findUserId(barcode);
        var partner = findPartner(partnerId);

        var persistedPoint = pointRepository.findByWithLock(userId, partner.getCategory())
            .orElseGet(() -> savePoint(partner, userId));
        persistedPoint.plus(amount);

        var history = new PointHistory(partner.getId(), amount, TransactionType.SAVE, persistedPoint.getId());
        pointHistoryRepository.save(history);
    }

    private Point savePoint(Partner partner, Long userId) {
        var point = new Point(0L, partner.getCategory(), userId);
        return pointRepository.save(point);
    }

    @Transactional
    public void use(String barcode, Long partnerId, Long amount) {
        var userId = findUserId(barcode);
        var partner = findPartner(partnerId);

        var point = pointRepository.findByWithLock(userId, partner.getCategory())
            .orElseThrow(
                () -> new NotFoundException("해당 유저의 %s 카테고리의 포인트가 존재하지 않습니다".formatted(partner.getCategory())));

        point.subtract(amount);

        var pointHistory = new PointHistory(partnerId, toNegative(amount), TransactionType.USE, point.getId());
        pointHistoryRepository.save(pointHistory);
    }

    public PointReadResponse readAll(
        String barcode,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        CustomPageRequest pageable
    ) {
        var userId = findUserId(barcode);
        var points = pointHistoryRepository.findBy(userId, startDateTime, endDateTime,
            pageable.toPage());

        return new PointReadResponse(
            new PageResponse(points.getNumber(), points.getSize(), points.getTotalPages()),
            points.toList()
        );
    }

    private Long findUserId(String barcode) {
        return barcodeRepository.findByBarcode(barcode)
            .orElseThrow(() -> new NotFoundException("%s 바코드를 찾을 수 없습니다".formatted(barcode)));
    }

    private Partner findPartner(Long partnerId) {
        return partnerRepository.findById(partnerId)
            .orElseThrow(() -> new NotFoundException("[%d] 존재하지 않는 파트너입니다".formatted(partnerId)));
    }
}
