package com.yoonsunmi.flashCoupon.domain.user.repository;

import com.yoonsunmi.flashCoupon.domain.user.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    // 특정 유저 쿠폰 발급 여부
    boolean existsByUserIdAndCouponId(Long userId, Long couponId);

    // 특정 유저 쿠폰 목록 조회
    List<UserCoupon> findAllByUserId(Long userId);
}
