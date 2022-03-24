package com.example.apelsinapi.controller;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.dto.PaymentDTO;
import com.example.apelsinapi.entity.Payment;
import com.example.apelsinapi.repository.PaymentRepository;
import com.example.apelsinapi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    final PaymentRepository paymentRepository;
    final PaymentService paymentService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Payment> categoryList = paymentRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(categoryList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Payment> categoryOptional = paymentRepository.findById(id);
        return ResponseEntity.status(categoryOptional.isEmpty() ? 404 : 200).body(categoryOptional.orElse(new Payment()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody PaymentDTO paymentDTO) {
        ApiResponse response = paymentService.save(paymentDTO);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isEmpty()) return ResponseEntity.status(404).body("Not Found Payment");
        Payment payment = paymentOptional.get();
        payment.setActive(false);
        paymentRepository.save(payment);
        ApiResponse response = new ApiResponse("Deleted", true);
        return ResponseEntity.ok().body(response);
    }
}
