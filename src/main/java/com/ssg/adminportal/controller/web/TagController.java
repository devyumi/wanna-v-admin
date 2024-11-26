package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.dto.TagSaveDTO;
import com.ssg.adminportal.dto.request.TagRequestDTO;
import com.ssg.adminportal.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews/tags")
@Slf4j
public class TagController {

    private final TagService tagService;

    @GetMapping
    public String getReviewTags(TagRequestDTO tagRequestDTO, Model model) {
        model.addAttribute("tags", tagService.findTags(tagRequestDTO));
        model.addAttribute("tagSaveDTO", new TagSaveDTO());
        model.addAttribute("tagUpdateDTO", new TagSaveDTO());
        return "review/tag/tags";
    }
}
