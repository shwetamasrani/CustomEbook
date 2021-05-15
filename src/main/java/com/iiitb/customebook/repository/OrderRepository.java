package com.iiitb.customebook.repository;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.bean.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Query(value = "SELECT o.order_id FROM orders_tbl o WHERE o.user_id = :userId and o.order_status = 'C'", nativeQuery = true)
    public Integer findInCartOrderIdForUser(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM orders_tbl o WHERE o.user_id = :userId and o.order_status = 'C'", nativeQuery = true)
    public Order findCartOrderForUser(@Param("userId") Integer userId);
}
