package com.lpnu.excursionclient.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Component
public final class JwtMethods {
    @Value("${application.security.access-jwt.secret}")
    private String accessJwtSecret;
    @Value("${application.security.refresh-jwt.secret}")
    private String refreshJwtSecret;
    @Value("${application.security.access-jwt.time-life}")
    private Long accessJwtTimeLife;
    @Value("${application.security.refresh-jwt.time-life}")
    private Long refreshJwtTimeLife;

    public JwtPair getJwtPair(String username) {
        return new JwtPair(getAccessJwt(username), getRefreshJwt(username));
    }

    private Jwt getAccessJwt(String username) {
        return getJwt(username, accessJwtSecret, accessJwtTimeLife);
    }

    private Jwt getRefreshJwt(String username) {
        return getJwt(username, refreshJwtSecret, refreshJwtTimeLife);
    }

    private static Jwt getJwt(String username, String jwtSecret, Long jwtTimeLife) {
        Instant issueDate = Instant.now();
        return new Jwt(
                Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(Date.from(issueDate))
                        .setExpiration(Date.from(issueDate.plus(jwtTimeLife, ChronoUnit.MINUTES)))
                        .signWith(getSignInKey(jwtSecret), SignatureAlgorithm.HS256)
                        .compact()
        );
    }

    public String extractUsernameFromAccessJwt(String jwt) {
        return extractClaim(jwt, accessJwtSecret, Claims::getSubject);
    }

    public String extractUsernameFromRefreshJwt(String jwt) {
        return extractClaim(jwt, refreshJwtSecret, Claims::getSubject);
    }

    public Date extractIssuedAtFromRefreshJwt(String jwt) {
        return extractClaim(jwt, refreshJwtSecret, Claims::getIssuedAt);
    }

    public Date extractExpiredAtFromRefreshJwt(String jwt) {
        return extractClaim(jwt, refreshJwtSecret, Claims::getExpiration);
    }

    public boolean isAccessJwtExpired(String jwt) {
        return isJwtExpired(jwt, accessJwtSecret);
    }

    public boolean isRefreshJwtExpired(String jwt) {
        return isJwtExpired(jwt, refreshJwtSecret);
    }

    private static boolean isJwtExpired(String jwt, String jwtSecret) {
        try {
            return extractExpiration(jwt, jwtSecret).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }

    }

    private static Date extractExpiration(String jwt, String jwtSecret) {
        return extractClaim(jwt, jwtSecret, Claims::getExpiration);
    }

    private static <T> T extractClaim(String jwt, String jwtSecret, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt, jwtSecret);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String jwt, String jwtSecret) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(jwtSecret))
                .build()
                .parseClaimsJws(jwt)
                .getBody();

    }

    private static Key getSignInKey(String jwtSecret) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
