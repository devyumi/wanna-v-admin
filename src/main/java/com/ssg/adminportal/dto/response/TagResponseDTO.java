package com.ssg.adminportal.dto.response;

import com.ssg.adminportal.domain.Tag;
import com.ssg.adminportal.dto.request.TagRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDTO {

    private String type;
    private List<Tag> tags;
    private List<Integer> count;

    @Builder
    public TagResponseDTO(TagRequestDTO tagRequestDTO, List<Tag> tags, List<Integer> count) {
        this.type = tagRequestDTO.getType();
        this.tags = tags;
        this.count = count;
    }
}
