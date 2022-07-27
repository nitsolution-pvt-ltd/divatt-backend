package com.divatt.designer.entity;

public class StockEntity {

	private String productDescription;
	private String productImage;
	private String size;
	private Integer stock;
	private Integer price;
	private String senderEmail;
	private String productLink;
	private String designerName;
	public StockEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StockEntity(String productDescription, String productImage, String size, Integer stock, Integer price,
			String senderEmail, String productLink, String designerName) {
		super();
		this.productDescription = productDescription;
		this.productImage = productImage;
		this.size = size;
		this.stock = stock;
		this.price = price;
		this.senderEmail = senderEmail;
		this.productLink = productLink;
		this.designerName = designerName;
	}
	@Override
	public String toString() {
		return "StockEntity [productDescription=" + productDescription + ", productImage=" + productImage + ", size="
				+ size + ", stock=" + stock + ", price=" + price + ", senderEmail=" + senderEmail + ", productLink="
				+ productLink + ", designerName=" + designerName + "]";
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getProductLink() {
		return productLink;
	}
	public void setProductLink(String productLink) {
		this.productLink = productLink;
	}
	public String getDesignerName() {
		return designerName;
	}
	public void setDesignerName(String designerName) {
		this.designerName = designerName;
	}
	
}
