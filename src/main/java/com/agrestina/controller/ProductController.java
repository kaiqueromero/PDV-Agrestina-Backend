package com.agrestina.controller;

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
    public ResponseEntity<ProductDTO> register(@Valid @RequestBody RegisterProductDTO dto) {
        var product = this.service.register(dto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<ProductDTO>> listActiveProducts(Pageable pagination) {
        var product = this.service.listActive(pagination);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<ProductDTO>> listInactiveProducts(Pageable pagination) {
        var product = this.service.listInactive(pagination);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductDTO>> listAllProducts(Pageable pagination) {
        var product = this.service.listAll(pagination);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<Page<ProductDTO>> listProductsByName(Pageable pagination , @PathVariable String name) {
        var product = this.service.findByName(pagination, name);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Page<ProductDTO>> listProductsById(Pageable pagination , @PathVariable String id) {
        var product = this.service.findById(pagination, id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id) {
        var product = this.service.delete(id);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("activate/{id}")
    @Transactional
    public ResponseEntity<ProductDTO> activate(@PathVariable Long id) {
        var product = this.service.activate(id);
        return ResponseEntity.ok(product);
    }

}
