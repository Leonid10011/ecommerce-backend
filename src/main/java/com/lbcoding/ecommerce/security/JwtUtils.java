package com.lbcoding.ecommerce.security;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.security.enterprise.identitystore.openid.JwtClaims;

import java.util.Set;

public class JwtUtils {
    public static String generateToken(String username, Set<String> roles, Long id){
        JwtClaimsBuilder claims = Jwt.claims();
        claims.subject(username);
        claims.groups(roles);
        claims.claim("userId", id);

        return claims.jws().keyId("quarkus").sign();
    }
}
