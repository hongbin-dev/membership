package me.hongbin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.hongbin.partner.dto.PartnerCreateRequest;
import me.hongbin.partner.dto.PartnerCreateResponse;
import me.hongbin.partner.service.PartnerService;

@RestController
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping("/partners")
    public ResponseEntity<PartnerCreateResponse> create(@Valid @RequestBody PartnerCreateRequest request) {
        var response = partnerService.create(request.name(), request.category());

        return ResponseEntity.ok(response);
    }
}
