package com.etiya.northwind.business.requests.carts;

import com.etiya.northwind.business.requests.orderDetails.CreateOrderDetailRequest;
import com.etiya.northwind.business.requests.orders.CreateOrderRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleCartRequest {
    private String customerId;
    private int productId;
    private CreateOrderRequest createOrderRequest;
    private CreateOrderDetailRequest createOrderDetailRequest;
}
