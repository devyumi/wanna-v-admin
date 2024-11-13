package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.ReviewTag;
import com.ssg.adminportal.dto.request.TagRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagCustomRepository {

    List<ReviewTag> findAll(TagRequestDTO tagRequestDTO);

    List<Integer> count(TagRequestDTO tagRequestDTO);
}
