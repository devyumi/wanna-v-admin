package com.ssg.adminportal.dto.request;

import com.ssg.adminportal.common.StatisticCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticRequestDTO {
    private StatisticCategory category;
    private String type;
    private DateTime createdAt;
}
