package com.unifacisa.rede.config;

import com.unifacisa.rede.entity.UserEntity;
import com.unifacisa.rede.login.LoggedUserCache;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class BasicAuthValidator {

    private final LoggedUserCache loggedUserCache;
    private final PasswordEncoder passwordEncoder;

    public void validateBasicAuth(HttpServletRequest request) {

        String auth = request.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Basic ")) {
            throw new RuntimeException("Credenciais Basic Auth ausentes");
        }

        String base64Credentials = auth.substring("Basic ".length());
        byte[] decoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decoded, StandardCharsets.UTF_8);

        String[] parts = credentials.split(":", 2);
        String username = parts[0];
        String password = parts[1];

        UserEntity loggedUser = loggedUserCache.getLoggedUser();

        if (!loggedUser.getUsername().equals(username)) {
            throw new RuntimeException("Usuário autenticado não corresponde ao logado");
        }

        if (!passwordEncoder.matches(password, loggedUser.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }
    }
}
