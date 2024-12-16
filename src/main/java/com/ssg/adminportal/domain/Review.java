package com.ssg.adminportal.domain;

import com.ssg.adminportal.common.ErrorCode;
import com.ssg.adminportal.common.Sentiment;
import com.ssg.adminportal.exception.CustomException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer rating;
    private String content;
    private String image;

    @Temporal(TemporalType.DATE)
    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private Sentiment sentiment;

    private String note;
    @OneToMany(mappedBy = "review")
    private List<ReviewTag> reviewTags;

    /**
     * 연관관계 편의 메서드
     */
    public void addRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        restaurant.getReviews().add(this);
    }

    /**
     * 감성 분석 결과 업데이트
     * @param sentiment
     */
    public void reviewSentiment(String sentiment) {
        switch (sentiment) {
            case "긍정" -> this.sentiment = Sentiment.POSITIVE;
            case "부정" -> this.sentiment = Sentiment.NEGATIVE;
            default -> throw new CustomException(ErrorCode.SENTIMENT_ANALYSIS_FAILURE);
        }

        this.updatedAt = LocalDateTime.now();
    }

}
