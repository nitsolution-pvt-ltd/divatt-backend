package com.divatt.user.utill;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.divatt.user.entity.OrderAndPaymentGlobalEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.serviceDTO.OrderPlacedDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class CommonUtility {

	@Autowired
	private MongoOperations mongoOperations;

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
			JSONObject json1 = (JSONObject) parser.parse(json);
			LOGGER.info("DATA#####**** = {}", json);
			LOGGER.info("DATA#####**** = {}", json1.get("address2") == null);
			if (json1.get("address2") != null) {
				json1.remove("address2");
				String address1 = json1.get("address1").toString();
				String address2 = json1.get("address2").toString();
				String country = json1.get("country").toString();
				String state = json1.get("state").toString();
				String city = json1.get("city").toString();
				String postalCode = json1.get("postalCode").toString();
				String landmark = json1.get("landmark").toString();
				String mobile = json1.get("mobile").toString();
				dto.setShippingAddress(address1 + "," + address2 + "," + country + "," + state + "," + city + ","
						+ postalCode + "," + landmark + "," + mobile);
			} else {
				String address1 = json1.get("address1").toString();
				String country = json1.get("country").toString();
				String state = json1.get("state").toString();
				String city = json1.get("city").toString();
				String postalCode = json1.get("postalCode").toString();
				String landmark = json1.get("landmark").toString();
				String mobile = json1.get("mobile").toString();
				dto.setShippingAddress(address1 + "," + country + "," + state + "," + city + "," + postalCode + ","
						+ landmark + "," + mobile);
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

}
