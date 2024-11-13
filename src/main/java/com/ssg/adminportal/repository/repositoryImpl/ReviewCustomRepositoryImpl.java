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
     * @param result
     * @param reviewRequestDTO
     */
    public void processWhere(JPAQuery<?> result, ReviewRequestDTO reviewRequestDTO) {
        if (reviewRequestDTO.getKeyword() == null) {
            switch (reviewRequestDTO.getType()) {
                //최신순
                case "new" -> {
                    result.orderBy(review.id.desc());
                }

                //등록순
                case "old" -> {
                    result.orderBy(review.id.asc());
                }

                //별점
                case "rating" -> {
                    result.where(review.rating.eq(Integer.parseInt(reviewRequestDTO.getScore())));
                }

                //상태
                case "status" -> {
                    result.where(review.isActive.eq(Boolean.parseBoolean(reviewRequestDTO.getIsActive())));
                }
            }
        } else {
            //키워드 검색
            result.where(review.content.contains(reviewRequestDTO.getKeyword()))
                    .orderBy(review.id.asc());
        }
    }
}
