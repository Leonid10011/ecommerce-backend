package com.lbcoding.ecommerce.service;
import com.lbcoding.ecommerce.dto.CredentialDTO;
import com.lbcoding.ecommerce.model.User;
import com.lbcoding.ecommerce.repository.UserRepository;
import com.lbcoding.ecommerce.security.JwtUtils;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

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
            Long UserId = userRepository.findUserByUsername(credentialDTO.getUsername()).get().getId();
            String token = generateJwtToken(credentialDTO.getUsername(),  UserId);
            return Response.status(Response.Status.OK).entity(token).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        }
    }

    private boolean isValidUser(String username, String password) {
        // Implementieren Sie die Logik zur Überprüfung der Anmeldeinformationen
        // Gibt true zurück, wenn der Benutzer gültig ist, andernfalls false
        Optional<User> existingUser = userRepository.findUserByUsername(username);

        if(existingUser.isPresent()){
            if(BcryptUtil.matches(password, existingUser.get().getPassword())){
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

    private String generateJwtToken(String username, Long id) {
        return  generateJwtToken(username, id, false);
    }
    private String generateJwtToken(String username, Long id, boolean admin) {
        Set<String> roles = new HashSet<String>();

        if(admin){
            roles.add("admin");
        }

        roles.add("user");

        return JwtUtils.generateToken(username, roles, id);
    }
}
