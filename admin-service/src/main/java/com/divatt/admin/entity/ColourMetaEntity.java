package com.divatt.admin.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "tbl_admin_mdata")
public class ColourMetaEntity {

	@Id
	private Integer id;
	private String meta_key;
	private List<ColourEntity> colors;
	public ColourMetaEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ColourMetaEntity(Integer id, String meta_key, List<ColourEntity> colors) {
		super();
		this.id = id;
		this.meta_key = meta_key;
		this.colors = colors;
	}
	@Override
	public String toString() {
		return "ColourMetaEntity [id=" + id + ", meta_key=" + meta_key + ", colors=" + colors + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMeta_key() {
		return meta_key;
	}
	public void setMeta_key(String meta_key) {
		this.meta_key = meta_key;
	}
	public List<ColourEntity> getColors() {
		return colors;
	}
	public void setColors(List<ColourEntity> colors) {
		this.colors = colors;
	}
	
}
