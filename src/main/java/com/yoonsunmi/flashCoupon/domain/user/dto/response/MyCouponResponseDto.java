package com.yoonsunmi.flashCoupon.domain.user.dto.response;

import com.yoonsunmi.flashCoupon.domain.user.entity.CouponStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyCouponResponseDto {

    @Schema(description = "쿠폰 id", example = "1")
    private Long userCouponId;

    @Schema(description = "쿠폰명", example = "10% 할인쿠폰")
    private String couponName;

    @Schema(description = "쿠폰 설명", example = "베스트 상품 10% 할인 쿠폰입니다.")
    private String description;

    @Schema(description = "쿠폰 지급 날짜", example = "2026-03-01T00:00:00")
    private LocalDateTime issuedAt;

    @Schema(description = "상태", example = "발급완료")
    private CouponStatus status;
}
