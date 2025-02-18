package com.agrestina.controller;

import com.agrestina.dto.client.ClientRequestDTO;
import com.agrestina.dto.client.ClientResponseDTO;
import com.agrestina.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController()
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<ClientResponseDTO> register (@RequestBody ClientRequestDTO body){
        var newClient = this.clientService.register(body);
        log.info("Cliente cadastrado com sucesso.");
        return ResponseEntity.ok(newClient);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<ClientResponseDTO>> ListActiveClients(Pageable pagination){
        var client = this.clientService.listActive(pagination);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<ClientResponseDTO>> ListInactiveClients(Pageable pagination){
        var client = this.clientService.listInactive(pagination);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ClientResponseDTO>> ListAllClients(Pageable pagination){
        var client = this.clientService.listAll(pagination);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<Page<ClientResponseDTO>> ListClientsByName(Pageable pagination, @PathVariable String name){
        var client = this.clientService.findByName(pagination, name);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Page<ClientResponseDTO>> ListClientsById(Pageable pagination, @PathVariable String id){
        var client = this.clientService.findById(pagination, id);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("delete/{name}")
    @Transactional
    public ResponseEntity<ClientResponseDTO> delete(@PathVariable String name) {
        var client = this.clientService.delete(name);
        return ResponseEntity.ok(new ClientResponseDTO(client));
    }

    @PatchMapping("activate/{name}")
    @Transactional
    public ResponseEntity<ClientResponseDTO> activate(@PathVariable String name) {
        var client = this.clientService.activate(name);
        return ResponseEntity.ok(new ClientResponseDTO(client));
    }
}
