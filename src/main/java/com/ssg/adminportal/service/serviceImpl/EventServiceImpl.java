package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.domain.Event;
import com.ssg.adminportal.repository.repositoryImpl.EventRepository;
import com.ssg.adminportal.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
