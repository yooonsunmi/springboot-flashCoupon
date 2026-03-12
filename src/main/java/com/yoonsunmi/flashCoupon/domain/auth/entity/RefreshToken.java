package com.yoonsunmi.flashCoupon.domain.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String token;  // 발급된 Refresh Token 값

    // 생성자
    public RefreshToken(String loginId, String token) {
        this.loginId = loginId;
        this.token = token;
    }

    public RefreshToken updateToken(String newToken) {
        this.token = newToken;
        return this;  // 수정된 자기 자신을 반환하여 Optional.map에서 사용 가능
    }
}
