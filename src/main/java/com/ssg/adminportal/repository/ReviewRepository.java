package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
}
