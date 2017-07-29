package com.vision.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vipul.pant on 7/29/17.
 */
public class Nutrients {

    @JsonProperty("attr_id")
    private int id;

    @JsonProperty("value")
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
