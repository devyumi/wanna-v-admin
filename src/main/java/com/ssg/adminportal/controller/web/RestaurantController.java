package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.common.*;
import com.ssg.adminportal.service.FileService;
import com.ssg.adminportal.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    @Value("${restaurant.image.dir}")
    private String restaurantDir;

    @Value("${food.image.dir}")
    private String foodDir;

    private final RestaurantService restaurantService;
    private final FileService fileService;

    @ModelAttribute("containFoodTypes")
    public ContainFoodType[] containFoodTypes() {
        return ContainFoodType.values();
    }

    @ModelAttribute("provideServiceTypes")
    public ProvideServiceType[] provideServiceTypes() {
        return ProvideServiceType.values();
    }

    @ModelAttribute("restaurantTypes")
    public RestaurantType[] restaurantTypes() {
        return RestaurantType.values();
    }

    @ModelAttribute("moodTypes")
    public MoodType[] moodTypes() {
        return MoodType.values();
    }


    @ModelAttribute("sortConditions")
    public Map<String, String> sortConditions() {
        Map<String, String> sortConditions = new HashMap<>();
        sortConditions.put("NEW", "최신 순");
        sortConditions.put("RATE", "평점 순");
        sortConditions.put("LIKE", "좋아요 순");
        sortConditions.put("REVIEW", "리뷰 순");
        return sortConditions;
    }


    @ModelAttribute("reservationTimeGaps")
    public ReservationTimeGap[] reservationGaps() {
        return ReservationTimeGap.values();
    }


    @ModelAttribute("dayOfWeeks")
    public List<String> dayOfWeeks() {
        List<String> dayOfWeeks = new ArrayList<>();
        dayOfWeeks.add("월요일");
        dayOfWeeks.add("화요일");
        dayOfWeeks.add("수요일");
        dayOfWeeks.add("목요일");
        dayOfWeeks.add("금요일");
        dayOfWeeks.add("토요일");
        dayOfWeeks.add("일요일");
        return dayOfWeeks;
    }


    @GetMapping("/{id}")
    public String getReviewSentiment(@PathVariable Long id, Model model) {
        model.addAttribute("sentiment", reviewService.getSentiment(id));
        return "restaurant/restaurant";
    }


    @GetMapping("/save")
    public String saveRestaurant(Model model) {
        model.addAttribute("restaurantSaveDto", new RestaurantSaveDto());
        return "restaurant/admin-saveForm";
    }

    @PostMapping("/save")
    @ResponseBody
    public String saveRestaurantPost(@ModelAttribute("restaurantSaveDto") RestaurantSaveDto restaurantSaveDto, RedirectAttributes redirectAttributes) {
        log.info("restaurant = {}" , restaurantSaveDto.getRestaurantImages());
        log.info("food = {}", restaurantSaveDto.getFoodSaveDtoList());


        List<MultipartFile> restaurantImages = restaurantSaveDto.getRestaurantImages();
        List<MultipartFile> foodImages = new ArrayList<>();
        List<FoodSaveDto> foodSaveDtoList = restaurantSaveDto.getFoodSaveDtoList();
        for (FoodSaveDto foodSaveDto : foodSaveDtoList) {
            foodImages.add(foodSaveDto.getFoodImage());
        }

        /**
         * 식당 스토리지 저장 + DB에 스토리지 Url 저장
         */
        List<FileDTO> restaurantImagesFileDto = fileService.uploadFiles(restaurantImages, restaurantDir);
        List<String> restaurantImagesUrl = restaurantImagesFileDto.stream().map(FileDTO::getUploadFileUrl).toList();
        restaurantSaveDto.setRestaurantImagesUrl(restaurantImagesUrl);

        /**
         * 음식 스토리지 저장 + DB에 스토리지 Url 저장
         */
        List<FileDTO> foodImagesFileDto = fileService.uploadFiles(foodImages, foodDir);
        List<String> foodImagesUrl = foodImagesFileDto.stream().map(FileDTO::getUploadFileUrl).toList();
        for (String foodImageUrl : foodImagesUrl) {
            foodSaveDtoList.forEach(foodSaveDto -> {
                foodSaveDto.setFoodImageUrl(foodImageUrl);
            });
        };

        log.info("restaurantSaveDto = {}" , restaurantSaveDto);
        log.info("foodSaveDtoList = {}" , restaurantSaveDto.getFoodSaveDtoList());

        Long saveId = restaurantService.save(restaurantSaveDto);
        redirectAttributes.addAttribute("saveId", saveId);
        return "success";
    }


    @GetMapping("/{id}/update")
    public String updateRestaurant(@PathVariable("id") Long id ,  Model model) {
        Restaurant restaurant = restaurantService.findOne(id);
        RestaurantUpdateDto restaurantUpdateDto = new RestaurantUpdateDto(restaurant.getName() , restaurant.getBusinessNum() , restaurant.getRestaurantTypes() , restaurant.getContainFoodTypes() , restaurant.getProvideServiceTypes() , restaurant.getMoodTypes() , restaurant.getAddress().getRoadAddress() , restaurant.getAddress().getLandLotAddress() , restaurant.getAddress().getZipCode() , restaurant.getAddress().getDetailAddress() , restaurant.getCanPark() , restaurant.getReservationTimeGap() , restaurant.getIsPenalty());
        model.addAttribute("restaurant", restaurant);
        return "restaurant/admin-updateForm";
    }


    @GetMapping("/{id}")
    public String getAdminRestaurant(@PathVariable("id") Long id, Model model) {
        model.addAttribute("restaurant", restaurantService.findOne(id));
        return "restaurant/admin-restaurant";
    }


    private Map<Integer, List<Review>> getReviewsByRating(Long id) {
        List<Review> reviewsByOneRating = restaurantService.findReviewsByRating(id, 1);
        List<Review> reviewsByTwoRating = restaurantService.findReviewsByRating(id, 2);
        List<Review> reviewsByThreeRating = restaurantService.findReviewsByRating(id, 3);
        List<Review> reviewsByFourRating = restaurantService.findReviewsByRating(id, 4);
        List<Review> reviewsByFiveRating = restaurantService.findReviewsByRating(id, 5);
        Map<Integer, List<Review>> reviewsByRating = new HashMap<>();
        reviewsByRating.put(1, reviewsByOneRating);
        reviewsByRating.put(2, reviewsByTwoRating);
        reviewsByRating.put(3, reviewsByThreeRating);
        reviewsByRating.put(4, reviewsByFourRating);
        reviewsByRating.put(5, reviewsByFiveRating);
        return reviewsByRating;
    }







}
