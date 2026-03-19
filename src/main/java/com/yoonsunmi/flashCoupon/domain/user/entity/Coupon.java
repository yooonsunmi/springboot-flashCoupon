package com.yoonsunmi.flashCoupon.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 무분별한 객체 생성 방지
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;            // 쿠폰명

    @Column(length = 200)
    private String description;     // 쿠폰 설명

    @Column(nullable = false)
    private int totalQuantity;      // 전체 수량

    private int issuedQuantity = 0; // 발급된 수량 (기본값 0)

    private LocalDateTime startAt;  // 사용 가능 시작일

    private LocalDateTime endAt;    // 사용 만료일 (고정 기간일 경우)

    private Integer validDays;      // 발급 후 사용 가능 일수 (상대 기간일 경우, 예: 30일)

    @Builder
    public Coupon(String name, String description, int totalQuantity, LocalDateTime startAt, LocalDateTime endAt, Integer validDays) {
        this.name = name;
        this.description = description;
        this.totalQuantity = totalQuantity;
        this.startAt = startAt;
        this.endAt = endAt;
        this.validDays = validDays;
        this.issuedQuantity = 0;
    }

    // 비즈니스 로직: 재고 확인 및 수량 증가
    public void increaseIssuedQuantity() {
        if (this.issuedQuantity >= this.totalQuantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.issuedQuantity++;
    }

}
