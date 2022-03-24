package com.example.apelsinapi.service;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.dto.ProductDTO;
import com.example.apelsinapi.entity.Category;
import com.example.apelsinapi.entity.Product;
import com.example.apelsinapi.repository.CategoryRepository;
import com.example.apelsinapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;


    public ApiResponse save(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setPhoto(productDTO.getPhoto());

        Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
        if (categoryOptional.isEmpty()) return new ApiResponse("Not Found Category", false);
        Category category = categoryOptional.get();
        product.setCategory(category);

        productRepository.save(product);
        return new ApiResponse("Saved", true);
    }

    public ApiResponse edit(Integer id, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) return new ApiResponse("Not Found Product", false);
        Product product = productOptional.get();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setPhoto(productDTO.getPhoto());
        product.setDescription(productDTO.getDescription());

        Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
        if (categoryOptional.isEmpty()) return new ApiResponse("Not found Category", false);
        Category category = categoryOptional.get();
        product.setCategory(category);

        productRepository.save(product);
        return new ApiResponse("Edited", true);
    }
}
