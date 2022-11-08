package com.divatt.designer.entity.profile;

public class ProfileImage {

	private Long designerId;
	private String image;

	public ProfileImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProfileImage(Long designerId, String image) {
		super();
		this.designerId = designerId;
		this.image = image;
	}

	public Long getDesignerId() {
		return designerId;
	}

	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ProfileImage [designerId=" + designerId + ", image=" + image + "]";
	}

}
