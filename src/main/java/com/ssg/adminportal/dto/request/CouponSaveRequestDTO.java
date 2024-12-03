package com.ssg.adminportal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponSaveRequestDTO {

    private Long eventId;
    private String name;
    private String type;
    private Integer amount;
    private Integer rate;
    private Boolean active;
    private LocalDateTime endDate;
}
