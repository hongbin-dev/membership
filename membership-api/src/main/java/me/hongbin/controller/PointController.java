package me.hongbin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.hongbin.generic.type.CustomPageRequest;
import me.hongbin.point.dto.PointReadRequest;
import me.hongbin.point.dto.PointReadResponse;
import me.hongbin.point.dto.PointSaveRequest;
import me.hongbin.point.dto.PointUseRequest;
import me.hongbin.point.service.PointService;

@RestController
public class PointController {

    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @PostMapping("/points")
    public ResponseEntity<Void> save(@Valid @RequestBody PointSaveRequest request) {
        pointService.save(request.barcode(), request.partnerId(), request.point());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/points/use")
    public ResponseEntity<Void> use(@Valid @RequestBody PointUseRequest request) {
        pointService.use(request.barcode(), request.partnerId(), request.point());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/points")
    public ResponseEntity<PointReadResponse> readAll(@Valid PointReadRequest request,
        CustomPageRequest customPageRequest) {
        var response = pointService.readAll(request.barcode(), request.startDateTime(), request.endDateTime(),
            customPageRequest);

        return ResponseEntity.ok(response);
    }
}
