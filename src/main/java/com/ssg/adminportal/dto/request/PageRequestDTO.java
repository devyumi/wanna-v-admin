/**
 * 추후 리팩토링 시, 사용 예정
 */
package com.ssg.adminportal.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageRequestDTO {

    @Builder.Default
    @Min(value = 1)
    @Positive
    private int page = 1;

    @Builder.Default
    @Min(value = 10)
    private int size = 10;

    @Builder.Default
    private String orderBy = "id";

    @Builder.Default
    private String orderByDir = "DESC";

//    private SearchTypeDTO search = new SearchTypeDTO();

    public int getOffset(){
        return (page - 1) * 10;
    }

}