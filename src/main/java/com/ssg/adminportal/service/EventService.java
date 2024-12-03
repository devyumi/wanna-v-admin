package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.Event;
import com.ssg.adminportal.dto.request.EventRequestDTO;
import com.ssg.adminportal.dto.request.PageListRequestDTO;
import com.ssg.adminportal.dto.response.EventListResponseDTO;

import java.util.List;

public interface EventService {
    List<Event> getAll();

    EventListResponseDTO getPageAll(PageListRequestDTO pageListRequestDTO);

    Event getEvent(Long eventId);

    void createEvent(Long adminId, EventRequestDTO requestDTO);
}
