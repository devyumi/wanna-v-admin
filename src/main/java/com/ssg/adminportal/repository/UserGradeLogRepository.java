package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.UserGradeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGradeLogRepository extends JpaRepository<UserGradeLog, Long> {
    @Query("SELECT u FROM UserGradeLog u WHERE u.id = :userId ORDER BY u.createdAt DESC LIMIT 1")
    UserGradeLog findByUserId(Long userId);
}
