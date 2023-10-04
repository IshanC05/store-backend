package com.myapps.store.ecommerce.service;

import com.myapps.store.ecommerce.exception.ResourceNotFoundException;
import com.myapps.store.ecommerce.model.Product;
import com.myapps.store.ecommerce.payload.ProductDto;
import com.myapps.store.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDto createProduct(ProductDto productDto) {
        // ProductDto to Product
        Product newProduct = toEntity(productDto);
        Product savedProduct = productRepository.save(newProduct);

        // Product to ProductDto
        ProductDto savedProductDto = toDto(savedProduct);
        return savedProductDto;
    }

    public List<ProductDto> viewAll() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream().map(this::toDto).collect(Collectors.toList());
    }

    public ProductDto viewProductById(int productId) {
        Product foundProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with " + "Id" + ":" + productId + " not found"));
        ProductDto foundProductDto = this.toDto(foundProduct);
        return foundProductDto;
    }

    public void deleteProductById(int productId) {
        Product fetchProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with " + "Id:" + productId + " not found"));
        ;
        productRepository.delete(fetchProduct);
    }

    public ProductDto updateProductById(int productId, ProductDto newProduct) {
        Product oldProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with Id:" + productId + " not found"));
        oldProduct.setProductName(newProduct.getProductName());
        oldProduct.setProductDesc(newProduct.getProductDesc());
        oldProduct.setProductPrice(newProduct.getProductPrice());
        oldProduct.setProductQuantity(newProduct.getProductQuantity());
        oldProduct.setLive(newProduct.isLive());
        oldProduct.setStock(newProduct.isStock());
        oldProduct.setImageName(newProduct.getImageName());
        Product savedProduct = productRepository.save(oldProduct);
        ProductDto savedProductDto = this.toDto(savedProduct);
        return savedProductDto;
    }

    // ProductDto to Product
    public Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductQuantity(productDto.getProductQuantity());
        product.setProductDesc(productDto.getProductDesc());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setImageName(productDto.getImageName());
        return product;
    }

    // ProductDto to Product
    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setProductQuantity(product.getProductQuantity());
        productDto.setProductDesc(product.getProductDesc());
        productDto.setLive(product.isLive());
        productDto.setStock(product.isStock());
        productDto.setImageName(product.getImageName());
        return productDto;
    }
}
