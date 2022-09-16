package com.divatt.admin.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


@Document(collection = "tbl_banner")
public class BannerEntity {

	@Transient
	public static final String SEQUENCE_NAME = "tbl_banner";

	@Id
	private Long id;
	
	@NotNull(message = "Title is requried!")
	@Field(name = "title")
	private String title;
	
	@Field(name = "description")
	private String description;
	
	@NotNull(message = "Image is requried!")
	@Field(name = "image")
	private String image;
	
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	@Field(name = "start_date")
	private Date startDate;
	
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	@Field(name = "end_date")
	private Date endDate;
	
	@Field(name = "is_active")
	private Boolean isActive;
	
	@Field(name = "is_deleted")
	private Boolean isDeleted;	
	
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date createdOn;

	public BannerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BannerEntity(Long id, @NotNull(message = "Title is requried!") String title, String description,
			@NotNull(message = "Image is requried!") String image, Date startDate, Date endDate, Boolean isActive,
			Boolean isDeleted, Date createdOn) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.image = image;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.createdOn = createdOn;
	}
	
	

	public BannerEntity(@NotNull(message = "Title is requried!") String title, String description,
			@NotNull(message = "Image is requried!") String image) {
		super();
		this.title = title;
		this.description = description;
		this.image = image;
	}

	@Override
	public String toString() {
		return "BannerEntity [id=" + id + ", title=" + title + ", description=" + description + ", image=" + image
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", isActive=" + isActive + ", isDeleted="
				+ isDeleted + ", createdOn=" + createdOn + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	
	
	
	

}
