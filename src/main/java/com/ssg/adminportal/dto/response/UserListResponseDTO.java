package com.ssg.adminportal.dto.response;

import com.ssg.adminportal.domain.User;
import com.ssg.adminportal.dto.request.UserListRequestDTO;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponseDTO {
    private Integer page;
    private Integer size;
    private Integer total;
    private Integer last;
    private Integer start;
    private Integer end;
    private List<User> users;
    @Builder
    public UserListResponseDTO(UserListRequestDTO requestDTO, List<User> users, Integer total) {
        this.page = requestDTO.getPage();
        this.size = requestDTO.getSize();
        this.users = users;
        this.total = total;
        this.last = (int) Math.ceil(total / (double) size);
        this.start = (page - 1) / 10 * 10 + 1;
        this.end = (last == 0) ? 1 : Math.min(start + 9, last);
    }
}
