package com.practice_application.practice_application.auth;

import com.practice_application.practice_application.dao.UserMasterDao;
import com.practice_application.practice_application.entity.UserMaster;
import com.practice_application.practice_application.response.JWTResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {


    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 90 * 24 * 60 * 60;

    public static final long JWT_ADMIN_TOKEN_VALIDITY = 10 * 60 ;

    @Value("${jwt.audience}")
    private String jwtAudience;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.type}")
    private String jwtType;

    @Autowired
    UserMasterDao userDao;

    @Autowired
    MongoTemplate mongoTemplate;


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }



    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }




    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public JWTResponse generateToken(UserMaster userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return JWTResponse.builder()
                .jwtToken(doGenerateToken(claims, userDetails))
                .build();
    }

    private String doGenerateToken(Map<String, Object> claims, UserMaster subject) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .setHeaderParam("type", jwtType)
                .setIssuer(jwtIssuer)
                .setAudience(jwtAudience)
                .setSubject(subject.getEmail())
                .setId(subject.getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .compact();
    }

  /*  public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) && !isTokenBlackList(token) || ignoreTokenExpiration(token) && !isTokenBlackList(token));
    }


    public Boolean validateToken(String token, UserMaster userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getEmail()) && !isTokenExpired(token) && !isTokenBlackList(token));
    }*/

    /*
     *
     * // SUPER ADMIN JWT TOKEN GENERATION MODULE
     *
     *
     */







}
