package com.divatt.user.serviceDTO;

public class CancelEmailJSON {
	
	private String OrderId;
	private String ProductName;
	private String ProductImages;
	private String ProductSize;
	private String SalePrice;
	private String userName;
	private String designerName;
	private String comment;
	public CancelEmailJSON() {
		super();
	}
	public CancelEmailJSON(String orderId, String productName, String productImages, String productSize,
			String salePrice, String userName, String designerName, String comment) {
		super();
		this.OrderId = orderId;
		this.ProductName = productName;
		this.ProductImages = productImages;
		this.ProductSize = productSize;
		this.SalePrice = salePrice;
		this.userName = userName;
		this.designerName = designerName;
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "CancelEmailJSON [OrderId=" + OrderId + ", ProductName=" + ProductName + ", ProductImages="
				+ ProductImages + ", ProductSize=" + ProductSize + ", SalePrice=" + SalePrice + ", userName=" + userName
				+ ", designerName=" + designerName + ", comment=" + comment + "]";
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getProductImages() {
		return ProductImages;
	}
	public void setProductImages(String productImages) {
		ProductImages = productImages;
	}
	public String getProductSize() {
		return ProductSize;
	}
	public void setProductSize(String productSize) {
		ProductSize = productSize;
	}
	public String getSalePrice() {
		return SalePrice;
	}
	public void setSalePrice(String salePrice) {
		SalePrice = salePrice;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDesignerName() {
		return designerName;
	}
	public void setDesignerName(String designerName) {
		this.designerName = designerName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
