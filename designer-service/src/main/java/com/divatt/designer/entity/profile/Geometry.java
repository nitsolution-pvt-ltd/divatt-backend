package com.divatt.designer.entity.profile;

import java.util.ArrayList;

public class Geometry {
	public ArrayList<Double> coordinates;
    public String type;
	public Geometry() {
		super();
	}
	public Geometry(ArrayList<Double> coordinates, String type) {
		super();
		this.coordinates = coordinates;
		this.type = type;
	}
	public ArrayList<Double> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(ArrayList<Double> coordinates) {
		this.coordinates = coordinates;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Geometry [coordinates=" + coordinates + ", type=" + type + "]";
	}
    
}
