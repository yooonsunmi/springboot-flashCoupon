package com.yoonsunmi.timetracking.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
    @Schema(description = "로그인 아이디", example = "admin")
    @NotBlank(message = "아이디는 필수입니다.")
    private String loginId;

    @Schema(description = "비밀번호", example = "admin1234")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
