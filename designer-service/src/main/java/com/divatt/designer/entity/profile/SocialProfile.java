package com.divatt.designer.entity.profile;

import org.springframework.data.mongodb.core.mapping.Field;

public class SocialProfile {
	
	@Field(name = "facebook_link")
	private String facebookLink;
	@Field(name = "instagram_link")
	private String instagramLink;
	@Field(name = "youtube_link")
	private String youtubeLink;
	@Field(name = "description")
	private String description;
	@Field(name = "address")
	private String address;
	
	public SocialProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SocialProfile(String facebookLink, String instagramLink, String youtubeLink, String description,
			String address) {
		super();
		this.facebookLink = facebookLink;
		this.instagramLink = instagramLink;
		this.youtubeLink = youtubeLink;
		this.description = description;
		this.address = address;
	}

	@Override
	public String toString() {
		return "SocialProfile [facebookLink=" + facebookLink + ", instagramLink=" + instagramLink + ", youtubeLink="
				+ youtubeLink + ", description=" + description + ", address=" + address + "]";
	}

	public String getFacebookLink() {
		return facebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

	public String getInstagramLink() {
		return instagramLink;
	}

	public void setInstagramLink(String instagramLink) {
		this.instagramLink = instagramLink;
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

}
