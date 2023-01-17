package com.divatt.user.serviceDTO;

public class OrderPlacedDTO {

	private String userName;

	private String designerId;

	private String orderId;

	private String billAddress1;

	private String billCity;

	private String billAddress2;

	private String billState;

	private String billPostalCode;

	private String shippingAddress;

	private String orderDate;

	private String images;

	private String productName;

	private String units;

	private String size;

	private String mrp;

	private String taxAmount;

	private String total;

	private String grandTotal;

	private String totalMrp;

	private String totalTax;

	private String discount;

	private String displayName;

	private String giftWrapAmount;

	private String totalGiftWrapAmount;

	public OrderPlacedDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderPlacedDTO(String userName, String designerId, String orderId, String billAddress1, String billCity,
			String billAddress2, String billState, String billPostalCode, String shippingAddress, String orderDate,
			String images, String productName, String units, String size, String mrp, String taxAmount, String total,
			String grandTotal, String totalMrp, String totalTax, String discount, String displayName,
			String giftWrapAmount, String totalGiftWrapAmount) {
		super();
		this.userName = userName;
		this.designerId = designerId;
		this.orderId = orderId;
		this.billAddress1 = billAddress1;
		this.billCity = billCity;
		this.billAddress2 = billAddress2;
		this.billState = billState;
		this.billPostalCode = billPostalCode;
		this.shippingAddress = shippingAddress;
		this.orderDate = orderDate;
		this.images = images;
		this.productName = productName;
		this.units = units;
		this.size = size;
		this.mrp = mrp;
		this.taxAmount = taxAmount;
		this.total = total;
		this.grandTotal = grandTotal;
		this.totalMrp = totalMrp;
		this.totalTax = totalTax;
		this.discount = discount;
		this.displayName = displayName;
		this.giftWrapAmount = giftWrapAmount;
		this.totalGiftWrapAmount = totalGiftWrapAmount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDesignerId() {
		return designerId;
	}

	public void setDesignerId(String designerId) {
		this.designerId = designerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBillAddress1() {
		return billAddress1;
	}

	public void setBillAddress1(String billAddress1) {
		this.billAddress1 = billAddress1;
	}

	public String getBillCity() {
		return billCity;
	}

	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}

	public String getBillAddress2() {
		return billAddress2;
	}

	public void setBillAddress2(String billAddress2) {
		this.billAddress2 = billAddress2;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public String getBillPostalCode() {
		return billPostalCode;
	}

	public void setBillPostalCode(String billPostalCode) {
		this.billPostalCode = billPostalCode;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMrp() {
		return mrp;
	}

	public void setMrp(String mrp) {
		this.mrp = mrp;
	}

	public String getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getTotalMrp() {
		return totalMrp;
	}

	public void setTotalMrp(String totalMrp) {
		this.totalMrp = totalMrp;
	}

	public String getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(String totalTax) {
		this.totalTax = totalTax;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGiftWrapAmount() {
		return giftWrapAmount;
	}

	public void setGiftWrapAmount(String giftWrapAmount) {
		this.giftWrapAmount = giftWrapAmount;
	}

	public String getTotalGiftWrapAmount() {
		return totalGiftWrapAmount;
	}

	public void setTotalGiftWrapAmount(String totalGiftWrapAmount) {
		this.totalGiftWrapAmount = totalGiftWrapAmount;
	}

	@Override
	public String toString() {
		return "OrderPlacedDTO [userName=" + userName + ", designerId=" + designerId + ", orderId=" + orderId
				+ ", billAddress1=" + billAddress1 + ", billCity=" + billCity + ", billAddress2=" + billAddress2
				+ ", billState=" + billState + ", billPostalCode=" + billPostalCode + ", shippingAddress="
				+ shippingAddress + ", orderDate=" + orderDate + ", images=" + images + ", productName=" + productName
				+ ", units=" + units + ", size=" + size + ", mrp=" + mrp + ", taxAmount=" + taxAmount + ", total="
				+ total + ", grandTotal=" + grandTotal + ", totalMrp=" + totalMrp + ", totalTax=" + totalTax
				+ ", discount=" + discount + ", displayName=" + displayName + ", giftWrapAmount=" + giftWrapAmount
				+ ", totalGiftWrapAmount=" + totalGiftWrapAmount + "]";
	}

}
