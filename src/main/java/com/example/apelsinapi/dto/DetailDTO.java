package com.example.apelsinapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailDTO {
    private Integer productId;
    private int quantity;
}
