package com.agrestina.dto.client;

import com.agrestina.exeption.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public record ClientRequestDTO (@NotBlank String name, @NotBlank String cpf, Optional<String> cnpj, @NotNull String address, @NotNull String telephone, @NotNull String email) {

        public ClientRequestDTO {
                ValidationUtil.validateNotNullOrBlank(name, "name");
                ValidationUtil.validateNotNullOrBlank(cpf, "cpf");
                ValidationUtil.validateNotNullOrBlank(address, "address");
                ValidationUtil.validateNotNullOrBlank(telephone, "telephone");
                ValidationUtil.validateNotNullOrBlank(email, "email");
        }
}
