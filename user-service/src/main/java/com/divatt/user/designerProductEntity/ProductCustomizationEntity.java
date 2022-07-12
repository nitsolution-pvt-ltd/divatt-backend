package com.divatt.user.designerProductEntity;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "tbl_designer_chart_list")
public class ProductCustomizationEntity {

	@Id
	private Integer productChartId;
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_designer_chart_list";
	private Integer designerId;
	private String productName;
	private String categoryName;
	private String subCategoryName;
	private Object chart;
	private String image;
	public ProductCustomizationEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductCustomizationEntity(Integer productChartId, Integer designerId, String productName,
			String categoryName, String subCategoryName, Object chart, String image) {
		super();
		this.productChartId = productChartId;
		this.designerId = designerId;
		this.productName = productName;
		this.categoryName = categoryName;
		this.subCategoryName = subCategoryName;
		this.chart = chart;
		this.image = image;
	}
	@Override
	public String toString() {
		return "ProductCustomizationEntity [productChartId=" + productChartId + ", designerId=" + designerId
				+ ", productName=" + productName + ", categoryName=" + categoryName + ", subCategoryName="
				+ subCategoryName + ", chart=" + chart + ", image=" + image + "]";
	}
	public Integer getProductChartId() {
		return productChartId;
	}
	public void setProductChartId(Integer productChartId) {
		this.productChartId = productChartId;
	}
	public Integer getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Integer designerId) {
		this.designerId = designerId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public Object getChart() {
		return chart;
	}
	public void setChart(Object chart) {
		this.chart = chart;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

}
