package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long>, StatisticCustomRepository {

}
