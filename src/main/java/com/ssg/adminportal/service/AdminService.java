package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.Admin;
import com.ssg.adminportal.dto.request.AdminListRequestDTO;
import com.ssg.adminportal.dto.request.CouponListRequestDTO;
import com.ssg.adminportal.dto.request.CouponRequestDTO;
import com.ssg.adminportal.dto.request.CouponSaveRequestDTO;
import com.ssg.adminportal.dto.response.AdminListResponseDTO;
import com.ssg.adminportal.dto.response.CouponEventResponseDTO;
import com.ssg.adminportal.dto.response.CouponListResponseDTO;

public interface AdminService {

    AdminListResponseDTO getAll(AdminListRequestDTO adminListRequestDTO);

    Admin getAdmin(Long adminId);
}
