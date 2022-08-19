package com.etiya.northwind.businessTests;

import com.etiya.northwind.business.abstracts.OrderService;
import com.etiya.northwind.business.concretes.CustomerManager;
import com.etiya.northwind.business.concretes.OrderManager;
import com.etiya.northwind.business.requests.customers.CreateCustomerRequest;
import com.etiya.northwind.business.requests.orders.CreateOrderRequest;
import com.etiya.northwind.business.responses.customers.CustomerListResponse;
import com.etiya.northwind.business.responses.customers.ReadCustomerResponse;
import com.etiya.northwind.business.responses.orders.OrderListResponse;
import com.etiya.northwind.business.responses.orders.ReadOrderResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperManager;
import com.etiya.northwind.core.utilities.mapping.ModelMapperService;
import com.etiya.northwind.core.utilities.results.DataResult;
import com.etiya.northwind.core.utilities.results.Result;
import com.etiya.northwind.core.utilities.results.SuccessDataResult;
import com.etiya.northwind.dataAccess.CustomerRepository;
import com.etiya.northwind.dataAccess.OrderRepository;
import com.etiya.northwind.entities.concretes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    ModelMapperService modelMapperService;
    OrderRepository orderRepository;
    OrderService orderService;


    @BeforeEach
    void setup() {

        modelMapperService = new ModelMapperManager(new ModelMapper());
        orderRepository = mock(OrderRepository.class);
        orderService= new OrderManager(orderRepository, modelMapperService);
    }

    @Test
    public void save() {



        CreateOrderRequest createOrderRequest=new CreateOrderRequest(1,"ALFKI",1);

        Result result = this.orderService.add(createOrderRequest);
        boolean expected = true;

        boolean actual = result.isSuccess();


        Assertions.assertEquals(expected,actual);


    }

    @Test
    void getById() {
        Order order = new Order(1,new Customer(),new Employee(),new ArrayList<>());
        // when(modelMapperManager.forResponse()).thenReturn(modelMapper);
        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));

        ReadOrderResponse readOrderResponse = this.modelMapperService.forResponse().map(order, ReadOrderResponse.class);

        DataResult<ReadOrderResponse> expected = new SuccessDataResult<>(readOrderResponse);


        DataResult<ReadOrderResponse> actual = orderService.getById(order.getOrderId());


        Assertions.assertEquals(expected.getData().getOrderId(), actual.getData().getOrderId());
    }

    @Test
    public void getAll() {
        List<Order> orders = new ArrayList<>();

        Order order=new Order();
        order.setOrderId(1);
        order.setCustomer(new Customer());
        order.setEmployee(new Employee());
        order.setOrderDetails(new ArrayList<>());

        orders.add(order);



        when(orderRepository.findAll()).thenReturn(orders);


        List<OrderListResponse> orderListResponses= orders.stream().map(order1 -> modelMapperService.forResponse()
                .map(order1,OrderListResponse.class)).collect(Collectors.toList());
        DataResult<List<OrderListResponse>> expected = new SuccessDataResult<>(orderListResponses);

        DataResult<List<OrderListResponse>> actual = orderService.getAll();

        Assertions.assertEquals(actual.getData().get(0).getOrderId(), expected.getData().get(0).getOrderId());

    }

}
