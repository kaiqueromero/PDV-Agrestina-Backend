package com.agrestina.controller;

import com.agrestina.dto.user.LoginRequestDTO;
import com.agrestina.dto.user.RegisterRequestDTO;
import com.agrestina.dto.user.ResponseDTO;
import com.agrestina.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<ResponseDTO> register (@RequestBody RegisterRequestDTO body){
        var newUser = this.service.register(body);
        log.info("Usuário cadastrado com sucesso. Login {}", body.login());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<ResponseDTO> login (@RequestBody LoginRequestDTO body){
        var user = this.service.login(body);
        log.info("Login efetuado com sucesso. Login {}", body.login());
        return ResponseEntity.ok(user);
    }
}