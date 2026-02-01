package com.yoonsunmi.timetracking.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeResponseDto {
    @Schema(description = "로그인 아이디", example = "admin")
    private String loginId;

    @Schema(description = "권한", example = "USER")
    private String role;
}
