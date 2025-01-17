package com.agrestina.service;

import com.agrestina.domain.inventory.Inventory;
import com.agrestina.domain.product.Product;
import com.agrestina.dto.product.ProductDTO;
import com.agrestina.dto.product.RegisterProductDTO;
import com.agrestina.repository.InventoryRepository;
import com.agrestina.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public ProductDTO register(RegisterProductDTO dto){
        var registered = productRepository.existsByNameIgnoringCase(dto.name());
        if(registered) {
            throw new ValidationException("Produto já cadastrado!");
        }
        var product = new Product(dto);
        productRepository.save(product);

        var inventory = new Inventory(dto.initialStock(), product);
        inventoryRepository.save(inventory);
        return new ProductDTO(product);
    }

    public ProductDTO findById(Long productId) {
        var product = productRepository.findById(productId).get();
        return new ProductDTO(product);
    }

    public ProductDTO delete(Long productId){
        var product = productRepository.findById(productId).get();
        product.disabled();
        return new ProductDTO(product);
    }

    public ProductDTO activate(Long productId){
        var product = productRepository.findById(productId).get();
        product.activate();
        return new ProductDTO(product);
    }

    public Page<ProductDTO> listActive(Pageable pagination){
        return productRepository.findActive(pagination).map(ProductDTO::new);
    }

    public Page<ProductDTO> findById(Pageable pagination, String productId){
        return productRepository.findProductsById(pagination, productId).map(ProductDTO::new);
    }

    public Page<ProductDTO> findByName(Pageable pagination, String name){
        return productRepository.findProductsByName(pagination, name).map(ProductDTO::new);
    }

    public Page<ProductDTO> listInactive(Pageable pagination){
        return productRepository.findInactive(pagination).map(ProductDTO::new);
    }

    public Page<ProductDTO> listAll(Pageable pagination){
        return productRepository.findAllProducts(pagination).map(ProductDTO::new);
    }
}
