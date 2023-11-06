package me.hongbin.barcode.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.hongbin.barcode.domain.BarcodeRepository;
import me.hongbin.barcode.domain.NumberGenerator;
import me.hongbin.barcode.dto.BarcodeCreateResponse;
import me.hongbin.generic.exception.BarcodeException;
import me.hongbin.generic.exception.NotFoundException;
import me.hongbin.generic.type.LockSupport;
import me.hongbin.user.domain.User;
import me.hongbin.user.domain.UserRepository;

@Service
@Transactional(readOnly = true)
public class BarcodeService {

    private static final int BARCODE_SIZE = 10;

    private final BarcodeRepository barcodeRepository;
    private final NumberGenerator numberGenerator;
    private final UserRepository userRepository;
    private final LockSupport lockSupport;

    public BarcodeService(BarcodeRepository barcodeRepository, NumberGenerator numberGenerator,
        UserRepository userRepository, LockSupport lockSupport) {
        this.barcodeRepository = barcodeRepository;
        this.numberGenerator = numberGenerator;
        this.userRepository = userRepository;
        this.lockSupport = lockSupport;
    }

    public BarcodeCreateResponse create(Long userId) {
        var user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다 id = %d".formatted(userId)));

        var barcode = fetchBarcode(userId, user);
        return new BarcodeCreateResponse(barcode);
    }

    private String fetchBarcode(Long userId, User user) {
        lockSupport.getLock(userId.toString());

        var barcode = barcodeRepository.findByUserId(user.id())
            .orElseGet(() -> saveNewBarcode(user.id()));

        lockSupport.release(userId.toString());
        return barcode;
    }

    private String saveNewBarcode(Long userId) {
        var number = numberGenerator.generate()
            .toString();
        var barcode = fillSize(number);

        if (barcodeRepository.existBarcode(barcode)) {
            throw new BarcodeException("바코드 생성에 실패했습니다. 다시 시도해주세요.");
        }

        barcodeRepository.save(userId, barcode);
        return barcode;
    }

    private static String fillSize(String barcode) {
        var length = barcode.length();
        var count = BARCODE_SIZE - length;

        return "0".repeat(count) + barcode;
    }
}
