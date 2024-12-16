package com.ssg.adminportal.scheduler;

import com.ssg.adminportal.mapper.StatisticMapper;
import com.ssg.adminportal.service.ReviewService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StatisticScheduler {

    private final StatisticMapper statisticMapper;
    private final ReviewService reviewService;

    /**
     * 대시보드 월별 통계
     * 매월 1일 새벽 2시 지난달 통계 쿼리 실행
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    public void updateMonthlyStatistics() {
        log.info(LocalDateTime.now() + " 월별 통계 업데이트 시작");
        statisticMapper.insertMonthlyStatistics();
        log.info(LocalDateTime.now() + " 월별 통계 업데이트 종료");
    }

     /**
     * 대시보드 주별 통계
     * 매주 월요일 새벽 2시 지난달 통계 쿼리 실행
     */
    @Scheduled(cron = "0 0 2 * * MON")
    public void updateWeeklyStatistics() {
        log.info(LocalDateTime.now() + " 주별 통계 업데이트 시작");
        statisticMapper.insertWeeklyStatistics();
        log.info(LocalDateTime.now() + " 주별 통계 업데이트 종료");
    }

    /**
     * 식당 리뷰 감정 분석
     * 매일 새벽 00시 30분 작일 분석 쿼리 실행
     */
    @Scheduled(cron = "0 30 0 * * *")
    public void updateReviewSentiment() {
        log.info(LocalDateTime.now() + " 리뷰 감정 분석 시작");
        reviewService.updateReviewSentiment();
        log.info(LocalDateTime.now() + " 리뷰 감정 분석 종료");
    }
}
