package com.divatt.designer.entity;

import java.util.List;

import com.divatt.designer.entity.profile.DesignerProfileEntity;

public class InvoiceMainData {
	private String orderId;
	private String orderDate;
	private String PANNo;
	private String GSTINno;
	private String address;
	private String mobile;
	private String postalCode;
	private String designerName;
	private String invoiceId;
	private String soldBy;
	private List<DesignerProductList> productList;
	private int totalTax;
	private int totalSale;
	private int totalMRP;
	public InvoiceMainData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InvoiceMainData(String orderId, String orderDate, String pANNo, String gSTINno, String address,
			String mobile, String postalCode, String designerName, String invoiceId, String soldBy,
			List<DesignerProductList> productList, int totalTax, int totalSale, int totalMRP) {
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
		this.soldBy = soldBy;
		this.productList = productList;
		this.totalTax = totalTax;
		this.totalSale = totalSale;
		this.totalMRP = totalMRP;
	}
	@Override
	public String toString() {
		return "InvoiceMainData [orderId=" + orderId + ", orderDate=" + orderDate + ", PANNo=" + PANNo + ", GSTINno="
				+ GSTINno + ", address=" + address + ", mobile=" + mobile + ", postalCode=" + postalCode
				+ ", designerName=" + designerName + ", invoiceId=" + invoiceId + ", soldBy=" + soldBy
				+ ", productList=" + productList + ", totalTax=" + totalTax + ", totalSale=" + totalSale + ", totalMRP="
				+ totalMRP + "]";
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
	public String getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(String soldBy) {
		this.soldBy = soldBy;
	}
	public List<DesignerProductList> getProductList() {
		return productList;
	}
	public void setProductList(List<DesignerProductList> productList) {
		this.productList = productList;
	}
	public int getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(int totalTax) {
		this.totalTax = totalTax;
	}
	public int getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(int totalSale) {
		this.totalSale = totalSale;
	}
	public int getTotalMRP() {
		return totalMRP;
	}
	public void setTotalMRP(int totalMRP) {
		this.totalMRP = totalMRP;
	}
	
}
