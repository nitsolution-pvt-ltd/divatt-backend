/**
 * 
 */
package com.ecom.ecomMongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author situpand
 *
 */
@Document(collection = "VariantMapping")
public class VariantMappings {
	@Id
	private Integer subCatId;
	private String subCatName;
	private Integer variantId;
	private String dependentAttributeIds;
	private Integer level;

	public Integer getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(Integer subCatId) {
		this.subCatId = subCatId;
	}

	public String getSubCatName() {
		return subCatName;
	}

	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
	}

	public Integer getVariantId() {
		return variantId;
	}

	public void setVariantId(Integer variantId) {
		this.variantId = variantId;
	}

	public String getDependentAttributeIds() {
		return dependentAttributeIds;
	}

	public void setDependentAttributeIds(String dependentAttributeIds) {
		this.dependentAttributeIds = dependentAttributeIds;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
