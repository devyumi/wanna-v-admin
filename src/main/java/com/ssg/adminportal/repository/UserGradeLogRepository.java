package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.UserGradeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGradeLogRepository extends JpaRepository<UserGradeLog, Long> {

    UserGradeLog findByUserId(Long userId);
}
