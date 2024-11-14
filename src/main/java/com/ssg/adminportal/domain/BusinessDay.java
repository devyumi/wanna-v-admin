package com.ssg.adminportal.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
public class BusinessDay {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String dayOfWeek; //요일


  @DateTimeFormat(pattern = "yyyy. MM. dd. a hh:mm")
  private LocalDateTime openTime; //영업 시작 시간

  @DateTimeFormat(pattern = "yyyy. MM. dd. a hh:mm")
  private LocalDateTime closeTime; //종료 시간

  @DateTimeFormat(pattern = "yyyy. MM. dd. a hh:mm")
  private LocalDateTime breakStartTime; //브레이크 댄스 타임 시작 시간

  @DateTimeFormat(pattern = "yyyy. MM. dd. a hh:mm")
  private LocalDateTime breakEndTime; //브레이크 댄스 타임 종료 시간

  @DateTimeFormat(pattern = "yyyy. MM. dd. a hh:mm")
  private LocalDateTime lastOrderTime; //라스트 오더 시간
  private Boolean isClose; //문 닫는 날인지 , 이때는 전부 null 값임

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id") //restaurant_id
  private Restaurant restaurant;



}
