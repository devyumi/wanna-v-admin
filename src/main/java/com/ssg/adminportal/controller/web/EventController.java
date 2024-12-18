package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.domain.Event;
import com.ssg.adminportal.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping()
    public String eventList(){
        return "promotion/event/events";
    }

    @GetMapping("/create")
    public String createEvent(){
        return "promotion/event/save";
    }

    @GetMapping("/{eventId}")
    public String user(@PathVariable Long eventId, Model model) {
        Event event = eventService.getEvent(eventId);
        model.addAttribute("event", event);
        return "promotion/event/event";
    }

}