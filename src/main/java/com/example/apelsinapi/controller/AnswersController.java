package com.example.apelsinapi.controller;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.entity.Customer;
import com.example.apelsinapi.entity.Invoice;
import com.example.apelsinapi.entity.Order;
import com.example.apelsinapi.repository.*;
import com.example.apelsinapi.service.AnswersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AnswersController {

    final CategoryRepository categoryRepository;
    final CustomerRepository customerRepository;
    final DetailRepository detailRepository;
    final InvoiceRepository invoiceRepository;
    final OrderRepository orderRepository;
    final PaymentRepository paymentRepository;
    final ProductRepository productRepository;
    final AnswersService answersService;

    @GetMapping("/expired_invoices")
    public HttpEntity<?> answerOne() {
        List<Invoice> expiredInvoices = invoiceRepository.getExpiredInvoices();
        return ResponseEntity.ok().body(expiredInvoices);
    }

    @GetMapping("/wrong_date_invoices")
    public HttpEntity<?> answerTwo() {
        ApiResponse response = answersService.getAllTwo();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/orders_without_details")
    public HttpEntity<?> answerThree() {
        List<Order> listNoDetail = orderRepository.getNoDetail();
        ApiResponse response = new ApiResponse("Mana", true, listNoDetail);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/customers_without_orders")
    public HttpEntity<?> answerFour() {
        List<Customer> customerList = customerRepository.getCustomersNoOrders();
        ApiResponse response = new ApiResponse("Mana", true, customerList);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/customers_last_orders")
    public HttpEntity<?> answerFive() {
        ApiResponse response = answersService.getLastOrders();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/overpaid_invoices")
    public HttpEntity<?> answerSix() {
        ApiResponse response = answersService.getOverPaidInvoice();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/high_demand_products")
    public HttpEntity<?> answerSeven() {
        ApiResponse response = answersService.getProductsQuantity();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/bulk_products")
    public HttpEntity<?> answerEight() {
        ApiResponse response = answersService.getProductsBulk();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/number_of_products_in_year")
    public HttpEntity<?> answerNine() {
        ApiResponse response = answersService.getYearOrders();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/orders_without_invoices")
    public HttpEntity<?> answerTen() {
        ApiResponse response = answersService.getGeneralReports();
        return ResponseEntity.ok().body(response);
    }

}
