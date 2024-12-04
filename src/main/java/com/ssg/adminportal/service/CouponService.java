package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.Coupon;
import com.ssg.adminportal.dto.request.CouponListRequestDTO;
import com.ssg.adminportal.dto.request.CouponRequestDTO;
import com.ssg.adminportal.dto.request.CouponSaveRequestDTO;
import com.ssg.adminportal.dto.response.CouponEventResponseDTO;
import com.ssg.adminportal.dto.response.CouponListResponseDTO;

public interface CouponService {

    CouponListResponseDTO getAll(CouponListRequestDTO couponListRequestDTO);

    CouponEventResponseDTO getCoupon(Long couponId);

    void updateCoupon(Long couponId, CouponRequestDTO coupon);

    void createCoupon(Long adminId, CouponSaveRequestDTO coupon);
}
