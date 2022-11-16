package com.divatt.user.serviceDTO;

public class OrdersDTO {

	private Boolean withDesignCustomization;
	private Boolean withCustomization;

	public OrdersDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrdersDTO(Boolean withDesignCustomization, Boolean withCustomization) {
		super();
		this.withDesignCustomization = withDesignCustomization;
		this.withCustomization = withCustomization;
	}

	public Boolean getWithDesignCustomization() {
		return withDesignCustomization;
	}

	public void setWithDesignCustomization(Boolean withDesignCustomization) {
		this.withDesignCustomization = withDesignCustomization;
	}

	public Boolean getWithCustomization() {
		return withCustomization;
	}

	public void setWithCustomization(Boolean withCustomization) {
		this.withCustomization = withCustomization;
	}

	@Override
	public String toString() {
		return "OrdersDTO [withDesignCustomization=" + withDesignCustomization + ", withCustomization="
				+ withCustomization + "]";
	}

}
