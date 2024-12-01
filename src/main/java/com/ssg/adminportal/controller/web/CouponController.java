package com.ssg.adminportal.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    @GetMapping()
    public String couponList(Model model){
        return "promotion/coupon/coupons";
    }
}