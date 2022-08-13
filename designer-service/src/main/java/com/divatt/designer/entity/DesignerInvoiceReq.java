package com.divatt.designer.entity;

import java.util.List;

import org.json.simple.JSONObject;

public class DesignerInvoiceReq {

	    public String deliveryCheckUrl;
	    public String orderId;
	    public int shippingCharges;
	    public String orderStatus;
	    public int discount;
	    public int mrp;
	    public int userId;
	    public String createdOn;
	    public double totalAmount;
	    public List<InvoiceProductList> OrderSKUDetails;
	    public String deliveryMode;
	    public JSONObject paymentData;
	    public JSONObject shippingAddress;
	    public String invoiceId;
	    public int id;
	    public JSONObject billingAddress;
	    public String deliveryDate;
	    public double taxAmount;
	    public String orderDate;
	    public String deliveryStatus;
		public DesignerInvoiceReq() {
			super();
			// TODO Auto-generated constructor stub
		}
		public DesignerInvoiceReq(String deliveryCheckUrl, String orderId, int shippingCharges, String orderStatus,
				int discount, int mrp, int userId, String createdOn, double totalAmount,
				List<InvoiceProductList> orderSKUDetails, String deliveryMode, JSONObject paymentData,
				JSONObject shippingAddress, String invoiceId, int id, JSONObject billingAddress, String deliveryDate,
				double taxAmount, String orderDate, String deliveryStatus) {
			super();
			this.deliveryCheckUrl = deliveryCheckUrl;
			this.orderId = orderId;
			this.shippingCharges = shippingCharges;
			this.orderStatus = orderStatus;
			this.discount = discount;
			this.mrp = mrp;
			this.userId = userId;
			this.createdOn = createdOn;
			this.totalAmount = totalAmount;
			this.OrderSKUDetails = orderSKUDetails;
			this.deliveryMode = deliveryMode;
			this.paymentData = paymentData;
			this.shippingAddress = shippingAddress;
			this.invoiceId = invoiceId;
			this.id = id;
			this.billingAddress = billingAddress;
			this.deliveryDate = deliveryDate;
			this.taxAmount = taxAmount;
			this.orderDate = orderDate;
			this.deliveryStatus = deliveryStatus;
		}
		public String getDeliveryCheckUrl() {
			return deliveryCheckUrl;
		}
		public void setDeliveryCheckUrl(String deliveryCheckUrl) {
			this.deliveryCheckUrl = deliveryCheckUrl;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public int getShippingCharges() {
			return shippingCharges;
		}
		public void setShippingCharges(int shippingCharges) {
			this.shippingCharges = shippingCharges;
		}
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
		public int getDiscount() {
			return discount;
		}
		public void setDiscount(int discount) {
			this.discount = discount;
		}
		public int getMrp() {
			return mrp;
		}
		public void setMrp(int mrp) {
			this.mrp = mrp;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getCreatedOn() {
			return createdOn;
		}
		public void setCreatedOn(String createdOn) {
			this.createdOn = createdOn;
		}
		public double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
		}
		public List<InvoiceProductList> getOrderSKUDetails() {
			return OrderSKUDetails;
		}
		public void setOrderSKUDetails(List<InvoiceProductList> orderSKUDetails) {
			OrderSKUDetails = orderSKUDetails;
		}
		public String getDeliveryMode() {
			return deliveryMode;
		}
		public void setDeliveryMode(String deliveryMode) {
			this.deliveryMode = deliveryMode;
		}
		public JSONObject getPaymentData() {
			return paymentData;
		}
		public void setPaymentData(JSONObject paymentData) {
			this.paymentData = paymentData;
		}
		public JSONObject getShippingAddress() {
			return shippingAddress;
		}
		public void setShippingAddress(JSONObject shippingAddress) {
			this.shippingAddress = shippingAddress;
		}
		public String getInvoiceId() {
			return invoiceId;
		}
		public void setInvoiceId(String invoiceId) {
			this.invoiceId = invoiceId;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public JSONObject getBillingAddress() {
			return billingAddress;
		}
		public void setBillingAddress(JSONObject billingAddress) {
			this.billingAddress = billingAddress;
		}
		public String getDeliveryDate() {
			return deliveryDate;
		}
		public void setDeliveryDate(String deliveryDate) {
			this.deliveryDate = deliveryDate;
		}
		public double getTaxAmount() {
			return taxAmount;
		}
		public void setTaxAmount(double taxAmount) {
			this.taxAmount = taxAmount;
		}
		public String getOrderDate() {
			return orderDate;
		}
		public void setOrderDate(String orderDate) {
			this.orderDate = orderDate;
		}
		public String getDeliveryStatus() {
			return deliveryStatus;
		}
		public void setDeliveryStatus(String deliveryStatus) {
			this.deliveryStatus = deliveryStatus;
		}
		@Override
		public String toString() {
			return "DesignerInvoiceReq [deliveryCheckUrl=" + deliveryCheckUrl + ", orderId=" + orderId
					+ ", shippingCharges=" + shippingCharges + ", orderStatus=" + orderStatus + ", discount=" + discount
					+ ", mrp=" + mrp + ", userId=" + userId + ", createdOn=" + createdOn + ", totalAmount="
					+ totalAmount + ", OrderSKUDetails=" + OrderSKUDetails + ", deliveryMode=" + deliveryMode
					+ ", paymentData=" + paymentData + ", shippingAddress=" + shippingAddress + ", invoiceId="
					+ invoiceId + ", id=" + id + ", billingAddress=" + billingAddress + ", deliveryDate=" + deliveryDate
					+ ", taxAmount=" + taxAmount + ", orderDate=" + orderDate + ", deliveryStatus=" + deliveryStatus
					+ "]";
		}
		
}
