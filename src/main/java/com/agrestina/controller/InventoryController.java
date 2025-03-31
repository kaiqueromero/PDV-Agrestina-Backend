package com.agrestina.controller;

import com.agrestina.domain.user.User;
import com.agrestina.dto.inventory.InventoryDTO;
import com.agrestina.dto.inventory.UpdateInventoryDTO;
import com.agrestina.service.InventoryService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("inventory")
@RequiredArgsConstructor
public class InventoryController {

    @Autowired
    private InventoryService service;

    @GetMapping("/active")
    public ResponseEntity<List<InventoryDTO>> listActives(@AuthenticationPrincipal User user){
        var inventories = service.listActives();
        log.info("Listagem de todos os inventários ativos realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<InventoryDTO>> listInactives(@AuthenticationPrincipal User user){
        var inventories = service.listInactives();
        log.info("Listagem de todos os inventários inativos realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryDTO>> listAll(@AuthenticationPrincipal User user){
        var inventories = service.listAll();
        log.info("Listagem de todos os inventários realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(inventories);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<InventoryDTO> update(@RequestBody @Valid UpdateInventoryDTO dto, @AuthenticationPrincipal User user){
        var inventoryUpdated = service.updateInventory(dto);
        log.info("Inventário ({}) atualizado com sucesso pelo usuário {}", inventoryUpdated, user);
        return ResponseEntity.ok(inventoryUpdated);
    }
}
