package com.gmail.bhaskar.ecommerce.repository;

import com.gmail.bhaskar.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);


    User findByActivationCode(String code);


    User findByEmail(String email);


    User findByPasswordResetCode(String code);
}