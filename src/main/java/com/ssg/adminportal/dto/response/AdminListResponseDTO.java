package com.ssg.adminportal.dto.response;

import com.ssg.adminportal.common.Role;
import com.ssg.adminportal.domain.Admin;
import com.ssg.adminportal.domain.Coupon;
import com.ssg.adminportal.dto.request.AdminListRequestDTO;
import com.ssg.adminportal.dto.request.CouponListRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminListResponseDTO {
    private Integer page;
    private Integer size;
    private Integer total;
    private Integer last;
    private Integer start;
    private Integer end;
    private String role;
    private String name;
    private List<Admin> admins;
    @Builder
    public AdminListResponseDTO(AdminListRequestDTO requestDTO, List<Admin> admins, Integer total, String role, String name) {
        this.page = requestDTO.getPage();
        this.size = requestDTO.getSize();
        this.admins = admins;
        this.total = total;
        this.role = role;
        this.name = name;
        this.last = (int) Math.ceil(total / (double) size);
        this.start = (page - 1) / 10 * 10 + 1;
        this.end = (last == 0) ? 1 : Math.min(start + 9, last);
    }
}
