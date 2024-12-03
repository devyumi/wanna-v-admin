package com.ssg.adminportal.repository;

import com.ssg.adminportal.common.Type;
import com.ssg.adminportal.domain.Admin;
import com.ssg.adminportal.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Boolean existsByCode(Integer code);

    Page<Coupon> findAllByType(Type type, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Coupon c")
    int countAllEvents();
}
