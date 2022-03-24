package com.example.apelsinapi.service;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.entity.Category;
import com.example.apelsinapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    final CategoryRepository categoryRepository;


    public ApiResponse save(Category category) {
        categoryRepository.save(category);
        return new ApiResponse("Saved", true);
    }

    public ApiResponse edit(Integer id, Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) return new ApiResponse("404 Category", false);
        Category category1 = categoryOptional.get();
        category1.setName(category.getName());
        categoryRepository.save(category1);
        return new ApiResponse("Edited", true);


    }
}
