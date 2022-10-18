package com.divatt.user.entity.measurement;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_measurement_list")
public class Measurement {
	
	
	
	@Id
	private int id;
	@Field(name="type")
	@NotNull(message = "Type is required!")
	private String type;
	@NotNull(message = "Name is required!")
	@Field(name="name")
	private String name;
	@NotNull(message = "Description is required!")
	@Field(name="description")
	private String description;
	
	public Measurement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Measurement(int id, String type, String name, String description) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Measurement [id=" + id + ", type=" + type + ", name=" + name + ", description=" + description + "]";
	}

	
}
