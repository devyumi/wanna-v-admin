package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.common.Type;
import com.ssg.adminportal.domain.Admin;
import com.ssg.adminportal.domain.Coupon;
import com.ssg.adminportal.domain.Event;
import com.ssg.adminportal.dto.request.CouponListRequestDTO;
import com.ssg.adminportal.dto.request.CouponRequestDTO;
import com.ssg.adminportal.dto.request.CouponSaveRequestDTO;
import com.ssg.adminportal.dto.response.CouponEventResponseDTO;
import com.ssg.adminportal.dto.response.CouponListResponseDTO;
import com.ssg.adminportal.repository.AdminRepository;
import com.ssg.adminportal.repository.CouponRepository;
import com.ssg.adminportal.repository.EventRepository;
import com.ssg.adminportal.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final EventRepository eventRepository;
    private final AdminRepository adminRepository;

    @Override
    public CouponListResponseDTO getAll(CouponListRequestDTO couponListRequestDTO) {
        Pageable pageable = PageRequest.of(couponListRequestDTO.getPage() - 1, couponListRequestDTO.getSize(), Sort.by(Sort.Order.asc("id")));
        Page<Coupon> couponPage;

        String type = couponListRequestDTO.getType();
        Boolean active = couponListRequestDTO.getActive();

        if (type.isEmpty() && active == null)
            couponPage = couponRepository.findAll(pageable);
        else if (type.isEmpty())
            couponPage = couponRepository.findAllByActive(active, pageable);
        else if (active == null)
            couponPage = couponRepository.findAllByType(Type.valueOf(type), pageable);
        else
            couponPage = couponRepository.findAllByTypeAndActive(Type.valueOf(type), active, pageable);

        return CouponListResponseDTO.builder()
                .requestDTO(couponListRequestDTO)
                .coupons(couponPage.getContent())
                .total((int) couponPage.getTotalElements())
                .type(type)
                .active(active)
                .build();
    }

    @Override
    public CouponEventResponseDTO getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        List<Event> events = eventRepository.findAll();

        return new CouponEventResponseDTO(coupon, events);
    }

    @Override
    public void updateCoupon(Long couponId, CouponRequestDTO coupon) {
        Boolean active = Boolean.getBoolean(coupon.getActive());

        Long adminId = 1L;

        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        Coupon findCoupon = couponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("쿠폰이 없습니다."));

        Event event = eventRepository.findById(coupon.getId()).orElse(null);

        Coupon updateCoupon = new Coupon(
                couponId,
                event,
                findCoupon.getCreatedBy(),
                admin,
                findCoupon.getName(),
                findCoupon.getCode(),
                findCoupon.getEndDate(),
                findCoupon.getType(),
                findCoupon.getDiscountAmount(),
                findCoupon.getDiscountRate(),
                active,
                findCoupon.getCreatedAt(),
                LocalDateTime.now());

        couponRepository.save(updateCoupon);
    }

    @Override
    public void createCoupon(Long adminId, CouponSaveRequestDTO coupon) {
        Event event = null;

        if (coupon.getEventId() != null)
            event = eventRepository.findById(coupon.getEventId()).orElse(null);

        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        Type type = Type.valueOf(coupon.getType());

        Coupon saveCoupon = new Coupon(
                null,
                event,
                admin,
                null,
                coupon.getName(),
                uniqueCode(),
                coupon.getEndDate(),
                type,
                coupon.getAmount(),
                coupon.getRate(),
                coupon.getActive(),
                LocalDateTime.now(),
                null
        );
        log.info(3);
        couponRepository.save(saveCoupon);
    }

    private Integer uniqueCode() {
        Random random = new Random();
        Integer code;
        do {
            code = 100000000 + random.nextInt(900000000);
        } while (couponRepository.existsByCode(code));
        return code;
    }
}
