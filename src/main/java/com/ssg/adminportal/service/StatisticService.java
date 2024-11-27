package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.Statistic;
import com.ssg.adminportal.dto.StatisticDTO;
import java.util.List;

public interface StatisticService {

    List<StatisticDTO> getMonthlyStatistics();

    List<StatisticDTO> getWeeklyStatistics();

    List<Statistic> getDashboardStats(String type);
}