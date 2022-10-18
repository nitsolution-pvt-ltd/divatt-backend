package com.divatt.designer.requestDTO;

import java.util.List;

public class SearchingFilterDTO {
	private List<String> designerIdList;
	private List<String> categoryList;
	private List<String> subCategoryList;
	private List<String> colour;
	private List<String> size;
	private Boolean cod;
	private Boolean customization;
	private Boolean  returnStatus;
	private Boolean priceType;
	private String maxPrice;
	private String minPrice;
	public SearchingFilterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SearchingFilterDTO(List<String> designerIdList, List<String> categoryList, List<String> subCategoryList,
			List<String> colour, List<String> size, Boolean cod, Boolean customization, Boolean returnStatus,
			Boolean priceType, String maxPrice, String minPrice) {
		super();
		this.designerIdList = designerIdList;
		this.categoryList = categoryList;
		this.subCategoryList = subCategoryList;
		this.colour = colour;
		this.size = size;
		this.cod = cod;
		this.customization = customization;
		this.returnStatus = returnStatus;
		this.priceType = priceType;
		this.maxPrice = maxPrice;
		this.minPrice = minPrice;
	}
	@Override
	public String toString() {
		return "SearchingFilterDTO [designerIdList=" + designerIdList + ", categoryList=" + categoryList
				+ ", subCategoryList=" + subCategoryList + ", colour=" + colour + ", size=" + size + ", cod=" + cod
				+ ", customization=" + customization + ", returnStatus=" + returnStatus + ", priceType=" + priceType
				+ ", maxPrice=" + maxPrice + ", minPrice=" + minPrice + "]";
	}
	public List<String> getDesignerIdList() {
		return designerIdList;
	}
	public void setDesignerIdList(List<String> designerIdList) {
		this.designerIdList = designerIdList;
	}
	public List<String> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}
	public List<String> getSubCategoryList() {
		return subCategoryList;
	}
	public void setSubCategoryList(List<String> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	public List<String> getColour() {
		return colour;
	}
	public void setColour(List<String> colour) {
		this.colour = colour;
	}
	public List<String> getSize() {
		return size;
	}
	public void setSize(List<String> size) {
		this.size = size;
	}
	public Boolean getCod() {
		return cod;
	}
	public void setCod(Boolean cod) {
		this.cod = cod;
	}
	public Boolean getCustomization() {
		return customization;
	}
	public void setCustomization(Boolean customization) {
		this.customization = customization;
	}
	public Boolean getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(Boolean returnStatus) {
		this.returnStatus = returnStatus;
	}
	public Boolean getPriceType() {
		return priceType;
	}
	public void setPriceType(Boolean priceType) {
		this.priceType = priceType;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	
}
