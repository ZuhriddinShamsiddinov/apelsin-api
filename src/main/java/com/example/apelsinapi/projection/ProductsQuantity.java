package com.example.apelsinapi.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductsQuantity {
    private Integer productId;
    private String name;
    private Integer quantity;
}
