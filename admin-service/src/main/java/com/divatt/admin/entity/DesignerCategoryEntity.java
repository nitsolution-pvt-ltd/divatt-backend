package com.divatt.admin.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection ="tbl_admin_mdata")
public class DesignerCategoryEntity {

	@Id
	private Integer id;
	
	@Field(name="meta_key")
	private String metakey;
	
	@Field( name = "levels")
	private List<String> designerLevels;

	public DesignerCategoryEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerCategoryEntity(Integer id, String metakey, List<String> designerLevels) {
		super();
		this.id = id;
		this.metakey = metakey;
		this.designerLevels = designerLevels;
	}

	@Override
	public String toString() {
		return "DesignerCategoryEntity [id=" + id + ", metakey=" + metakey + ", designerLevels=" + designerLevels + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMetakey() {
		return metakey;
	}

	public void setMetakey(String metakey) {
		this.metakey = metakey;
	}

	public List<String> getDesignerLevels() {
		return designerLevels;
	}

	public void setDesignerLevels(List<String> designerLevels) {
		this.designerLevels = designerLevels;
	}

	
}
