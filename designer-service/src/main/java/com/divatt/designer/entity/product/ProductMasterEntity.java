package com.divatt.designer.entity.product;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
@Document(collection = "tbl_products")
public class ProductMasterEntity {

	@Id
	private Integer productId;
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_products";
	
	
	private Integer designerId;
	//@NotEmpty(message = "Category ID Required")
	private Integer categoryId;
	//@NotEmpty(message = "SUb-Category ID Required")
	private Integer subCategoryId;
	@NotEmpty(message = "Gender Category Required")
	private String gender;
	@NotEmpty(message = "Product Name Required")
	private String productName;
	@NotEmpty(message = "Product Description Required")
	private String productDescription;
	private AgeEntity age;
	//@NotEmpty(message = "COD Status Required")
	private Boolean cod;
	//@NotEmpty(message = "Customization Status Required")
	private Boolean customization;
	private PurchaseEntity purchaseQuantity;
	@NotEmpty(message = "Price Type Required")
	private String priceType;
	private Float taxPercentage;
	//@NotEmpty(message = "Tax Status")
	private Boolean taxInclusive;
	private Boolean giftWrap;
	//@NotEmpty(message = "Quantity Required")
	private GiftEntity giftWrapAmount;
	//@NotEmpty(message = "Price Field Required")
	private PriceEntity price;
	//@NotEmpty(message = "Product Images Required")
	private ImagesEntity images[];
	//@NotEmpty(message = "Stock On Hand Required")
	private StandardSOH standeredSOH[];
	private Integer customizationSOH;
	private Object extraSpecifications;
	//@NotEmpty(message = "Product Specification Required")
	private Specification specifications;
	private Boolean isSubmitted;
	//@NotEmpty(message = "Product Approval Required")
	private Boolean isApprove;
	private Boolean isActive;
	private Boolean isDeleted;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date createdOn;
	private String createdBy;
	private String submittedBy;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date submittedOn;
	private String updatedBy;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date updatedOn;
	@NotEmpty(message = "Approval Name Required")
	private String approvedBy;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date approvedOn;
	private String comment;
	private String SKQCode;
	public ProductMasterEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductMasterEntity(Integer productId, Integer designerId, Integer categoryId, Integer subCategoryId,
			@NotEmpty(message = "Gender Category Required") String gender,
			@NotEmpty(message = "Product Name Required") String productName,
			@NotEmpty(message = "Product Description Required") String productDescription, AgeEntity age, Boolean cod,
			Boolean customization, PurchaseEntity purchaseQuantity,
			@NotEmpty(message = "Price Type Required") String priceType, Float taxPercentage, Boolean taxInclusive,
			Boolean giftWrap, GiftEntity giftWrapAmount, PriceEntity price, ImagesEntity[] images,
			StandardSOH[] standeredSOH, Integer customizationSOH, Object extraSpecifications,
			Specification specifications, Boolean isSubmitted, Boolean isApprove, Boolean isActive, Boolean isDeleted,
			Date createdOn, String createdBy, String submittedBy, Date submittedOn, String updatedBy, Date updatedOn,
			@NotEmpty(message = "Approval Name Required") String approvedBy, Date approvedOn, String comment,
			String sKQCode) {
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
		this.isSubmitted = isSubmitted;
		this.isApprove = isApprove;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.submittedBy = submittedBy;
		this.submittedOn = submittedOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.approvedBy = approvedBy;
		this.approvedOn = approvedOn;
		this.comment = comment;
		this.SKQCode = sKQCode;
	}
	@Override
	public String toString() {
		return "ProductMasterEntity [productId=" + productId + ", designerId=" + designerId + ", categoryId="
				+ categoryId + ", subCategoryId=" + subCategoryId + ", gender=" + gender + ", productName="
				+ productName + ", productDescription=" + productDescription + ", age=" + age + ", cod=" + cod
				+ ", customization=" + customization + ", purchaseQuantity=" + purchaseQuantity + ", priceType="
				+ priceType + ", taxPercentage=" + taxPercentage + ", taxInclusive=" + taxInclusive + ", giftWrap="
				+ giftWrap + ", giftWrapAmount=" + giftWrapAmount + ", price=" + price + ", images="
				+ Arrays.toString(images) + ", standeredSOH=" + Arrays.toString(standeredSOH) + ", customizationSOH="
				+ customizationSOH + ", extraSpecifications=" + extraSpecifications + ", specifications="
				+ specifications + ", isSubmitted=" + isSubmitted + ", isApprove=" + isApprove + ", isActive="
				+ isActive + ", isDeleted=" + isDeleted + ", createdOn=" + createdOn + ", createdBy=" + createdBy
				+ ", submittedBy=" + submittedBy + ", submittedOn=" + submittedOn + ", updatedBy=" + updatedBy
				+ ", updatedOn=" + updatedOn + ", approvedBy=" + approvedBy + ", approvedOn=" + approvedOn
				+ ", comment=" + comment + ", SKQCode=" + SKQCode + "]";
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
	public AgeEntity getAge() {
		return age;
	}
	public void setAge(AgeEntity age) {
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
	public PurchaseEntity getPurchaseQuantity() {
		return purchaseQuantity;
	}
	public void setPurchaseQuantity(PurchaseEntity purchaseQuantity) {
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
	public GiftEntity getGiftWrapAmount() {
		return giftWrapAmount;
	}
	public void setGiftWrapAmount(GiftEntity giftWrapAmount) {
		this.giftWrapAmount = giftWrapAmount;
	}
	public PriceEntity getPrice() {
		return price;
	}
	public void setPrice(PriceEntity price) {
		this.price = price;
	}
	public ImagesEntity[] getImages() {
		return images;
	}
	public void setImages(ImagesEntity[] images) {
		this.images = images;
	}
	public StandardSOH[] getStanderedSOH() {
		return standeredSOH;
	}
	public void setStanderedSOH(StandardSOH[] standeredSOH) {
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
	public Specification getSpecifications() {
		return specifications;
	}
	public void setSpecifications(Specification specifications) {
		this.specifications = specifications;
	}
	public Boolean getIsSubmitted() {
		return isSubmitted;
	}
	public void setIsSubmitted(Boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}
	public Boolean getIsApprove() {
		return isApprove;
	}
	public void setIsApprove(Boolean isApprove) {
		this.isApprove = isApprove;
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
	public Date getSubmittedOn() {
		return submittedOn;
	}
	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSKQCode() {
		return SKQCode;
	}
	public void setSKQCode(String sKQCode) {
		SKQCode = sKQCode;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
}
