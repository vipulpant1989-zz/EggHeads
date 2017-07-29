package com.vision.utils;

/**
 * Created by vipul.pant on 7/30/17.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vision.beans.NutritionFacts;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

    public class NutiFactsApi {


        public static String  getNutitionFacts(String name) throws IOException, JSONException
        {
            //CREATE AN AUTHENTICATOR:
            Authenticator.setDefault(new Authenticator() {
                String username="vipul031989";
                String password="India@1234";
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password.toCharArray());
                }
            });
            URL url = new URL("https://www.nutritics.com/api/v1.1/LIST/food="
                    +name+"&attr=energyKcal,energyKj,carbohydrate,protein,fat,starch,oligosaccharide,fibre,nsp,sugars,galactose,fructose,sucrose,maltose,lactose,sodium,potassium,chloride,calcium,phosphorus,allergens");
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine, output = "";
            while ((inputLine = in.readLine()) != null) {
                output += inputLine;
            }

            // HOW TO GET THE FIRST RESULT:
            JSONObject foodObj= new JSONObject(output);
            return foodObj.get("1").toString();

        }
    }
