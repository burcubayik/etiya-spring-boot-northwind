package com.etiya.northwind.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CartId.class)
@Table(name="carts")
public class Cart implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @Column(name="customer_id")
    private String customerId;

    @ManyToOne
    @JoinColumn(name="customer_id",insertable = false, updatable = false)
    private Customer customer;

    @Id
    @Column(name="product_id")
    private int productId;

    @ManyToOne
    @JoinColumn(name="product_id",insertable = false, updatable = false)
    private Product product;

    @Column(name="quantity")
    private int quantity;

    @Column(name="unit_price")
    private int unitPrice;



}
