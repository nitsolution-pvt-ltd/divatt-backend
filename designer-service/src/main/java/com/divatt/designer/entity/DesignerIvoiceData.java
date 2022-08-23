package com.divatt.designer.entity;

public class DesignerIvoiceData {

	private String orderId;
	private String orderDate;
	private String PANNo;
	private String GSTINno;
	private String address;
	private String mobile;
	private String postalCode;
	private String designerName;
	private String invoiceId;
	public DesignerIvoiceData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DesignerIvoiceData(String orderId, String orderDate, String pANNo, String gSTINno, String address,
			String mobile, String postalCode, String designerName, String invoiceId) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.PANNo = pANNo;
		this.GSTINno = gSTINno;
		this.address = address;
		this.mobile = mobile;
		this.postalCode = postalCode;
		this.designerName = designerName;
		this.invoiceId = invoiceId;
	}
	@Override
	public String toString() {
		return "DesignerIvoiceData [orderId=" + orderId + ", orderDate=" + orderDate + ", PANNo=" + PANNo + ", GSTINno="
				+ GSTINno + ", address=" + address + ", mobile=" + mobile + ", postalCode=" + postalCode
				+ ", designerName=" + designerName + ", invoiceId=" + invoiceId + "]";
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getPANNo() {
		return PANNo;
	}
	public void setPANNo(String pANNo) {
		PANNo = pANNo;
	}
	public String getGSTINno() {
		return GSTINno;
	}
	public void setGSTINno(String gSTINno) {
		GSTINno = gSTINno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getDesignerName() {
		return designerName;
	}
	public void setDesignerName(String designerName) {
		this.designerName = designerName;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	
}
