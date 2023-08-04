package com.samarth.blog.services;

import com.samarth.blog.payloads.CategoryDto;
import com.samarth.blog.payloads.CustomResponse;

public interface CategoryService {

    //Create category
    CategoryDto createCategory(CategoryDto categoryDto);

    // Update Category
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    // Delete Category
    void deleteCategory(Integer categoryId);

    //Get all Category
    CustomResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //Get Category By id
    CategoryDto getCategoryById(Integer categoryId);

}
