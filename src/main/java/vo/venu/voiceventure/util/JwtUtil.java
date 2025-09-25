package vo.venu.voiceventure.util;

import vo.venu.voiceventure.dto.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vo.venu.voiceventure.helper.RedisHelper;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final RedisHelper redisHelper;

    @Value("${jwt.tokenTTL:600}")
    private Long customerTokenTTL;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateAccessToken(String username, Map<String, Object> claims, Date accessTokenExpiry) throws Exception {
        return createToken(claims, username, accessTokenExpiry);
    }

    private String createToken(Map<String, Object> claims, String subject, Date expriryDate) throws Exception {
        Date now = new Date(System.currentTimeMillis());

        JwtBuilder builder;
        try {
            builder = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(now)
                    .setExpiration(expriryDate)
                    .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes());
        } catch (Exception e) {
            throw new IOException("Token creation Failed" + e.getMessage());
        }
        return builder.compact();
    }

    public String extractUsername(String token) throws Exception {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String token, String username) throws Exception {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public Claims extractAllClaims(String token) throws Exception {
        try {
            return Jwts.parser()
//                    .verifyWith(EncryptionHelper.getJwtPublicKey())
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new Exception("Failed to extract claims" + e.getMessage());
        }
    }

    public boolean isTokenExpired(String token) throws Exception {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public long getTokenExpiryTimeLeft(String token) throws Exception {
        Date expiration = extractAllClaims(token).getExpiration();
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = expiration.getTime();
        long timeLefInMillis = expirationTimeMillis - currentTimeMillis;
        return timeLefInMillis / 1000;
    }

    public void redisTokenStore(String userId, String opaqueToken, String reaJwt) {
        TokenDTO tokenDTO = new TokenDTO(reaJwt, userId);
        redisHelper.saveData(opaqueToken, tokenDTO, customerTokenTTL);
    }

}
