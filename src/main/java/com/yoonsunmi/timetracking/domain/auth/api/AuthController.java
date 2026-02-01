package com.yoonsunmi.timetracking.domain.auth.api;

import com.yoonsunmi.timetracking.domain.auth.application.AuthService;
import com.yoonsunmi.timetracking.domain.auth.dto.response.MeResponseDto;
import com.yoonsunmi.timetracking.domain.user.dto.request.LoginRequestDto;
import com.yoonsunmi.timetracking.domain.user.dto.response.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 API")
@Validated
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "토큰 정상 여부 확인", tags = "Auth")
    @GetMapping("/me")
    public MeResponseDto me(Authentication authentication) {
        String loginId = (String) authentication.getPrincipal();

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse(null);

        return new MeResponseDto(loginId, role);
    }

    @Operation(summary = "로그인 및 토큰 발급", tags = "Auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패 (아이디/비밀번호 불일치)")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

}


