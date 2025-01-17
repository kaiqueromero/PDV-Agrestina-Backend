package com.agrestina.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public record ClientRequestDTO (@NotBlank String name, @NotBlank String cpf, Optional<String> cnpj, @NotNull String address, @NotNull String telephone, @NotNull String email) {

        public ClientRequestDTO {
                if (name == null || name.isBlank()) {
                        log.info("Nome não pode ser nulo ou em branco");
                        throw new IllegalArgumentException("Nome não pode ser nulo ou em branco");
                }
                if (cpf == null || cpf.isBlank()) {
                        throw new IllegalArgumentException("Documento não pode ser nulo ou em branco");
                }
                if (address == null || address.isBlank()) {
                        throw new IllegalArgumentException("Endereco não pode ser nulo ou em branco");
                }
                if (telephone == null || telephone.isBlank()) {
                        throw new IllegalArgumentException("Telefone não pode ser nulo ou em branco");
                }
                if (email == null || email.isBlank()) {
                        throw new IllegalArgumentException("Email não pode ser nulo ou em branco");
                }
        }
}
