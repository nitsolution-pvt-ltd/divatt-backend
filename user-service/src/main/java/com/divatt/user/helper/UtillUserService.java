package com.divatt.user.helper;

import com.divatt.user.entity.OrderInvoiceEntity;
import com.divatt.user.serviceDTO.InvoiceUpdatedModel;

public class UtillUserService {

	public static InvoiceUpdatedModel invoiceUpdatedModelMapper(OrderInvoiceEntity invoiceEntity) {
		InvoiceUpdatedModel invoiceUpdatedModel= new InvoiceUpdatedModel();
		invoiceUpdatedModel.setBilledUserName(invoiceEntity.getUserDetails().getBilling_address().getFullName());
		invoiceUpdatedModel.setBillingCity(invoiceEntity.getUserDetails().getBilling_address().getCity());
		invoiceUpdatedModel.setBillingMobile(invoiceEntity.getUserDetails().getBilling_address().getMobile());
		invoiceUpdatedModel.setBillingState(invoiceEntity.getUserDetails().getBilling_address().getState());
		invoiceUpdatedModel.setBllingAddress(invoiceEntity.getUserDetails().getBilling_address().getAddress2());
		invoiceUpdatedModel.setDiscount(invoiceEntity.getProductDetails().getDiscount()+"");
		invoiceUpdatedModel.setGrossAmount(invoiceEntity.getProductDetails().getSalesPrice()+"");
		invoiceUpdatedModel.setInvoiceId(invoiceEntity.getInvoiceId());
		invoiceUpdatedModel.setOrderDate(invoiceEntity.getOrderDatetime());
		invoiceUpdatedModel.setOrderId(invoiceEntity.getOrderId());
		invoiceUpdatedModel.setPincode(invoiceEntity.getUserDetails().getBilling_address().getPostalCode());
		invoiceUpdatedModel.setProductName(invoiceEntity.getProductDetails().getProductName());
		invoiceUpdatedModel.setQty(invoiceEntity.getProductDetails().getUnits()+"");
		invoiceUpdatedModel.setSellerAddress(invoiceEntity.getUserDetails().getShipping_address().getAddress2());
		invoiceUpdatedModel.setSellerCity(invoiceEntity.getUserDetails().getShipping_address().getCity());
		invoiceUpdatedModel.setSellerMobile(invoiceEntity.getDesignerDetails().getMobile());
		invoiceUpdatedModel.setSellerName(invoiceEntity.getProductDetails().getDesignerId()+"");
		invoiceUpdatedModel.setSellerAddress(invoiceEntity.getDesignerDetails().getAddress());
		invoiceUpdatedModel.setShippingAddress(invoiceEntity.getUserDetails().getShipping_address().getAddress2());
		invoiceUpdatedModel.setShippingUserName(invoiceEntity.getUserDetails().getShipping_address().getFullName());
		invoiceUpdatedModel.setShippingCity(invoiceEntity.getUserDetails().getShipping_address().getCity());
		invoiceUpdatedModel.setShippingPincode(invoiceEntity.getUserDetails().getBilling_address().getMobile());
		invoiceUpdatedModel.setShippingState(invoiceEntity.getUserDetails().getShipping_address().getState());
		return invoiceUpdatedModel;
	}
	public static InvoiceUpdatedModel invoiceMapperRestMap(OrderInvoiceEntity invoiceEntity) {
		InvoiceUpdatedModel invoiceUpdatedModel= new InvoiceUpdatedModel();
		invoiceUpdatedModel.setProductName(invoiceEntity.getProductDetails().getProductName());
		invoiceUpdatedModel.setQty(invoiceEntity.getProductDetails().getUnits()+"");
		return invoiceUpdatedModel;
	}
}
