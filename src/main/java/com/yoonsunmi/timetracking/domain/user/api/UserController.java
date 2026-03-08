package com.yoonsunmi.timetracking.domain.user.api;

import com.yoonsunmi.timetracking.domain.user.dto.response.MeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 API")
@Validated
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    @Operation(summary = "내 정보 조회", tags = "User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "조회 실패(토큰 만료 재발급 필요)")
    })
    @GetMapping("/me")
    public MeResponseDto me(@AuthenticationPrincipal UserDetails userDetails) {
        String loginId = userDetails.getUsername();

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .findFirst()
                .map(auth -> auth.substring(5))  // "ROLE_" 이후의 문자열만 추출
                .orElse("USER");                           // 기본값 설정

        return new MeResponseDto(loginId, role);
    }

}
