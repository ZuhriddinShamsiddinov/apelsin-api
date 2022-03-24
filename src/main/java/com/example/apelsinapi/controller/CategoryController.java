package com.example.apelsinapi.controller;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.entity.Category;
import com.example.apelsinapi.repository.CategoryRepository;
import com.example.apelsinapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    final CategoryRepository categoryRepository;
    final CategoryService categoryService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Category> categoryList = categoryRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(categoryList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return ResponseEntity.status(categoryOptional.isEmpty() ? 404 : 200).body(categoryOptional.orElse(new Category()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody Category category) {
        ApiResponse response = categoryService.save(category);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody Category category) {
        ApiResponse response = categoryService.edit(id, category);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) return ResponseEntity.status(404).body("Not Found Category");
        Category category = categoryOptional.get();
        category.setActive(false);
        categoryRepository.save(category);
        ApiResponse response = new ApiResponse("Deleted", true);
        return ResponseEntity.ok().body(response);
    }
}
