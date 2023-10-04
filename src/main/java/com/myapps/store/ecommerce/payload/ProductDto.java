package com.myapps.store.ecommerce.payload;

public class ProductDto {

    private int productId;
    private String productName;
    private double productPrice;
    private String productDesc;
    private boolean stock;
    private int productQuantity;
    private boolean live;
    private String imageName;
    private CategoryDto category;

    public ProductDto() {
        super();
    }

    public ProductDto(int productId, String productName, double productPrice, String productDesc, boolean stock, int productQuantity, boolean live, String imageName, CategoryDto category) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.stock = stock;
        this.productQuantity = productQuantity;
        this.live = live;
        this.imageName = imageName;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategoryDto(CategoryDto category) {
        this.category = category;
    }
}
