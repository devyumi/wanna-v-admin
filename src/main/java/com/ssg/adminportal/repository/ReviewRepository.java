package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

    @Query("SELECT r FROM Review r " +
        "WHERE r.sentiment IS NULL")
    List<Review> findAllBySentimentIsNull();
}