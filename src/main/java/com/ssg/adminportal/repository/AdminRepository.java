package com.ssg.adminportal.repository;

import com.ssg.adminportal.common.Role;
import com.ssg.adminportal.domain.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Page<Admin> findAllByRole(Role role, Pageable pageable);

    Page<Admin> findAllByNameContaining(String name, Pageable pageable);

    Page<Admin> findAllByRoleAndNameContaining(Role role, String name, Pageable pageable);
}
