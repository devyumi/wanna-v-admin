package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.Tag;
import com.ssg.adminportal.dto.TagSaveDTO;
import com.ssg.adminportal.dto.request.TagRequestDTO;
import com.ssg.adminportal.dto.response.TagResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface TagService {

    TagResponseDTO findTags(TagRequestDTO tagRequestDTO);

    Tag findTag(Long tagId);

    void saveTag(TagSaveDTO tagSaveDTO);

    void updateTag(Long tagId, TagSaveDTO tagSaveDTO);

    void deleteTag(Long tagId);

}
