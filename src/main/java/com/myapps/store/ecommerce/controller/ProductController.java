package com.myapps.store.ecommerce.controller;

import com.myapps.store.ecommerce.payload.ProductDto;
import com.myapps.store.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // add a product
    @PostMapping(path = "/create/{categoryId}")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, @PathVariable int categoryId) {
        ProductDto createdProductDto = productService.createProduct(productDto, categoryId);
        return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
    }

    // get all products
    @GetMapping(path = "/view")
    public ResponseEntity<List<ProductDto>> viewAllProducts() {
        List<ProductDto> allProductsDto = productService.viewAll();
        return new ResponseEntity<>(allProductsDto, HttpStatus.OK);
    }

    // get product by id
    @GetMapping(path = "/view/{productId}")
    public ResponseEntity<ProductDto> viewProductById(@PathVariable int productId) {
        ProductDto product = productService.viewProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // delete product
    @DeleteMapping(path = "delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.NO_CONTENT);
    }

    // update product
    @PutMapping(path = "/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int productId, @RequestBody ProductDto newProductDto) {
        ProductDto updatedProductDto = productService.updateProductById(productId, newProductDto);
        return new ResponseEntity<>(updatedProductDto, HttpStatus.ACCEPTED);
    }

    // find product by category
    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable int categoryId) {
        List<ProductDto> allProductsByCategory = productService.findProductByCategory(categoryId);
        return new ResponseEntity<>(allProductsByCategory, HttpStatus.ACCEPTED);
    }
}
