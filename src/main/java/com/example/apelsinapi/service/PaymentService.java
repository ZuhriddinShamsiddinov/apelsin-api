package com.example.apelsinapi.service;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.dto.PaymentDTO;
import com.example.apelsinapi.entity.Invoice;
import com.example.apelsinapi.entity.Payment;
import com.example.apelsinapi.repository.InvoiceRepository;
import com.example.apelsinapi.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    final PaymentRepository paymentRepository;
    final InvoiceRepository invoiceRepository;

    public ApiResponse save(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());

        Optional<Invoice> invoiceOptional = invoiceRepository.findById(paymentDTO.getInvoiceId());
        if (invoiceOptional.isEmpty()) return new ApiResponse("NOT FOUND INVOICE", false);
        Invoice invoice = invoiceOptional.get();
        payment.setInvoice(invoice);
        paymentRepository.save(payment);
        return new ApiResponse("Saved", true);
    }

}
