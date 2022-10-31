package com.divatt.designer.entity;

import java.util.List;

import org.json.simple.JSONObject;

public class Measurements {
    
    private List<JSONObject> sizes;

    public Measurements() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Measurements(List<JSONObject> sizes) {
        super();
        this.sizes = sizes;
    }

    @Override
    public String toString() {
        return "Measurements [sizes=" + sizes + "]";
    }

    public List<JSONObject> getSizes() {
        return sizes;
    }

    public void setSizes(List<JSONObject> sizes) {
        this.sizes = sizes;
    }
    

}
