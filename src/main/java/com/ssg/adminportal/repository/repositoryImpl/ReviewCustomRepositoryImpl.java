package com.ssg.adminportal.repository.repositoryImpl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.adminportal.domain.QRestaurant;
import com.ssg.adminportal.domain.QReview;
import com.ssg.adminportal.domain.QUser;
import com.ssg.adminportal.domain.Review;
import com.ssg.adminportal.dto.request.ReviewRequestDTO;
import com.ssg.adminportal.repository.ReviewCustomRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
                result.where(review.isActive.eq(Boolean.parseBoolean(reviewRequestDTO.getIsActive())));
            }
        } else {
            //키워드 검색
            result.where(review.content.contains(reviewRequestDTO.getKeyword()))
                    .orderBy(review.id.asc());
        }
    }
}
