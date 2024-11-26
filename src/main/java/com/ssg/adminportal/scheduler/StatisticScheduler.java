package com.ssg.adminportal.scheduler;

import com.ssg.adminportal.mapper.StatisticMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticScheduler {

    private final StatisticMapper statisticMapper;

    /**
     * 매월 1일 새벽 2시 지난달 통계 쿼리 실행
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    public void updateMonthlyStatistics() {
        statisticMapper.insertMonthlyStatistics();
    }

     /**
     * 매주 월요일 새벽 2시 지난달 통계 쿼리 실행
     */
    @Scheduled(cron = "0 0 2 * * MON")
    public void updateWeeklyStatistics() {
        statisticMapper.insertWeeklyStatistics();
    }
}
