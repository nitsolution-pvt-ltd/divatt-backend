package com.divatt.designer.entity;

import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.profile.DesignerProfileEntity;

public class ListProduct {

	private DesignerProfileEntity designerProfileEntity;
	private ProductMasterEntity productMasterEntity;
	public ListProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListProduct(DesignerProfileEntity designerProfileEntity, ProductMasterEntity productMasterEntity) {
		super();
		this.designerProfileEntity = designerProfileEntity;
		this.productMasterEntity = productMasterEntity;
	}
	@Override
	public String toString() {
		return "ListProduct [designerProfileEntity=" + designerProfileEntity + ", productMasterEntity="
				+ productMasterEntity + "]";
	}
	public DesignerProfileEntity getDesignerProfileEntity() {
		return designerProfileEntity;
	}
	public void setDesignerProfileEntity(DesignerProfileEntity designerProfileEntity) {
		this.designerProfileEntity = designerProfileEntity;
	}
	public ProductMasterEntity getProductMasterEntity() {
		return productMasterEntity;
	}
	public void setProductMasterEntity(ProductMasterEntity productMasterEntity) {
		this.productMasterEntity = productMasterEntity;
	}
	
}
