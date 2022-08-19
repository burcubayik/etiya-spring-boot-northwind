package com.etiya.northwind.business.responses.employees;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.NIP;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListResponse {

    private int employeeId;
    private int cityId;
    private int countryId;
    private String firstName;
    private Integer reportTo;
    private String lastName;
    private String title;

}
