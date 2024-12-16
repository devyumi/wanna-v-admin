package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.domain.UserGradeLog;
import com.ssg.adminportal.repository.UserGradeLogRepository;
import com.ssg.adminportal.service.UserGradeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserGradeLogServiceImpl implements UserGradeLogService {

    private final UserGradeLogRepository userGradeLogRepository;

    @Override
    public UserGradeLog getUser(Long userId) {
        return userGradeLogRepository.findByUserId(userId);
    }
}
