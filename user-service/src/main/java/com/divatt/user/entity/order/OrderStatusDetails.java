package com.divatt.user.entity.order;

import org.json.simple.JSONObject;

import com.divatt.user.dto.ForceReturnOnDTO;

public class OrderStatusDetails {

	private String command;
	private JSONObject ordersDetails;
	private JSONObject packedDetails;
	private JSONObject shippedDetails;
	private JSONObject deliveryDetails;
	private JSONObject cancelOrderDetails;
	private JSONObject cancelRequestDetails;
	private JSONObject cancelFromUser;
	private JSONObject returnFromUser;
	private JSONObject returnFromAdmin;
	private ForceReturnOnDTO forceReturnOnDTO;

	public OrderStatusDetails() {
		super();
		// TODO Auto-generated constructor stub
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

	public JSONObject getCancelFromUser() {
		return cancelFromUser;
	}

	public void setCancelFromUser(JSONObject cancelFromUser) {
		this.cancelFromUser = cancelFromUser;
	}

	public JSONObject getReturnFromUser() {
		return returnFromUser;
	}

	public void setReturnFromUser(JSONObject returnFromUser) {
		this.returnFromUser = returnFromUser;
	}

	public JSONObject getReturnFromAdmin() {
		return returnFromAdmin;
	}

	public void setReturnFromAdmin(JSONObject returnFromAdmin) {
		this.returnFromAdmin = returnFromAdmin;
	}

	public ForceReturnOnDTO getForceReturnOnDTO() {
		return forceReturnOnDTO;
	}

	public void setForceReturnOnDTO(ForceReturnOnDTO forceReturnOnDTO) {
		this.forceReturnOnDTO = forceReturnOnDTO;
	}

	
}
