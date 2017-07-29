package com.vision.service;

import com.vision.beans.Food;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vipul.pant on 7/29/17.
 */


public interface INutritionSearch {

     static final String API_KEY = "b0930eee";
     static final String API_APP_ID = "d7ccd4e4fa57102838e7ff9800270f3b";
     static final String API_END_POINT = "https://trackapi.nutritionix.com/v2/natural/nutrients?api_key="+API_APP_ID;



    List<Food> searchByName(String name);

    String getByName(String name);
}
