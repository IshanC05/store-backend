package com.myapps.store.ecommerce.controller;

import com.myapps.store.ecommerce.payload.ApiResponse;
import com.myapps.store.ecommerce.payload.CategoryDto;
import com.myapps.store.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // create category
    @PostMapping(path = "/create")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // update category
    @PutMapping(path = "/update/{categoryId}")
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto categoryDto, @PathVariable int categoryId) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.ACCEPTED);
    }

    // delete category
    @DeleteMapping(path = "/delete/{categoryId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted with id:" + categoryId + " " + "successfully.", true), HttpStatus.OK);
    }

    // get category by id
    @GetMapping(path = "/view/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int categoryId) {
        CategoryDto categoryById = categoryService.viewCategoryById(categoryId);
        return new ResponseEntity<>(categoryById, HttpStatus.OK);
    }

    // get all categories
    @GetMapping(path = "/viewAll")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> allCategories = categoryService.viewAll();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }
}
