package com.yoonsunmi.flashCoupon.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponCreateRequestDto {

    @NotBlank(message = "쿠폰 이름은 필수입니다.")
    @Schema(description = "쿠폰명", example = "선착순 100명 할인 쿠폰")
    private String name;

    @Schema(description = "설명", example = "회원 대상으로 발급되는 선착순 쿠폰입니다.")
    private String description;

    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
    @Schema(description = "총 수량", example = "100")
    private int totalQuantity;

    @Schema(description = "시작일", example = "2026-03-25T00:00:00")
    private LocalDateTime startAt;

    @Schema(description = "종료일", example = "2026-04-25T23:59:59")
    private LocalDateTime endAt;

    @Schema(description = "발급 후 사용 가능 일수", example = "7")
    private Integer validDays;
}
