package com.divatt.designer.entity;

public class EmailEntity {

	private String productName;
	private String productDesc;
	private String producyDiscount;
	private String productId;
	private String productDesignerName;
	private String productImage;
	private String userName;
	private String productPrice;
	private String designerImage;
	public EmailEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmailEntity(String productName, String productDesc, String producyDiscount, String productId,
			String productDesignerName, String productImage, String userName, String productPrice,
			String designerImage) {
		super();
		this.productName = productName;
		this.productDesc = productDesc;
		this.producyDiscount = producyDiscount;
		this.productId = productId;
		this.productDesignerName = productDesignerName;
		this.productImage = productImage;
		this.userName = userName;
		this.productPrice = productPrice;
		this.designerImage = designerImage;
	}
	@Override
	public String toString() {
		return "EmailEntity [productName=" + productName + ", productDesc=" + productDesc + ", producyDiscount="
				+ producyDiscount + ", productId=" + productId + ", productDesignerName=" + productDesignerName
				+ ", productImage=" + productImage + ", userName=" + userName + ", productPrice=" + productPrice
				+ ", designerImage=" + designerImage + "]";
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProducyDiscount() {
		return producyDiscount;
	}
	public void setProducyDiscount(String producyDiscount) {
		this.producyDiscount = producyDiscount;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductDesignerName() {
		return productDesignerName;
	}
	public void setProductDesignerName(String productDesignerName) {
		this.productDesignerName = productDesignerName;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getDesignerImage() {
		return designerImage;
	}
	public void setDesignerImage(String designerImage) {
		this.designerImage = designerImage;
	}
	
}
