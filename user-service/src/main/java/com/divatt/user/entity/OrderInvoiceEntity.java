package com.divatt.user.entity;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	private Object designerDetails;
	
	@Field(name = "user_details") 
	private Object userDetails;
	
	@Field(name = "product_details") 
	private Object productDetails;

	public OrderInvoiceEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OrderInvoiceEntity [id=" + id + ", invoiceId=" + invoiceId + ", invoiceDatetime=" + invoiceDatetime
				+ ", orderId=" + orderId + ", orderDatetime=" + orderDatetime + ", designerDetails=" + designerDetails
				+ ", userDetails=" + userDetails + ", productDetails=" + productDetails + "]";
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

	public Object getDesignerDetails() {
		return designerDetails;
	}

	public void setDesignerDetails(Object designerDetails) {
		this.designerDetails = designerDetails;
	}

	public Object getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(Object userDetails) {
		this.userDetails = userDetails;
	}

	public Object getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(Object productDetails) {
		this.productDetails = productDetails;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	public OrderInvoiceEntity(Integer id, String invoiceId, String invoiceDatetime,
			@NotEmpty(message = "Order id is required") String orderId,
			@NotEmpty(message = "Order datetime is required") String orderDatetime, Object designerDetails,
			Object userDetails, Object productDetails) {
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

	
	

}
