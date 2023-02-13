package com.divatt.user.entity;


import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_invoices")
public class OrderInvoiceEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_invoices";
	
	@Id
	private Integer id;
	
	@Field(name = "invoice_id") 
	private String invoiceId;
	
	@Field(name = "invoice_datetime") 
	private String invoiceDatetime;
	
	
	@Field(name = "order_id") 
	@NotEmpty(message = "Order id is required")
	private String orderId;
	
	@NotEmpty(message = "Order datetime is required")
	@Field(name = "order_datetime") 
	private String orderDatetime;
	
	@Field(name = "designer_details") 
	private DesignerDetails designerDetails;
	
	@Field(name = "user_details") 
	private UserDetails userDetails;
	
	@Field(name = "product_details") 
	private ProductDetails productDetails;

	public OrderInvoiceEntity() {
		super();
	}

	@Override
	public String toString() {
		return "OrderInvoiceEntity [id=" + id + ", invoiceId=" + invoiceId + ", invoiceDatetime=" + invoiceDatetime
				+ ", orderId=" + orderId + ", orderDatetime=" + orderDatetime + ", designerDetails=" + designerDetails
				+ ", userDetails=" + userDetails + ", productDetails=" + productDetails + "]";
	}

	public OrderInvoiceEntity(Integer id, String invoiceId, String invoiceDatetime,
			@NotEmpty(message = "Order id is required") String orderId,
			@NotEmpty(message = "Order datetime is required") String orderDatetime, DesignerDetails designerDetails,
			UserDetails userDetails, ProductDetails productDetails) {
		super();
		this.id = id;
		this.invoiceId = invoiceId;
		this.invoiceDatetime = invoiceDatetime;
		this.orderId = orderId;
		this.orderDatetime = orderDatetime;
		this.designerDetails = designerDetails;
		this.userDetails = userDetails;
		this.productDetails = productDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceDatetime() {
		return invoiceDatetime;
	}

	public void setInvoiceDatetime(String invoiceDatetime) {
		this.invoiceDatetime = invoiceDatetime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderDatetime() {
		return orderDatetime;
	}

	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}

	public DesignerDetails getDesignerDetails() {
		return designerDetails;
	}

	public void setDesignerDetails(DesignerDetails designerDetails) {
		this.designerDetails = designerDetails;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public ProductDetails getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(ProductDetails productDetails) {
		this.productDetails = productDetails;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	

}
