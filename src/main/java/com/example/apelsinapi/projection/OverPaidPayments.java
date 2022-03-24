package com.example.apelsinapi.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OverPaidPayments {
    private UUID invoiceId;
    private double overpaidMoney;
    private double invoiceAmount;
    private double sumPaymentsAmount;
}
