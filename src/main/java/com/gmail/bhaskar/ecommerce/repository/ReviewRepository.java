package com.gmail.bhaskar.ecommerce.repository;

import com.gmail.bhaskar.ecommerce.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {
}
