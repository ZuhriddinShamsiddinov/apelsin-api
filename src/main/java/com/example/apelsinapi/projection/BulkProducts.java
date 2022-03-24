package com.example.apelsinapi.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BulkProducts {
    private Integer productId;
    private String name;
    private double price;
}
