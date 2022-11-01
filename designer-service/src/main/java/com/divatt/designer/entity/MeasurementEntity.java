package com.divatt.designer.entity;

import java.sql.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection ="tbl_designer_measurements")
public class MeasurementEntity {

    @Id
    @Field(name ="_id")
    private Integer chartId;
    @Transient
    public static final String SEQUENCE_NAME = "tbl_designer_measurements";
    @Field(name = "designer_id")
    private Long designerId;
    @Field(name ="chat_name")
    private String chartName;
    @Field(name ="measurements")
    private List<Measurements> measurements;
    @Field(name ="size_type")
    private String sizeType;
    @JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
    @Field(name ="created_on")
    private Date createdOn;
    public MeasurementEntity() {
        super();
        // TODO Auto-generated constructor stub
    }
    public MeasurementEntity(Integer chartId, Long designerId, String chartName, List<Measurements> measurements,
            String sizeType, Date createdOn) {
        super();
        this.chartId = chartId;
        this.designerId = designerId;
        this.chartName = chartName;
        this.measurements = measurements;
        this.sizeType = sizeType;
        this.createdOn = createdOn;
    }
    @Override
    public String toString() {
        return "MeasurementEntity [chartId=" + chartId + ", designerId=" + designerId + ", chartName=" + chartName
                + ", measurements=" + measurements + ", sizeType=" + sizeType + ", createdOn=" + createdOn + "]";
    }
    public Integer getChartId() {
        return chartId;
    }
    public void setChartId(Integer chartId) {
        this.chartId = chartId;
    }
    public Long getDesignerId() {
        return designerId;
    }
    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }
    public String getChartName() {
        return chartName;
    }
    public void setChartName(String chartName) {
        this.chartName = chartName;
    }
    public List<Measurements> getMeasurements() {
        return measurements;
    }
    public void setMeasurements(List<Measurements> measurements) {
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

    
}
