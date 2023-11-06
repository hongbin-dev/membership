package me.hongbin.point.dto;

import jakarta.validation.constraints.NotNull;

public record PointSaveRequest(
    @NotNull(message = "바코드는 필수입니다.") String barcode,
    @NotNull(message = "파트너 ID는 필수입니다.") Long partnerId,
    @NotNull(message = "포인트양은 필수입니다.") Long point
) {
}
