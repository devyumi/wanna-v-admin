package com.ssg.adminportal.controller.api;

import com.ssg.adminportal.domain.Coupon;
import com.ssg.adminportal.dto.request.CouponListRequestDTO;
import com.ssg.adminportal.dto.request.CouponRequestDTO;
import com.ssg.adminportal.dto.request.CouponSaveRequestDTO;
import com.ssg.adminportal.dto.request.PageListRequestDTO;
import com.ssg.adminportal.dto.response.CouponListResponseDTO;
import com.ssg.adminportal.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponRestController {

    private final CouponService couponService;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> couponList(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(defaultValue = "") String type) {

        CouponListRequestDTO requestDTO = CouponListRequestDTO.builder()
            .page(page)
            .size(size)
            .type(type)
            .build();

        CouponListResponseDTO responseDTO = couponService.getAll(requestDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", responseDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{couponId}")
    public ResponseEntity<Map<String, Object>> updateCoupon(
            @PathVariable Long couponId,
            @ModelAttribute CouponRequestDTO coupon) {
        Map<String, Object> response = new HashMap<>();

        try {
            couponService.updateCoupon(couponId, coupon);

            response.put("success", true);
            response.put("message", "수정이 완료되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "수정에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> createCoupon(@RequestBody CouponSaveRequestDTO coupon) {

        Long adminId = 1L;

        couponService.createCoupon(adminId, coupon);

        return null;
    }
}
