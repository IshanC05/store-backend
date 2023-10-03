package com.myapps.store.ecommerce.controller;

import com.myapps.store.ecommerce.model.Product;
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
    @PostMapping(path = "/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<Product>(createdProduct, HttpStatus.CREATED);
    }

    // get all products
    @GetMapping(path = "/view")
    public ResponseEntity<List<Product>> viewAllProducts() {
        List<Product> allProducts = productService.viewAll();
        return new ResponseEntity<List<Product>>(allProducts, HttpStatus.OK);
    }

    // get product by id
    @GetMapping(path = "/view/{productId}")
    public ResponseEntity<Product> viewProductById(@PathVariable int productId) {
        Product product = productService.viewProductById(productId);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    // delete product
    @DeleteMapping(path = "delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<String>("Product deleted successfully", HttpStatus.NO_CONTENT);
    }

    // update product
    @PutMapping(path = "/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable int productId, @RequestBody Product newProduct) {
        Product updatedProduct = productService.updateProductById(productId, newProduct);
        return new ResponseEntity<Product>(updatedProduct, HttpStatus.ACCEPTED);
    }
}
