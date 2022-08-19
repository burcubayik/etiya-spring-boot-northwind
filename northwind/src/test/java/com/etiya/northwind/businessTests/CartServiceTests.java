package com.etiya.northwind.businessTests;

import com.etiya.northwind.business.abstracts.CartService;
import com.etiya.northwind.business.abstracts.OrderDetailService;
import com.etiya.northwind.business.abstracts.OrderService;
import com.etiya.northwind.business.concretes.CartManager;
import com.etiya.northwind.business.concretes.OrderDetailManager;
import com.etiya.northwind.business.concretes.OrderManager;
import com.etiya.northwind.business.requests.carts.CreateCartRequest;
import com.etiya.northwind.business.requests.carts.UpdateCartRequest;
import com.etiya.northwind.business.requests.orderDetails.CreateOrderDetailRequest;
import com.etiya.northwind.business.responses.carts.CartListResponse;
import com.etiya.northwind.business.responses.carts.ReadCartResponse;
import com.etiya.northwind.business.responses.orderDetails.OrderDetailListResponse;
import com.etiya.northwind.business.responses.orderDetails.ReadOrderDetailResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperManager;
import com.etiya.northwind.core.utilities.mapping.ModelMapperService;
import com.etiya.northwind.core.utilities.results.DataResult;
import com.etiya.northwind.core.utilities.results.Result;
import com.etiya.northwind.core.utilities.results.SuccessDataResult;
import com.etiya.northwind.dataAccess.CartRepository;
import com.etiya.northwind.dataAccess.OrderDetailRepository;
import com.etiya.northwind.dataAccess.OrderRepository;
import com.etiya.northwind.entities.concretes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {
    ModelMapperService modelMapperService;
    CartRepository cartRepository;
    CartService cartService;
    OrderDetailService orderDetailService;

   OrderService orderService;

   OrderDetailRepository orderDetailRepository;

   OrderRepository orderRepository;



    @BeforeEach
    void setup() {

        modelMapperService = new ModelMapperManager(new ModelMapper());
        cartRepository = mock(CartRepository.class);
        orderRepository=mock(OrderRepository.class);
        orderDetailRepository=mock(OrderDetailRepository.class);
        orderDetailService=new OrderDetailManager(orderDetailRepository,modelMapperService);
        orderService=new OrderManager(orderRepository,modelMapperService);
        cartService= new CartManager(modelMapperService,cartRepository,orderDetailService,orderService);
    }

    @Test
    public void save() {

        CreateCartRequest createCartRequest=new CreateCartRequest("aksdg",2,5,60);

        Result result = this.cartService.add(createCartRequest);
        boolean expected = true;

        boolean actual = result.isSuccess();


        Assertions.assertEquals(expected,actual);


    }
    @Test
    public void update() {

        UpdateCartRequest updateCartRequest=new UpdateCartRequest("aksdg",2,5,60);

        Result result = this.cartService.update(updateCartRequest);
        boolean expected = true;

        boolean actual = result.isSuccess();


        Assertions.assertEquals(expected,actual);


    }

    @Test
    void getById() {
        Cart cart = new Cart("asdfg",new Customer(),1,new Product(),50,20);
        // when(modelMapperManager.forResponse()).thenReturn(modelMapper)
        when(cartRepository.getByCustomer_CustomerIdAndProduct_ProductId(cart.getCustomerId(), cart.getProductId())).thenReturn(cart);

       ReadCartResponse readCartResponse= this.modelMapperService.forResponse().map(cart, ReadCartResponse.class);

        DataResult<ReadCartResponse> expected = new SuccessDataResult<>(readCartResponse);

        DataResult<ReadCartResponse> actual = cartService.getById(cart.getCustomerId(),cart.getProductId());

        Assertions.assertEquals(expected.getData().getCustomerId(), actual.getData().getCustomerId());
        Assertions.assertEquals(expected.getData().getProductId(), actual.getData().getProductId());
    }


    @Test
    public void getAll() {
        List<Cart> carts = new ArrayList<>();

        Cart cart=new Cart("ANATR",new Customer(),1,new Product(),10,50);
        carts.add(cart);

        when(cartRepository.findAll()).thenReturn(carts);


        List<CartListResponse> cartListResponses= carts.stream().map(carts1 -> modelMapperService.forResponse()
                .map(carts1,CartListResponse.class)).collect(Collectors.toList());
        DataResult<List<CartListResponse>> expected = new SuccessDataResult<>(cartListResponses);

        DataResult<List<CartListResponse>> actual = cartService.getAll();

        Assertions.assertEquals(actual.getData().get(0).getCustomerId(), expected.getData().get(0).getCustomerId());
        Assertions.assertEquals(expected.getData().get(0).getProductId(), actual.getData().get(0).getProductId());

    }


}
