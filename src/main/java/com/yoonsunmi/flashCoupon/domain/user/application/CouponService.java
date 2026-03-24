package com.yoonsunmi.flashCoupon.domain.user.application;

import com.yoonsunmi.flashCoupon.domain.auth.entity.AppUser;
import com.yoonsunmi.flashCoupon.domain.auth.repository.AppUserRepository;
import com.yoonsunmi.flashCoupon.domain.user.dto.request.CouponCreateRequestDto;
import com.yoonsunmi.flashCoupon.domain.user.dto.response.MyCouponResponseDto;
import com.yoonsunmi.flashCoupon.domain.user.entity.Coupon;
import com.yoonsunmi.flashCoupon.domain.user.entity.CouponStatus;
import com.yoonsunmi.flashCoupon.domain.user.entity.UserCoupon;
import com.yoonsunmi.flashCoupon.domain.user.repository.CouponRepository;
import com.yoonsunmi.flashCoupon.domain.user.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final AppUserRepository appUserRepository;


    /**
     * 쿠폰 지급
     * @param couponId
     * @param userId
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

    /**
     * 내 쿠폰 조회
     * @param userId
     * @return resultList
     */
    @Transactional(readOnly = true)
    public List<MyCouponResponseDto> getMyCoupons(Long userId) {
        List<UserCoupon> userCoupons = userCouponRepository.findAllByUserIdWithCoupon(userId);

        List<MyCouponResponseDto> resultList = new ArrayList<>();

        for (UserCoupon userCoupon : userCoupons) {
            MyCouponResponseDto dto = new MyCouponResponseDto(
                    userCoupon.getId(),
                    userCoupon.getCoupon().getName(),
                    userCoupon.getCoupon().getDescription(),
                    userCoupon.getIssuedAt(),
                    userCoupon.getStatus()
            );

            resultList.add(dto);
        }

        return resultList;
    }

    /**
     * 쿠폰 등록
     * @param requestDto
     * @return
     */
    @Transactional
    public Long createCoupon(CouponCreateRequestDto requestDto) {

        Coupon coupon = Coupon.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .totalQuantity(requestDto.getTotalQuantity())
                .startAt(requestDto.getStartAt())
                .endAt(requestDto.getEndAt())
                .validDays(requestDto.getValidDays())
                .build();

        return couponRepository.save(coupon).getId();
    }
}
