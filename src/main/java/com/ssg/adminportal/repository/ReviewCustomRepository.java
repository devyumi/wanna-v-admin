package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.Review;
import com.ssg.adminportal.dto.request.ReviewRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewCustomRepository {

    List<Review> findAll(ReviewRequestDTO reviewRequestDTO);

    Integer count(ReviewRequestDTO reviewRequestDTO);
}
