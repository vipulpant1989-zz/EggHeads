package com.vision.service;

import com.vision.beans.Food;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vipul.pant on 7/29/17.
 */

@Service
public interface INutritionSearch {

     static final String API_KEY = "b0930eee";
     static final String API_APP_ID = "8b2e2c10a06e85b0d3074e7b1c1616f9";
     static final String API_END_POINT = "https://trackapi.nutritionix.com/v2/natural/nutrients";



    List<Food> searchByName(String name);
}
