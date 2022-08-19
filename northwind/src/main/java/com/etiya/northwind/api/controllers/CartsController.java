package com.etiya.northwind.api.controllers;

import com.etiya.northwind.business.abstracts.CartService;
import com.etiya.northwind.business.requests.carts.CreateCartRequest;
import com.etiya.northwind.business.requests.carts.DeleteCartRequest;
import com.etiya.northwind.business.requests.carts.SaleCartRequest;
import com.etiya.northwind.business.requests.carts.UpdateCartRequest;
import com.etiya.northwind.business.responses.carts.CartListResponse;
import com.etiya.northwind.business.responses.carts.ReadCartResponse;
import com.etiya.northwind.core.utilities.results.DataResult;
import com.etiya.northwind.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/carts")
public class CartsController {
    private CartService cartService;

    public CartsController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CartListResponse>> getAll(){

        return this.cartService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateCartRequest createCartRequest) {
        return this.cartService.add(createCartRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateCartRequest updateCartRequest) {
        return this.cartService.update(updateCartRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteCartRequest deleteCartRequest) {
        return this.cartService.delete(deleteCartRequest);
    }

    @GetMapping("/getbyid")
    public DataResult<ReadCartResponse> getById(String customerId, int productId)
    {
        return this.cartService.getById(customerId,productId);
    }

    @DeleteMapping("/sales")
    public Result sales(@RequestBody SaleCartRequest saleCartRequest) {
        return this.cartService.sales(saleCartRequest);
    }
}
