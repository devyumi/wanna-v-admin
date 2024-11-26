package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.Statistic;
import com.ssg.adminportal.dto.request.StatisticRequestDTO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticCustomRepository {
    List<Statistic> getDashboardStats(StatisticRequestDTO requestDTO);

}
