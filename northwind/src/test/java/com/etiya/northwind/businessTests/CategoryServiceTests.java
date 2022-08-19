package com.etiya.northwind.businessTests;

import com.etiya.northwind.business.concretes.CategoryManager;
import com.etiya.northwind.business.responses.categories.CategoryListResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperManager;
import com.etiya.northwind.core.utilities.results.DataResult;
import com.etiya.northwind.core.utilities.results.SuccessDataResult;
import com.etiya.northwind.dataAccess.CategoryRepository;
import com.etiya.northwind.entities.concretes.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ModelMapperManager modelMapperManager;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    CategoryManager categoryManager;


    @Test
    public void getAll() {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("jefsdjx");
        categories.add(category);

        CategoryListResponse categoryListResponse= new CategoryListResponse();
        categoryListResponse.setCategoryId(category.getCategoryId());
        categoryListResponse.setCategoryName(category.getCategoryName());
        List<CategoryListResponse> responses = new ArrayList<>();
        responses.add(categoryListResponse);


        when(modelMapperManager.forResponse()).thenReturn(modelMapper);
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryManager.getAll()).thenReturn((DataResult<List<CategoryListResponse>>) responses);


       // DataResult<List<CategoryListResponse>> responses1 = categoryManager.getAll();

        Assertions.assertEquals(1, responses.size());
        Assertions.assertEquals(category, responses.get(0));
    }
}
