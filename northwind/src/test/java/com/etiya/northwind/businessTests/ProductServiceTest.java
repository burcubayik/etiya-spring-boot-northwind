package com.etiya.northwind.businessTests;

import com.etiya.northwind.business.concretes.ProductManager;
import com.etiya.northwind.business.requests.categories.CreateCategoryRequest;
import com.etiya.northwind.business.requests.products.CreateProductRequest;
import com.etiya.northwind.business.responses.customers.CustomerListResponse;
import com.etiya.northwind.business.responses.customers.ReadCustomerResponse;
import com.etiya.northwind.business.responses.products.ProductListResponse;
import com.etiya.northwind.business.responses.products.ReadProductResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperManager;
import com.etiya.northwind.core.utilities.mapping.ModelMapperService;
import com.etiya.northwind.core.utilities.results.DataResult;
import com.etiya.northwind.core.utilities.results.Result;
import com.etiya.northwind.core.utilities.results.SuccessDataResult;
import com.etiya.northwind.dataAccess.ProductRepository;
import com.etiya.northwind.entities.concretes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class ProductServiceTest {

    ModelMapperService modelMapperService;
    ProductRepository productRepository;
    ProductManager productManager;

    @BeforeEach
    void setup() {

        modelMapperService = new ModelMapperManager(new ModelMapper());
        productRepository = mock(ProductRepository.class);
        productManager = new ProductManager(productRepository, modelMapperService);
    }

    @Test
    public void save() {

       CreateProductRequest createProductRequest=new CreateProductRequest(1,"deneme",50.0,10,1,1);

       Result result = this.productManager.add(createProductRequest);
       boolean expected = true;

       boolean actual = result.isSuccess();


       Assertions.assertEquals(expected,actual);


    }

    @Test
    void getById() {
        Product product=new Product(1,"deneme",50.0,10,new Category(), new Supplier(),new ArrayList<>());
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

        ReadProductResponse readProductResponse = this.modelMapperService.forResponse().map(product, ReadProductResponse.class);

        DataResult<ReadProductResponse> expected = new SuccessDataResult<>(readProductResponse);
        //expected.getData().getProductId();

        DataResult<ReadProductResponse> actual = productManager.getById(product.getProductId());
        //actual.getData().getProductId();



        Assertions.assertEquals(expected.getData().getProductId(), actual.getData().getProductId());
    }

    @Test
    public void getAll() {
        List<Product> products = new ArrayList<>();

        Product product=new Product(1,"deneme",50.0,10,new Category(), new Supplier(),new ArrayList<>());
        products.add(product);
        when(productRepository.findAll()).thenReturn(products);


        List<ProductListResponse> productListResponse= products.stream().map(product1 -> modelMapperService.forResponse()
                .map(product1,ProductListResponse.class)).collect(Collectors.toList());
        DataResult<List<ProductListResponse>> expected = new SuccessDataResult<>(productListResponse);

        DataResult<List<ProductListResponse>> actual = productManager.getAll();

        Assertions.assertEquals(actual.getData().get(0).getProductName(), expected.getData().get(0).getProductName());

    }

}
