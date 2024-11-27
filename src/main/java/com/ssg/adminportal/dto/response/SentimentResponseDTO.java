package com.ssg.adminportal.dto.response;

import com.ssg.adminportal.common.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentimentResponseDTO {

    private Sentiment sentiment;
    private Integer count;
}
