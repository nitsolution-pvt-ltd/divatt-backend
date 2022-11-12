package com.divatt.user.serviceDTO;

public class ShippedDTO {

	private String courierName;
	private String trackingName;

	public ShippedDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShippedDTO(String courierName, String trackingName) {
		super();
		this.courierName = courierName;
		this.trackingName = trackingName;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getTrackingName() {
		return trackingName;
	}

	public void setTrackingName(String trackingName) {
		this.trackingName = trackingName;
	}

	@Override
	public String toString() {
		return "ShippedDTO [courierName=" + courierName + ", trackingName=" + trackingName + "]";
	}

}
