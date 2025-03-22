package com.elm0n0.tienda.security.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.elm0n0.tienda.auth.enums.AuthRoles;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

	@Value("${jwt.expiration.time}")
    private long accessTokenExpirationTime;
	
	@Value("${jwt.refresh.token.expiration.time}")
    private long refreshTokenExpirationTime;

    @Value("${jwt.secret.key}")
    private String secretKey;
    
    private static final String ROLES = "roles";
    private static final String PASSWORD = "password";

    private SecretKey getSignInKey() {
    	 byte[] bytes = Base64.getDecoder()
    	.decode(secretKey.getBytes(StandardCharsets.UTF_8));
    	         return new SecretKeySpec(bytes, "HmacSHA256"); }

    public String generateAccessToken(CustomUserDetails userDetails) {
    	
    	Instant now = Instant.now();
        Instant expirationTimeInstant = now.plusMillis(this.accessTokenExpirationTime);
    	
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim(ROLES, userDetails.getRoles())
                .claim(PASSWORD, userDetails.getPassword())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationTimeInstant))
                .signWith(getSignInKey())
                .compact();
    }
    
    public String generateRefreshToken(CustomUserDetails userDetails) {
        Instant now = Instant.now();
        Instant expirationTimeInstant = now.plusMillis(refreshTokenExpirationTime);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim(ROLES, userDetails.getRoles())
                .claim(PASSWORD, userDetails.getPassword())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationTimeInstant))
                .signWith(getSignInKey())
                .compact();
    }
    
    public <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
        		.verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    public Map<String,LocalDateTime> getTokenLocalDates(String token){
    	Map<String,LocalDateTime> resultado = new HashMap<>();
    	Claims claims = extractAllClaims(token);
        Date date = claims.getIssuedAt();
        LocalDateTime localDateIssuedAt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        resultado.put("IssuedAt", localDateIssuedAt);
        date = claims.getExpiration();
        LocalDateTime localDateExpiration = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        resultado.put("Expiration", localDateExpiration);
    	return resultado;
    }
    
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
    
    public CustomUserDetails extractUserDetails(String token) {
        Claims claims = extractAllClaims(token);
        String username = claims.getSubject();
        @SuppressWarnings("unchecked")
        List<String> rolesList = claims.get("roles", List.class);
        String password = claims.get("password", String.class);
        
        Set<AuthRoles> roles = rolesList.stream()
                .map(role -> AuthRoles.valueOf(role)) 
                .collect(Collectors.toSet());
        
        return new CustomUserDetails(username, password, roles);
    }
}
