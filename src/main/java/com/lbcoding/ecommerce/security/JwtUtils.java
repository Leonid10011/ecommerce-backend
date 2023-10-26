package com.lbcoding.ecommerce.security;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

import java.util.Set;

public class JwtUtils {
    public static String generateToken(String username, Set<String> roles, Long id){
        JwtClaimsBuilder claims = Jwt.claims();
        claims.subject(username);
        claims.groups(roles);
        claims.upn(Long.toString(id));

        return claims.jws().keyId("quarkus").sign();
    }
}
