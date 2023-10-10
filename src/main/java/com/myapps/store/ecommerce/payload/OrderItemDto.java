package com.myapps.store.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderItemDto {

    private int orderItemId;
    private ProductDto product;
    private double totalProductPrice;

    @JsonIgnore
    private OrderDto order;

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public double getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(double totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

}
