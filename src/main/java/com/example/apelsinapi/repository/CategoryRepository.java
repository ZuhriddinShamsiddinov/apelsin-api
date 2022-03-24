package com.example.apelsinapi.repository;

import com.example.apelsinapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByActiveTrue();
}
