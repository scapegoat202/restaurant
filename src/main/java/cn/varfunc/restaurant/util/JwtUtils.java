package cn.varfunc.restaurant.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class JwtUtils {
    private static final Key key = Keys.hmacShaKeyFor("rN3Pin7mzPUaEBmyfhM0GNd07hyGyfdf".getBytes());

    public static String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    private static Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    private static boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        log.debug("token expiration date: {}", expiration);
        return expiration.toInstant().isBefore(Instant.now());
    }

    private static Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 14);
        return calendar.getTime();
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        log.info("validateToken called");
        String username = getUsernameFromToken(token);
        log.debug("username: {}", username);
        log.debug("username in user details: {}", userDetails.getUsername());

        boolean isUsernameEquals = username.equals(userDetails.getUsername());
        boolean isTokenExpired = isTokenExpired(token);
        log.debug("isUsernameEquals: {}", isUsernameEquals);
        log.debug("isTokenExpired: {}", isTokenExpired);

        boolean result = isUsernameEquals && !isTokenExpired;
        log.debug("is token valid: {}", result);
        return result;
    }

    public static String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .signWith(key)
                .setExpiration(generateExpirationDate())
                .setIssuedAt(Date.from(Instant.now()))
                .setSubject(userDetails.getUsername())
                .compact();
    }
}
