package com.ssg.adminportal.repository.repositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.adminportal.common.ErrorCode;
import com.ssg.adminportal.domain.QStatistic;
import com.ssg.adminportal.domain.Statistic;
import com.ssg.adminportal.exception.CustomException;
import com.ssg.adminportal.repository.StatisticCustomRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class StatisticCustomRepositoryImpl implements StatisticCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QStatistic statistic = QStatistic.statistic;

    @Override
    public List<Statistic> getDashboardStats(String type) {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();

        try {
            switch (type) {
                case "M":
                    startDate = endDate.minusYears(1);
                    break;
                case "W":
                    startDate = endDate.minusMonths(3);
                    break;
                default:
                    throw new CustomException(ErrorCode.INVALID_REQUEST_TYPE);
            }

            return queryFactory.selectFrom(statistic)
                .where(statistic.type.eq(type)
                    .and(statistic.createdAt.between(startDate, endDate)))
                .fetch(); // 조건에 맞는 결과 반환
        } catch (CustomException e) {
            log.error("CustomException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw new CustomException(ErrorCode.DATE_CALCULATION_ERROR); // 날짜 계산 중 발생한 오류 처리
        }

    }
}
