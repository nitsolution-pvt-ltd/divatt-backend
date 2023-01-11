package com.divatt.user.utill;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.divatt.user.entity.OrderAndPaymentGlobalEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.repo.orderPaymenRepo.UserOrderPaymentRepo;
import com.divatt.user.serviceDTO.OrderPlacedDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;

@Component
public class CommonUtility {

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private Environment env;
	
	@Autowired
	private Gson gson;

	@Autowired
	private UserOrderPaymentRepo userOrderPaymentRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);

	public OrderPlacedDTO placedOrder(OrderAndPaymentGlobalEntity orderAndPaymentGlobalEntity) {

		OrderPlacedDTO dto = new OrderPlacedDTO();

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(orderAndPaymentGlobalEntity.getOrderDetailsEntity().getUserId()));
		UserLoginEntity userLoginEntity = mongoOperations.findOne(query, UserLoginEntity.class);
		String userName = userLoginEntity.getFirstName() + " " + userLoginEntity.getLastName();
		dto.setUserName(userName);
		dto.setOrderId(orderAndPaymentGlobalEntity.getOrderDetailsEntity().getOrderId());
		dto.setOrderDate(orderAndPaymentGlobalEntity.getOrderDetailsEntity().getOrderDate());
		dto.setBillAddress1(orderAndPaymentGlobalEntity.getOrderDetailsEntity().getBillingAddress().getAddress1());
		dto.setBillAddress2(orderAndPaymentGlobalEntity.getOrderDetailsEntity().getBillingAddress().getAddress2());
		dto.setBillCity(orderAndPaymentGlobalEntity.getOrderDetailsEntity().getBillingAddress().getCity());
		dto.setBillState(orderAndPaymentGlobalEntity.getOrderDetailsEntity().getBillingAddress().getState());
		dto.setBillPostalCode(orderAndPaymentGlobalEntity.getOrderDetailsEntity().getBillingAddress().getPostalCode());
//		String ship = orderAndPaymentGlobalEntity.getOrderDetailsEntity().getShippingAddress().toString();
//		LOGGER.info("DATA ********************* = {}", ship);
//		String shippingAddress = ship.substring(1, ship.toString().length() - 1).replaceAll("=", " : ");
//		LOGGER.info("DATA *********************|*********** = {}", shippingAddress);

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONParser parser = new JSONParser();

		try {
			String json = ow
					.writeValueAsString(orderAndPaymentGlobalEntity.getOrderDetailsEntity().getShippingAddress());
			org.json.simple.JSONObject json1 = (org.json.simple.JSONObject) parser.parse(json);
			LOGGER.info("DATA#####**** = {}", json);
			LOGGER.info("DATA#####**** = {}", json1.get("address2") == null);
			if (json1.get("address2") != null) {
				String address1 = json1.get("address1").toString();
				String address2 = json1.get("address2").toString();
				String country = json1.get("country").toString();
				String state = json1.get("state").toString();
				String city = json1.get("city").toString();
				String postalCode = json1.get("postalCode").toString();
				String landmark = json1.get("landmark").toString();
				String mobile = json1.get("mobile").toString();
				dto.setShippingAddress(address1 + ", " + address2 + ", " + country + ", " + state + ", " + city + ", "
						+ postalCode + ", " + landmark + ", " + mobile);
			} else {
				json1.remove("address2");
				String address1 = json1.get("address1").toString();
				String country = json1.get("country").toString();
				String state = json1.get("state").toString();
				String city = json1.get("city").toString();
				String postalCode = json1.get("postalCode").toString();
				String landmark = json1.get("landmark").toString();
				String mobile = json1.get("mobile").toString();
				dto.setShippingAddress(address1 + ", " + country + ", " + state + ", " + city + ", " + postalCode + ", "
						+ landmark + ", " + mobile);
			}
		} catch (JsonProcessingException | ParseException e) {
			e.printStackTrace();
		}

		return dto;

	}

	public OrderPlacedDTO skuOrders(OrderSKUDetailsEntity orderSKUDetailsEntity) {
		OrderPlacedDTO dto = new OrderPlacedDTO();
		dto.setImages(orderSKUDetailsEntity.getImages());
		dto.setProductName(orderSKUDetailsEntity.getProductName());
		dto.setTaxAmount(orderSKUDetailsEntity.getTaxAmount() + "");
		if (orderSKUDetailsEntity.getSalesPrice() == 0) {
			dto.setMrp(orderSKUDetailsEntity.getMrp() + "");
			dto.setTotal((orderSKUDetailsEntity.getMrp() - orderSKUDetailsEntity.getTaxAmount()) + "");
		} else {
			dto.setMrp(orderSKUDetailsEntity.getSalesPrice() + "");
			dto.setTotal((orderSKUDetailsEntity.getSalesPrice() - orderSKUDetailsEntity.getTaxAmount()) + "");
		}
		dto.setSize(orderSKUDetailsEntity.getSize());
		dto.setUnits(orderSKUDetailsEntity.getUnits() + "");

		return dto;
	}

	@SuppressWarnings("all")
	public void orderRefund(List<OrderSKUDetailsEntity> findByOrderSKU, OrderSKUDetailsEntity skuDetailsEntity,
			JSONObject getPaymentData, List<OrderPaymentEntity> findByOrderIdList)
			throws RazorpayException, JsonMappingException, JsonProcessingException {

		final RazorpayClient razorpayClient = new RazorpayClient(env.getProperty("key"), env.getProperty("secretKey"));
		JSONObject refundRequest = new JSONObject();

		if (findByOrderSKU.size() == 1) {
			refundRequest.put("payment_id", getPaymentData.get("razorpay_payment_id"));
		} else if (findByOrderSKU.size() > 1) {
			refundRequest.put("amount", skuDetailsEntity.getSalesPrice());
			refundRequest.put("payment_id", getPaymentData.get("razorpay_payment_id"));
		}
		Refund refund = razorpayClient.Payments.refund(refundRequest);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("orderSKU count {}", findByOrderSKU.size());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("orderSKU count {}", refund.toJson());
		}
		
		OrderPaymentEntity orderPaymentEntity = findByOrderIdList.get(0);
		Payment payment = razorpayClient.Payments.fetch(getPaymentData.get("razorpay_payment_id").toString());
		ObjectMapper obj = new ObjectMapper();
		Map<String, Object> map = obj.readValue(payment.toString(), new TypeReference<Map<String, Object>>() { });
		Map<String, Object> refundMap = obj.readValue(refund.toString(), new TypeReference<Map<String, Object>>() { });
		orderPaymentEntity.setPaymentResponse(map);
		orderPaymentEntity.setPaymentStatus("REFUNDED");
		orderPaymentEntity.setRefund(refundMap);
		userOrderPaymentRepo.save(orderPaymentEntity);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Update order payment {}", "refund updated");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update order payment {}", gson.toJson(orderPaymentEntity));
		}
	}
	
}
