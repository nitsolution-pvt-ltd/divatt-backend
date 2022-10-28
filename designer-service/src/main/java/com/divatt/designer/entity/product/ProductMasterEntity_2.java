package com.divatt.designer.entity.product;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl2_products")
public class ProductMasterEntity_2 {
	@Id
	private Integer productId;
	@Field(name = "SKU")
	@NotEmpty(message = "Product SKU Required")
	private String sku;
	@Transient
	public static final String SEQUENCE_NAME = "tbl2_products";	
	@Field(name = "designerId")
	private Integer designerId;
	@Field(name = "categoryId")
	//@NotEmpty(message = "Category ID Required")
	private Integer categoryId;
	@Field(name = "subCategoryId")
	//@NotEmpty(message = "SUb-Category ID Required")
	private Integer subCategoryId;
	@Field(name = "purchase_min_quantity")
	private Integer purchase_min_quantity;
	@Field(name = "purchase_max_quantity")
	private Integer purchase_max_quantity;
	@Field(name = "hsn_code")
	private Integer hsnCode;
	
	@Field(name = "product_details")  
	private productDetails product_details;
	
	@Field(name = "design_customization_features")
	private String 	design_customization_features ;
	
	private Boolean with_customization ;
	private Boolean with_design_customization;
	private Boolean with_gift_wrap;
	private Boolean return_acceptable ;
	private Boolean cancel_acceptable ;
	//@NotEmpty(message = "COD Status Required")
	private Boolean cod ;
	@NotEmpty(message = "Price Type Required")
	private String price_type ;
	@Field(name = "color")
	@NotEmpty(message = "Price Type Required")
	private String colour ;
	@Field(name = "sizes")
	private List<String> sizes ;
	private Integer soh ;
	private Integer oos ;
	private Integer notify ;
	private String price_code;
	@Field(name = "MRP")
	private Integer mrp ;
	private Deal deal ;
	private Integer gift_wrap_amount;
	
	private ExtraSpecifications extraSpecifications ;
	private String product_weight ;
	private String shipment_time ;
	
	@Field(name = "images")
	//@NotEmpty(message = "Product Images Required")
	private ImageEntity image[];
	private Boolean is_active ;
	private Boolean is_deleted ;
	private String product_stage ;
	private ProductStageDetails product_stage_details ;
	private String crated_on ;
	private String created_by ;
	private String updated_on ;
	private String updated_by ;
	public ProductMasterEntity_2() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductMasterEntity_2(Integer productId, @NotEmpty(message = "Product SKU Required") String sku,
			Integer designerId, Integer categoryId, Integer subCategoryId, Integer purchase_min_quantity,
			Integer purchase_max_quantity, Integer hsnCode, productDetails product_details,
			String design_customization_features, Boolean with_customization, Boolean with_design_customization,
			Boolean with_gift_wrap, Boolean return_acceptable, Boolean cancel_acceptable, Boolean cod,
			@NotEmpty(message = "Price Type Required") String price_type,
			@NotEmpty(message = "Price Type Required") String colour, List<String> sizes, Integer soh, Integer oos,
			Integer notify, String price_code, Integer mrp, Deal deal, Integer gift_wrap_amount,
			ExtraSpecifications extraSpecifications, String product_weight, String shipment_time, ImageEntity[] image,
			Boolean is_active, Boolean is_deleted, String product_stage, ProductStageDetails product_stage_details,
			String crated_on, String created_by, String updated_on, String updated_by) {
		super();
		this.productId = productId;
		this.sku = sku;
		this.designerId = designerId;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.purchase_min_quantity = purchase_min_quantity;
		this.purchase_max_quantity = purchase_max_quantity;
		this.hsnCode = hsnCode;
		this.product_details = product_details;
		this.design_customization_features = design_customization_features;
		this.with_customization = with_customization;
		this.with_design_customization = with_design_customization;
		this.with_gift_wrap = with_gift_wrap;
		this.return_acceptable = return_acceptable;
		this.cancel_acceptable = cancel_acceptable;
		this.cod = cod;
		this.price_type = price_type;
		this.colour = colour;
		this.sizes = sizes;
		this.soh = soh;
		this.oos = oos;
		this.notify = notify;
		this.price_code = price_code;
		this.mrp = mrp;
		this.deal = deal;
		this.gift_wrap_amount = gift_wrap_amount;
		this.extraSpecifications = extraSpecifications;
		this.product_weight = product_weight;
		this.shipment_time = shipment_time;
		this.image = image;
		this.is_active = is_active;
		this.is_deleted = is_deleted;
		this.product_stage = product_stage;
		this.product_stage_details = product_stage_details;
		this.crated_on = crated_on;
		this.created_by = created_by;
		this.updated_on = updated_on;
		this.updated_by = updated_by;
	}
	@Override
	public String toString() {
		return "ProductMasterEntity_2 [productId=" + productId + ", sku=" + sku + ", designerId=" + designerId
				+ ", categoryId=" + categoryId + ", subCategoryId=" + subCategoryId + ", purchase_min_quantity="
				+ purchase_min_quantity + ", purchase_max_quantity=" + purchase_max_quantity + ", hsnCode=" + hsnCode
				+ ", product_details=" + product_details + ", design_customization_features="
				+ design_customization_features + ", with_customization=" + with_customization
				+ ", with_design_customization=" + with_design_customization + ", with_gift_wrap=" + with_gift_wrap
				+ ", return_acceptable=" + return_acceptable + ", cancel_acceptable=" + cancel_acceptable + ", cod="
				+ cod + ", price_type=" + price_type + ", colour=" + colour + ", sizes=" + sizes + ", soh=" + soh
				+ ", oos=" + oos + ", notify=" + notify + ", price_code=" + price_code + ", mrp=" + mrp + ", deal="
				+ deal + ", gift_wrap_amount=" + gift_wrap_amount + ", extraSpecifications=" + extraSpecifications
				+ ", product_weight=" + product_weight + ", shipment_time=" + shipment_time + ", image="
				+ Arrays.toString(image) + ", is_active=" + is_active + ", is_deleted=" + is_deleted
				+ ", product_stage=" + product_stage + ", product_stage_details=" + product_stage_details
				+ ", crated_on=" + crated_on + ", created_by=" + created_by + ", updated_on=" + updated_on
				+ ", updated_by=" + updated_by + "]";
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
	public Integer getPurchase_min_quantity() {
		return purchase_min_quantity;
	}
	public void setPurchase_min_quantity(Integer purchase_min_quantity) {
		this.purchase_min_quantity = purchase_min_quantity;
	}
	public Integer getPurchase_max_quantity() {
		return purchase_max_quantity;
	}
	public void setPurchase_max_quantity(Integer purchase_max_quantity) {
		this.purchase_max_quantity = purchase_max_quantity;
	}
	public Integer getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(Integer hsnCode) {
		this.hsnCode = hsnCode;
	}
	public productDetails getProduct_details() {
		return product_details;
	}
	public void setProduct_details(productDetails product_details) {
		this.product_details = product_details;
	}
	public String getDesign_customization_features() {
		return design_customization_features;
	}
	public void setDesign_customization_features(String design_customization_features) {
		this.design_customization_features = design_customization_features;
	}
	public Boolean getWith_customization() {
		return with_customization;
	}
	public void setWith_customization(Boolean with_customization) {
		this.with_customization = with_customization;
	}
	public Boolean getWith_design_customization() {
		return with_design_customization;
	}
	public void setWith_design_customization(Boolean with_design_customization) {
		this.with_design_customization = with_design_customization;
	}
	public Boolean getWith_gift_wrap() {
		return with_gift_wrap;
	}
	public void setWith_gift_wrap(Boolean with_gift_wrap) {
		this.with_gift_wrap = with_gift_wrap;
	}
	public Boolean getReturn_acceptable() {
		return return_acceptable;
	}
	public void setReturn_acceptable(Boolean return_acceptable) {
		this.return_acceptable = return_acceptable;
	}
	public Boolean getCancel_acceptable() {
		return cancel_acceptable;
	}
	public void setCancel_acceptable(Boolean cancel_acceptable) {
		this.cancel_acceptable = cancel_acceptable;
	}
	public Boolean getCod() {
		return cod;
	}
	public void setCod(Boolean cod) {
		this.cod = cod;
	}
	public String getPrice_type() {
		return price_type;
	}
	public void setPrice_type(String price_type) {
		this.price_type = price_type;
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
	public String getPrice_code() {
		return price_code;
	}
	public void setPrice_code(String price_code) {
		this.price_code = price_code;
	}
	public Integer getMrp() {
		return mrp;
	}
	public void setMrp(Integer mrp) {
		this.mrp = mrp;
	}
	public Deal getDeal() {
		return deal;
	}
	public void setDeal(Deal deal) {
		this.deal = deal;
	}
	public Integer getGift_wrap_amount() {
		return gift_wrap_amount;
	}
	public void setGift_wrap_amount(Integer gift_wrap_amount) {
		this.gift_wrap_amount = gift_wrap_amount;
	}
	public ExtraSpecifications getExtraSpecifications() {
		return extraSpecifications;
	}
	public void setExtraSpecifications(ExtraSpecifications extraSpecifications) {
		this.extraSpecifications = extraSpecifications;
	}
	public String getProduct_weight() {
		return product_weight;
	}
	public void setProduct_weight(String product_weight) {
		this.product_weight = product_weight;
	}
	public String getShipment_time() {
		return shipment_time;
	}
	public void setShipment_time(String shipment_time) {
		this.shipment_time = shipment_time;
	}
	public ImageEntity[] getImage() {
		return image;
	}
	public void setImage(ImageEntity[] image) {
		this.image = image;
	}
	public Boolean getIs_active() {
		return is_active;
	}
	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}
	public Boolean getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getProduct_stage() {
		return product_stage;
	}
	public void setProduct_stage(String product_stage) {
		this.product_stage = product_stage;
	}
	public ProductStageDetails getProduct_stage_details() {
		return product_stage_details;
	}
	public void setProduct_stage_details(ProductStageDetails product_stage_details) {
		this.product_stage_details = product_stage_details;
	}
	public String getCrated_on() {
		return crated_on;
	}
	public void setCrated_on(String crated_on) {
		this.crated_on = crated_on;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getUpdated_on() {
		return updated_on;
	}
	public void setUpdated_on(String updated_on) {
		this.updated_on = updated_on;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	
}
