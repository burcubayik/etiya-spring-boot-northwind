package com.etiya.northwind.business.responses.suppliers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierListResponse {

    private int supplierId;
    private int productId;
    private String companyName;
    private String address;
    private int cityId;
    private int countryId;
}
