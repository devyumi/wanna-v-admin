/**
 * 추후 리팩토링 시, 사용 예정
 */
package com.ssg.adminportal.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageResponseDTO<T> {

    private T data;

}
