package com.upendra.order_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table(name = "t_order")
@Entity
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderId;
    private String skuCode;
    private Integer quantity;
    private BigDecimal price;

    public Order() {
    }

    public Order(String orderId, String skuCode, Integer quantity, BigDecimal price) {
        this.orderId = orderId;
        this.skuCode = skuCode;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", skuCode='" + skuCode + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
