package com.vision.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vipul.pant on 7/29/17.
 *
 "food_name": "banana",
 "brand_name": null,
 "serving_qty": 1,
 "serving_unit": "medium (7\" to 7-7/8\" long)",
 "serving_weight_grams": 118,
 "nf_calories": 105.02,
 "nf_total_fat": 0.39,
 "nf_saturated_fat": 0.13,
 "nf_cholesterol": 0,
 "nf_sodium": 1.18,
 "nf_total_carbohydrate": 26.95,
 "nf_dietary_fiber": 3.07,
 "nf_sugars": 14.43,
 "nf_protein": 1.29,
 "nf_potassium": 422.44,
 "nf_p": 25.96,
 "full_nutrients": [
 */

public class Food {

    @JsonProperty("food_name")
    private String name;

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("serving_qty")
    private int quantity;

    @JsonProperty("serving_unit")
    private int servingUnit;

    @JsonProperty("serving_weight_grams")
    private int weightGrams;

    @JsonProperty("nf_calories")
    private int calories;

    @JsonProperty("nf_total_fat")
    private int fat;

    @JsonProperty("nf_saturated_fat")
    private int saturatedFat;

    @JsonProperty("nf_cholesterol")
    private int cholesterol;

    @JsonProperty("nf_sodium")
    private int sodium;

    @JsonProperty("nf_total_carbohydrate")
    private int carbohydrate;

    @JsonProperty("nf_dietary_fiber")
    private int dietaryFiber;

    @JsonProperty("serving_unit")
    private int sugars;

    @JsonProperty("nf_protein")
    private int protein;

    @JsonProperty("nf_potassium")
    private int potassium;

    @JsonProperty("full_nutrients")
    private List<Nutrients> nutrients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(int weightGrams) {
        this.weightGrams = weightGrams;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(int saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public int getDietaryFiber() {
        return dietaryFiber;
    }

    public void setDietaryFiber(int dietaryFiber) {
        this.dietaryFiber = dietaryFiber;
    }

    public int getSugars() {
        return sugars;
    }

    public void setSugars(int sugars) {
        this.sugars = sugars;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getPotassium() {
        return potassium;
    }

    public void setPotassium(int potassium) {
        this.potassium = potassium;
    }

    public List<Nutrients> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrients> nutrients) {
        this.nutrients = nutrients;
    }
}
