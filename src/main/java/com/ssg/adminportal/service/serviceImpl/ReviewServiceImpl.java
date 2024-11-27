package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.common.ErrorCode;
import com.ssg.adminportal.config.ClovaConfig;
import com.ssg.adminportal.domain.Review;
import com.ssg.adminportal.dto.request.ReviewRequestDTO;
import com.ssg.adminportal.dto.response.ReviewResponseDTO;
import com.ssg.adminportal.exception.CustomException;
import com.ssg.adminportal.repository.ReviewRepository;
import com.ssg.adminportal.service.ReviewService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ClovaConfig clovaConfig;
    private final RestTemplate restTemplate;

    /**
     * 리뷰 전체 조회 (페이징 및 필터링 적용)
     *
     * @param reviewRequestDTO
     * @return
     */
    @Transactional(readOnly = true)
    public ReviewResponseDTO findReviews(ReviewRequestDTO reviewRequestDTO) {
        return ReviewResponseDTO.builder()
            .reviewRequestDto(reviewRequestDTO)
            .reviews(reviewRepository.findAll(reviewRequestDTO))
            .total(reviewRepository.count(reviewRequestDTO))
            .build();
    }

    /**
     * 리뷰 상세 조회
     *
     * @param reviewId
     * @return
     */
    @Transactional(readOnly = true)
    public Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 id 값입니다."));
    }

    /**
     * 리뷰 숨기기 (== 상태 false 업데이트)
     *
     * @param reviewId
     */
    @Transactional
    public void updateReviewActiveFalse(Long reviewId, String note) {
        Review review = findReview(reviewId);
        reviewRepository.save(
            Review.builder()
                .id(review.getId())
                .restaurant(review.getRestaurant())
                .user(review.getUser())
                .rating(review.getRating())
                .content(review.getContent())
                .image(review.getImage())
                .visitDate(review.getVisitDate())
                .createdAt(review.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .isActive(false)
                .note(note)
                .build());
    }

    /**
     * 리뷰 보이기 (== 상태 true 업데이트)
     *
     * @param reviewId
     */
    @Transactional
    public void updateReviewActiveTrue(Long reviewId) {
        Review review = findReview(reviewId);
        reviewRepository.save(
            Review.builder()
                .id(review.getId())
                .restaurant(review.getRestaurant())
                .user(review.getUser())
                .rating(review.getRating())
                .content(review.getContent())
                .image(review.getImage())
                .visitDate(review.getVisitDate())
                .createdAt(review.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .isActive(true)
                .note(null)
                .build());
    }

    /**
     * 리뷰 감정 분석
     */
    @Override
    public void updateReviewSentiment() {

        List<Review> reviewList = reviewRepository.findAllBySentimentIsNull();

        for (Review review : reviewList) {
            try {
                String result = analyzeSentiment(review.getContent());
                review.reviewSentiment(result);
            } catch (Exception e) {
                log.error("Failed to analyze sentiment for review ID: " + review.getId(), e);
                continue;
            }
        }
        reviewRepository.saveAll(reviewList);

    }

    /**
     * Clova Studio API 호출을 위한 헤서 설정
     */
    private String analyzeSentiment(String review) {

        try {

            // 요청 url
            String url = clovaConfig.getUrl();

            // 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-NCP-CLOVASTUDIO-API-KEY", clovaConfig.getApiKey());
            headers.set("X-NCP-APIGW-API-KEY", clovaConfig.getApiGatewayKey());
            headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", clovaConfig.getRequestId());
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 바디 설정
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("includeAiFilters", true);
            requestBody.put("text", review);

            // 요청 엔티티 설정
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // API 호출
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                Map.class);

            // 응답 처리
            return extractSentimentFromResponse(response);

        } catch (HttpClientErrorException e) {
            throw new CustomException(ErrorCode.CLOVA_API_CLIENT_ERROR);
        } catch (HttpServerErrorException e) {
            throw new CustomException(ErrorCode.CLOVA_API_SERVER_ERROR);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SENTIMENT_ANALYSIS_FAILURE);
        }
    }

    private String extractSentimentFromResponse(ResponseEntity<Map> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                Map<String, Object> result = (Map<String, Object>) responseBody.get("result");
                if (result != null && result.containsKey("outputText")) {
                    return result.get("outputText").toString();
                }
            }
        }
        throw new CustomException(ErrorCode.SENTIMENT_ANALYSIS_FAILURE);
    }

}
