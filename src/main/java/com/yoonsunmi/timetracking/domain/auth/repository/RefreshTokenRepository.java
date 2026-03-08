package com.yoonsunmi.timetracking.domain.auth.repository;

import com.yoonsunmi.timetracking.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByLoginId(String loginId);
    void deleteByLoginId(String loginId);
}
