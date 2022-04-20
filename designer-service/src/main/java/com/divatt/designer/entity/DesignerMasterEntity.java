package com.divatt.designer.entity;

import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.profile.DesignerProfileEntity;

public class DesignerMasterEntity {

	private ProductMasterEntity productMasterEntity;
	private DesignerProfileEntity designerProfileEntity;
	public DesignerMasterEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DesignerMasterEntity(ProductMasterEntity productMasterEntity, DesignerProfileEntity designerProfileEntity) {
		super();
		this.productMasterEntity = productMasterEntity;
		this.designerProfileEntity = designerProfileEntity;
	}
	@Override
	public String toString() {
		return "DesignerMasterEntity [productMasterEntity=" + productMasterEntity + ", designerProfileEntity="
				+ designerProfileEntity + "]";
	}
	public ProductMasterEntity getProductMasterEntity() {
		return productMasterEntity;
	}
	public void setProductMasterEntity(ProductMasterEntity productMasterEntity) {
		this.productMasterEntity = productMasterEntity;
	}
	public DesignerProfileEntity getDesignerProfileEntity() {
		return designerProfileEntity;
	}
	public void setDesignerProfileEntity(DesignerProfileEntity designerProfileEntity) {
		this.designerProfileEntity = designerProfileEntity;
	}
	
}
