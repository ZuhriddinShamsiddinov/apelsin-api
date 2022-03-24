package com.example.apelsinapi.service;

import com.example.apelsinapi.dto.ApiResponse;
import com.example.apelsinapi.entity.*;
import com.example.apelsinapi.projection.*;
import com.example.apelsinapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswersService {
    final OrderRepository orderRepository;
    final InvoiceRepository invoiceRepository;
    final PaymentRepository paymentRepository;
    final ProductRepository productRepository;
    final DetailRepository detailRepository;
    final CustomerRepository customerRepository;

    public ApiResponse getAllTwo() {
        List<Invoice> invoiceList = invoiceRepository.getWrongInvoices();
        List<InvoiceProjection> collect = invoiceList.stream().map(this::invoiceToInvoice).collect(Collectors.toList());
        return new ApiResponse("Mana", true, collect);
    }

    public InvoiceProjection invoiceToInvoice(Invoice invoice) {
        return new InvoiceProjection(
                invoice.getOrder().getId(),
                invoice.getOrder().getDate(),
                invoice.getId(),
                invoice.getIssued()
        );
    }

    public ApiResponse getLastOrders() {
        List<Order> lastCustomerOrders = orderRepository.getLastCustomerOrders();
        List<Object> collect = lastCustomerOrders.stream().map(this::lastOrders).collect(Collectors.toList());
        return new ApiResponse("Mana", true, collect);
    }

    public LastOrders lastOrders(Order order) {
        return new LastOrders(
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getDate()
        );
    }

    public ApiResponse getOverPaidInvoice() {
        List<Invoice> overPaidInvoices = invoiceRepository.getOverPaidInvoices();
        List<OverPaidPayments> collect = overPaidInvoices.stream().map(this::overPaidPayments).collect(Collectors.toList());
        return new ApiResponse("Mana", true, collect);
    }

    public OverPaidPayments overPaidPayments(Invoice invoice) {
        OverPaidPayments overPaidPayments = new OverPaidPayments();
        List<Payment> allByInvoice_id = paymentRepository.findAllByInvoice_Id(invoice.getId());
        int sumAmount = 0;
        for (Payment payment : allByInvoice_id) {
            if (payment.getAmount() == 0) return null;
            if (payment.getAmount() != 0 && invoice.getId() == payment.getInvoice().getId()) {
                sumAmount += payment.getAmount();
            }
        }
        overPaidPayments.setInvoiceId(invoice.getId());
        overPaidPayments.setOverpaidMoney(sumAmount - invoice.getAmount());
        overPaidPayments.setInvoiceAmount(invoice.getAmount());
        overPaidPayments.setSumPaymentsAmount(sumAmount);
        return overPaidPayments;
    }

    public ApiResponse getProductsQuantity() {
        List<Product> productsQuantity = productRepository.getProductsQuantity();
        List<Object> collect = productsQuantity.stream().map(this::productsQuantity).collect(Collectors.toList());
        return new ApiResponse("Mana", true, collect);
    }

    public ProductsQuantity productsQuantity(Product product) {
        List<Detail> detailList = detailRepository.findAllByProduct_Id(product.getId());
        ProductsQuantity productsQuantity = new ProductsQuantity();
        for (Detail detail : detailList) {
            productsQuantity.setName(product.getName());
            productsQuantity.setProductId(product.getId());
            productsQuantity.setQuantity(detail.getQuantity());
        }
        return productsQuantity;
    }


    public ApiResponse getProductsBulk() {
        List<Product> productList = productRepository.getProductsBulk();
        List<Object> collect = productList.stream().map(this::bulkProducts).collect(Collectors.toList());
        return new ApiResponse("Mana", true, collect);
    }

    public BulkProducts bulkProducts(Product product) {
        return new BulkProducts(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }


    public ApiResponse getYearOrders() {
        List<Customer> customerList = customerRepository.getCustomerName();
        List<Object> collect = customerList.stream().map(this::countryOrders).collect(Collectors.toList());
        return new ApiResponse("Mana", true, collect);
    }

    public CountryOrders countryOrders(Customer customer) {
        int count = 0;
        List<Order> orderList = orderRepository.findAllByCustomer_Id(customer.getId());
        for (Order order : orderList) {
            if (order.isActive() && Objects.equals(order.getCustomer().getId(), customer.getId())) {
                count++;
            }
        }
        return new CountryOrders(customer.getCountry(), count);
    }

    public ApiResponse getGeneralReports() {
        List<Detail> generalOrders = detailRepository.getGeneralOrders();
        List<Object> collect = generalOrders.stream().map(this::generalReport).collect(Collectors.toList());
        return new ApiResponse("Mana", true, collect);
    }

    public GeneralReport generalReport(Detail detail) {
        return new GeneralReport(
                detail.getOrder().getId(),
                detail.getOrder().getDate(),
                detail.getQuantity() * detail.getProduct().getPrice()
        );
    }
}
