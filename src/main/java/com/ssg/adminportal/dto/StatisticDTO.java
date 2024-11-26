package com.ssg.adminportal.dto;

import com.ssg.adminportal.common.StatisticCategory;
import lombok.Getter;

@Getter
public class StatisticDTO {
    private Integer year;
    private Integer month;
    private Integer week;
    private Double value;
    private StatisticCategory category;
    private String type; // M: 월별, W: 주별
}
