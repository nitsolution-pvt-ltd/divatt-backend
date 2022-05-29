package com.divatt.admin.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "tbl_admin_mdata")
public class ColourMetaEntity {

	@Id
	private Integer id;
	private String metaKey;
	private List<ColourEntity> colors;
	public ColourMetaEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ColourMetaEntity(Integer id, String metaKey, List<ColourEntity> colors) {
		super();
		this.id = id;
		this.metaKey = metaKey;
		this.colors = colors;
	}
	@Override
	public String toString() {
		return "ColourMetaEntity [id=" + id + ", metaKey=" + metaKey + ", colors=" + colors + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMetaKey() {
		return metaKey;
	}
	public void setMetaKey(String metaKey) {
		this.metaKey = metaKey;
	}
	public List<ColourEntity> getColors() {
		return colors;
	}
	public void setColors(List<ColourEntity> colors) {
		this.colors = colors;
	}

}
