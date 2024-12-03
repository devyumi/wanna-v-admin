package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.domain.Event;
import com.ssg.adminportal.dto.request.CouponSaveRequestDTO;
import com.ssg.adminportal.dto.response.CouponEventResponseDTO;
import com.ssg.adminportal.service.CouponService;
import com.ssg.adminportal.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;
    private final EventService eventService;

    @GetMapping()
    public String couponList(Model model){
        return "promotion/coupon/coupons";
    }

    @GetMapping("{couponId}")
    public String couponList(@PathVariable Long couponId, Model model){
        CouponEventResponseDTO coupon = couponService.getCoupon(couponId);
        model.addAttribute("coupon", coupon);
        return "promotion/coupon/coupon";
    }

    @GetMapping("/create")
    public String createCoupon(Model model){
        List<Event> events = eventService.getAll();
        model.addAttribute("events", events);
        return "promotion/coupon/save";
    }
}