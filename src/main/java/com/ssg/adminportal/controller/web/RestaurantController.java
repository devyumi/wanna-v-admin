package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public String getReviewSentiment(@PathVariable Long id, Model model) {
        model.addAttribute("sentiment", reviewService.getSentiment(id));
        return "restaurant/restaurant";
    }
}
