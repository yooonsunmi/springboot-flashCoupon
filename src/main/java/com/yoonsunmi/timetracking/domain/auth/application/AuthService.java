package com.yoonsunmi.timetracking.domain.auth.application;

import com.yoonsunmi.timetracking.domain.user.dto.request.LoginRequestDto;
import com.yoonsunmi.timetracking.domain.user.dto.response.LoginResponseDto;
import com.yoonsunmi.timetracking.domain.user.entity.AppUser;
import com.yoonsunmi.timetracking.domain.user.repository.AppUserRepository;
import com.yoonsunmi.timetracking.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginResponseDto login(LoginRequestDto dto) {

        AppUser user = userRepository.findByLoginId(dto.getLoginId())
                .filter(AppUser::isActive)
                .orElseThrow(() ->
                        new ResponseStatusException(UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtProvider.createToken(
                user.getLoginId(),
                user.getRole().name()   // USER / ADMIN
        );

        return new LoginResponseDto(token);
    }

}

