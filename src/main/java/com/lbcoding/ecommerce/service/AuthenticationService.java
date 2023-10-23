package com.lbcoding.ecommerce.service;
import com.lbcoding.ecommerce.dto.CredentialDTO;
import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.model.User;
import com.lbcoding.ecommerce.repository.UserRepository;
import com.lbcoding.ecommerce.security.JwtUtils;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@ApplicationScoped
public class AuthenticationService {
    @Inject
    UserRepository userRepository;

    public Response login(CredentialDTO credentialDTO) {
        // Validieren Sie die Anmeldeinformationen und den Benutzer
        if (isValidUser(credentialDTO.getUsername(), credentialDTO.getPassword())) {
            String token = generateJwtToken(credentialDTO.getUsername());
            return Response.status(Response.Status.OK).entity(token).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        }
    }

    private boolean isValidUser(String username, String password) {
        // Implementieren Sie die Logik zur Überprüfung der Anmeldeinformationen
        // Gibt true zurück, wenn der Benutzer gültig ist, andernfalls false
        User existingUser = userRepository.findUserByUsername(username);

        if(existingUser != null){
            if(BcryptUtil.matches(password, existingUser.getPassword())){
                return true;
            }
        }

        // Hier kannst du deine Anmeldeprüfung implementieren
        // Zum Beispiel: Überprüfe, ob der Benutzername und das Passwort in der Datenbank vorhanden und korrekt sind
        // Du kannst auch die BcryptUtil-Methode verwenden, um das Passwort zu überprüfen
        // Return true, wenn die Anmeldung erfolgreich ist, andernfalls false
        System.out.println("Not Correct");
        return false;
    }

    private String generateJwtToken(String username) {
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        roles.add("admin");

        return JwtUtils.generateToken(username, roles);
    }
}
