package com.example.cartservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "check_out")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_out_id")
    private Integer checkOutId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "price")
    private float price;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_quantity")
    private int productQuantity;
    @Column(name = "product_total")
    private float productTotal;

    public Integer getCheckOutId() {
        return checkOutId;
    }

    public void setCheckOutId(Integer checkOutId) {
        this.checkOutId = checkOutId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public float getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(float productTotal) {
        this.productTotal = productTotal;
    }

    public  float getPrice() {
        return price;
    }

    public void setPrice( float price) {
        this.price = price;
    }



}
