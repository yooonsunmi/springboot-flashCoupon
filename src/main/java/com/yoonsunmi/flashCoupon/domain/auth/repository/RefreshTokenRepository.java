package com.yoonsunmi.flashCoupon.domain.auth.repository;

import com.yoonsunmi.flashCoupon.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByLoginId(String loginId);
    void deleteByLoginId(String loginId);
}
