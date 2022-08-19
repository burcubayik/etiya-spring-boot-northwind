package com.etiya.northwind.businessTests;

import com.etiya.northwind.business.concretes.CategoryManager;
import com.etiya.northwind.business.concretes.CustomerManager;
import com.etiya.northwind.business.requests.categories.CreateCategoryRequest;
import com.etiya.northwind.business.requests.customers.CreateCustomerRequest;
import com.etiya.northwind.business.responses.categories.CategoryListResponse;
import com.etiya.northwind.business.responses.customers.CustomerListResponse;
import com.etiya.northwind.business.responses.customers.ReadCustomerResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperManager;
import com.etiya.northwind.core.utilities.results.DataResult;
import com.etiya.northwind.core.utilities.results.Result;
import com.etiya.northwind.core.utilities.results.SuccessDataResult;
import com.etiya.northwind.dataAccess.CategoryRepository;
import com.etiya.northwind.dataAccess.CustomerRepository;
import com.etiya.northwind.entities.concretes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {


    ModelMapperManager modelMapperManager;
    CustomerRepository customerRepository;
    CustomerManager customerManager;


    @BeforeEach
    void setup() {

        modelMapperManager = new ModelMapperManager(new ModelMapper());
        customerRepository = mock(CustomerRepository.class);
        customerManager = new CustomerManager(customerRepository, modelMapperManager);
    }

    @Test
    public void save() {



        CreateCustomerRequest createCustomerRequest=new CreateCustomerRequest("AKLAŞ",2,3,"etiya","istanbul");

        Result result = this.customerManager.add(createCustomerRequest);
        boolean expected = true;

        boolean actual = result.isSuccess();


        Assertions.assertEquals(expected,actual);


    }
    @Test
    void getById() {
        Customer customer = new Customer("aaaaa","dsd","klşlli",new City(),new ArrayList<>(),new Country());
       // when(modelMapperManager.forResponse()).thenReturn(modelMapper);
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        ReadCustomerResponse readCustomerResponse = this.modelMapperManager.forResponse().map(customer, ReadCustomerResponse.class);

        DataResult<ReadCustomerResponse> expected = new SuccessDataResult<>(readCustomerResponse);
        expected.getData().getCustomerId();

        DataResult<ReadCustomerResponse> actual = customerManager.getById(customer.getCustomerId());
        actual.getData().getCustomerId();



        Assertions.assertEquals(expected.getData().getCustomerId(), actual.getData().getCustomerId());
    }

    @Test
    public void getAll() {
        List<Customer> customers = new ArrayList<>();

        Customer customer=new Customer();
        customer.setCustomerId("akla");
        customer.setCompanyName("etiya");
        customer.setAddress("İstanbul");
        customers.add(customer);



        when(customerRepository.findAll()).thenReturn(customers);


        List<CustomerListResponse> customerListResponses= customers.stream().map(customer1 -> modelMapperManager.forResponse()
                .map(customer1,CustomerListResponse.class)).collect(Collectors.toList());
        DataResult<List<CustomerListResponse>> expected = new SuccessDataResult<>(customerListResponses);

        DataResult<List<CustomerListResponse>> actual = customerManager.getAll();

        Assertions.assertEquals(actual.getData().get(0).getCompanyName(), expected.getData().get(0).getCompanyName());

    }
}
