package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.domain.Statistic;
import com.ssg.adminportal.dto.StatisticDTO;
import com.ssg.adminportal.dto.request.StatisticRequestDTO;
import com.ssg.adminportal.mapper.StatisticMapper;
import com.ssg.adminportal.repository.StatisticCustomRepository;
import com.ssg.adminportal.service.StatisticService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticMapper statisticMapper;
    private final StatisticCustomRepository statisticCustomRepository;


    @Override
    public List<StatisticDTO> getMonthlyStatistics() {
        return statisticMapper.getMonthlyStatistics();
    }

    @Override
    public List<StatisticDTO> getWeeklyStatistics() {
        return statisticMapper.getWeeklyStatistics();
    }

    @Override
    public List<Statistic> getDashboardStats(StatisticRequestDTO requestDTO) {
        return statisticCustomRepository.getDashboardStats(requestDTO);
    }
}
