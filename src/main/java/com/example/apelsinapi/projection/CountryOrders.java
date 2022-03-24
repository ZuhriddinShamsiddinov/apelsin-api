package com.example.apelsinapi.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountryOrders {
    private String name;
    private Integer countOrders;
}
