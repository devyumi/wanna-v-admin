package com.ssg.adminportal.mapper;

import com.ssg.adminportal.dto.StatisticDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticMapper {

    void insertMonthlyStatistics();

    void insertWeeklyStatistics();

    List<StatisticDTO> getMonthlyStatistics();

    List<StatisticDTO> getWeeklyStatistics();
}
