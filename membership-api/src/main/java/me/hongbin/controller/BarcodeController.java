package me.hongbin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.hongbin.barcode.dto.BarcodeCreateRequest;
import me.hongbin.barcode.dto.BarcodeCreateResponse;
import me.hongbin.barcode.service.BarcodeService;

@RestController
public class BarcodeController {

    private final BarcodeService barcodeService;

    public BarcodeController(BarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }

    @PostMapping("/barcodes")
    public ResponseEntity<BarcodeCreateResponse> create(@Valid @RequestBody BarcodeCreateRequest request) {
        var response = barcodeService.create(request.userId());

        return ResponseEntity.ok(response);
    }
}
