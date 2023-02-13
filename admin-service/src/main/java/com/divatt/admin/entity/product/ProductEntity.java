package com.divatt.admin.entity.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "tbl_products")
public class ProductEntity {

	
	private Integer productId;
	private Integer designerId;
	private Integer categoryId;
	private Integer subCategoryId;
	private String gender;
	private String productName;
	private String productDescription;
	private Object age;
	private Boolean cod;
	private Boolean customization;
	private Object purchaseQuantity;
	private String priceType;
	private Float taxPercentage;
	private Boolean taxInclusive;
	private Boolean giftWrap;
	private Object giftWrapAmount;
	private Object price;
	private Object images[];

	private Object standeredSOH[];
	private Integer customizationSOH;
	private Object extraSpecifications;
	private Object specifications;
	private Boolean isActive;
	private Boolean isDeleted;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date createdOn;
	private String createdBy;
	private String submittedBy;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date adminStatusOn;
	private String updatedBy;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date updatedOn;
	private String approvedBy;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date approvedOn;
	private List<Object> comments = new ArrayList<>();
	private String SKQCode;
	private String adminStatus;
	private String designerName;
	public ProductEntity() {
		super();
	}
	
	public ProductEntity(Integer productId, Integer designerId, Integer categoryId, Integer subCategoryId,
			String gender, String productName, String productDescription, Object age, Boolean cod,
			Boolean customization, Object purchaseQuantity, String priceType, Float taxPercentage, Boolean taxInclusive,
			Boolean giftWrap, Object giftWrapAmount, Object price, Object[] images, Object[] standeredSOH,
			Integer customizationSOH, Object extraSpecifications, Object specifications, Boolean isActive,
			Boolean isDeleted, Date createdOn, String createdBy, String submittedBy, Date adminStatusOn,
			String updatedBy, Date updatedOn, String approvedBy, Date approvedOn, List<Object> comments, String sKQCode,
			String adminStatus, String designerName) {
		super();
		this.productId = productId;
		this.designerId = designerId;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.gender = gender;
		this.productName = productName;
		this.productDescription = productDescription;
		this.age = age;
		this.cod = cod;
		this.customization = customization;
		this.purchaseQuantity = purchaseQuantity;
		this.priceType = priceType;
		this.taxPercentage = taxPercentage;
		this.taxInclusive = taxInclusive;
		this.giftWrap = giftWrap;
		this.giftWrapAmount = giftWrapAmount;
		this.price = price;
		this.images = images;
		this.standeredSOH = standeredSOH;
		this.customizationSOH = customizationSOH;
		this.extraSpecifications = extraSpecifications;
		this.specifications = specifications;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.submittedBy = submittedBy;
		this.adminStatusOn = adminStatusOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.approvedBy = approvedBy;
		this.approvedOn = approvedOn;
		this.comments = comments;
		this.SKQCode = sKQCode;
		this.adminStatus = adminStatus;
		this.designerName = designerName;
	}
	@Override
	public String toString() {
		return "ProductEntity [productId=" + productId + ", designerId=" + designerId + ", categoryId=" + categoryId
				+ ", subCategoryId=" + subCategoryId + ", gender=" + gender + ", productName=" + productName
				+ ", productDescription=" + productDescription + ", age=" + age + ", cod=" + cod + ", customization="
				+ customization + ", purchaseQuantity=" + purchaseQuantity + ", priceType=" + priceType
				+ ", taxPercentage=" + taxPercentage + ", taxInclusive=" + taxInclusive + ", giftWrap=" + giftWrap
				+ ", giftWrapAmount=" + giftWrapAmount + ", price=" + price + ", images=" + Arrays.toString(images)
				+ ", standeredSOH=" + Arrays.toString(standeredSOH) + ", customizationSOH=" + customizationSOH
				+ ", extraSpecifications=" + extraSpecifications + ", specifications=" + specifications + ", isActive="
				+ isActive + ", isDeleted=" + isDeleted + ", createdOn=" + createdOn + ", createdBy=" + createdBy
				+ ", submittedBy=" + submittedBy + ", adminStatusOn=" + adminStatusOn + ", updatedBy=" + updatedBy
				+ ", updatedOn=" + updatedOn + ", approvedBy=" + approvedBy + ", approvedOn=" + approvedOn
				+ ", comments=" + comments + ", SKQCode=" + SKQCode + ", adminStatus=" + adminStatus + ", designerName="
				+ designerName + "]";
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Integer designerId) {
		this.designerId = designerId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public Object getAge() {
		return age;
	}
	public void setAge(Object age) {
		this.age = age;
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
	public Object getPurchaseQuantity() {
		return purchaseQuantity;
	}
	public void setPurchaseQuantity(Object purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public Float getTaxPercentage() {
		return taxPercentage;
	}
	public void setTaxPercentage(Float taxPercentage) {
		this.taxPercentage = taxPercentage;
	}
	public Boolean getTaxInclusive() {
		return taxInclusive;
	}
	public void setTaxInclusive(Boolean taxInclusive) {
		this.taxInclusive = taxInclusive;
	}
	public Boolean getGiftWrap() {
		return giftWrap;
	}
	public void setGiftWrap(Boolean giftWrap) {
		this.giftWrap = giftWrap;
	}
	public Object getGiftWrapAmount() {
		return giftWrapAmount;
	}
	public void setGiftWrapAmount(Object giftWrapAmount) {
		this.giftWrapAmount = giftWrapAmount;
	}
	public Object getPrice() {
		return price;
	}
	public void setPrice(Object price) {
		this.price = price;
	}
	public Object[] getImages() {
		return images;
	}
	public void setImages(Object[] images) {
		this.images = images;
	}
	public Object[] getStanderedSOH() {
		return standeredSOH;
	}
	public void setStanderedSOH(Object[] standeredSOH) {
		this.standeredSOH = standeredSOH;
	}
	public Integer getCustomizationSOH() {
		return customizationSOH;
	}
	public void setCustomizationSOH(Integer customizationSOH) {
		this.customizationSOH = customizationSOH;
	}
	public Object getExtraSpecifications() {
		return extraSpecifications;
	}
	public void setExtraSpecifications(Object extraSpecifications) {
		this.extraSpecifications = extraSpecifications;
	}
	public Object getSpecifications() {
		return specifications;
	}
	public void setSpecifications(Object specifications) {
		this.specifications = specifications;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	public Date getAdminStatusOn() {
		return adminStatusOn;
	}
	public void setAdminStatusOn(Date adminStatusOn) {
		this.adminStatusOn = adminStatusOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}
	public List<Object> getComments() {
		return comments;
	}
	public void setComments(List<Object> comment) {
		this.comments = comment;
	}
	public String getSKQCode() {
		return SKQCode;
	}
	public void setSKQCode(String sKQCode) {
		SKQCode = sKQCode;
	}
	public String getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}
	public String getDesignerName() {
		return designerName;
	}
	public void setDesignerName(String designerName) {
		this.designerName = designerName;
	}
	
	
}
