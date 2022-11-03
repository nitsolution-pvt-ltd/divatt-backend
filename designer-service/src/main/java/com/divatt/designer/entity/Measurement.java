package com.divatt.designer.entity;



import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection ="tbl_designer_measurements")
public class Measurement {

    @Id
//    @Field(name ="_id")
    private Integer Id;
    @Transient
    public static final String SEQUENCE_NAME = "tbl_designer_measurements";
//    @Field(name = "designer_id")
    private Integer designerId;
//    @Field(name ="chat_name")
    private String chartName;
//    @Field(name ="measurements")
    private WomenSizeMeasurements measurementsWomen;
//    @Field(name ="size_type")
    private String sizeType;
    private MenandKidsMeasurements measurementsMen;
    @JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
//    @Field(name ="created_on")
    private Date createdOn;
	public Measurement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Measurement(Integer id, Integer designerId, String chartName, WomenSizeMeasurements measurementsWomen,
			String sizeType, MenandKidsMeasurements measurementsMen, Date createdOn) {
		super();
		Id = id;
		this.designerId = designerId;
		this.chartName = chartName;
		this.measurementsWomen = measurementsWomen;
		this.sizeType = sizeType;
		this.measurementsMen = measurementsMen;
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
	public WomenSizeMeasurements getMeasurementsWomen() {
		return measurementsWomen;
	}
	public void setMeasurementsWomen(WomenSizeMeasurements measurementsWomen) {
		this.measurementsWomen = measurementsWomen;
	}
	public String getSizeType() {
		return sizeType;
	}
	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}
	public MenandKidsMeasurements getMeasurementsMen() {
		return measurementsMen;
	}
	public void setMeasurementsMen(MenandKidsMeasurements measurementsMen) {
		this.measurementsMen = measurementsMen;
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
		return "Measurement [Id=" + Id + ", designerId=" + designerId + ", chartName=" + chartName
				+ ", measurementsWomen=" + measurementsWomen + ", sizeType=" + sizeType + ", measurementsMen="
				+ measurementsMen + ", createdOn=" + createdOn + "]";
	}
	
}
