package com.gmail.bhaskar.ecommerce.service;

import com.gmail.bhaskar.ecommerce.domain.Order;
import com.gmail.bhaskar.ecommerce.domain.User;

import java.util.List;


public interface OrderService {

    List<Order> findAll();


    Order save(Order order);


    List<Order> findOrderByUser(User user);


    Order postOrder(Order validOrder, User userSession);
}
