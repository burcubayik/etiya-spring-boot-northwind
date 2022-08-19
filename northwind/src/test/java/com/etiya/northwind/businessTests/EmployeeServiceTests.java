package com.etiya.northwind.businessTests;

import com.etiya.northwind.business.abstracts.EmployeeService;
import com.etiya.northwind.business.concretes.CategoryManager;
import com.etiya.northwind.business.concretes.EmployeeManager;
import com.etiya.northwind.business.requests.categories.CreateCategoryRequest;
import com.etiya.northwind.business.requests.employees.CreateEmployeeRequest;
import com.etiya.northwind.business.responses.customers.CustomerListResponse;
import com.etiya.northwind.business.responses.customers.ReadCustomerResponse;
import com.etiya.northwind.business.responses.employees.EmployeeListResponse;
import com.etiya.northwind.business.responses.employees.ReadEmployeeResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperManager;
import com.etiya.northwind.core.utilities.mapping.ModelMapperService;
import com.etiya.northwind.core.utilities.results.DataResult;
import com.etiya.northwind.core.utilities.results.Result;
import com.etiya.northwind.core.utilities.results.SuccessDataResult;
import com.etiya.northwind.dataAccess.CategoryRepository;
import com.etiya.northwind.dataAccess.EmployeeRepository;
import com.etiya.northwind.entities.concretes.City;
import com.etiya.northwind.entities.concretes.Country;
import com.etiya.northwind.entities.concretes.Customer;
import com.etiya.northwind.entities.concretes.Employee;
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
public class EmployeeServiceTests {
   ModelMapperService modelMapperService;
    EmployeeRepository employeeRepository;
    EmployeeService employeeService;


    @BeforeEach
    void setup() {

        modelMapperService = new ModelMapperManager(new ModelMapper());
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeManager(employeeRepository, modelMapperService);
    }
    @Test
    public void save() {


        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest(1,1,1,2,"Burcu","Bayık","developer");

        Result result = this.employeeService.add(createEmployeeRequest);
        boolean expected = true;

        boolean actual = result.isSuccess();


        Assertions.assertEquals(expected,actual);


    }

    @Test
    void getById() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setCity(new City());
        // when(modelMapperManager.forResponse()).thenReturn(modelMapper);
        when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));

        ReadEmployeeResponse readEmployeeResponse = this.modelMapperService.forResponse().map(employee, ReadEmployeeResponse.class);

        DataResult<ReadEmployeeResponse> expected = new SuccessDataResult<>(readEmployeeResponse);

        DataResult<ReadEmployeeResponse> actual = employeeService.getById(employee.getEmployeeId());


        Assertions.assertEquals(expected.getData().getEmployeeId(), actual.getData().getEmployeeId());
    }

    @Test
    public void getAll() {
        List<Employee> employees = new ArrayList<>();

        Employee employee=new Employee();
        employee.setEmployeeId(1);
        employee.setTitle("etiya");
        employee.setCity(new City());
        employees.add(employee);



        when(employeeRepository.findAll()).thenReturn(employees);


        List<EmployeeListResponse> employeeListResponses= employees.stream().map(employee1 -> modelMapperService.forResponse()
                .map(employee1,EmployeeListResponse.class)).collect(Collectors.toList());
        DataResult<List<EmployeeListResponse>> expected = new SuccessDataResult<>(employeeListResponses);

        DataResult<List<EmployeeListResponse>> actual = employeeService.getAll();

        Assertions.assertEquals(actual.getData().get(0).getEmployeeId(), expected.getData().get(0).getEmployeeId());

    }
}
