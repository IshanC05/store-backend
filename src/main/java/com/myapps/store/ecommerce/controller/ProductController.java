package com.myapps.store.ecommerce.controller;

import com.myapps.store.ecommerce.payload.AppConstants;
import com.myapps.store.ecommerce.payload.ProductDto;
import com.myapps.store.ecommerce.payload.ProductResponse;
import com.myapps.store.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // add a product
    @PostMapping(path = "/create/{categoryId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, @PathVariable int categoryId) {
        ProductDto createdProductDto = productService.createProduct(productDto, categoryId);
        return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
    }

    // get all products
    @GetMapping(path = "/view")
    public ProductResponse viewAllProducts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_STRING, required = false) int pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir) {
        ProductResponse productResponse = productService.viewAll(pageNumber, pageSize, sortBy, sortDir);
        return productResponse;
    }

    // get product by id
    @GetMapping(path = "/view/{productId}")
    public ResponseEntity<ProductDto> viewProductById(@PathVariable int productId) {
        ProductDto product = productService.viewProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // delete product
    @DeleteMapping(path = "delete/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.NO_CONTENT);
    }

    // update product
    @PutMapping(path = "/update/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int productId, @RequestBody ProductDto newProductDto) {
        ProductDto updatedProductDto = productService.updateProductById(productId, newProductDto);
        return new ResponseEntity<>(updatedProductDto, HttpStatus.ACCEPTED);
    }

    // find product by category
    @GetMapping(path = "/category/{categoryId}")
    public ProductResponse getProductsByCategory(@PathVariable int categoryId, @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_STRING, required = false) int pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir) {
        ProductResponse productResponse = productService.findProductByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return productResponse;
    }
}
