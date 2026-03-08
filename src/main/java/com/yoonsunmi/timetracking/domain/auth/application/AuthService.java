package com.yoonsunmi.timetracking.domain.auth.application;

import com.yoonsunmi.timetracking.domain.auth.dto.request.JoinRequestDto;
import com.yoonsunmi.timetracking.domain.auth.dto.request.LoginRequestDto;
import com.yoonsunmi.timetracking.domain.auth.dto.response.JoinResponseDto;
import com.yoonsunmi.timetracking.domain.auth.dto.response.LoginResponseDto;
import com.yoonsunmi.timetracking.domain.auth.entity.AppUser;
import com.yoonsunmi.timetracking.domain.auth.entity.RefreshToken;
import com.yoonsunmi.timetracking.domain.auth.repository.AppUserRepository;
import com.yoonsunmi.timetracking.domain.auth.repository.RefreshTokenRepository;
import com.yoonsunmi.timetracking.global.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponseDto login(LoginRequestDto dto) {
        AppUser user = userRepository.findByLoginId(dto.getLoginId())
                .filter(AppUser::isActive)
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(user.getLoginId(), user.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(user.getLoginId());

        // 해당 유저의 리프레시 토큰이 있다면 업데이트, 없으면 신규 저장
        RefreshToken tokenEntity = refreshTokenRepository.findByLoginId(user.getLoginId())
                .map(entity -> entity.updateToken(refreshToken))
                .orElse(new RefreshToken(user.getLoginId(), refreshToken));

        refreshTokenRepository.save(tokenEntity);

        return new LoginResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public void logout(String accessToken) {
        if (!jwtProvider.validateToken(accessToken)) {
            return; // 이미 만료된 토큰이면 그냥 종료
        }

        String loginId = jwtProvider.getLoginId(accessToken);

        refreshTokenRepository.deleteByLoginId(loginId);
    }

    @Transactional
    public LoginResponseDto refreshToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new ResponseStatusException(UNAUTHORIZED, "리프레시 토큰이 만료되었습니다. 다시 로그인해주세요.");
        }

        String loginId = jwtProvider.getLoginId(refreshToken);

        // DB에 저장된 토큰이랑 비교
        Optional<RefreshToken> token = refreshTokenRepository.findByLoginId(loginId);
        if (token.isEmpty() || !token.get().getToken().equals(refreshToken)) {
            throw new ResponseStatusException(UNAUTHORIZED, "잘못된 토큰입니다.");
        }

        // 유저 정보 조회
        AppUser user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "사용자를 찾을 수 없습니다."));

        String newAccessToken = jwtProvider.createAccessToken(user.getLoginId(), user.getRole().name());
        String newRefreshToken = jwtProvider.createRefreshToken(user.getLoginId());

        // 리프레시 토큰 update
        RefreshToken tokenEntity = token.get();
        tokenEntity.updateToken(newRefreshToken);

        return new LoginResponseDto(newAccessToken, newRefreshToken);
    }
    
    @Transactional
    public JoinResponseDto join(JoinRequestDto dto) {
        // 회원가입 로직
        return null;
    }

}

