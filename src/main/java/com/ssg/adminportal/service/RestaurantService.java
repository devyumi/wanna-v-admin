package com.ssg.adminportal.service;


import com.ssg.adminportal.domain.BusinessDay;
import com.ssg.adminportal.domain.Restaurant;
import com.ssg.adminportal.domain.Review;
import com.ssg.adminportal.dto.request.RestaurantAdminSearchCond;
import com.ssg.adminportal.dto.request.RestaurantSaveDTO;
import com.ssg.adminportal.dto.request.RestaurantSearchCond;
import com.ssg.adminportal.dto.request.RestaurantUpdateDTO;

import java.util.List;

public interface RestaurantService {
    Long save(RestaurantSaveDTO restaurantSaveDto);


    List<Review> findReviewsByRating(Long id, Integer rating);


    List<Restaurant> findRestaurantsAdmin(RestaurantAdminSearchCond restaurantAdminSearchCond);

    Restaurant findOne(Long id);

    List<Restaurant> findSimilarRestaurants(Long id);

    List<Restaurant> findRestaurants(RestaurantSearchCond restaurantSearchCond, String search);


    BusinessDay findToday(Restaurant restaurant);

    void updateRestaurant(RestaurantUpdateDTO restaurantUpdateDto);

    void updateBusinessStatus();

}
