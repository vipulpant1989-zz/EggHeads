package com.vision.com.vision.serviceImpl;

import com.vision.beans.Food;
import com.vision.service.INutritionSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vipul.pant on 7/29/17.
 */
public class NutritionSearch implements INutritionSearch{

    @Autowired
    RestTemplate restTemplate;

    private HttpEntity<String> getRequestWithHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-app-id", API_KEY);
        headers.add("x-app-key", API_APP_ID);
        return new HttpEntity<>(headers);
    }


    @Override
    public List<Food> searchByName(String name) {

        HttpEntity<String> request = getRequestWithHeaders();
        ResponseEntity<Food[]> responseEntity = restTemplate.postForEntity(API_END_POINT , request , Food[].class);
        return Arrays.asList(responseEntity.getBody());
    }
}
