package com.agrestina.dto.user;

import com.agrestina.domain.user.UserRole;
import com.agrestina.exeption.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record RegisterRequestDTO(@NotBlank String name, @NotBlank String login, @NotBlank String password, @NotBlank UserRole userRole) {

    public RegisterRequestDTO {
        ValidationUtil.validateNotNullOrBlank(name(), "Nome");
        ValidationUtil.validateNotNullOrBlank(login(), "Login");
        ValidationUtil.validateNotNullOrBlank(password(), "Senha");
        ValidationUtil.validateNotNull(userRole(), "Tipo de usuário");
    }
}