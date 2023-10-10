package com.myapps.store.ecommerce.model;


import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    @OneToOne
    private Product product;

    private double totalProductPrice;

    private int productQuantity;

    @ManyToOne
    private Order order;

    public OrderItem(int orderItemId, Product product, double totalProductPrice, int productQuantity, Order order) {
        super();
        this.orderItemId = orderItemId;
        this.product = product;
        this.totalProductPrice = totalProductPrice;
        this.productQuantity = productQuantity;
        this.order = order;
    }

    public OrderItem() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(double totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
