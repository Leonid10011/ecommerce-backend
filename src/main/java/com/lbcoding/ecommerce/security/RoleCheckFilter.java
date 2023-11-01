package com.lbcoding.ecommerce.security;

import com.auth0.jwt.interfaces.Claim;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import jakarta.ws.rs.ext.Provider;

import java.util.*;

public class RoleCheckFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        // Fügen Sie hier Ihre Überprüfungslogik hinzu
        // Überprüfen Sie den JWT-Token und die Rollen im Request-Header
        System.out.println("TEST\n" + requestContext.getHeaderString("Authorization"));
        // Zum Beispiel:
        // 1. Holen Sie das JWT-Token aus dem Header
        String token = requestContext.getHeaderString("Authorization");

        System.out.println("Extracted " + extractRolesFromToken(token));
        // 2. Überprüfen Sie das Token und extrahieren Sie die Rolleninformation
        //    Beachten Sie, dass Sie das Token validieren und decodieren müssen
        //    Hier wird das Token nur als Platzhalter verwendet.
        if (isValidToken(token)) {
            List<String> userRoles = extractRolesFromToken(token);
            System.out.println("Userroles " + userRoles);
            assert userRoles != null;
            if(userRoles.contains("User"))
                System.out.println("User");
            // 3. Überprüfen Sie, ob die Rolle "User" im Set der Benutzerrollen vorhanden ist
        } else {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("test").build());
        }
    }

    private boolean isValidToken(String token) {
        // Fügen Sie hier Ihre Token-Validierungslogik hinzu
        // Überprüfen Sie die Signatur und Ablaufzeit des Tokens
        // Rückgabe true, wenn das Token gültig ist, andernfalls false
        return true; // Hier muss Ihre tatsächliche Validierungslogik stehen
    }

    private List<String> extractRolesFromToken(String token) {
        // Fügen Sie hier Ihre Logik hinzu, um die Rolleninformation aus dem Token zu extrahieren
        // Dies hängt von der Struktur Ihres Tokens ab
        // Rückgabe der extrahierten Rollen als Set<String>
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
        // Extrahiere die Rollen aus dem Token und füge sie zum Set hinzu
        // Beispiel: roles.add("User");

    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }
}
