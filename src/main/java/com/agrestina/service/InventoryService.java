package com.agrestina.service;

import com.agrestina.dto.inventory.InventoryDTO;
import com.agrestina.dto.inventory.UpdateInventoryDTO;
import com.agrestina.repository.InventoryRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    public List<InventoryDTO> list(){
        return repository.findActiveInventories()
                .stream().map(InventoryDTO::new).collect(Collectors.toList());
    }

    public List<InventoryDTO> listAll(){
        return repository.findInactiveInventories()
                .stream().map(InventoryDTO::new).collect(Collectors.toList());
    }

    public InventoryDTO updateInventory(UpdateInventoryDTO dto){
        var inventory = repository.findByProductId(dto.productId());
        if(!inventory.getProduct().isActive())
            throw new ValidationException("Produto excluído!");
        inventory.add(dto.quantity());
        return new InventoryDTO(inventory);
    }
}
