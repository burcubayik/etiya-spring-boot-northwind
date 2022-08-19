package com.etiya.northwind.business.responses.employees;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadEmployeeResponse {
    private int employeeId;

    private int cityId;

    private int countryId;
    private Integer reportTo;

    private String firstName;

    private String lastName;

    private String title;
}
