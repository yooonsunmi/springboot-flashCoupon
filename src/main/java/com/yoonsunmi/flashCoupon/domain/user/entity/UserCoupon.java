package com.yoonsunmi.flashCoupon.domain.user.entity;

import com.yoonsunmi.flashCoupon.domain.auth.entity.AppUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ManyToOne으로 관계를 맺으면 DB에서는 자동으로 coupon_id FK가 생성
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(nullable = false)
    private LocalDateTime issuedAt;             // 쿠폰 발급 일시

    @Enumerated(EnumType.STRING)                // Enum은 반드시 STRING으로 저장해야 나중에 순서가 바뀌어도 안전!
    private CouponStatus status;                // 쿠폰 발급 상태

    @Builder
    public UserCoupon(Coupon coupon, AppUser user, CouponStatus status) {
        this.coupon = coupon;
        this.user = user;
        this.status = status;
        this.issuedAt = LocalDateTime.now();    // 발급 시점 자동 기록
    }

}
