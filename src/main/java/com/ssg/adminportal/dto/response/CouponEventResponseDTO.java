package com.ssg.adminportal.dto.response;

import com.ssg.adminportal.domain.Coupon;
import com.ssg.adminportal.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponEventResponseDTO {

    private Coupon coupon;
    private List<Event> events;
}
