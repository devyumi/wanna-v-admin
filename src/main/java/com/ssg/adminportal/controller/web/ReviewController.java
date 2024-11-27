package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.dto.request.ReviewRequestDTO;
import com.ssg.adminportal.dto.request.ReviewUpdateStatusDTO;
import com.ssg.adminportal.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public String getReviews(ReviewRequestDTO reviewRequestDTO, Model model) {
        model.addAttribute("reviews", reviewService.findReviews(reviewRequestDTO));
        return "review/reviews";
    }

    @GetMapping("/{id}")
    public String getReview(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.findReview(id));
        model.addAttribute("reviewUpdateStatusDTO", new ReviewUpdateStatusDTO());
        return "review/review";
    }

    @PostMapping("/{id}/update-false")
    public String updateReviewFalse(@PathVariable Long id,
                                    @ModelAttribute @Validated ReviewUpdateStatusDTO reviewUpdateStatusDTO, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("id", id);

        if (bindingResult.hasErrors()) {
            printErrorLog(bindingResult);
            redirectAttributes.addFlashAttribute("alertMessage", bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return "redirect:/reviews/{id}";
        }
        reviewService.updateReviewActiveFalse(id, reviewUpdateStatusDTO.getNote());
        log.info("{}반 리뷰 숨김 완료", id);
        redirectAttributes.addFlashAttribute("alertMessage", "숨김 처리 되었습니다.");
        return "redirect:/reviews/{id}";
    }

    @PostMapping("/{id}/update-true")
    public String updateReviewTrue(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reviewService.updateReviewActiveTrue(id);
        log.info("{}번 리뷰 게시 완료", id);
        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addFlashAttribute("alertMessage", "게시 처리 되었습니다.");
        return "redirect:/reviews/{id}";
    }

    private static void printErrorLog(BindingResult result) {
        log.info("{}", "*".repeat(20));
        for (FieldError fieldError : result.getFieldErrors()) {
            log.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.info("{}", "*".repeat(20));
    }
}
