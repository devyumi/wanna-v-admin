package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.Review;
import com.ssg.adminportal.dto.request.ReviewRequestDTO;
import com.ssg.adminportal.dto.response.SentimentResponseDTO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewCustomRepository {

    List<Review> findAll(ReviewRequestDTO reviewRequestDTO);

    Integer count(ReviewRequestDTO reviewRequestDTO);

    List<SentimentResponseDTO> countBySentimentAndRestaurant(Long restaurantId);
}
