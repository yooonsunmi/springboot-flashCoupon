package com.yoonsunmi.flashCoupon.domain.user.application;

import com.yoonsunmi.flashCoupon.domain.auth.entity.AppUser;
import com.yoonsunmi.flashCoupon.domain.auth.repository.AppUserRepository;
import com.yoonsunmi.flashCoupon.domain.user.entity.Coupon;
import com.yoonsunmi.flashCoupon.domain.user.entity.CouponStatus;
import com.yoonsunmi.flashCoupon.domain.user.entity.UserCoupon;
import com.yoonsunmi.flashCoupon.domain.user.repository.CouponRepository;
import com.yoonsunmi.flashCoupon.domain.user.repository.UserCouponRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final AppUserRepository appUserRepository;

    /**
     * 쿠폰 지급
     */
    @Transactional
    public void couponIssue(Long couponId, Long userId) {
        Coupon coupon = couponRepository.findByIdWithLock(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (userCouponRepository.existsByUserIdAndCouponId(userId, couponId)) {
            throw new IllegalStateException("이미 발급받은 쿠폰입니다.");
        }

        // 재고 확인 및 수량 증가 메서드 호출
        coupon.increaseIssuedQuantity();

        UserCoupon userCoupon = new UserCoupon(coupon, appUser, CouponStatus.ISSUED);

        userCouponRepository.save(userCoupon);
    }

}
