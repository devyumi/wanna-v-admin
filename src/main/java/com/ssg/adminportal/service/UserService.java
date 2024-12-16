package com.ssg.adminportal.service;

import com.ssg.adminportal.dto.request.UserListRequestDTO;
import com.ssg.adminportal.dto.response.UserListResponseDTO;

public interface UserService {

    UserListResponseDTO getPageAll(UserListRequestDTO userListRequestDTO);
}
