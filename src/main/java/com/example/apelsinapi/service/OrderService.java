package com.example.apelsinapi.service;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.dto.DetailDTO;
import com.example.apelsinapi.dto.OrderDTO;
import com.example.apelsinapi.entity.*;
import com.example.apelsinapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final InvoiceRepository invoiceRepository;
    final DetailRepository detailRepository;
    final ProductRepository productRepository;

    public ApiResponse save(OrderDTO orderDTO) {
        Order order = new Order();

        Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());
        if (customerOptional.isEmpty()) return new ApiResponse("NOT FOUND CUSTOMER", false);
        Customer customer = customerOptional.get();
        order.setCustomer(customer);
        Order save = orderRepository.save(order);


        List<DetailDTO> detailDTOList = orderDTO.getDetailDTOList();


        for (DetailDTO detailDTO : detailDTOList) {
            Detail detail = new Detail();
            detail.setOrder(save);

            Optional<Product> productOptional = productRepository.findById(detailDTO.getProductId());
            if (productOptional.isEmpty()) return new ApiResponse("NOT FOUND PRODUCT", false);
            Product product = productOptional.get();
            detail.setProduct(product);
            double amount = (product.getPrice() * detailDTO.getQuantity());

            detail.setQuantity(detailDTO.getQuantity());
            detailRepository.save(detail);

            Invoice invoice = new Invoice();
            invoice.setOrder(save);
            invoice.setAmount(amount);
            Date today = new Date();
            Date due = new Date(today.getTime() + (3000 * 60 * 60 * 24));
            invoice.setDue(due);
            invoiceRepository.save(invoice);
        }


        return new ApiResponse("Saved", true);
    }


}
