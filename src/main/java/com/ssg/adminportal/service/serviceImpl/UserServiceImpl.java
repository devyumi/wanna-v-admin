package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.domain.User;
import com.ssg.adminportal.dto.request.UserListRequestDTO;
import com.ssg.adminportal.dto.response.UserListResponseDTO;
import com.ssg.adminportal.repository.UserRepository;
import com.ssg.adminportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserListResponseDTO getPageAll(UserListRequestDTO userListRequestDTO) {
        Pageable pageable = PageRequest.of(userListRequestDTO.getPage() - 1, userListRequestDTO.getSize(), Sort.by(Sort.Order.asc("id")));

        Page<User> userPage;

        Integer total = null;

        if (userListRequestDTO.getUsername().isEmpty()) {
            userPage = userRepository.findAll(pageable);
            total = userRepository.countAllUsers();
        }
        else {
            String usernameFilter = "%" + userListRequestDTO.getUsername() + "%";
            userPage = userRepository.findByUsernameLikeIgnoreCase(usernameFilter, pageable);
            total = userRepository.countByUsernameLikeIgnoreCase(usernameFilter);
        }

        return UserListResponseDTO.builder()
                .requestDTO(userListRequestDTO)
                .users(userPage.getContent())
                .username(userListRequestDTO.getUsername())
                .total(total)
                .build();
    }
}
