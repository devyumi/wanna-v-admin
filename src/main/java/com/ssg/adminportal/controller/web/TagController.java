package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.dto.TagSaveDTO;
import com.ssg.adminportal.dto.request.TagRequestDTO;
import com.ssg.adminportal.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/write")
    public String saveTag(@ModelAttribute @Validated TagSaveDTO tagSaveDTO, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            printErrorLog(bindingResult);
            redirectAttributes.addFlashAttribute("alertMessage", bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return "redirect:/reviews/tags";
        }
        tagService.saveTag(tagSaveDTO);
        redirectAttributes.addFlashAttribute("alertMessage", "추가 되었습니다.");
        return "redirect:/reviews/tags";
    }

    @PostMapping("/{id}/update")
    public String updateTag(@PathVariable Long id,
                            @ModelAttribute @Validated TagSaveDTO tagSaveDTO, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            printErrorLog(bindingResult);
            redirectAttributes.addFlashAttribute("alertMessage", bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return "redirect:/reviews/tags";
        }
        tagService.updateTag(id, tagSaveDTO);
        redirectAttributes.addFlashAttribute("alertMessage", "수정 되었습니다.");
        return "redirect:/reviews/tags";
    }

    @PostMapping("/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("alertMessage", "삭제 되었습니다.");
        return "redirect:/reviews/tags";
    }

    private static void printErrorLog(BindingResult result) {
        log.info("{}", "*".repeat(20));
        for (FieldError fieldError : result.getFieldErrors()) {
            log.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.info("{}", "*".repeat(20));
    }
}
