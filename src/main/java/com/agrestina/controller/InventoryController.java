package com.agrestina.controller;

import com.agrestina.dto.inventory.InventoryDTO;
import com.agrestina.dto.inventory.UpdateInventoryDTO;
import com.agrestina.service.InventoryService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<InventoryDTO>> list(){
        var inventories = service.list();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<InventoryDTO>> listAll(){
        var inventories = service.listAll();
        return ResponseEntity.ok(inventories);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<InventoryDTO> update(@RequestBody @Valid UpdateInventoryDTO dto){
        var inventoryUpdated = service.updateInventory(dto);
        return ResponseEntity.ok(inventoryUpdated);
    }
}
