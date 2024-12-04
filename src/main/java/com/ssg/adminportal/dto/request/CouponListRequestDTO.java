package com.ssg.adminportal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponListRequestDTO {

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    private String type;

    private Boolean active;

    public Integer getOffset() {
        return (page - 1) * size;
    }
}
