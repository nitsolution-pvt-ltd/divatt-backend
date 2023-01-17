package com.divatt.user.entity.product;



import nonapi.io.github.classgraph.json.Id;


public class DesignerProfileEntity {
	
	
	
	@Id
	private Long id;
	
	
	private Long designerId;
	

	private DesignerProfile designerProfile;
	

	
	private String designerName;
	
	
	public DesignerProfileEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getDesignerId() {
		return designerId;
	}


	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}


	public DesignerProfile getDesignerProfile() {
		return designerProfile;
	}


	public void setDesignerProfile(DesignerProfile designerProfile) {
		this.designerProfile = designerProfile;
	}


	public String getDesignerName() {
		return designerName;
	}


	public void setDesignerName(String designerName) {
		this.designerName = designerName;
	}


	public DesignerProfileEntity(Long id, Long designerId, DesignerProfile designerProfile, String designerName) {
		super();
		this.id = id;
		this.designerId = designerId;
		this.designerProfile = designerProfile;
		this.designerName = designerName;
	}


	@Override
	public String toString() {
		return "DesignerProfileEntity [id=" + id + ", designerId=" + designerId + ", designerProfile=" + designerProfile
				+ ", designerName=" + designerName + "]";
	}

}
