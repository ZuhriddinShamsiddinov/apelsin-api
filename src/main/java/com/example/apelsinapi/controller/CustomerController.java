package com.example.apelsinapi.controller;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.entity.Customer;
import com.example.apelsinapi.repository.CustomerRepository;
import com.example.apelsinapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {
    final CustomerService customerService;
    final CustomerRepository customerRepository;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Customer> customerList = customerRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(customerList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return ResponseEntity.status(customerOptional.isEmpty() ? 404 : 200).body(customerOptional.orElse(new Customer()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody Customer customer) {
        ApiResponse response = customerService.save(customer);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody Customer customer) {
        ApiResponse response = customerService.edit(id, customer);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) return ResponseEntity.status(404).body("Not Found Category");
        Customer product = customerOptional.get();
        product.setActive(false);
        customerRepository.save(product);
        ApiResponse response = new ApiResponse("Deleted", true);
        return ResponseEntity.ok().body(response);
    }
}
