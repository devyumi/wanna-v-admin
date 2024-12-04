package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.common.Role;
import com.ssg.adminportal.domain.Admin;
import com.ssg.adminportal.dto.request.AdminListRequestDTO;
import com.ssg.adminportal.dto.response.AdminListResponseDTO;
import com.ssg.adminportal.repository.AdminRepository;
import com.ssg.adminportal.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public AdminListResponseDTO getAll(AdminListRequestDTO adminListRequestDTO) {

        Pageable pageable = PageRequest.of(adminListRequestDTO.getPage() - 1, adminListRequestDTO.getSize(), Sort.by(Sort.Order.asc("id")));
        Page<Admin> adminPage;

        if (adminListRequestDTO.getRole().isEmpty() && adminListRequestDTO.getName().isEmpty())
            adminPage = adminRepository.findAll(pageable);
        else if (!adminListRequestDTO.getRole().isEmpty() && adminListRequestDTO.getName().isEmpty())
            adminPage = adminRepository.findAllByRole(Role.valueOf(adminListRequestDTO.getRole()), pageable);
        else if (adminListRequestDTO.getRole().isEmpty() && !adminListRequestDTO.getName().isEmpty())
            adminPage = adminRepository.findAllByNameContaining(adminListRequestDTO.getName(), pageable);
        else
            adminPage = adminRepository.findAllByRoleAndNameContaining(Role.valueOf(adminListRequestDTO.getRole()), adminListRequestDTO.getName(), pageable);

        return AdminListResponseDTO.builder()
                .requestDTO(adminListRequestDTO)
                .admins(adminPage.getContent())
                .total((int) adminPage.getTotalElements())
                .role(adminListRequestDTO.getRole())
                .build();
    }

    @Override
    public Admin getAdmin(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("직원이 없습니다."));
    }
}
