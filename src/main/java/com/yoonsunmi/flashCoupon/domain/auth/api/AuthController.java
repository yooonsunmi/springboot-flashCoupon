package com.yoonsunmi.flashCoupon.domain.auth.api;

import com.yoonsunmi.flashCoupon.domain.auth.application.AuthService;
import com.yoonsunmi.flashCoupon.domain.auth.dto.request.JoinRequestDto;
import com.yoonsunmi.flashCoupon.domain.auth.dto.request.LoginRequestDto;
import com.yoonsunmi.flashCoupon.domain.auth.dto.request.RefreshRequestDto;
import com.yoonsunmi.flashCoupon.domain.auth.dto.response.JoinResponseDto;
import com.yoonsunmi.flashCoupon.domain.auth.dto.response.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 API")
@Validated
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 및 토큰 발급", tags = "Auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패(아이디/비밀번호 불일치)")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @Operation(summary = "로그아웃", tags = "Auth")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // 헤더에 담긴 access Token 으로 처리
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);
            authService.logout(accessToken);
        }

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @Operation(summary = "회원가입", tags = "Auth")
    @PostMapping("/join")
    public ResponseEntity<JoinResponseDto> join(@RequestBody @Valid JoinRequestDto dto) {
        return null;
    }

    @Operation(summary = "토큰 재발급", tags = "Auth")
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(@RequestBody RefreshRequestDto request) {
        LoginResponseDto loginResponseDto = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(loginResponseDto);
    }

}


