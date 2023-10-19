package com.myapps.store.ecommerce.service;

import com.myapps.store.ecommerce.exception.ResourceNotFoundException;
import com.myapps.store.ecommerce.model.Category;
import com.myapps.store.ecommerce.model.Product;
import com.myapps.store.ecommerce.payload.CategoryDto;
import com.myapps.store.ecommerce.payload.ProductDto;
import com.myapps.store.ecommerce.payload.ProductResponse;
import com.myapps.store.ecommerce.repository.CategoryRepository;
import com.myapps.store.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductDto createProduct(ProductDto productDto, int categoryId) {
        // fetch category first
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category  with " + "Id" + ":" + categoryId + " not found"));

        // ProductDto to Product
        Product newProduct = toEntity(productDto);
        newProduct.setCategory(category);

        Product savedProduct = productRepository.save(newProduct);

        // Product to ProductDto
        ProductDto savedProductDto = toDto(savedProduct);
        return savedProductDto;
    }

    public ProductResponse viewAll(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = null;

        if (sortDir.trim().toLowerCase().equals("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> page = productRepository.findAll(pageable);

        List<Product> pageProducts = page.getContent();

        List<Product> allLiveProducts = pageProducts.stream().filter(p -> p.isLive()).collect(Collectors.toList());

        List<ProductDto> allLiveProductsDto = allLiveProducts.stream().map(this::toDto).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(allLiveProductsDto);
        productResponse.setPageNumber(page.getNumber());
        productResponse.setPageSize(page.getSize());
        productResponse.setTotalPages(page.getTotalPages());
        productResponse.setLastPage(page.isLast());

        return productResponse;
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

    public ProductResponse findProductByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with Id:" + categoryId + " not found"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = this.productRepository.findByCategory(category, pageable);
        List<Product> product = page.getContent();
        List<ProductDto> productDto = product.stream().map(p -> toDto(p)).collect(Collectors.toList());

        ProductResponse response = new ProductResponse();
        response.setContent(productDto);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
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

        // get CategoryDto values from product
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(product.getCategory().getCategoryId());
        categoryDto.setTitle(product.getCategory().getTitle());

        // set CategoryDto values in productDto
        productDto.setCategoryDto(categoryDto);
        return productDto;
    }
}
