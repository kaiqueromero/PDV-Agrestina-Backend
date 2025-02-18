package com.agrestina.service;

import com.agrestina.domain.client.Client;
import com.agrestina.dto.client.ClientRequestDTO;
import com.agrestina.dto.client.ClientResponseDTO;
import com.agrestina.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);


    @Autowired
    private ClientRepository clientRepository;

    public ClientResponseDTO register(ClientRequestDTO dto) {
        logger.info("Registering new client: {}", dto.name());
        Optional<Client> client = this.clientRepository.findByName(dto.name());

        if (client.isEmpty()) {
            Client newClient = new Client();
            newClient.setName(dto.name());
            newClient.setCpf(dto.cpf());
            newClient.setCnpj(dto.cnpj().orElse(null));
            newClient.setAddress(dto.address());
            newClient.setTelephone(dto.telephone());
            newClient.setEmail(dto.email());
            newClient.setActive(true);
            clientRepository.save(newClient);
            logger.info("Client registered successfully: {}", newClient.getName());

            return ResponseEntity.ok(new ClientResponseDTO(newClient)).getBody();
        } else {
            logger.warn("Client already registered: {}", dto.name());
            throw new RuntimeException("Client already registered");
        }
    }

    public Client delete(String name){
        var client = clientRepository.findByName(name).get();
        client.disabled();
        return client;
    }

    public Client activate(String name){
        var client = clientRepository.findByName(name).get();
        client.activate();
        return client;
    }

    public Page<ClientResponseDTO> listActive(Pageable pagination) {
        return clientRepository.findActive(pagination).map(ClientResponseDTO::new);
    }

    public Page<ClientResponseDTO> findById(Pageable pagination, String id) {
        return clientRepository.findClientById(pagination, id).map(ClientResponseDTO::new);
    }

    public Page<ClientResponseDTO> findByName(Pageable pagination, String name) {
        return clientRepository.findClientByName(pagination, name).map(ClientResponseDTO::new);
    }

    public Page<ClientResponseDTO> listInactive(Pageable pagination) {
        return clientRepository.findInactive(pagination).map(ClientResponseDTO::new);
    }

    public Page<ClientResponseDTO> listAll(Pageable pagination) {
        return clientRepository.findAllClients(pagination).map(ClientResponseDTO::new);
    }
}
