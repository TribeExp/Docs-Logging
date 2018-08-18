package com.basakdm.excartest.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements Serializable {

    public String getUsernameFromToken(String token) {
        log.info("token = " + token + ", getUsernameFromToken()");
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        log.info("token = " + token + ", getExpirationDateFromToken()");
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        log.info("claims = " + claims + ", getClaimFromToken()");
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        log.info("token = " + token + ", getAllClaimsFromToken()");
        return Jwts.parser()
                .setSigningKey(Constants.SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        log.info("token = " + token + ", isTokenExpired()");
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        log.info("authorities = " + authorities + ", generateToken()");
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(Constants.AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        log.info("token = " + token + ", validateToken()");
        final String username = getUsernameFromToken(token);
        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(token));
    }

    UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final UserDetails userDetails) {

        log.info("token = " + token + ", existingAuth = " + existingAuth + ", userDetails = " + userDetails + ", getAuthentication()");
        final JwtParser jwtParser = Jwts.parser().setSigningKey(Constants.SIGNING_KEY);
        log.info("jwtParser = " + jwtParser);
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        log.info("claimsJws = " + claimsJws);
        final Claims claims = claimsJws.getBody();
        log.info("claims = " + claims);
        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(Constants.AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        log.info("authorities = " + authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
