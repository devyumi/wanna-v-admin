package com.ssg.adminportal.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticMapper {

    void insertMonthlyStatistics();

    void insertWeeklyStatistics();
}
