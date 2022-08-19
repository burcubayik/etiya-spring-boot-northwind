package com.etiya.northwind.businessTests;

import com.etiya.northwind.business.abstracts.OrderDetailService;
import com.etiya.northwind.business.abstracts.OrderService;
import com.etiya.northwind.business.abstracts.ProductService;
import com.etiya.northwind.business.concretes.OrderDetailManager;
import com.etiya.northwind.business.concretes.OrderManager;
import com.etiya.northwind.business.concretes.ProductManager;
import com.etiya.northwind.business.requests.orderDetails.CreateOrderDetailRequest;
import com.etiya.northwind.business.requests.orders.CreateOrderRequest;
import com.etiya.northwind.business.responses.customers.CustomerListResponse;
import com.etiya.northwind.business.responses.customers.ReadCustomerResponse;
import com.etiya.northwind.business.responses.orderDetails.OrderDetailListResponse;
import com.etiya.northwind.business.responses.orderDetails.ReadOrderDetailResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperManager;
import com.etiya.northwind.core.utilities.mapping.ModelMapperService;
import com.etiya.northwind.core.utilities.results.DataResult;
import com.etiya.northwind.core.utilities.results.Result;
import com.etiya.northwind.core.utilities.results.SuccessDataResult;
import com.etiya.northwind.dataAccess.OrderDetailRepository;
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

public class OrderDetailServiceTest {
    ModelMapperService modelMapperService;
    OrderDetailRepository orderDetailRepository;
    OrderDetailService orderDetailService;



    @BeforeEach
    void setup() {

        modelMapperService = new ModelMapperManager(new ModelMapper());
        orderDetailRepository = mock(OrderDetailRepository.class);
        orderDetailService= new OrderDetailManager(orderDetailRepository, modelMapperService);
    }

    @Test
    public void save() {

        CreateOrderDetailRequest createOrderDetailRequest=new CreateOrderDetailRequest(10248, 1,50,0.20,500);

        Result result = this.orderDetailService.add(createOrderDetailRequest);
        boolean expected = true;

        boolean actual = result.isSuccess();


        Assertions.assertEquals(expected,actual);


    }

    @Test
    void getById() {
        OrderDetail orderDetail = new OrderDetail(10248,new Order(),1,new Product(),50,0.20,500);
        // when(modelMapperManager.forResponse()).thenReturn(modelMapper);

        when(orderDetailRepository.getByOrder_OrderIdAndProduct_ProductId(orderDetail.getOrderId(), orderDetail.getProductId())).thenReturn(orderDetail);

        ReadOrderDetailResponse readOrderDetailResponse= this.modelMapperService.forResponse().map(orderDetail, ReadOrderDetailResponse.class);

        DataResult<ReadOrderDetailResponse> expected = new SuccessDataResult<>(readOrderDetailResponse);

        DataResult<ReadOrderDetailResponse> actual = orderDetailService.getById(orderDetail.getOrderId(),orderDetail.getProductId());

        Assertions.assertEquals(expected.getData().getOrderId(), actual.getData().getOrderId());
        Assertions.assertEquals(expected.getData().getProductId(), actual.getData().getProductId());
    }


    @Test
   public void getAll() {
        List<OrderDetail> orderDetails = new ArrayList<>();

        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setOrder(new Order());
        orderDetail.setOrderId(10248);
        orderDetail.setProduct(new Product());
        orderDetail.setProductId(1);
        orderDetail.setQuantity(20);
        orderDetail.setUnitPrice(200);
        orderDetail.setDiscount(2.20);
        orderDetails.add(orderDetail);

        when(orderDetailRepository.findAll()).thenReturn(orderDetails);


        List<OrderDetailListResponse> orderDetailListResponses= orderDetails.stream().map(orderDetail1 -> modelMapperService.forResponse()
                .map(orderDetail1,OrderDetailListResponse.class)).collect(Collectors.toList());
        DataResult<List<OrderDetailListResponse>> expected = new SuccessDataResult<>(orderDetailListResponses);

        DataResult<List<OrderDetailListResponse>> actual = orderDetailService.getAll();

        Assertions.assertEquals(actual.getData().get(0).getOrderId(), expected.getData().get(0).getOrderId());
        Assertions.assertEquals(expected.getData().get(0).getProductId(), actual.getData().get(0).getProductId());

    }



}
