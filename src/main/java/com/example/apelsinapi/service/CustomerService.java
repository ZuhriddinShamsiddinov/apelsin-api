package com.example.apelsinapi.service;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.entity.Customer;
import com.example.apelsinapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    final CustomerRepository customerRepository;

    public ApiResponse save(Customer customer) {
        customerRepository.save(customer);
        return new ApiResponse("Saved", true);
    }

    public ApiResponse edit(Integer id, Customer customerOut) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) return new ApiResponse("NOT FOUND CUSTOMER", false);
        Customer customer = customerOptional.get();
        customer.setName(customerOut.getName());
        customer.setPhone(customerOut.getPhone());
        customer.setAddress(customerOut.getAddress());
        customer.setCountry(customerOut.getCountry());
        customerRepository.save(customer);
        return new ApiResponse("Edited", true);
    }
}
