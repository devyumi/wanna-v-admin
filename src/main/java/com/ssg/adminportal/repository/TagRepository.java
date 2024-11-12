package com.ssg.adminportal.repository;

import com.wanna_v_local.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {
}
