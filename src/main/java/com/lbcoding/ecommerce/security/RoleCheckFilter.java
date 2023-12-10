package com.lbcoding.ecommerce.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;

import java.util.Base64;
import java.util.List;

public class RoleCheckFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {

        System.out.println("TEST\n" + requestContext.getHeaderString("Authorization"));

        String token = requestContext.getHeaderString("Authorization");

        System.out.println("Extracted " + extractRolesFromToken(token));

        if (isValidToken(token)) {
            List<String> userRoles = extractRolesFromToken(token);
            System.out.println("Userroles " + userRoles);
            assert userRoles != null;
            if(userRoles.contains("User"))
                System.out.println("User");

        } else {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("test").build());
        }
    }

    private boolean isValidToken(String token) {

        return true;
    }

    private List<String> extractRolesFromToken(String token) {

        if(token.startsWith("Bearer ")){
            token = token.substring("Bearer ".length());
        }
        try {
            DecodedJWT decodedJWT = JWT.decode(token);

            Claim rolesClaim = decodedJWT.getClaim("groups");
            List<String> roles = rolesClaim.asList(String.class);
            if (!rolesClaim.isNull()) {
                // Jetzt haben Sie eine Liste der Rollen aus dem Token
                for (String role : roles) {
                    System.out.println("Rolle: " + role);
                }

                return roles;
            } else {
                System.out.println("Der 'roles'-Claim existiert nicht im Token oder ist nicht vom richtigen Typ.");
                return roles;
            }

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }
}
