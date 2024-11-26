package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/")
    public String showDashboard(Model model) {
        model.addAttribute("monthlyStatistics", statisticService.getMonthlyStatistics());
        return "dashboard/statistic"; // 대시보드 페이지 반환
    }
}