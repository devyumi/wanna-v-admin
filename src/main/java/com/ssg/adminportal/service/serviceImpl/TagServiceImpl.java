package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.domain.Tag;
import com.ssg.adminportal.dto.TagSaveDTO;
import com.ssg.adminportal.dto.request.TagRequestDTO;
import com.ssg.adminportal.dto.response.TagResponseDTO;
import com.ssg.adminportal.repository.TagRepository;
import com.ssg.adminportal.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    /**
     * 태그 전체 조회 (개수 포함)
     *
     * @param tagRequestDTO
     * @return
     */
    @Transactional(readOnly = true)
    public TagResponseDTO findTags(TagRequestDTO tagRequestDTO) {
        return TagResponseDTO.builder()
                .tagRequestDTO(tagRequestDTO)
                .tags(tagRepository.findAll(tagRequestDTO))
                .count(tagRepository.count(tagRequestDTO))
                .build();
    }

    /**
     * 태그 상세 조회
     *
     * @param tagId
     * @return
     */
    @Transactional(readOnly = true)
    public Tag findTag(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new IllegalArgumentException("잘못된 id 값입니다."));
    }

    /**
     * 태그 추가
     *
     * @param tagSaveDTO
     */
    @Transactional
    public void saveTag(TagSaveDTO tagSaveDTO) {
        tagRepository.save(Tag.builder()
                .category(tagSaveDTO.getCategory())
                .name(tagSaveDTO.getName())
                .build());
    }

    /**
     * 태그 업데이트
     *
     * @param tagId
     * @param tagSaveDTO
     */
    @Transactional
    public void updateTag(Long tagId, TagSaveDTO tagSaveDTO) {
        Tag tag = findTag(tagId);
        tagRepository.save(Tag.builder()
                .id(tagId)
                .category(tag.getCategory())
                .name(tagSaveDTO.getName())
                .build());
    }

    /**
     * 태그 삭제
     *
     * @param tagId
     */
    @Transactional
    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }
}
