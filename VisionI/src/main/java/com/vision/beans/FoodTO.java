package com.vision.beans;

import java.util.Map;

/**
 * Created by vipul.pant on 7/30/17.
 */
public class FoodTO {

    private String name;
    private Map<String , NutritionFacts> nutritionFact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, NutritionFacts> getNutritionFact() {
        return nutritionFact;
    }

    public void setNutritionFact(Map<String, NutritionFacts> nutritionFact) {
        this.nutritionFact = nutritionFact;
    }
}
