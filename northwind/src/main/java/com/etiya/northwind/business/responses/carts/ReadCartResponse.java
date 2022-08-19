package com.etiya.northwind.business.responses.carts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadCartResponse {
    private String customerId;
    private int productId;
    private int quantity;
    private int unitPrice;


}
