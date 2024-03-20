package az.code.lmscodeacademy.security.jwt;

import az.code.lmscodeacademy.entity.authority.Authority;
import az.code.lmscodeacademy.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.security.secret}")
    private String secret;

    @Value("${spring.security.token.expirationTime}")
    private Long expTimeInMinutes;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String issueToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(expTimeInMinutes))))
                .setHeader(Map.of("type", "JWT"))
                .signWith(key, SignatureAlgorithm.HS512)
                .claim("email", user.getEmail())
                .claim("username",user.getUsername())
                .claim("authority", user.getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toList()))
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

