package com.gmail.bhaskar.ecommerce.repository;

import com.gmail.bhaskar.ecommerce.domain.Order;
import com.gmail.bhaskar.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByUser(User user);
}