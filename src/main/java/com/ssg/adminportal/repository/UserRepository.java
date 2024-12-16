package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT COUNT(u) FROM User u")
    int countAllUsers();
    Page<User> findByUsernameLikeIgnoreCase(String username, Pageable pageable);

    Integer countByUsernameLikeIgnoreCase(String username);
}
