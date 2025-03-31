package com.agrestina.controller;

import com.agrestina.domain.user.User;
import com.agrestina.dto.client.ClientRequestDTO;
import com.agrestina.dto.client.ClientResponseDTO;
import com.agrestina.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<ClientResponseDTO> register (@RequestBody ClientRequestDTO body,  @AuthenticationPrincipal User user){
        var newClient = this.clientService.register(body);
        log.info("Cliente cadastrado com sucesso pelo usuário {} \n {}", user, newClient);
        return ResponseEntity.ok(newClient);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<ClientResponseDTO>> ListActiveClients(Pageable pagination, @AuthenticationPrincipal User user){
        var client = this.clientService.listActive(pagination);
        log.info("Listagem de todos os clientes ativos realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<ClientResponseDTO>> ListInactiveClients(Pageable pagination, @AuthenticationPrincipal User user){
        var client = this.clientService.listInactive(pagination);
        log.info("Listagem de todos os clientes desativados realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ClientResponseDTO>> ListAllClients(Pageable pagination, @AuthenticationPrincipal User user){
        var client = this.clientService.listAll(pagination);
        log.info("Listagem de todos os clientes realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<Page<ClientResponseDTO>> ListClientsByName(Pageable pagination, @PathVariable String name, @AuthenticationPrincipal User user){
        var client = this.clientService.findByName(pagination, name);
        log.info("Busca de cliente por nome ({}) realizada com sucesso pelo usuário {}", name, user);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Page<ClientResponseDTO>> ListClientsById(Pageable pagination, @PathVariable String id, @AuthenticationPrincipal User user){
        var client = this.clientService.findById(pagination, id);
        log.info("Busca de cliente por id ({}) realizada com sucesso pelo usuário {}", id, user);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("delete/{name}")
    @Transactional
    public ResponseEntity<ClientResponseDTO> delete(@PathVariable String name, @AuthenticationPrincipal User user) {
        var client = this.clientService.delete(name);
        log.info("Cliente ({}) desativado com sucesso pelo usuário {}", client, user);
        return ResponseEntity.ok(new ClientResponseDTO(client));
    }

    @PatchMapping("activate/{name}")
    @Transactional
    public ResponseEntity<ClientResponseDTO> activate(@PathVariable String name, @AuthenticationPrincipal User user) {
        var client = this.clientService.activate(name);
        log.info("Cliente ({}) ativado com sucesso pelo usuário {}", client, user);
        return ResponseEntity.ok(new ClientResponseDTO(client));
    }
}
