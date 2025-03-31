package com.agrestina.controller;

import com.agrestina.domain.user.User;
import com.agrestina.dto.product.ProductDTO;
import com.agrestina.dto.product.RegisterProductDTO;
import com.agrestina.service.ProductService;
import jakarta.validation.Valid;
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
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<ProductDTO> register(@Valid @RequestBody RegisterProductDTO dto, @AuthenticationPrincipal User user) {
        var product = this.service.register(dto);
        log.info("Produto ({}) registrado com sucesso pelo usuário {}", product.id(), user);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<ProductDTO>> listActiveProducts(Pageable pagination, @AuthenticationPrincipal User user) {
        var product = this.service.listActive(pagination);
        log.info("Listagem de produtos ativos realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<ProductDTO>> listInactiveProducts(Pageable pagination, @AuthenticationPrincipal User user) {
        var product = this.service.listInactive(pagination);
        log.info("Listagem de produtos inativos realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductDTO>> listAllProducts(Pageable pagination, @AuthenticationPrincipal User user) {
        var product = this.service.listAll(pagination);
        log.info("Listagem de todos os produtos realizada com sucesso pelo usuário {}", user);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<Page<ProductDTO>> listProductsByName(Pageable pagination , @PathVariable String name, @AuthenticationPrincipal User user) {
        var product = this.service.findByName(pagination, name);
        log.info("Listagem de produtos por nome ({}) realizada com sucesso pelo usuário {}", name, user);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Page<ProductDTO>> listProductsById(Pageable pagination , @PathVariable String id, @AuthenticationPrincipal User user) {
        var product = this.service.findById(pagination, id);
        log.info("Listagem de produtos por Id ({}) realizada com sucesso pelo usuário {}", id, user);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        var product = this.service.delete(id);
        log.info("Produto ({}) desativado com sucesso pelo usuário {}", product.id(), user);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("activate/{id}")
    @Transactional
    public ResponseEntity<ProductDTO> activate(@PathVariable Long id, @AuthenticationPrincipal User user) {
        var product = this.service.activate(id);
        log.info("Produto ({}) ativado com sucesso pelo usuário {}", product.id(), user);
        return ResponseEntity.ok(product);
    }

}
