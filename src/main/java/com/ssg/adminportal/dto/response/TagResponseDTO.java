package com.ssg.adminportal.dto.response;

import com.ssg.adminportal.domain.ReviewTag;
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
    private List<ReviewTag> tags;
    private List<Integer> count;

    @Builder
    public TagResponseDTO(TagRequestDTO tagRequestDTO, List<ReviewTag> tags, List<Integer> count) {
        this.type = tagRequestDTO.getType();
        this.tags = tags;
        this.count = count;
    }
}
