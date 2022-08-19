package com.etiya.northwind.dataAccess;

import com.etiya.northwind.entities.concretes.Cart;
import com.etiya.northwind.entities.concretes.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CartRepository extends JpaRepository<Cart, CartId> {

    @Transactional
    @Modifying
    @Query("Delete from Cart Where customer_id=:customerId and product_id=:productId")
    void deleteCartWithCustomerAndProductId(@Param("customerId")String customerId,@Param("productId")int productId);

    Cart getByCustomer_CustomerIdAndProduct_ProductId( String customerId,int productId);
}
