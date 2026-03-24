package com.yoonsunmi.flashCoupon.domain.user.api;

import com.yoonsunmi.flashCoupon.domain.auth.entity.UserDetail;
import com.yoonsunmi.flashCoupon.domain.user.application.CouponService;
import com.yoonsunmi.flashCoupon.domain.user.dto.response.MyCouponResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coupon", description = "쿠폰 API")
@RequestMapping("/coupons")
@Validated
@RequiredArgsConstructor
@RestController
@Slf4j
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "쿠폰 지급")
    @PostMapping("/{couponId}/issue")
    public ResponseEntity<String> couponIssue(@AuthenticationPrincipal UserDetail userDetail, @PathVariable Long couponId) {
        couponService.couponIssue(couponId, userDetail.getUserId());
        return ResponseEntity.ok("쿠폰 발급이 완료되었습니다.");
    }

    @Operation(summary = "내 쿠폰 조회")
    @GetMapping("/myCoupon")
    public ResponseEntity<List<MyCouponResponseDto>> getMyCoupons(@AuthenticationPrincipal UserDetail userDetail) {
        List<MyCouponResponseDto> myCoupons = couponService.getMyCoupons(userDetail.getUserId());
        return ResponseEntity.ok(myCoupons);
    }

}
