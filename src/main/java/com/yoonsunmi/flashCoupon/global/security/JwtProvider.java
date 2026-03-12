package com.yoonsunmi.flashCoupon.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private SecretKey key;

    @Value("${jwt.secret}")
    private String secretKeyBase64;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.access-token-minutes:30}")
    private long accessTokenMinutes;

    @Value("${jwt.refresh-token-days:14}")
    private long refreshTokenDays;

    @PostConstruct
    protected void init() {
        byte[] decoded = Base64.getDecoder().decode(secretKeyBase64);
        this.key = Keys.hmacShaKeyFor(decoded);
    }

    /** 토큰 생성: subject=loginId, claim(role)=USER/ADMIN */
    public String createAccessToken(String loginId, String role) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + (accessTokenMinutes * 60 * 1000L));

        return Jwts.builder()
                .issuer(issuer)
                .subject(loginId)
                .claim("role", role)  // "USER" / "ADMIN"
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    /** 리프레시 토큰 생성: subject=loginId */
    public String createRefreshToken(String loginId) {
        Date now = new Date();
        // 1일 = 24시간 * 60분 * 60초 * 1000밀리초
        Date exp = new Date(now.getTime() + refreshTokenDays * 24 * 60 * 60 * 1000L);

        return Jwts.builder()
                .issuer(issuer)
                .subject(loginId)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    /** 파싱 + 서명검증 + issuer 검증 */
    private Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token);
    }

    public String getLoginId(String token) {
        return parse(token).getPayload().getSubject();
    }

    public String getRole(String token) {
        return parse(token).getPayload().get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
