package com.divatt.user.helper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.divatt.user.entity.OrderInvoiceEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.repo.OrderSKUDetailsRepo;
import com.divatt.user.serviceDTO.InvoiceUpdatedModel;

@Service
public class UtillUserService {
	
	@Autowired private MongoOperations mongoOperations;
	
	@Autowired private static OrderSKUDetailsRepo dat1;
	
	private static Logger LOGGER=LoggerFactory.getLogger(UtillUserService.class);
	private static List<Integer> mrpList= new ArrayList<>();
	private static List<Integer> salePriceList=new ArrayList<>();
	private static List<Integer> cgstList=new ArrayList<>();
	private static List<Integer> sgstList= new ArrayList<>();
	private static List<Integer> totalList= new ArrayList<>();
	private static List<Integer> discountedAmount= new ArrayList<>();
	public static InvoiceUpdatedModel invoiceUpdatedModelMapper(OrderInvoiceEntity invoiceEntity,OrderSKUDetailsEntity detailsEntity) {
		
		
		InvoiceUpdatedModel invoiceUpdatedModel= new InvoiceUpdatedModel();
		invoiceUpdatedModel.setBilledUserName(invoiceEntity.getUserDetails().getBilling_address().getFullName());
		invoiceUpdatedModel.setBillingCity(invoiceEntity.getUserDetails().getBilling_address().getCity());
		invoiceUpdatedModel.setBillingMobile(invoiceEntity.getUserDetails().getBilling_address().getMobile());
		invoiceUpdatedModel.setBillingState(invoiceEntity.getUserDetails().getBilling_address().getState());
		invoiceUpdatedModel.setBllingAddress(invoiceEntity.getUserDetails().getBilling_address().getAddress2());
		invoiceUpdatedModel.setDiscount(invoiceEntity.getProductDetails().getDiscount()+"");
		invoiceUpdatedModel.setGrossAmount(invoiceEntity.getProductDetails().getMrp()+"");
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
		invoiceUpdatedModel.setTotal(invoiceEntity.getProductDetails().getSalesPrice()+"");
		invoiceUpdatedModel.setDiscount(invoiceEntity.getProductDetails().getDiscount()+"");
		invoiceUpdatedModel.setSgst(invoiceEntity.getProductDetails().getTaxAmount()/2+"");
		invoiceUpdatedModel.setCgst(invoiceEntity.getProductDetails().getTaxAmount()/2+"");
//		mrpList.add(invoiceEntity.getProductDetails().getMrp());
//		salePriceList.add(invoiceEntity.getProductDetails().getSalesPrice());
//		cgstList.add(invoiceEntity.getProductDetails().getTaxAmount()/2);
//		sgstList.add(invoiceEntity.getProductDetails().getTaxAmount()/2);
//		discountedAmount.add(invoiceEntity.getProductDetails().getDiscount());
//		invoiceUpdatedModel.setCgstAmountList(cgstList);
//		invoiceUpdatedModel.setDiscountedAmountList(discountedAmount);
//		invoiceUpdatedModel.setGrossAmountList(mrpList);
//		invoiceUpdatedModel.setSaleAmountList(salePriceList);
//		invoiceUpdatedModel.setSgstAmountList(sgstList);
		return invoiceUpdatedModel;
	}
	public static InvoiceUpdatedModel invoiceMapperRestMap(OrderInvoiceEntity invoiceEntity, OrderSKUDetailsEntity detailsEntity) {
//		List<Integer> mrpList= new ArrayList<>();
//		List<Integer> salePriceList=new ArrayList<>();
//		List<Integer> cgstList=new ArrayList<>();
//		List<Integer> sgstList= new ArrayList<>();
//		List<Integer> totalList= new ArrayList<>();
//		List<Integer> discountedAmount= new ArrayList<>();
		InvoiceUpdatedModel invoiceUpdatedModel= new InvoiceUpdatedModel();
		invoiceUpdatedModel.setProductName(invoiceEntity.getProductDetails().getProductName());
		invoiceUpdatedModel.setQty(invoiceEntity.getProductDetails().getUnits()+"");
		invoiceUpdatedModel.setGrossAmount(invoiceEntity.getProductDetails().getMrp()+"");
		invoiceUpdatedModel.setTotal(invoiceEntity.getProductDetails().getSalesPrice()+"");
		invoiceUpdatedModel.setSgst(invoiceEntity.getProductDetails().getTaxAmount()/2+"");
		invoiceUpdatedModel.setCgst(invoiceEntity.getProductDetails().getTaxAmount()/2+"");
		invoiceUpdatedModel.setDiscount(invoiceEntity.getProductDetails().getDiscount()+"");
		LOGGER.info(invoiceEntity.getProductDetails().getTaxAmount()/2+"");
//		mrpList.add(invoiceEntity.getProductDetails().getMrp());
//		salePriceList.add(invoiceEntity.getProductDetails().getSalesPrice());
//		cgstList.add(invoiceEntity.getProductDetails().getTaxAmount()/2);
//		sgstList.add(invoiceEntity.getProductDetails().getTaxAmount()/2);
//		discountedAmount.add(invoiceEntity.getProductDetails().getDiscount());
//		invoiceUpdatedModel.setCgstAmountList(cgstList);
//		invoiceUpdatedModel.setDiscountedAmountList(discountedAmount);
//		invoiceUpdatedModel.setGrossAmountList(mrpList);
//		invoiceUpdatedModel.setSaleAmountList(salePriceList);
//		invoiceUpdatedModel.setSgstAmountList(sgstList);
		return invoiceUpdatedModel;
	}
}
