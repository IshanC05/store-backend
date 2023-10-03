package com.myapps.store.ecommerce.service;

import com.myapps.store.ecommerce.exception.ResourceNotFoundException;
import com.myapps.store.ecommerce.model.Product;
import com.myapps.store.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> viewAll() {
        return productRepository.findAll();
    }

    public Product viewProductById(int productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with " +
                "Id:" + productId + " not found"));
    }

    public void deleteProductById(int productId) {
        Product fetchProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with " +
                "Id:" + productId + " not found"));
        ;
        productRepository.delete(fetchProduct);
    }

    public Product updateProductById(int productId, Product newProduct) {
        Product oldProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(
                "Product with Id:" + productId + " not found"));
        oldProduct.setProductName(newProduct.getProductName());
        oldProduct.setProductDesc(newProduct.getProductDesc());
        oldProduct.setProductPrice(newProduct.getProductPrice());
        oldProduct.setProductQuantity(newProduct.getProductQuantity());
        oldProduct.setLive(newProduct.isLive());
        oldProduct.setStock(newProduct.isStock());
        oldProduct.setImageName(newProduct.getImageName());
        return productRepository.save(oldProduct);
    }
}
