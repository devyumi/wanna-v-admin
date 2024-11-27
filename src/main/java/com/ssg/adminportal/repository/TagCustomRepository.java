package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.Tag;
import com.ssg.adminportal.dto.request.TagRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagCustomRepository {

    List<Tag> findAll(TagRequestDTO tagRequestDTO);

    List<Integer> count(TagRequestDTO tagRequestDTO);
}
