package com.agrestina.dto.client;

import com.agrestina.domain.client.Client;
import com.agrestina.domain.product.Product;
import com.agrestina.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

public record ClientResponseDTO(String name, String cpf, Optional<String> cnpj, String address, String telephone, String email, Boolean active) {

        public ClientResponseDTO(Client client) {
            this(client.getName(), client.getCpf(), Optional.ofNullable(client.getCnpj()), client.getAddress(), client.getTelephone(),client.getEmail(), client.isActive());
        }

}
