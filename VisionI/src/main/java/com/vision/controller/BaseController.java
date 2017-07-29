package com.vision.controller;

import com.vision.beans.Food;
import com.vision.service.INutritionSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by vipul.pant on 7/29/17.
 */

@RestController
public class BaseController {

    @Autowired
    INutritionSearch nutritionSearch ;

    @RequestMapping(value = "/")
    public String getApiInfo(){
        return "{version : 1.0.0 , name : 'Heath Vision API'}";
    }
    @RequestMapping(value = "/nutrition/{itemName}" ,method = RequestMethod.GET , consumes = MediaType.APPLICATION_JSON_VALUE , produces =  MediaType.APPLICATION_JSON_VALUE)
    public String getNutritionSearch(@PathVariable String itemName){
        System.out.println("ITEMS ======================= " + itemName);

        return nutritionSearch.getByName(itemName);
    }


}