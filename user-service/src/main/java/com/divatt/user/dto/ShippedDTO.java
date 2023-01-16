package com.divatt.user.dto;

public class ShippedDTO {

	private String courierName;
	private String awbNumber;

	public ShippedDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShippedDTO(String courierName, String awbNumber) {
		super();
		this.courierName = courierName;
		this.awbNumber = awbNumber;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getAwbNumber() {
		return awbNumber;
	}

	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}

	@Override
	public String toString() {
		return "ShippedDTO [courierName=" + courierName + ", awbNumber=" + awbNumber + "]";
	}

}
