package com.example.apelsinapi.controller;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.dto.ProductDTO;
import com.example.apelsinapi.entity.Product;
import com.example.apelsinapi.repository.ProductRepository;
import com.example.apelsinapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    final ProductRepository productRepository;
    final ProductService productService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Product> productList = productRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return ResponseEntity.status(productOptional.isEmpty() ? 404 : 200).body(productOptional.orElse(new Product()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody ProductDTO productDTO) {
        ApiResponse response = productService.save(productDTO);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        ApiResponse response = productService.edit(id, productDTO);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) return ResponseEntity.status(404).body("Not Found Category");
        Product product = productOptional.get();
        product.setActive(false);
        productRepository.save(product);
        ApiResponse response = new ApiResponse("Deleted", true);
        return ResponseEntity.ok().body(response);
    }
}
