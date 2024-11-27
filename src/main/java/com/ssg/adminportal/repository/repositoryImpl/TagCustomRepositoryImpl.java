package com.ssg.adminportal.repository.repositoryImpl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.adminportal.domain.QReviewTag;
import com.ssg.adminportal.domain.QTag;
import com.ssg.adminportal.domain.Tag;
import com.ssg.adminportal.dto.request.TagRequestDTO;
import com.ssg.adminportal.repository.TagCustomRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TagCustomRepositoryImpl implements TagCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QTag tag = QTag.tag;
    private final QReviewTag reviewTag = QReviewTag.reviewTag;

    @Override
    public List<Tag> findAll(TagRequestDTO tagRequestDTO) {
        //기본
        JPAQuery<Tag> result = queryFactory
                .selectFrom(tag)
                .leftJoin(reviewTag).on(reviewTag.tag.id.eq(tag.id));

        //동적쿼리
        processWhere(result, tagRequestDTO);
        return result.groupBy(tag.id).fetch();
    }

    @Override
    public List<Integer> count(TagRequestDTO tagRequestDTO) {
        //기본
        JPAQuery<Long> result = queryFactory
                .select(reviewTag.tag.id.count())
                .from(tag)
                .leftJoin(reviewTag).on(reviewTag.tag.id.eq(tag.id));

        //동적쿼리
        processWhere(result, tagRequestDTO);
        return result.groupBy(tag.id).fetch()
                .stream()
                .map(Long::intValue)
                .collect(Collectors.toList());
    }

    public void processWhere(JPAQuery<?> result, TagRequestDTO tagRequestDTO) {

        if (tagRequestDTO.getType() == null || tagRequestDTO.getType().equals("price")) {
            result.where(QTag.tag.category.contains("맛/가격"));
        } else if (tagRequestDTO.getType().equals("service")) {
            result.where(QTag.tag.category.contains("서비스/기타"));
        } else if (tagRequestDTO.getType().equals("pattern")) {
            result.where(QTag.tag.category.contains("이용형태"));
        } else if (tagRequestDTO.getType().equals("mood")) {
            result.where(QTag.tag.category.contains("분위기"));
        }
    }
}
