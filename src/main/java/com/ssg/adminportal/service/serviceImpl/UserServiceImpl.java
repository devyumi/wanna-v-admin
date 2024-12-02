package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.domain.User;
import com.ssg.adminportal.dto.request.ProductListRequestDTO;
import com.ssg.adminportal.dto.request.UserListRequestDTO;
import com.ssg.adminportal.dto.response.ProductResponseDTO;
import com.ssg.adminportal.dto.response.UserListResponseDTO;
import com.ssg.adminportal.repository.UserRepository;
import com.ssg.adminportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserListResponseDTO getPageAll(UserListRequestDTO userListRequestDTO) {
        Pageable pageable = PageRequest.of(userListRequestDTO.getPage() - 1, userListRequestDTO.getSize());

        Page<User> userPage = userRepository.findAll(pageable);
        return UserListResponseDTO.builder()
                .requestDTO(userListRequestDTO)
                .users(userPage.getContent())
                .total(userRepository.countAllUsers())
                .build();
    }
}
