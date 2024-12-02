package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.common.ContainFoodType;
import com.ssg.adminportal.common.MoodType;
import com.ssg.adminportal.common.ProvideServiceType;
import com.ssg.adminportal.common.ReservationTimeGap;
import com.ssg.adminportal.common.RestaurantType;
import com.ssg.adminportal.domain.Restaurant;
import com.ssg.adminportal.domain.Review;
import com.ssg.adminportal.dto.FileDTO;
import com.ssg.adminportal.dto.request.FoodSaveDTO;
import com.ssg.adminportal.dto.request.RestaurantAdminSearchCond;
import com.ssg.adminportal.dto.request.RestaurantSaveDTO;
import com.ssg.adminportal.service.FileService;
import com.ssg.adminportal.service.RestaurantService;
import com.ssg.adminportal.service.ReviewService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final ReviewService reviewService;

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

    @GetMapping()
    public String getAdminRestaurants(
        @ModelAttribute("restaurantAdminSearchCond") RestaurantAdminSearchCond restaurantAdminSearchCond,
        Model model) {
        model.addAttribute("restaurants",
            restaurantService.findRestaurantsAdmin(restaurantAdminSearchCond));
        return "restaurant/restaurants";
    }

    @GetMapping("/{id}")
    public String getReviewSentiment(@PathVariable("id") Long id, Model model) {
        model.addAttribute("restaurant", restaurantService.findOne(id));
        model.addAttribute("sentiment", reviewService.getSentiment(id));
        return "restaurant/restaurant";
    }


    @GetMapping("/save")
    public String saveRestaurant(Model model) {
        model.addAttribute("restaurantSaveDto", new RestaurantSaveDTO());
        return "restaurant/saveForm";
    }

    @PostMapping("/save")
    @ResponseBody
    public String saveRestaurantPost(
        @ModelAttribute("restaurantSaveDto") RestaurantSaveDTO restaurantSaveDto,
        RedirectAttributes redirectAttributes) {

        List<MultipartFile> restaurantImages = restaurantSaveDto.getRestaurantImages();
        List<MultipartFile> foodImages = new ArrayList<>();
        List<FoodSaveDTO> foodSaveDTOList = restaurantSaveDto.getFoodSaveDTOList();
        for (FoodSaveDTO foodSaveDto : foodSaveDTOList) {
            foodImages.add(foodSaveDto.getFoodImage());
        }

        /**
         * 식당 스토리지 저장 + DB에 스토리지 Url 저장
         */
        List<FileDTO> restaurantImagesFileDto = fileService.uploadFiles(restaurantImages,
            restaurantDir);
        List<String> restaurantImagesUrl = restaurantImagesFileDto.stream()
            .map(FileDTO::getUploadFileUrl).toList();
        restaurantSaveDto.setRestaurantImagesUrl(restaurantImagesUrl);

        /**
         * 음식 스토리지 저장 + DB에 스토리지 Url 저장
         */
        List<FileDTO> foodImagesFileDto = fileService.uploadFiles(foodImages, foodDir);
        List<String> foodImagesUrl = foodImagesFileDto.stream().map(FileDTO::getUploadFileUrl)
            .toList();
        for (String foodImageUrl : foodImagesUrl) {
            foodSaveDTOList.forEach(foodSaveDTO -> {
                foodSaveDTO.setFoodImageUrl(foodImageUrl);
            });
        }
        ;

        Long saveId = restaurantService.save(restaurantSaveDto);
        redirectAttributes.addAttribute("saveId", saveId);
        return "success";
    }

    @GetMapping("/{id}/update")
    public String updateRestaurant(@PathVariable("id") Long id, Model model) {
        Restaurant restaurant = restaurantService.findOne(id);
        //RestaurantUpdateDTO restaurantUpdateDto = new RestaurantUpdateDTO(restaurant.getName() , restaurant.getBusinessNum() , restaurant.getRestaurantTypes() , restaurant.getContainFoodTypes() , restaurant.getProvideServiceTypes() , restaurant.getMoodTypes() , restaurant.getAddress().getRoadAddress() , restaurant.getAddress().getLandLotAddress() , restaurant.getAddress().getZipCode() , restaurant.getAddress().getDetailAddress() , restaurant.getCanPark() , restaurant.getReservationTimeGap() , restaurant.getIsPenalty());
        model.addAttribute("restaurant", restaurant);
        return "restaurant/updateForm";
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
