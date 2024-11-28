package com.ssg.adminportal.repository.repositoryImpl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.adminportal.common.Sentiment;
import com.ssg.adminportal.domain.QRestaurant;
import com.ssg.adminportal.domain.QReview;
import com.ssg.adminportal.domain.QUser;
import com.ssg.adminportal.domain.Review;
import com.ssg.adminportal.dto.request.ReviewRequestDTO;
import com.ssg.adminportal.dto.response.SentimentResponseDTO;
import com.ssg.adminportal.repository.ReviewCustomRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QReview review = QReview.review;

    @Override
    public List<Review> findAll(ReviewRequestDTO reviewRequestDTO) {

        //기본
        JPAQuery<Review> result = queryFactory.selectFrom(review)
            .innerJoin(review.restaurant, QRestaurant.restaurant)
            .innerJoin(review.user, QUser.user);

        //동적쿼리
        processWhere(result, reviewRequestDTO);
        return result
            .offset(reviewRequestDTO.getOffset())
            .limit(reviewRequestDTO.getSize())
            .fetchJoin().fetch();
    }

    @Override
    public Integer count(ReviewRequestDTO reviewRequestDTO) {
        //기본
        JPAQuery<Long> result = queryFactory.select(review.count())
            .from(review)
            .innerJoin(review.restaurant, QRestaurant.restaurant)
            .innerJoin(review.user, QUser.user);

        //동적쿼리
        processWhere(result, reviewRequestDTO);
        return result.fetchFirst().intValue();
    }

    @Override
    public List<SentimentResponseDTO> countBySentimentAndRestaurant(Long restaurantId) {
        List<Tuple> results = queryFactory
            .select(review.sentiment, review.id.count())
            .from(review)
            .where(review.restaurant.id.eq(restaurantId))
            .groupBy(review.sentiment)
            .fetch();

        return results.stream()
            .map(result -> new SentimentResponseDTO(
                result.get(0, Sentiment.class),
                result.get(1, Long.class).intValue()
            ))
            .collect(Collectors.toList());
    }

    /**
     * 동적쿼리 메서드
     *
     * @param result
     * @param reviewRequestDTO
     */
    public void processWhere(JPAQuery<?> result, ReviewRequestDTO reviewRequestDTO) {

        if (reviewRequestDTO.getKeyword() == null) {
            if (reviewRequestDTO.getType() == null) {
                result.orderBy(review.id.desc());
            } else if (reviewRequestDTO.getType().equals("new")) {
                result.orderBy(review.id.desc());
            } else if (reviewRequestDTO.getType().equals("old")) {
                result.orderBy(review.id.asc());
            } else if (reviewRequestDTO.getType().equals("rating")) {
                result.where(review.rating.eq(Integer.parseInt(reviewRequestDTO.getScore())));
            } else if (reviewRequestDTO.getType().equals("status")) {
                result.where(
                    review.isActive.eq(Boolean.parseBoolean(reviewRequestDTO.getIsActive())));
            }
        } else {
            //키워드 검색
            result.where(review.content.contains(reviewRequestDTO.getKeyword()))
                .orderBy(review.id.asc());
        }
    }
}
