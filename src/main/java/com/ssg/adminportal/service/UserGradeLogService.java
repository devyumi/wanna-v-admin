package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.UserGradeLog;
import com.ssg.adminportal.dto.request.UserListRequestDTO;
import com.ssg.adminportal.dto.response.UserListResponseDTO;

public interface UserGradeLogService {

    UserGradeLog getUser(Long userId);
}
