package com.divatt.designer.services;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.divatt.designer.entity.DesignerInvoiceReq;
import com.divatt.designer.entity.DesignerIvoiceData;
import com.divatt.designer.entity.DesignerProductList;
import com.divatt.designer.entity.InvoiceProductList;
import com.divatt.designer.entity.OrderDetailsEntity;
import com.divatt.designer.entity.ProductInvoice;
import com.divatt.designer.entity.UserAddressEntity;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.DesignerProfileRepo;
import com.divatt.designer.response.GlobalResponce;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

@Service
public class OrderService {
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private DesignerProfileRepo designerProfileRepo;

	public GlobalResponce changeStatus(String orderId, String statusKeyword) {
		try {
//			RestTemplate restTemplate= new RestTemplate();
			ResponseEntity<OrderDetailsEntity> serviceResponse= restTemplate.getForEntity("https://localhost:8082/dev/userOrder/getOrder/"+orderId, OrderDetailsEntity.class);
			//System.out.println(serviceResponse.getBody());
			OrderDetailsEntity updatedOrder=serviceResponse.getBody();
			updatedOrder.setOrderStatus(statusKeyword);
			System.out.println(updatedOrder);
			restTemplate.put("https://localhost:8082/dev/userOrder/updateOrder/" + orderId,updatedOrder ,
					String.class);
			return new GlobalResponce("Success", "Order status updated", 200);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getDesignerInvoice(String orderId) {
		try {
			Map<String, Object> invoiceData= new HashMap<String, Object>();
			ResponseEntity<DesignerInvoiceReq> orderDetailsData= restTemplate.getForEntity("https://localhost:8082/dev/userOrder/getOrder/"+orderId, DesignerInvoiceReq.class);
			DesignerIvoiceData designerIvoiceData= new DesignerIvoiceData();
			UserAddressEntity userAddressData= restTemplate.getForEntity("https://localhost:8082/dev/user/getUserAddress/"+
					orderDetailsData.getBody().getUserId(), UserAddressEntity.class).getBody();
			Optional<DesignerProfileEntity> designerData = designerProfileRepo.findBydesignerId(Long.valueOf(orderDetailsData.getBody().getOrderSKUDetails().get(0).getDesignerId()));
			designerIvoiceData.setAddress(designerData.get().getSocialProfile().getAddress());
			designerIvoiceData.setDesignerName(designerData.get().getDesignerName());
			designerIvoiceData.setGSTINno(designerData.get().getBoutiqueProfile().getGSTIN());
			designerIvoiceData.setInvoiceId(orderDetailsData.getBody().getInvoiceId());
			designerIvoiceData.setMobile(designerData.get().getDesignerProfile().getMobileNo());
			designerIvoiceData.setOrderDate(orderDetailsData.getBody().getOrderDate());
			designerIvoiceData.setOrderId(orderId);
			designerIvoiceData.setPANNo("PANST12558ER");
			designerIvoiceData.setPostalCode("700036");
			int totalSalePrice=0;
			int totalMRP=0;
			int totalTax=0;
			List<DesignerProductList> productList= new ArrayList<>();
			DesignerProductList totalAmount= new DesignerProductList();
			for(int i=0;i<orderDetailsData.getBody().getOrderSKUDetails().size();i++) {
				DesignerProductList designerProductList= new DesignerProductList();
				designerProductList.setProductName(orderDetailsData.getBody().getOrderSKUDetails().get(i).getProductSku());
				designerProductList.setProductDescription(orderDetailsData.getBody().getOrderSKUDetails().get(i).getProductName());
				designerProductList.setUnits(orderDetailsData.getBody().getOrderSKUDetails().get(i).getUnits());
				designerProductList.setSize(orderDetailsData.getBody().getOrderSKUDetails().get(i).getSize());
				designerProductList.setMrp(orderDetailsData.getBody().getOrderSKUDetails().get(i).getMrp());
				designerProductList.setTaxAmount(orderDetailsData.getBody().getOrderSKUDetails().get(i).getTaxAmount());
				designerProductList.setSalesPrice(orderDetailsData.getBody().getOrderSKUDetails().get(i).getMrp()+orderDetailsData.getBody().getOrderSKUDetails().get(i).getTaxAmount());
				totalSalePrice=totalSalePrice+orderDetailsData.getBody().getOrderSKUDetails().get(i).getMrp()+orderDetailsData.getBody().getOrderSKUDetails().get(i).getTaxAmount();
				totalMRP=totalMRP+orderDetailsData.getBody().getOrderSKUDetails().get(i).getMrp();
				totalTax=totalTax+orderDetailsData.getBody().getOrderSKUDetails().get(i).getTaxAmount();
				productList.add(designerProductList);
			}
			totalAmount.setMrp(totalMRP);
			totalAmount.setTaxAmount(totalTax);
			totalAmount.setSalesPrice(totalSalePrice);
			invoiceData.put("ProductData", productList);
			invoiceData.put("TotalData", totalAmount);
			invoiceData.put("DesignerInvoice", designerIvoiceData);
			invoiceData.put("UserInvoice", userAddressData);
			Context context= new Context();
			context.setVariables(invoiceData);
			String htmlContent=templateEngine.process("invoiceUpdatedDesigner.html", context);
			ByteArrayOutputStream target = new ByteArrayOutputStream();
			ConverterProperties converterProperties = new ConverterProperties();
			converterProperties.setBaseUri("http://localhost:8082");
			HtmlConverter.convertToPdf(htmlContent, target, converterProperties);  
			byte[] bytes = target.toByteArray();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=" + "OrderInvoice"+orderId+".pdf");
			return ResponseEntity.ok()
					.headers(headers)
					.contentType(MediaType.APPLICATION_PDF)
					.body(bytes);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
