package com.example.apelsinapi.controller;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.entity.Category;
import com.example.apelsinapi.entity.Order;
import com.example.apelsinapi.entity.Payment;
import com.example.apelsinapi.entity.Product;
import com.example.apelsinapi.repository.CategoryRepository;
import com.example.apelsinapi.repository.OrderRepository;
import com.example.apelsinapi.repository.PaymentRepository;
import com.example.apelsinapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks")
public class TasksController {
    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;
    final OrderRepository orderRepository;
    final PaymentRepository paymentRepository;

    @GetMapping("/category/list")
    public HttpEntity<?> taskOne() {
        List<Category> categoryList = categoryRepository.findAllByActiveTrue();
        ApiResponse response = new ApiResponse("Mana", true, categoryList);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/category")
    public HttpEntity<?> taskTwo(@RequestParam Integer productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Product product = productOptional.get();
        Category category = product.getCategory();
        ApiResponse response = new ApiResponse("Mana", true, category);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/product/list")
    public HttpEntity<?> taskThree() {
        List<Product> productList = productRepository.findAllByActiveTrue();
        ApiResponse response = new ApiResponse("Mana", true, productList);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/product/details")
    public HttpEntity<?> taskFour(@RequestParam Integer productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Product product = productOptional.get();
        ApiResponse response = new ApiResponse("Mana", true, product);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/order/details")
    public HttpEntity<?> taskSix(@RequestParam Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Order order = orderOptional.get();
        ApiResponse response = new ApiResponse("Mana", true, order);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/payment/details")
    public HttpEntity<?> taskEight(@RequestParam Integer paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Payment payment = paymentOptional.get();
        ApiResponse response = new ApiResponse("Mana", true, payment);
        return ResponseEntity.ok().body(response);
    }
}
