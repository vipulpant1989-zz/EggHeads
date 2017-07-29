package com.vision.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vision.beans.Food;
import com.vision.service.INutritionSearch;
import com.vision.utils.NutiFactsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by vipul.pant on 7/29/17.
 */
@Service
public class NutritionSearch implements INutritionSearch{

    @Autowired
    RestTemplate restTemplate;



    private static final ObjectMapper objectMapper = new ObjectMapper();

    private HttpEntity<String> getRequestWithHeaders(String query) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        Map<String , String> mapParam = new HashMap<>();
        mapParam.put("query" , query);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-app-id", API_KEY);
        headers.add("x-app-key", API_APP_ID);
        headers.add("x-remote-user-id", "0");
        String requestBody = objectMapper.writeValueAsString(mapParam);
        System.out.println("requestBody =======" + requestBody);
        return new HttpEntity<>(requestBody , headers);
    }


    @Override
    public List<Food> searchByName(String name) {

        HttpEntity<String> request = null;
        ResponseEntity<Object> responseEntity =null;
        try {
            request = getRequestWithHeaders(name);
            System.out.println("Request entity ====================="+ request);
            responseEntity = restTemplate.postForEntity(API_END_POINT , request , Object.class);
            System.out.print("Response Entity ====================== "+ responseEntity.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Collections.singletonList(null);
    }

    @Override
    public String getByName(String name) {
        try{
            return NutiFactsApi.getNutitionFacts(name);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
