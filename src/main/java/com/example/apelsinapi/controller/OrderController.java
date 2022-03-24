package com.example.apelsinapi.controller;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.dto.OrderDTO;
import com.example.apelsinapi.entity.Order;
import com.example.apelsinapi.repository.OrderRepository;
import com.example.apelsinapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    final OrderRepository orderRepository;
    final OrderService orderService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Order> orderList = orderRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(orderList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Order> productOptional = orderRepository.findById(id);
        return ResponseEntity.status(productOptional.isEmpty() ? 404 : 200).body(productOptional.orElse(new Order()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody OrderDTO productDTO) {
        ApiResponse response = orderService.save(productDTO);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Order> productOptional = orderRepository.findById(id);
        if (productOptional.isEmpty()) return ResponseEntity.status(404).body("Not Found Category");
        Order product = productOptional.get();
        product.setActive(false);
        orderRepository.save(product);
        ApiResponse response = new ApiResponse("Deleted", true);
        return ResponseEntity.ok().body(response);
    }
}
