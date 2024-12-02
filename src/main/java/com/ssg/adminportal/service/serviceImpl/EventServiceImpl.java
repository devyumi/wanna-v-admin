package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.common.ErrorCode;
import com.ssg.adminportal.config.NcpConfig;
import com.ssg.adminportal.domain.Admin;
import com.ssg.adminportal.domain.Event;
import com.ssg.adminportal.domain.Product;
import com.ssg.adminportal.domain.User;
import com.ssg.adminportal.dto.FileDTO;
import com.ssg.adminportal.dto.request.EventRequestDTO;
import com.ssg.adminportal.dto.request.ProductRequestDTO;
import com.ssg.adminportal.dto.request.UserListRequestDTO;
import com.ssg.adminportal.dto.response.EventListResponseDTO;
import com.ssg.adminportal.dto.response.UserListResponseDTO;
import com.ssg.adminportal.exception.CustomException;
import com.ssg.adminportal.repository.AdminRepository;
import com.ssg.adminportal.repository.EventRepository;
import com.ssg.adminportal.service.EventService;
import com.ssg.adminportal.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final AdminRepository adminRepository;
    private final FileService fileService;
    private final NcpConfig ncpConfig;

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public EventListResponseDTO getPageAll(UserListRequestDTO userListRequestDTO) {
        Pageable pageable = PageRequest.of(userListRequestDTO.getPage() - 1, userListRequestDTO.getSize());

        Page<Event> eventPage = eventRepository.findAll(pageable);
        return EventListResponseDTO.builder()
                .requestDTO(userListRequestDTO)
                .events(eventPage.getContent())
                .total(eventRepository.countAllEvents())
                .build();
    }

    @Override
    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("이벤트가 없어"));
    }

    @Transactional
    public void createEvent(Long adminId, EventRequestDTO requestDTO) {
        String thumbNailUrl = null;
        String detailUrl = null;
        if (!requestDTO.getThumbnail().isEmpty() && !requestDTO.getDetail().isEmpty()) {
            FileDTO thumbnailFile = fileService.uploadFile(requestDTO.getThumbnail(), ncpConfig.getProductPath());
            FileDTO detailFile = fileService.uploadFile(requestDTO.getDetail(), ncpConfig.getProductPath());
            thumbNailUrl = thumbnailFile.getUploadFileUrl();
            detailUrl = detailFile.getUploadFileUrl();
        }

        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));

        eventRepository.save(Event.builder()
                .title(requestDTO.getTitle())
                .thumbnail(thumbNailUrl)
                .detail(detailUrl)
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .createdBy(admin)
                .createdAt(requestDTO.getCreatedAt())
                .build());
    }
}
