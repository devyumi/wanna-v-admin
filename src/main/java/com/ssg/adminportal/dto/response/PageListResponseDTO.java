/**
 * 추후 리팩토링 시, 사용 예정
 */
package com.ssg.adminportal.dto.response;

import com.ssg.adminportal.dto.PageInfoDTO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageListResponseDTO<T> {

    private PageInfoDTO pageInfoDTO;
    private List<T> dataList;

}
