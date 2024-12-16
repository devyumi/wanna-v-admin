package com.ssg.adminportal.controller.api;

import com.ssg.adminportal.domain.Statistic;
import com.ssg.adminportal.dto.StatisticDTO;
import com.ssg.adminportal.service.StatisticService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticRestController {

    private final StatisticService statisticService;

    // 월별 통계 API
    @GetMapping("/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyStatistics() {
        List<StatisticDTO> monthlyStatistics = statisticService.getMonthlyStatistics();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", monthlyStatistics);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 주별 통계 API
    @GetMapping("/weekly")
    public ResponseEntity<Map<String, Object>> getWeeklyStatistics() {
        List<StatisticDTO> weeklyStatistics = statisticService.getWeeklyStatistics();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", weeklyStatistics);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 통계 API
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getStatistics(@RequestParam String type) {
        List<Statistic> statistics = statisticService.getDashboardStats(type);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", statistics);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}