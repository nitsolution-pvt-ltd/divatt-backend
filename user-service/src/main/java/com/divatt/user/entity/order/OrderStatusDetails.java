package com.divatt.user.entity.order;

import org.json.simple.JSONObject;

public class OrderStatusDetails {

	private String command;
	private JSONObject ordersDetails;
	private JSONObject packedDetails;
	private JSONObject shippedDetails;
	private JSONObject deliveryDetails;
	private JSONObject cancelOrderDetails;
	private JSONObject cancelRequestDetails;

	public OrderStatusDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderStatusDetails(String command, JSONObject ordersDetails, JSONObject packedDetails,
			JSONObject shippedDetails, JSONObject deliveryDetails, JSONObject cancelOrderDetails, JSONObject cancelRequestDetails) {
		super();
		this.command = command;
		this.ordersDetails = ordersDetails;
		this.packedDetails = packedDetails;
		this.shippedDetails = shippedDetails;
		this.deliveryDetails = deliveryDetails;
		this.cancelOrderDetails = cancelOrderDetails;
		this.cancelRequestDetails = cancelRequestDetails;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public JSONObject getOrdersDetails() {
		return ordersDetails;
	}

	public void setOrdersDetails(JSONObject ordersDetails) {
		this.ordersDetails = ordersDetails;
	}

	public JSONObject getPackedDetails() {
		return packedDetails;
	}

	public void setPackedDetails(JSONObject packedDetails) {
		this.packedDetails = packedDetails;
	}

	public JSONObject getShippedDetails() {
		return shippedDetails;
	}

	public void setShippedDetails(JSONObject shippedDetails) {
		this.shippedDetails = shippedDetails;
	}

	public JSONObject getDeliveryDetails() {
		return deliveryDetails;
	}

	public void setDeliveryDetails(JSONObject deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}

	public JSONObject getCancelOrderDetails() {
		return cancelOrderDetails;
	}

	public void setCancelOrderDetails(JSONObject cancelOrderDetails) {
		this.cancelOrderDetails = cancelOrderDetails;
	}
	
	public JSONObject getCancelRequestDetails() {
		return cancelRequestDetails;
	}

	public void setCancelRequestDetails(JSONObject cancelRequestDetails) {
		this.cancelRequestDetails = cancelRequestDetails;
	}

	@Override
	public String toString() {
		return "OrderStatusDetails [command=" + command + ", ordersDetails=" + ordersDetails + ", packedDetails="
				+ packedDetails + ", shippedDetails=" + shippedDetails + ", deliveryDetails=" + deliveryDetails
				+ ", cancelOrderDetails=" + cancelOrderDetails + ", cancelRequestDetails=" + cancelRequestDetails +"]";
	}

}
