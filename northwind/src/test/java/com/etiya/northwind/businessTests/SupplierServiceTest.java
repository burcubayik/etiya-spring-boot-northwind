package com.etiya.northwind.businessTests;

import com.etiya.northwind.business.abstracts.SupplierService;
import com.etiya.northwind.business.concretes.CustomerManager;
import com.etiya.northwind.business.concretes.SupplierManager;
import com.etiya.northwind.business.requests.customers.CreateCustomerRequest;
import com.etiya.northwind.business.requests.suppliers.CreateSupplierRequest;
import com.etiya.northwind.business.responses.customers.CustomerListResponse;
import com.etiya.northwind.business.responses.customers.ReadCustomerResponse;
import com.etiya.northwind.business.responses.suppliers.ReadSupplierResponse;
import com.etiya.northwind.business.responses.suppliers.SupplierListResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperManager;
import com.etiya.northwind.core.utilities.mapping.ModelMapperService;
import com.etiya.northwind.core.utilities.results.DataResult;
import com.etiya.northwind.core.utilities.results.Result;
import com.etiya.northwind.core.utilities.results.SuccessDataResult;
import com.etiya.northwind.dataAccess.CustomerRepository;
import com.etiya.northwind.dataAccess.SupplierRepository;
import com.etiya.northwind.entities.concretes.City;
import com.etiya.northwind.entities.concretes.Country;
import com.etiya.northwind.entities.concretes.Customer;
import com.etiya.northwind.entities.concretes.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {

    ModelMapperService modelMapperService;
    SupplierRepository supplierRepository;
    SupplierService supplierService;

    @BeforeEach
    void setup() {
        modelMapperService = new ModelMapperManager(new ModelMapper());
        supplierRepository = mock(SupplierRepository.class);
        supplierService = new SupplierManager(supplierRepository, modelMapperService);
    }

    @Test
    public void save() {

        CreateSupplierRequest createSupplierRequest = new CreateSupplierRequest(2, 4, 3, "etiya", "Ankara");

        Result result = this.supplierService.add(createSupplierRequest);
        boolean expected = true;

        boolean actual = result.isSuccess();

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void getById() {
        Supplier supplier = new Supplier(1,"etiya","İstanbul",new ArrayList<>(),new City(),new Country());
        // when(modelMapperManager.forResponse()).thenReturn(modelMapper);
        when(supplierRepository.findById(supplier.getSupplierId())).thenReturn(Optional.of(supplier));

        ReadSupplierResponse readSupplierResponse = this.modelMapperService.forResponse().map(supplier, ReadSupplierResponse.class);

        DataResult<ReadSupplierResponse> expected = new SuccessDataResult<>(readSupplierResponse);
        //expected.getData().getSupplierId();

        DataResult<ReadSupplierResponse> actual = supplierService.getById(supplier.getSupplierId());
        //actual.getData().getSupplierId();


        Assertions.assertEquals(expected.getData().getSupplierId(), actual.getData().getSupplierId());
    }

    @Test
    public void getAll() {
        List<Supplier> suppliers = new ArrayList<>();

        Supplier supplier=new Supplier();
        supplier.setSupplierId(1);
        supplier.setCity(new City());
        supplier.setCountry(new Country());
        supplier.setAddress("deneme adres");
        supplier.setProducts(new ArrayList<>());
        suppliers.add(supplier);

        when(supplierRepository.findAll()).thenReturn(suppliers);


        List<SupplierListResponse> supplierListResponses= suppliers.stream().map(supplier1 -> modelMapperService.forResponse()
                .map(supplier1,SupplierListResponse.class)).collect(Collectors.toList());
        DataResult<List<SupplierListResponse>> expected = new SuccessDataResult<>(supplierListResponses);

        DataResult<List<SupplierListResponse>> actual = supplierService.getAll();

        Assertions.assertEquals(actual.getData().get(0).getCompanyName(), expected.getData().get(0).getCompanyName());

    }
}
