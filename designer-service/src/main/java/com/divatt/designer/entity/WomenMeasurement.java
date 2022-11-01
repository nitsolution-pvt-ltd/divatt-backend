package com.divatt.designer.entity;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection ="tbl_designer_measurements")
public class WomenMeasurement {

    @Id
    @Field(name ="_id")
    private Integer Id;
    @Transient
    public static final String SEQUENCE_NAME = "tbl_designer_measurements";
    @Field(name = "designer_id")
    private Integer designerId;
    @Field(name ="chat_name")
    private String chartName;
    @Field(name ="measurements")
    private WomenSizeMeasurements measurements;
    @Field(name ="size_type")
    private String sizeType;
    @JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
    @Field(name ="created_on")
    private Date createdOn;
	public WomenMeasurement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WomenMeasurement(Integer id, Integer designerId, String chartName, WomenSizeMeasurements measurements,
			String sizeType, Date createdOn) {
		super();
		Id = id;
		this.designerId = designerId;
		this.chartName = chartName;
		this.measurements = measurements;
		this.sizeType = sizeType;
		this.createdOn = createdOn;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Integer getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Integer designerId) {
		this.designerId = designerId;
	}
	public String getChartName() {
		return chartName;
	}
	public void setChartName(String chartName) {
		this.chartName = chartName;
	}
	public WomenSizeMeasurements getMeasurements() {
		return measurements;
	}
	public void setMeasurements(WomenSizeMeasurements measurements) {
		this.measurements = measurements;
	}
	public String getSizeType() {
		return sizeType;
	}
	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
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
	@Override
	public String toString() {
		return "WomenMeasurement [Id=" + Id + ", designerId=" + designerId + ", chartName=" + chartName + ", sizeType="
				+ sizeType + ", createdOn=" + createdOn + "]";
	}
    


}
