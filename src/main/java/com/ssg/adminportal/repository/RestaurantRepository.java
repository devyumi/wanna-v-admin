package com.ssg.adminportal.repository;



import com.ssg.adminportal.domain.Restaurant;
import com.ssg.adminportal.dto.request.RestaurantAdminSearchCond;
import com.ssg.adminportal.dto.request.RestaurantSearchCond;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    Long save(Restaurant restaurant);

    Optional<Restaurant> findById(Long id);

    List<Restaurant> findAll(RestaurantSearchCond restaurantSearchCond , String search);

    List<Restaurant> findAllAdmin(RestaurantAdminSearchCond restaurantAdminSearchCond);


    List<Restaurant> findSimilarRestaurantsAll(RestaurantSearchCond restaurantSearchCond);


}
