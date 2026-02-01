package com.yoonsunmi.timetracking.global.security;

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

    @Value("${jwt.access-token-minutes:60}")
    private long accessTokenMinutes;

    @PostConstruct
    protected void init() {
        byte[] decoded = Base64.getDecoder().decode(secretKeyBase64);
        this.key = Keys.hmacShaKeyFor(decoded);
    }

    /** 토큰 생성: subject=loginId, claim(role)=USER/ADMIN */
    public String createToken(String loginId, String role) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + accessTokenMinutes * 60_000L);

        return Jwts.builder()
                .issuer(issuer)
                .subject(loginId)
                .claim("role", role)  // "USER" / "ADMIN"
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
