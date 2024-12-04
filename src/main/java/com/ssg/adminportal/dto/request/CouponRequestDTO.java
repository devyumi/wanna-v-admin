package com.ssg.adminportal.dto.request;

import com.ssg.adminportal.common.Type;
import com.ssg.adminportal.domain.Admin;
import com.ssg.adminportal.domain.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequestDTO {
    private Long id;
    private String active;
}
