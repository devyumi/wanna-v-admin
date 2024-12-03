package com.ssg.adminportal.dto.response;

import com.ssg.adminportal.domain.Coupon;
import com.ssg.adminportal.dto.request.CouponListRequestDTO;
import com.ssg.adminportal.dto.request.PageListRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponListResponseDTO {
    private Integer page;
    private Integer size;
    private Integer total;
    private Integer last;
    private Integer start;
    private Integer end;
    private String type;
    private List<Coupon> coupons;
    @Builder
    public CouponListResponseDTO(CouponListRequestDTO requestDTO, List<Coupon> coupons, Integer total, String type) {
        this.page = requestDTO.getPage();
        this.size = requestDTO.getSize();
        this.coupons = coupons;
        this.total = total;
        this.type = type;
        this.last = (int) Math.ceil(total / (double) size);
        this.start = (page - 1) / 10 * 10 + 1;
        this.end = (last == 0) ? 1 : Math.min(start + 9, last);
    }
}
