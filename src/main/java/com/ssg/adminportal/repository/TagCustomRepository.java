package com.ssg.adminportal.repository;

import com.wanna_v_local.domain.ReviewTag;
import com.wanna_v_local.dto.request.TagRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagCustomRepository {

    List<ReviewTag> findAll(TagRequestDTO tagRequestDTO);

    List<Integer> count(TagRequestDTO tagRequestDTO);
}
