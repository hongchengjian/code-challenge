package com.hrs.security.util;

import com.hrs.security.domain.CustomUserDetail;
import com.hrs.security.domain.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtUtil {
    public static final String ROLE_REFRESH_TOKEN = "ROLE_REFRESH_TOKEN";

    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_AUTHORITIES = "authorities";

    private static final Map<Long, Long> USER_TOKEN_DEADLINE = new ConcurrentHashMap<>();

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long access_token_expiration;

    @Value("${jwt.expiration}")
    private Long refresh_token_expiration;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public CustomUserDetail getUserFromToken(String token) {
        CustomUserDetail userDetail;
        try {
            final Claims claims = getClaimsFromToken(token);
            String username = claims.getSubject();
            String roleName = claims.get(CLAIM_KEY_AUTHORITIES).toString();
            userDetail = new CustomUserDetail(username, new ArrayList<SimpleGrantedAuthority>(){{add(new SimpleGrantedAuthority(roleName));}});
            userDetail.setUserId(Long.parseLong(String.valueOf(claims.get(CLAIM_KEY_USER_ID))));
        } catch (Exception e) {
            userDetail = null;
        }
        return userDetail;
    }

    public long getUserIdFromToken(String token) {
        long userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = Long.parseLong(String.valueOf(claims.get(CLAIM_KEY_USER_ID)));
        } catch (Exception e) {
            userId = 0;
        }
        return userId;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public String generateAccessToken(CustomUserDetail userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        claims.put(CLAIM_KEY_AUTHORITIES, authoritiesToArray(userDetail.getAuthorities()).get(0));
        return generateAccessToken(userDetail.getUsername(), claims);
    }

    public JwtTokenDto generateAccessTokenDto(CustomUserDetail userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        claims.put(CLAIM_KEY_AUTHORITIES, authoritiesToArray(userDetail.getAuthorities()).get(0));
        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setAccess_token(generateAccessToken(userDetail.getUsername(), claims));
        Claims jwtClaims = getClaimsFromToken(jwtTokenDto.getAccess_token());
        jwtTokenDto.setJti(jwtClaims.getId());
        jwtTokenDto.setExpires_in(jwtClaims.getExpiration().getTime());
        jwtTokenDto.setRefresh_token(generateRefreshToken(userDetail));
        return jwtTokenDto;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public void putUserTokenDeadTime(Long userId, Long time) {
        USER_TOKEN_DEADLINE.put(userId, time);
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateAccessToken(claims.getSubject(), claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        CustomUserDetail userDetail = (CustomUserDetail) userDetails;
        final long userId = getUserIdFromToken(token);
        final String username = getUsernameFromToken(token);
        return (userId == userDetail.getUserId()
                && username.equals(userDetail.getUsername())
                && !isTokenExpired(token)
                && !validateTokenRefreshedOrLoginOut(token, userDetail)
        );
    }

    /**
     * 验证token是否已经被刷新或者退出
     * @param token
     * @param userDetail
     * @return
     */
    private boolean validateTokenRefreshedOrLoginOut(String token, CustomUserDetail userDetail) {
        Long userId = getUserIdFromToken(token);
        Long deadTime = USER_TOKEN_DEADLINE.get(userId);
        if (deadTime == null) {
            return false;
        }
        return getCreatedDateFromToken(token).before(new Date(deadTime));
    }

    public String generateRefreshToken(CustomUserDetail userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        // 只授于更新 token 的权限
        claims.put(CLAIM_KEY_AUTHORITIES, JwtUtil.ROLE_REFRESH_TOKEN);
        return generateRefreshToken(userDetail.getUsername(), claims);
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Map<String, Object> generateClaims(CustomUserDetail userDetail) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USER_ID, userDetail.getUserId());
        return claims;
    }

    private String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, access_token_expiration);
    }

    private List authoritiesToArray(Collection<? extends GrantedAuthority> authorities) {
        List<String> list = new ArrayList<>();
        for (GrantedAuthority ga : authorities) {
            list.add(ga.getAuthority());
        }
        return list;
    }


    private String generateRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, refresh_token_expiration);
    }



    private String generateToken(String subject, Map<String, Object> claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(expiration))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

}





