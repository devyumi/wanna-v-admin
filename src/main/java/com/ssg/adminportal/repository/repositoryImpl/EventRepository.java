package com.ssg.adminportal.repository.repositoryImpl;

import com.ssg.adminportal.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
