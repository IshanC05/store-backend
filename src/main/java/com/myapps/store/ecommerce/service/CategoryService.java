package com.myapps.store.ecommerce.service;

import com.myapps.store.ecommerce.exception.ResourceNotFoundException;
import com.myapps.store.ecommerce.model.Category;
import com.myapps.store.ecommerce.payload.CategoryDto;
import com.myapps.store.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category newCategory = this.mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(newCategory);
        return this.mapper.map(savedCategory, CategoryDto.class);
    }

    public CategoryDto updateCategory(CategoryDto newCategoryDto, int categoryId) {
        Category oldCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category  with " + "Id" + ":" + categoryId + " not found"));
        oldCategory.setCategoryId(categoryId);
        oldCategory.setTitle(newCategoryDto.getTitle());
        Category savedCategory = categoryRepository.save(oldCategory);
        return this.mapper.map(savedCategory, CategoryDto.class);
    }

    public void deleteCategory(int categoryId) {
        Category categoryToBeDeleted = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category  with " + "Id" + ":" + categoryId + " not found"));
        categoryRepository.delete(categoryToBeDeleted);
    }

    public CategoryDto viewCategoryById(int categoryId) {
        Category foundCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category  with " + "Id" + ":" + categoryId + " not found"));
        return this.mapper.map(foundCategory, CategoryDto.class);
    }

    public List<CategoryDto> viewAll() {
        List<Category> allCategories = categoryRepository.findAll();
        return allCategories.stream().map(category -> this.mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }
}
