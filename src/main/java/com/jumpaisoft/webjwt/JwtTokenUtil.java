package com.jumpaisoft.webjwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

        public String getJWTToken(String username) {
                final String secretKey = "mySecretKey";// that is equal key secret that we JwtTokenFilter
                // private final String jwtIssuer = "example.io";

                List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                                .commaSeparatedStringToAuthorityList("ROLE_USER");

                String token = Jwts
                                .builder()
                                .setId("softtekJWT")
                                .setSubject(username)
                                .claim("authorities",
                                                grantedAuthorities.stream()
                                                                .map(GrantedAuthority::getAuthority)
                                                                .collect(Collectors.toList()))
                                .setIssuedAt(new Date(System.currentTimeMillis()))
                                .setExpiration(new Date(System.currentTimeMillis() + 600000)) // define time of the
                                                                                              // token spired in
                                                                                              // milisecond 60000 = 1min
                                .signWith(SignatureAlgorithm.HS512,
                                                secretKey.getBytes())
                                .compact();

                return "Crow " + token;
        }
}
