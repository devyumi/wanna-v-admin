package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.Statistic;
import com.ssg.adminportal.dto.StatisticDTO;
import com.ssg.adminportal.dto.request.StatisticRequestDTO;
import java.util.List;

public interface StatisticService {

    List<StatisticDTO> getMonthlyStatistics();

    List<StatisticDTO> getWeeklyStatistics();

    List<Statistic> getDashboardStats(StatisticRequestDTO requestDTO);
}