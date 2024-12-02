package com.ssg.adminportal.dto.response;

import com.ssg.adminportal.domain.Event;
import com.ssg.adminportal.domain.User;
import com.ssg.adminportal.dto.request.UserListRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventListResponseDTO {
    private Integer page;
    private Integer size;
    private Integer total;
    private Integer last;
    private Integer start;
    private Integer end;
    private List<Event> events;
    @Builder
    public EventListResponseDTO(UserListRequestDTO requestDTO, List<Event> events, Integer total) {
        this.page = requestDTO.getPage();
        this.size = requestDTO.getSize();
        this.events = events;
        this.total = total;
        this.last = (int) Math.ceil(total / (double) size);
        this.start = (page - 1) / 10 * 10 + 1;
        this.end = (last == 0) ? 1 : Math.min(start + 9, last);
    }
}
