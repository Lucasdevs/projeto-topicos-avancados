package com.unifacisa.rede.controller;

import com.unifacisa.rede.dto.ResponseDTO;
import com.unifacisa.rede.dto.UserDTO;
import com.unifacisa.rede.entity.UserEntity;
import com.unifacisa.rede.login.LoggedUserCache;
import com.unifacisa.rede.mapper.UserMapper;
import com.unifacisa.rede.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUserCache loggedUserCache;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO dto) {

        UserEntity user = new UserEntity();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setMessage(dto.getMessage());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setPhoto(null);

        userRepository.save(user);

        loggedUserCache.setLoggedUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.INSTANCE.toDTO(user));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO dto) {

        UserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            var res = ResponseDTO.builder().detail("Usuário ou senha inválidos").build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(res);
        }

        loggedUserCache.setLoggedUser(user);

        var res = ResponseDTO.builder().detail("Login realizado com sucesso!").build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        UserEntity user = loggedUserCache.getLoggedUser();

        if(user == null){
            var res = ResponseDTO.builder().detail("Nenhum usuário logado").build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(res);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.toDTO(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        loggedUserCache.clear();
        return ResponseEntity.noContent().build();
    }

}
