package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.dto.request.ReviewRequestDTO;
import com.ssg.adminportal.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("reviews")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public String getReviews(ReviewRequestDTO reviewRequestDTO, Model model) {
        model.addAttribute("reviews", reviewService.findReviews(reviewRequestDTO));
        return "review/reviews";
    }
}
