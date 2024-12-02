package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.common.DateTimeUtil;
import com.hikadobushido.ecommerce_java.config.JwtSecretConfig;
import com.hikadobushido.ecommerce_java.model.UserInfo;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService{

    private final JwtSecretConfig jwtSecretConfig;
    private final SecretKey signKey;

    @Override
    public String generateToken(UserInfo userInfo) {
        LocalDateTime expirationTime = LocalDateTime.now().plus(jwtSecretConfig.getJwtExpirationTime());
        Date expirationDate = DateTimeUtil.convertLocalDateTimeToDate(expirationTime);

        return Jwts.builder()
                .content(userInfo.getUsername())
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(signKey)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .decryptWith(signKey)
                    .build();
            parser.parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .decryptWith(signKey)
                .build();
        return parser.parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
