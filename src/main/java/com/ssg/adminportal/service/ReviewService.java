package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.Review;
import com.ssg.adminportal.dto.request.ReviewRequestDTO;
import com.ssg.adminportal.dto.response.ReviewResponseDTO;
import com.ssg.adminportal.dto.response.SentimentResponseDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    ReviewResponseDTO findReviews(ReviewRequestDTO reviewRequestDTO);
    Review findReview(Long reviewId);
    void updateReviewActiveFalse(Long reviewId, String note);
    void updateReviewActiveTrue(Long reviewId);
    void updateReviewSentiment();
    List<SentimentResponseDTO> getSentiment(Long restaurantId);
}
