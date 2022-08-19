package com.etiya.northwind.business.responses.categories;

import com.etiya.northwind.entities.concretes.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadCategoryResponse {
    private int categoryId;
    private String categoryName;
    private List<Product> products;
}
