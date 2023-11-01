package com.lbcoding.ecommerce.security;

import io.smallrye.jwt.build.Jwt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JwtUtils {
    public static String generateToken(String username, Set<String> roles2, Long id){
        Set<String> roles = new HashSet<>(
                Arrays.asList("User", "Admin")
        );

        return Jwt.issuer("my-app")
                .subject(username)
                .groups(roles)
                .upn(Long.toString(id))
                .expiresAt(
                        System.currentTimeMillis() + 3600
                )
                .sign();
    }
}