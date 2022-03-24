package com.example.apelsinapi.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceProjection {
    private Integer orderId;
    private Timestamp orderDate;
    private UUID invoiceId;
    private Timestamp issued;
}