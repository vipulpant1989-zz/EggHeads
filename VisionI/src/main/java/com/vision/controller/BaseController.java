package com.vision.controller;

import com.vision.beans.ImageModel;
import com.vision.service.IClassifierService;
import com.vision.service.INutritionSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vipul.pant on 7/29/17.
 */

@RestController
public class BaseController {

    @Autowired
    INutritionSearch nutritionSearch ;

    @Autowired
    IClassifierService classifierService;

    @RequestMapping(value = "/")
    public String getApiInfo(){
        return "{version : 1.0.0 , name : 'Heath Vision API'}";
    }

    @RequestMapping(value = "/nutrition/{itemName}" ,method = RequestMethod.GET , consumes = MediaType.ALL_VALUE , produces =  MediaType.APPLICATION_JSON_VALUE)
    public String getNutritionSearch(@PathVariable String itemName){
        return nutritionSearch.getByName(itemName);
    }


    @RequestMapping(value = "/detect", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> upload(@RequestBody ImageModel imageModel) {

        List<String> detects = new ArrayList<>();
        try {
            //just temporary save file info into ufile
            if(imageModel.getBase64() == null){
               return new ResponseEntity<>( "" , HttpStatus.NO_CONTENT);

            }
            BASE64Decoder decoder = new BASE64Decoder();
            String base64String =   imageModel.getBase64().replace(" " , "");
            byte[] decodedBytes = decoder.decodeBuffer(base64String.split(",")[1]);
            detects = classifierService.detectImage(decodedBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }


        ResponseEntity<String> responseEntity =  detects.isEmpty() ? new ResponseEntity<>("" , HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(detects.get(0) , HttpStatus.OK);
        return responseEntity;

    }


}
