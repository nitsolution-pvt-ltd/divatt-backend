package com.divatt.designer.entity.product;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.divatt.designer.entity.profile.DesignerProfile;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "tbl2_products")
public class ProductMasterEntity2 implements Cloneable {
	@Id
	@Field(name = "_id")
	private Integer productId;
	@Field(name = "SKU")
	@NotEmpty(message = "Product SKU Required")
	private String sku;
	@Transient
	public static final String SEQUENCE_NAME = "tbl2_products";
	@Field(name = "designerId")
	private Integer designerId;
	@Field(name = "categoryId")
	// @NotEmpty(message = "Category ID Required")
	private Integer categoryId;
	@Field(name = "subCategoryId")
	// @NotEmpty(message = "SUb-Category ID Required")
	private Integer subCategoryId;
	@Field(name = "purchase_min_quantity")
	private Integer purchaseMinQuantity;
	@Field(name = "purchase_max_quantity")
	private Integer purchaseMaxQuantity;
	@Field(name = "hsn_code")
	private Integer hsnCode;

	@Field(name = "product_details")
	private productDetails productDetails;

	@Field(name = "design_customization_features")
	private String designCustomizationFeatures;
	@Field(name = "with_customization")
	private Boolean withCustomization;
	@Field(name = "with_design_customization")
	private Boolean withDesignCustomization;
	@Field(name = "with_gift_wrap")
	private Boolean withGiftWrap;
	@Field(name = "return_acceptable")
	private Boolean returnAcceptable;
	@Field(name = "cancel_acceptable")
	private Boolean cancelAcceptable;
	// @NotEmpty(message = "COD Status Required")
	@Field(name = "cod")
	private Boolean cod;
	@NotEmpty(message = "Price Type Required")
	@Field(name = "price_type")
	private String priceType;
	@Field(name = "color")
	@NotEmpty(message = "Color Required")
	private String colour;
	@Field(name = "sizes")
	private List<String> sizes;
	@Field(name = "soh")
	private Integer soh;
	@Field(name = "oos")
	private Integer oos;
	@Field(name = "notify")
	private Integer notify;
	@Field(name = "price_code")
	private String priceCode;
	@Field(name = "MRP")
	private Double mrp;
	@Field(name = "adminStatus")
	private String adminStatus;
	@Field(name = "deal")
	private Deal deal;
	@Field(name = "gift_wrap_amount")
	private Double giftWrapAmount;
	@Field(name = "extraSpecifications")
	private ExtraSpecifications extraSpecifications;
	@Field(name = "product_weight")
	private String productWeight;
	@Field(name = "shipment_time")
	private String shipmentTime;

	@Field(name = "images")
	private ImageEntity images[];
	@Field(name = "is_active")
	private Boolean isActive;
	@Field(name = "is_deleted")
	private Boolean isDeleted;
	@Field(name = "product_stage")
	private String productStage;
	@Field(name = "product_stage_details")
	private ProductStageDetails productStageDetails;

	@Field(name = "created_on")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	private Date createdOn;
	@Field(name = "created_by")
	private String createdBy;
	@Field(name = "updated_on")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	private Date updatedOn;
	@Field(name = "updated_by")
	private String updatedBy;
	private String subCategoryName;
	private String categoryName;
	private String weightUnit;
	private DesignerProfile designerProfile;

	public ProductMasterEntity2() {
		super();
	}

	public ProductMasterEntity2(Integer productId, @NotEmpty(message = "Product SKU Required") String sku,
			Integer designerId, Integer categoryId, Integer subCategoryId, Integer purchaseMinQuantity,
			Integer purchaseMaxQuantity, Integer hsnCode,
			com.divatt.designer.entity.product.productDetails productDetails, String designCustomizationFeatures,
			Boolean withCustomization, Boolean withDesignCustomization, Boolean withGiftWrap, Boolean returnAcceptable,
			Boolean cancelAcceptable, Boolean cod, @NotEmpty(message = "Price Type Required") String priceType,
			@NotEmpty(message = "Color Required") String colour, List<String> sizes, Integer soh, Integer oos,
			Integer notify, String priceCode, Double mrp, String adminStatus, Deal deal, Double giftWrapAmount,
			ExtraSpecifications extraSpecifications, String productWeight, String shipmentTime, ImageEntity[] images,
			Boolean isActive, Boolean isDeleted, String productStage, ProductStageDetails productStageDetails,
			Date createdOn, String createdBy, Date updatedOn, String updatedBy, String subCategoryName,
			String categoryName, String weightUnit, DesignerProfile designerProfile) {
		super();
		this.productId = productId;
		this.sku = sku;
		this.designerId = designerId;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.purchaseMinQuantity = purchaseMinQuantity;
		this.purchaseMaxQuantity = purchaseMaxQuantity;
		this.hsnCode = hsnCode;
		this.productDetails = productDetails;
		this.designCustomizationFeatures = designCustomizationFeatures;
		this.withCustomization = withCustomization;
		this.withDesignCustomization = withDesignCustomization;
		this.withGiftWrap = withGiftWrap;
		this.returnAcceptable = returnAcceptable;
		this.cancelAcceptable = cancelAcceptable;
		this.cod = cod;
		this.priceType = priceType;
		this.colour = colour;
		this.sizes = sizes;
		this.soh = soh;
		this.oos = oos;
		this.notify = notify;
		this.priceCode = priceCode;
		this.mrp = mrp;
		this.adminStatus = adminStatus;
		this.deal = deal;
		this.giftWrapAmount = giftWrapAmount;
		this.extraSpecifications = extraSpecifications;
		this.productWeight = productWeight;
		this.shipmentTime = shipmentTime;
		this.images = images;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.productStage = productStage;
		this.productStageDetails = productStageDetails;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
		this.subCategoryName = subCategoryName;
		this.categoryName = categoryName;
		this.weightUnit = weightUnit;
		this.designerProfile = designerProfile;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public Integer getPurchaseMinQuantity() {
		return purchaseMinQuantity;
	}

	public void setPurchaseMinQuantity(Integer purchaseMinQuantity) {
		this.purchaseMinQuantity = purchaseMinQuantity;
	}

	public Integer getPurchaseMaxQuantity() {
		return purchaseMaxQuantity;
	}

	public void setPurchaseMaxQuantity(Integer purchaseMaxQuantity) {
		this.purchaseMaxQuantity = purchaseMaxQuantity;
	}

	public Integer getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(Integer hsnCode) {
		this.hsnCode = hsnCode;
	}

	public productDetails getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(productDetails productDetails) {
		this.productDetails = productDetails;
	}

	public String getDesignCustomizationFeatures() {
		return designCustomizationFeatures;
	}

	public void setDesignCustomizationFeatures(String designCustomizationFeatures) {
		this.designCustomizationFeatures = designCustomizationFeatures;
	}

	public Boolean getWithCustomization() {
		return withCustomization;
	}

	public void setWithCustomization(Boolean withCustomization) {
		this.withCustomization = withCustomization;
	}

	public Boolean getWithDesignCustomization() {
		return withDesignCustomization;
	}

	public void setWithDesignCustomization(Boolean withDesignCustomization) {
		this.withDesignCustomization = withDesignCustomization;
	}

	public Boolean getWithGiftWrap() {
		return withGiftWrap;
	}

	public void setWithGiftWrap(Boolean withGiftWrap) {
		this.withGiftWrap = withGiftWrap;
	}

	public Boolean getReturnAcceptable() {
		return returnAcceptable;
	}

	public void setReturnAcceptable(Boolean returnAcceptable) {
		this.returnAcceptable = returnAcceptable;
	}

	public Boolean getCancelAcceptable() {
		return cancelAcceptable;
	}

	public void setCancelAcceptable(Boolean cancelAcceptable) {
		this.cancelAcceptable = cancelAcceptable;
	}

	public Boolean getCod() {
		return cod;
	}

	public void setCod(Boolean cod) {
		this.cod = cod;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}

	public Integer getSoh() {
		return soh;
	}

	public void setSoh(Integer soh) {
		this.soh = soh;
	}

	public Integer getOos() {
		return oos;
	}

	public void setOos(Integer oos) {
		this.oos = oos;
	}

	public Integer getNotify() {
		return notify;
	}

	public void setNotify(Integer notify) {
		this.notify = notify;
	}

	public String getPriceCode() {
		return priceCode;
	}

	public void setPriceCode(String priceCode) {
		this.priceCode = priceCode;
	}

	public Double getMrp() {
		return mrp;
	}

	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	public String getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	public Double getGiftWrapAmount() {
		return giftWrapAmount;
	}

	public void setGiftWrapAmount(Double giftWrapAmount) {
		this.giftWrapAmount = giftWrapAmount;
	}

	public ExtraSpecifications getExtraSpecifications() {
		return extraSpecifications;
	}

	public void setExtraSpecifications(ExtraSpecifications extraSpecifications) {
		this.extraSpecifications = extraSpecifications;
	}

	public String getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(String productWeight) {
		this.productWeight = productWeight;
	}

	public String getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(String shipmentTime) {
		this.shipmentTime = shipmentTime;
	}

	public ImageEntity[] getImages() {
		return images;
	}

	public void setImages(ImageEntity[] images) {
		this.images = images;
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

	public String getProductStage() {
		return productStage;
	}

	public void setProductStage(String productStage) {
		this.productStage = productStage;
	}

	public ProductStageDetails getProductStageDetails() {
		return productStageDetails;
	}

	public void setProductStageDetails(ProductStageDetails productStageDetails) {
		this.productStageDetails = productStageDetails;
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

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public DesignerProfile getDesignerProfile() {
		return designerProfile;
	}

	public void setDesignerProfile(DesignerProfile designerProfile) {
		this.designerProfile = designerProfile;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	@Override
	public String toString() {
		return "ProductMasterEntity2 [productId=" + productId + ", sku=" + sku + ", designerId=" + designerId
				+ ", categoryId=" + categoryId + ", subCategoryId=" + subCategoryId + ", purchaseMinQuantity="
				+ purchaseMinQuantity + ", purchaseMaxQuantity=" + purchaseMaxQuantity + ", hsnCode=" + hsnCode
				+ ", productDetails=" + productDetails + ", designCustomizationFeatures=" + designCustomizationFeatures
				+ ", withCustomization=" + withCustomization + ", withDesignCustomization=" + withDesignCustomization
				+ ", withGiftWrap=" + withGiftWrap + ", returnAcceptable=" + returnAcceptable + ", cancelAcceptable="
				+ cancelAcceptable + ", cod=" + cod + ", priceType=" + priceType + ", colour=" + colour + ", sizes="
				+ sizes + ", soh=" + soh + ", oos=" + oos + ", notify=" + notify + ", priceCode=" + priceCode + ", mrp="
				+ mrp + ", adminStatus=" + adminStatus + ", deal=" + deal + ", giftWrapAmount=" + giftWrapAmount
				+ ", extraSpecifications=" + extraSpecifications + ", productWeight=" + productWeight
				+ ", shipmentTime=" + shipmentTime + ", images=" + Arrays.toString(images) + ", isActive=" + isActive
				+ ", isDeleted=" + isDeleted + ", productStage=" + productStage + ", productStageDetails="
				+ productStageDetails + ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", updatedOn="
				+ updatedOn + ", updatedBy=" + updatedBy + ", subCategoryName=" + subCategoryName + ", categoryName="
				+ categoryName + ", weightUnit=" + weightUnit + ", designerProfile=" + designerProfile + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adminStatus == null) ? 0 : adminStatus.hashCode());
		result = prime * result + ((cancelAcceptable == null) ? 0 : cancelAcceptable.hashCode());
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((cod == null) ? 0 : cod.hashCode());
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((deal == null) ? 0 : deal.hashCode());
		result = prime * result + ((designCustomizationFeatures == null) ? 0 : designCustomizationFeatures.hashCode());
		result = prime * result + ((designerId == null) ? 0 : designerId.hashCode());
		result = prime * result + ((designerProfile == null) ? 0 : designerProfile.hashCode());
		result = prime * result + ((extraSpecifications == null) ? 0 : extraSpecifications.hashCode());
		result = prime * result + ((giftWrapAmount == null) ? 0 : giftWrapAmount.hashCode());
		result = prime * result + ((hsnCode == null) ? 0 : hsnCode.hashCode());
		result = prime * result + Arrays.hashCode(images);
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((mrp == null) ? 0 : mrp.hashCode());
		result = prime * result + ((notify == null) ? 0 : notify.hashCode());
		result = prime * result + ((oos == null) ? 0 : oos.hashCode());
		result = prime * result + ((priceCode == null) ? 0 : priceCode.hashCode());
		result = prime * result + ((priceType == null) ? 0 : priceType.hashCode());
		result = prime * result + ((productDetails == null) ? 0 : productDetails.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((productStage == null) ? 0 : productStage.hashCode());
		result = prime * result + ((productStageDetails == null) ? 0 : productStageDetails.hashCode());
		result = prime * result + ((productWeight == null) ? 0 : productWeight.hashCode());
		result = prime * result + ((purchaseMaxQuantity == null) ? 0 : purchaseMaxQuantity.hashCode());
		result = prime * result + ((purchaseMinQuantity == null) ? 0 : purchaseMinQuantity.hashCode());
		result = prime * result + ((returnAcceptable == null) ? 0 : returnAcceptable.hashCode());
		result = prime * result + ((shipmentTime == null) ? 0 : shipmentTime.hashCode());
		result = prime * result + ((sizes == null) ? 0 : sizes.hashCode());
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		result = prime * result + ((soh == null) ? 0 : soh.hashCode());
		result = prime * result + ((subCategoryId == null) ? 0 : subCategoryId.hashCode());
		result = prime * result + ((subCategoryName == null) ? 0 : subCategoryName.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
		result = prime * result + ((weightUnit == null) ? 0 : weightUnit.hashCode());
		result = prime * result + ((withCustomization == null) ? 0 : withCustomization.hashCode());
		result = prime * result + ((withDesignCustomization == null) ? 0 : withDesignCustomization.hashCode());
		result = prime * result + ((withGiftWrap == null) ? 0 : withGiftWrap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductMasterEntity2 other = (ProductMasterEntity2) obj;
		if (adminStatus == null) {
			if (other.adminStatus != null)
				return false;
		} else if (!adminStatus.equals(other.adminStatus))
			return false;
		if (cancelAcceptable == null) {
			if (other.cancelAcceptable != null)
				return false;
		} else if (!cancelAcceptable.equals(other.cancelAcceptable))
			return false;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (cod == null) {
			if (other.cod != null)
				return false;
		} else if (!cod.equals(other.cod))
			return false;
		if (colour == null) {
			if (other.colour != null)
				return false;
		} else if (!colour.equals(other.colour))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (deal == null) {
			if (other.deal != null)
				return false;
		} else if (!deal.equals(other.deal))
			return false;
		if (designCustomizationFeatures == null) {
			if (other.designCustomizationFeatures != null)
				return false;
		} else if (!designCustomizationFeatures.equals(other.designCustomizationFeatures))
			return false;
		if (designerId == null) {
			if (other.designerId != null)
				return false;
		} else if (!designerId.equals(other.designerId))
			return false;
		if (designerProfile == null) {
			if (other.designerProfile != null)
				return false;
		} else if (!designerProfile.equals(other.designerProfile))
			return false;
		if (extraSpecifications == null) {
			if (other.extraSpecifications != null)
				return false;
		} else if (!extraSpecifications.equals(other.extraSpecifications))
			return false;
		if (giftWrapAmount == null) {
			if (other.giftWrapAmount != null)
				return false;
		} else if (!giftWrapAmount.equals(other.giftWrapAmount))
			return false;
		if (hsnCode == null) {
			if (other.hsnCode != null)
				return false;
		} else if (!hsnCode.equals(other.hsnCode))
			return false;
		if (!Arrays.equals(images, other.images))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (mrp == null) {
			if (other.mrp != null)
				return false;
		} else if (!mrp.equals(other.mrp))
			return false;
		if (notify == null) {
			if (other.notify != null)
				return false;
		} else if (!notify.equals(other.notify))
			return false;
		if (oos == null) {
			if (other.oos != null)
				return false;
		} else if (!oos.equals(other.oos))
			return false;
		if (priceCode == null) {
			if (other.priceCode != null)
				return false;
		} else if (!priceCode.equals(other.priceCode))
			return false;
		if (priceType == null) {
			if (other.priceType != null)
				return false;
		} else if (!priceType.equals(other.priceType))
			return false;
		if (productDetails == null) {
			if (other.productDetails != null)
				return false;
		} else if (!productDetails.equals(other.productDetails))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productStage == null) {
			if (other.productStage != null)
				return false;
		} else if (!productStage.equals(other.productStage))
			return false;
		if (productStageDetails == null) {
			if (other.productStageDetails != null)
				return false;
		} else if (!productStageDetails.equals(other.productStageDetails))
			return false;
		if (productWeight == null) {
			if (other.productWeight != null)
				return false;
		} else if (!productWeight.equals(other.productWeight))
			return false;
		if (purchaseMaxQuantity == null) {
			if (other.purchaseMaxQuantity != null)
				return false;
		} else if (!purchaseMaxQuantity.equals(other.purchaseMaxQuantity))
			return false;
		if (purchaseMinQuantity == null) {
			if (other.purchaseMinQuantity != null)
				return false;
		} else if (!purchaseMinQuantity.equals(other.purchaseMinQuantity))
			return false;
		if (returnAcceptable == null) {
			if (other.returnAcceptable != null)
				return false;
		} else if (!returnAcceptable.equals(other.returnAcceptable))
			return false;
		if (shipmentTime == null) {
			if (other.shipmentTime != null)
				return false;
		} else if (!shipmentTime.equals(other.shipmentTime))
			return false;
		if (sizes == null) {
			if (other.sizes != null)
				return false;
		} else if (!sizes.equals(other.sizes))
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		if (soh == null) {
			if (other.soh != null)
				return false;
		} else if (!soh.equals(other.soh))
			return false;
		if (subCategoryId == null) {
			if (other.subCategoryId != null)
				return false;
		} else if (!subCategoryId.equals(other.subCategoryId))
			return false;
		if (subCategoryName == null) {
			if (other.subCategoryName != null)
				return false;
		} else if (!subCategoryName.equals(other.subCategoryName))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedOn == null) {
			if (other.updatedOn != null)
				return false;
		} else if (!updatedOn.equals(other.updatedOn))
			return false;
		if (weightUnit == null) {
			if (other.weightUnit != null)
				return false;
		} else if (!weightUnit.equals(other.weightUnit))
			return false;
		if (withCustomization == null) {
			if (other.withCustomization != null)
				return false;
		} else if (!withCustomization.equals(other.withCustomization))
			return false;
		if (withDesignCustomization == null) {
			if (other.withDesignCustomization != null)
				return false;
		} else if (!withDesignCustomization.equals(other.withDesignCustomization))
			return false;
		if (withGiftWrap == null) {
			if (other.withGiftWrap != null)
				return false;
		} else if (!withGiftWrap.equals(other.withGiftWrap))
			return false;
		return true;
	}

	

}
