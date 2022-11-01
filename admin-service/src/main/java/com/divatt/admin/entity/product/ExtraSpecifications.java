package com.divatt.admin.entity.product;

import org.springframework.data.mongodb.core.mapping.Field;

public class ExtraSpecifications {

	private String sleeveLength;

	private String sleeve;

	public ExtraSpecifications() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExtraSpecifications(String sleeveLength, String sleeve) {
		super();
		this.sleeveLength = sleeveLength;
		this.sleeve = sleeve;
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

	@Override
	public String toString() {
		return "ExtraSpecifications [sleeveLength=" + sleeveLength + ", sleeve=" + sleeve + "]";
	}

}
