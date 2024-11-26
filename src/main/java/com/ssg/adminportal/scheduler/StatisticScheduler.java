package com.ssg.adminportal.scheduler;

import com.ssg.adminportal.mapper.StatisticMapper;
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

    /**
     * 매월 1일 새벽 2시 지난달 통계 쿼리 실행
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    public void updateMonthlyStatistics() {
        log.info(LocalDateTime.now() + " 월별 통계 업데이트 시작");
        statisticMapper.insertMonthlyStatistics();
        log.info(LocalDateTime.now() + " 월별 통계 업데이트 종료");
    }

     /**
     * 매주 월요일 새벽 2시 지난달 통계 쿼리 실행
     */
    @Scheduled(cron = "0 0 2 * * MON")
    public void updateWeeklyStatistics() {
        log.info(LocalDateTime.now() + " 주별 통계 업데이트 시작");
        statisticMapper.insertWeeklyStatistics();
        log.info(LocalDateTime.now() + " 주별 통계 업데이트 종료");
    }
}
