package com.example.apelsinapi.repository;

import com.example.apelsinapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findAllByActiveTrue();

    List<Payment> findAllByInvoice_Id(UUID id);
}
