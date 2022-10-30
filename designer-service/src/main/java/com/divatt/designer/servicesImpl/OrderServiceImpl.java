package com.divatt.designer.servicesImpl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.divatt.designer.config.JWTConfig;
import com.divatt.designer.entity.DesignerInvoiceReq;
import com.divatt.designer.entity.DesignerIvoiceData;
import com.divatt.designer.entity.DesignerProductList;
import com.divatt.designer.entity.InvoiceMainData;
import com.divatt.designer.entity.OrderDetailsEntity;
import com.divatt.designer.entity.OrderSKUDetailsEntity;
import com.divatt.designer.entity.UserAddressEntity;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.DesignerProfileRepo;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.OrderService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private DesignerProfileRepo designerProfileRepo;

	@Autowired
	private JWTConfig jwtConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	public GlobalResponce changeStatus(String orderId, String statusKeyword) {
		try {
//				RestTemplate restTemplate= new RestTemplate();
			ResponseEntity<OrderDetailsEntity> serviceResponse = restTemplate
					.getForEntity("https://localhost:8082/dev/userOrder/getOrder/" + orderId, OrderDetailsEntity.class);
			// System.out.println(serviceResponse.getBody());
			OrderDetailsEntity updatedOrder = serviceResponse.getBody();
			updatedOrder.setOrderStatus(statusKeyword);
			System.out.println(updatedOrder);
			restTemplate.put("https://localhost:8082/dev/userOrder/updateOrder/" + orderId, updatedOrder, String.class);
			return new GlobalResponce("Success", "Order status updated", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getDesignerInvoice(String orderId) {
		try {
			Map<String, Object> invoiceData = new HashMap<String, Object>();
			ResponseEntity<DesignerInvoiceReq> orderDetailsData = restTemplate
					.getForEntity("https://localhost:8082/dev/userOrder/getOrder/" + orderId, DesignerInvoiceReq.class);
			DesignerIvoiceData designerIvoiceData = new DesignerIvoiceData();
			UserAddressEntity userAddressData = restTemplate.getForEntity(
					"https://localhost:8082/dev/user/getUserAddress/" + orderDetailsData.getBody().getUserId(),
					UserAddressEntity.class).getBody();
			Optional<DesignerProfileEntity> designerData = designerProfileRepo.findBydesignerId(
					Long.valueOf(orderDetailsData.getBody().getOrderSKUDetails().get(0).getDesignerId()));
			designerIvoiceData.setAddress(designerData.get().getSocialProfile().getAddress());
			designerIvoiceData.setDesignerName(designerData.get().getDesignerName());
			designerIvoiceData.setGSTINno(designerData.get().getBoutiqueProfile().getGSTIN());
			designerIvoiceData.setInvoiceId(orderDetailsData.getBody().getInvoiceId());
			designerIvoiceData.setMobile(designerData.get().getDesignerProfile().getMobileNo());
			designerIvoiceData.setOrderDate(orderDetailsData.getBody().getOrderDate());
			designerIvoiceData.setOrderId(orderId);
			designerIvoiceData.setPANNo("PANST12558ER");
			designerIvoiceData.setPostalCode("700036");
			int totalSalePrice = 0;
			int totalMRP = 0;
			int totalTax = 0;
			List<DesignerProductList> productList = new ArrayList<>();
			DesignerProductList totalAmount = new DesignerProductList();
			for (int i = 0; i < orderDetailsData.getBody().getOrderSKUDetails().size(); i++) {
				DesignerProductList designerProductList = new DesignerProductList();
				designerProductList
						.setProductName(orderDetailsData.getBody().getOrderSKUDetails().get(i).getProductSku());
				designerProductList
						.setProductDescription(orderDetailsData.getBody().getOrderSKUDetails().get(i).getProductName());
				designerProductList.setUnits(orderDetailsData.getBody().getOrderSKUDetails().get(i).getUnits());
				designerProductList.setSize(orderDetailsData.getBody().getOrderSKUDetails().get(i).getSize());
				designerProductList.setMrp(orderDetailsData.getBody().getOrderSKUDetails().get(i).getMrp());
				designerProductList.setTaxAmount(orderDetailsData.getBody().getOrderSKUDetails().get(i).getTaxAmount());
				designerProductList.setSalesPrice(orderDetailsData.getBody().getOrderSKUDetails().get(i).getMrp()
						+ orderDetailsData.getBody().getOrderSKUDetails().get(i).getTaxAmount());
				totalSalePrice = totalSalePrice + orderDetailsData.getBody().getOrderSKUDetails().get(i).getMrp()
						+ orderDetailsData.getBody().getOrderSKUDetails().get(i).getTaxAmount();
				totalMRP = totalMRP + orderDetailsData.getBody().getOrderSKUDetails().get(i).getMrp();
				totalTax = totalTax + orderDetailsData.getBody().getOrderSKUDetails().get(i).getTaxAmount();
				productList.add(designerProductList);
			}
			totalAmount.setMrp(totalMRP);
			totalAmount.setTaxAmount(totalTax);
			totalAmount.setSalesPrice(totalSalePrice);
			invoiceData.put("ProductData", productList);
			invoiceData.put("TotalData", totalAmount);
			invoiceData.put("DesignerInvoice", designerIvoiceData);
			invoiceData.put("UserInvoice", userAddressData);
			Context context = new Context();
			context.setVariables(invoiceData);
			String htmlContent = templateEngine.process("invoiceUpdatedDesigner.html", context);
			ByteArrayOutputStream target = new ByteArrayOutputStream();
			ConverterProperties converterProperties = new ConverterProperties();
			converterProperties.setBaseUri("http://localhost:8082");
			HtmlConverter.convertToPdf(htmlContent, target, converterProperties);
			byte[] bytes = target.toByteArray();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=" + "OrderInvoice" + orderId + ".pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bytes);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public String getUserPDFService(String orderId) {
		try {
			DesignerInvoiceReq orderDetailsData = restTemplate
					.getForEntity("https://localhost:9095/dev/userOrder/getOrder/" + orderId, DesignerInvoiceReq.class)
					.getBody();
			orderDetailsData.getBillingAddress();
			Map<String, Object> userproductDetails = new HashMap<String, Object>();
			List<InvoiceMainData> invoiceData = new ArrayList<InvoiceMainData>();
			List<DesignerProfileEntity> designerProfileList = designerProfileRepo.findAll();
			List<Long> allDesignerList = new ArrayList<Long>();
			for (int i = 0; i < designerProfileList.size(); i++) {
				for (int a = 0; a < orderDetailsData.getOrderSKUDetails().size(); a++) {
					if (designerProfileList.get(i).getDesignerId()
							.equals(Long.valueOf(orderDetailsData.getOrderSKUDetails().get(a).getDesignerId()))) {
						if (!allDesignerList
								.contains(Long.valueOf(orderDetailsData.getOrderSKUDetails().get(a).getDesignerId()))) {
							allDesignerList
									.add(Long.valueOf(orderDetailsData.getOrderSKUDetails().get(a).getDesignerId()));
						}
					}
				}
			}
			for (int i = 0; i < allDesignerList.size(); i++) {
				int totalTax = 0;
				int totalSale = 0;
				int totalMRP = 0;
				DesignerProfileEntity designerProfileEntity = designerProfileRepo
						.findBydesignerId(Long.valueOf(orderDetailsData.getOrderSKUDetails().get(i).getDesignerId()))
						.get();
				InvoiceMainData invoiceMainData = new InvoiceMainData();
				invoiceMainData.setOrderId(orderId);
				invoiceMainData.setAddress(designerProfileEntity.getSocialProfile().getAddress());
				invoiceMainData.setDesignerName(designerProfileEntity.getDesignerName());
				invoiceMainData.setGSTINno(designerProfileEntity.getBoutiqueProfile().getGSTIN());
				invoiceMainData.setInvoiceId(orderDetailsData.getInvoiceId());
				invoiceMainData.setMobile(designerProfileEntity.getDesignerProfile().getMobileNo());
				invoiceMainData.setOrderDate(orderDetailsData.getOrderDate());
				invoiceMainData.setPANNo("PANST12358");
				invoiceMainData.setPostalCode("700036");
				invoiceMainData.setSoldBy(designerProfileEntity.getBoutiqueProfile().getFirmName());
				List<DesignerProductList> designerProductData = new ArrayList<DesignerProductList>();
				for (int a = 0; a < orderDetailsData.getOrderSKUDetails().size(); a++) {
					if (orderDetailsData.getOrderSKUDetails().get(i).getDesignerId() == orderDetailsData
							.getOrderSKUDetails().get(a).getDesignerId()) {
						DesignerProductList productData = new DesignerProductList();
						productData.setProductName(orderDetailsData.getOrderSKUDetails().get(a).getProductSku());
						productData
								.setProductDescription(orderDetailsData.getOrderSKUDetails().get(a).getProductName());
						productData.setUnits(orderDetailsData.getOrderSKUDetails().get(a).getUnits());
						productData.setSize(orderDetailsData.getOrderSKUDetails().get(a).getSize());
						productData.setSalesPrice(orderDetailsData.getOrderSKUDetails().get(a).getMrp()
								+ orderDetailsData.getOrderSKUDetails().get(a).getTaxAmount());
						productData.setTaxAmount(orderDetailsData.getOrderSKUDetails().get(a).getTaxAmount());
						productData.setMrp(orderDetailsData.getOrderSKUDetails().get(a).getMrp());
						productData.setProductId(orderDetailsData.getOrderSKUDetails().get(a).getProductId());
						totalTax = totalTax + orderDetailsData.getOrderSKUDetails().get(a).getTaxAmount();
						totalSale = totalSale + orderDetailsData.getOrderSKUDetails().get(a).getMrp()
								+ orderDetailsData.getOrderSKUDetails().get(a).getTaxAmount();
						totalMRP = totalMRP + orderDetailsData.getOrderSKUDetails().get(a).getMrp();
						designerProductData.add(productData);
						invoiceMainData.setProductList(designerProductData);
					}
				}
				invoiceMainData.setTotalMRP(totalMRP);
				invoiceMainData.setTotalSale(totalSale);
				invoiceMainData.setTotalTax(totalTax);
				invoiceData.add(invoiceMainData);
			}
			userproductDetails.put("UserInvoiceData", invoiceData);
			Context context = new Context();
			context.setVariables(userproductDetails);
			String htmlContent = templateEngine.process("invoiceUpdated.html", context);
			return htmlContent;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Object getCountOrderService(String token) {
		try {
			LOGGER.info(jwtConfig.extractUsername(token.substring(7)));

			List<Long> collect = designerProfileRepo.findAll().stream().filter(
					e -> jwtConfig.extractUsername(token.substring(7)).equals(e.getDesignerProfile().getEmail()))
					.map(e -> e.getDesignerId()).collect(Collectors.toList());
			LOGGER.info(collect.get(0) + "");
			return restTemplate
					.getForEntity("https://localhost:8082/dev/userOrder/designerOrderCount/" + collect.get(0),
							Object.class)
					.getBody();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce changeitemStatus(String orderId, String status) {
		try {

			LOGGER.info(orderId);

			OrderSKUDetailsEntity serviceResponse = restTemplate.getForObject(
					"https://localhost:8082/dev/userOrder/orderDetails/" + orderId, OrderSKUDetailsEntity.class);
			String itemStatus = serviceResponse.getOrderItemStatus();

			if (!serviceResponse.getOrderItemStatus().equals(status)) {

				LOGGER.info(serviceResponse.toString());

				serviceResponse.setOrderItemStatus(status);
			} else {
				throw new CustomException("ItemStatus already in " + status);

			}
			LOGGER.info(serviceResponse.toString());

			restTemplate.put("https://localhost:8082/dev/userOrder/updateOrder/" + orderId, serviceResponse,
					OrderSKUDetailsEntity.class);

			return new GlobalResponce("Success", "Order status updated Change " + itemStatus + " to " + status, 200);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());

		}
	}

}
