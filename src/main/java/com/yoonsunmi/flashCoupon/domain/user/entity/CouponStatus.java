package com.yoonsunmi.flashCoupon.domain.user.entity;

import lombok.Getter;

@Getter
public enum CouponStatus {
    ISSUED("발급완료"),
    USED("사용완료"),
    EXPIRED("만료");

    // getter
    private final String description;  // 필드

    CouponStatus(String description) { // 생성자 (항상 private)
        this.description = description;
    }
}
