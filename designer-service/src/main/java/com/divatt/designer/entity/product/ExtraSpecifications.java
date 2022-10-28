package com.divatt.designer.entity.product;

import org.springframework.data.mongodb.core.mapping.Field;

public class ExtraSpecifications {
 @Field(name = "Sleeve Length")
 private String	sleeveLength;
 @Field(name = "Sleeve")
 private String	sleeve;
public ExtraSpecifications() {
	super();
	// TODO Auto-generated constructor stub
}
public ExtraSpecifications(String sleeveLength, String sleeve) {
	super();
	this.sleeveLength = sleeveLength;
	this.sleeve = sleeve;
}
@Override
public String toString() {
	return "ExtraSpecifications [sleeveLength=" + sleeveLength + ", sleeve=" + sleeve + "]";
}
public String getSleeveLength() {
	return sleeveLength;
}
public void setSleeveLength(String sleeveLength) {
	this.sleeveLength = sleeveLength;
}
public String getSleeve() {
	return sleeve;
}
public void setSleeve(String sleeve) {
	this.sleeve = sleeve;
}

	
}
