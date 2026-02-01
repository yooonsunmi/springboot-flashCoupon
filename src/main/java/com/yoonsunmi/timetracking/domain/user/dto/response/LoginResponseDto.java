package com.yoonsunmi.timetracking.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    @Schema(description = "JWT Access Token")
    private String accessToken;
}
