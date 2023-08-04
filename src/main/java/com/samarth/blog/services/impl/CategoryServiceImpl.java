package com.samarth.blog.services.impl;

import com.samarth.blog.entity.Category;
import com.samarth.blog.exceptions.ResourceNotFoundException;
import com.samarth.blog.payloads.CategoryDto;
import com.samarth.blog.payloads.CustomResponse;
import com.samarth.blog.repositories.CategoryRepo;
import com.samarth.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addcat = categoryRepo.save(category);
        return modelMapper.map(addcat, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category Id not found", "catetegoryId", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        return modelMapper.map(categoryRepo.save(category), CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category Id not found", "catetegoryId", categoryId));
        categoryRepo.delete(category);
    }

    @Override
    public CustomResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> pagecategory = categoryRepo.findAll(p);

        List<CategoryDto> categoryDtos = pagecategory.stream().map((category) -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        CustomResponse<CategoryDto> customResponse = new CustomResponse();

        customResponse.setContent(categoryDtos);
        customResponse.setPageNumber(pagecategory.getNumber());
        customResponse.setPageSize(pagecategory.getSize());
        customResponse.setTotalElements(pagecategory.getTotalElements());
        customResponse.setTotalPages(pagecategory.getTotalPages());
        customResponse.setLastPage(pagecategory.isLast());

        return customResponse;

    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category Id not found", "catetegoryId", categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }
}
