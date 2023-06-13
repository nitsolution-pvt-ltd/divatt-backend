package com.divatt.user.utill;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.divatt.user.constant.MessageConstant;
import com.divatt.user.constant.RestTemplateConstants;
import com.divatt.user.dto.OrderPlacedDTO;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderPaymentEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.entity.product.DesignerProfileEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.OrderSKUDetailsRepo;
import com.divatt.user.repo.UserOrderPaymentRepo;
import com.divatt.user.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
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
	JavaMailSender mailSender;

	@Autowired
	private UserOrderPaymentRepo userOrderPaymentRepo;

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	@Autowired
	private OrderSKUDetailsRepo orderSKUDetailsRepo;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${DESIGNER}")
	private String DESIGNER_SERVICE;

	@Value("${AUTH}")
	private String AUTH_SERVICE;

	@Value("${ADMIN}")
	private String ADMIN_SERVICE;

	@Value("${USERS}")
	private String USER_SERVICE;

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);

	public OrderPlacedDTO placedOrder(OrderDetailsEntity orderAndPaymentGlobalEntity) {

		OrderPlacedDTO dto = new OrderPlacedDTO();

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(orderAndPaymentGlobalEntity.getUserId()));
		UserLoginEntity userLoginEntity = mongoOperations.findOne(query, UserLoginEntity.class);
		//String userName = userLoginEntity.getFirstName() + " " + userLoginEntity.getLastName();
		String userName = orderAndPaymentGlobalEntity.getBillingAddress().getFullName();
		dto.setUserName(userName);
		dto.setOrderId(orderAndPaymentGlobalEntity.getOrderId());
		dto.setOrderDate(orderAndPaymentGlobalEntity.getOrderDate());
		dto.setBillAddress1(orderAndPaymentGlobalEntity.getBillingAddress().getAddress1());
		dto.setBillAddress2(orderAndPaymentGlobalEntity.getBillingAddress().getAddress2());
		dto.setBillCity(orderAndPaymentGlobalEntity.getBillingAddress().getCity());
		dto.setBillState(orderAndPaymentGlobalEntity.getBillingAddress().getState());
		dto.setBillPostalCode(orderAndPaymentGlobalEntity.getBillingAddress().getPostalCode());

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONParser parser = new JSONParser();

		try {
			String json = ow.writeValueAsString(orderAndPaymentGlobalEntity.getShippingAddress());
			org.json.simple.JSONObject json1 = (org.json.simple.JSONObject) parser.parse(json);

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
		dto.setDesignerId(orderSKUDetailsEntity.getDesignerId() + "");
		dto.setImages(orderSKUDetailsEntity.getImages());
		dto.setProductName(orderSKUDetailsEntity.getProductName());
		dto.setTaxAmount(orderSKUDetailsEntity.getTaxAmount() + "");

		Long mrp = orderSKUDetailsEntity.getMrp();
		Long salesPrice = orderSKUDetailsEntity.getSalesPrice();

		dto.setSize(orderSKUDetailsEntity.getSize());
		dto.setDiscount(orderSKUDetailsEntity.getDiscount() + "");
		dto.setUnits(orderSKUDetailsEntity.getUnits() + "");
		dto.setDisplayName(orderSKUDetailsEntity.getDisplayName());
		if (orderSKUDetailsEntity.getGiftwrapStatus()) {
			dto.setGiftWrapAmount(orderSKUDetailsEntity.getGiftWrapAmount().toString());

			if (salesPrice == 0) {
				dto.setMrp((mrp - orderSKUDetailsEntity.getTaxAmount() - orderSKUDetailsEntity.getGiftWrapAmount()
						+ orderSKUDetailsEntity.getDiscount()) + "");
				dto.setTotal(mrp + "");
			} else {
				dto.setMrp((salesPrice - orderSKUDetailsEntity.getTaxAmount()
						- orderSKUDetailsEntity.getGiftWrapAmount() + orderSKUDetailsEntity.getDiscount()) + "");
				dto.setTotal(salesPrice + "");
			}
		} else {
			dto.setGiftWrapAmount("0");
			if (salesPrice == 0) {
				dto.setMrp((mrp - orderSKUDetailsEntity.getTaxAmount() + orderSKUDetailsEntity.getDiscount()) + "");
				dto.setTotal(mrp + "");
			} else {
				dto.setMrp(
						(salesPrice - orderSKUDetailsEntity.getTaxAmount() + orderSKUDetailsEntity.getDiscount()) + "");
				dto.setTotal(salesPrice + "");
			}
		}

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
		Map<String, Object> map = obj.readValue(payment.toString(), new TypeReference<Map<String, Object>>() {
		});
		Map<String, Object> refundMap = obj.readValue(refund.toString(), new TypeReference<Map<String, Object>>() {
		});
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

	public void userOrder(OrderPaymentEntity orderPaymentEntity) {
		try {
			OrderDetailsEntity orderDetailsEntity = orderDetailsRepo.findByOrderIds(orderPaymentEntity.getOrderId());
			String fullName = orderDetailsEntity.getBillingAddress().getFullName();
			List<OrderSKUDetailsEntity> orderSKUDetailsEntity = orderSKUDetailsRepo
					.findByOrderId(orderPaymentEntity.getOrderId());

			List<OrderPlacedDTO> orders = new ArrayList<>();
			List<OrderPlacedDTO> ordersdata = new ArrayList<>();
			Set<Integer> byDesigner = new HashSet<>();
			Map<String, Object> data = new HashMap<>();

			final DecimalFormat df = new DecimalFormat("0.00");
			Double mrp = 0.00;
			Double taxAmount = 0.00;
			Double total = 0.00;
			Double grandTotal = 0.00;
			Double totalMrp = 0.00;
			Double totalTax = 0.00;
			Double discount = 0.00;

			String tmrp = null;
			String ttaxAmount = null;
			String ttotal = null;
			String ttotalMrp = null;
			String tgrandTotal = null;
			String ttotalTax = null;
			String tDiscount = null;

			String designerEmail = null;
			// String displayName = null;
			String designerName = null;
			String tgrossGrandTotal = null;
			String totalGiftWrapAmount = null;
			String ttotalGiftWrapAmount = null;
			Set<String> displayName = new HashSet<>();

			ordersdata.add(placedOrder(orderDetailsEntity));
			for (OrderSKUDetailsEntity orderSKUDetailsEntityRow : orderSKUDetailsEntity) {

				int designerId = orderSKUDetailsEntityRow.getDesignerId();
				byDesigner.add(designerId);
				orders.add(skuOrders(orderSKUDetailsEntityRow));
				taxAmount = taxAmount + Double.parseDouble(orderSKUDetailsEntityRow.getTaxAmount() + "" == null ? "0"
						: orderSKUDetailsEntityRow.getTaxAmount() + "");
//			if (orderSKUDetailsEntityRow.getSalesPrice() == 0) {
//				String mrp2 = orderSKUDetailsEntityRow.getMrp() + "";
//				mrp = mrp + Double.parseDouble(mrp2 == null ? "0" : mrp2);
//				totalMrp = totalMrp + Double.parseDouble(mrp + "" == null ? "0" : mrp + "");
//				grandTotal = grandTotal + Double.parseDouble(
//						orderSKUDetailsEntityRow.getMrp() == null ? "0" : orderSKUDetailsEntityRow.getMrp().toString());
//			} else {
//				String salesPrice = orderSKUDetailsEntityRow.getSalesPrice() + "";
//				totalMrp = totalMrp + Double.parseDouble(mrp + "" == null ? "0" : mrp + "");
//				grandTotal = grandTotal + Double.parseDouble(salesPrice == null ? "0" : salesPrice);
//			}
				Double grossGrandTotal = 0.00;
				Double tgrossandTotal = 0.00;
				Double GiftWrapAmount = 0.00;
				for (OrderPlacedDTO order : orders) {
					grossGrandTotal = grossGrandTotal + Double.parseDouble(order.getMrp());
					tgrossandTotal = tgrossandTotal + Double.parseDouble(order.getTotal());
				}

				totalTax = totalTax + Double.parseDouble(totalTax + "" == null ? "0" : totalTax + "");
				discount = discount + Double.parseDouble(orderSKUDetailsEntityRow.getDiscount() + "" == null ? "0"
						: orderSKUDetailsEntityRow.getDiscount() + "");

				if (orderSKUDetailsEntityRow.getGiftwrapStatus()) {
					GiftWrapAmount = GiftWrapAmount
							+ Double.parseDouble(orderSKUDetailsEntityRow.getGiftWrapAmount() + "" == null ? "0"
									: orderSKUDetailsEntityRow.getGiftWrapAmount() + "");
				} else {
					GiftWrapAmount = GiftWrapAmount + Double.parseDouble("0" == null ? "0" : "0");
				}

				tmrp = String.valueOf(df.format(mrp));
				ttaxAmount = String.valueOf(taxAmount);
				ttotal = String.valueOf(total);
				ttotalMrp = String.valueOf(totalMrp);
				ttotalTax = String.valueOf(totalTax);
				tgrandTotal = String.valueOf(tgrossandTotal);
				tgrossGrandTotal = String.valueOf(grossGrandTotal);
				tDiscount = String.valueOf(discount);
				ttotalGiftWrapAmount = String.valueOf(GiftWrapAmount);
				try {
					DesignerProfileEntity forEntity = restTemplate
							.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_BYID + designerId,
									DesignerProfileEntity.class)
							.getBody();
					designerEmail = forEntity.getDesignerProfile().getEmail();
					designerName = forEntity.getDesignerName();
					displayName.add(forEntity.getDesignerProfile().getDisplayName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(orderDetailsEntity.getUserId()));
			UserLoginEntity userLoginEntity = mongoOperations.findOne(query, UserLoginEntity.class);

			String userName = userLoginEntity.getFirstName() + " " + userLoginEntity.getLastName();
			String email = userLoginEntity.getEmail();
			String orderId = orderDetailsEntity.getOrderId();

			// data.put("displayName", displayName);
			data.put("orderId", orderId);
			data.put("userName", userName);
			data.put("data", orders);
			data.put("datas", ordersdata);
			data.put("tmrp", tmrp);
			data.put("ttotal", ttotal);
			data.put("ttaxAmount", orderDetailsEntity.getTaxAmount());
			data.put("ttotalMrp", ttotalMrp);
			data.put("ttotalTax", ttotalTax);
			data.put("tgrandTotal", orderDetailsEntity.getTotalAmount());
			data.put("tDiscount", orderDetailsEntity.getDiscount());
			data.put("totalGiftWrapAmount", orderDetailsEntity.getGiftWrapAmount());
			data.put("fullName",fullName);

			String dis = "";
			data.put("tgrossGrandTotal", orderDetailsEntity.getNetPrice());
			for (String displayName2 : displayName) {
				dis = dis + displayName2 + " & ";

			}
			dis = dis.substring(0, dis.length() - 2) + dis.substring(dis.length() - 1);
			data.put("displayName", dis);
			Context context = new Context();
			context.setVariables(data);
			String htmlContent = templateEngine.process("orderPlaced.html", context);
			File createPdfSupplier;
			try {
				createPdfSupplier = createPdfSupplier(orderDetailsEntity);
				EmailSenderThread emailSenderThread = new EmailSenderThread(email,
						MessageConstant.ORDER_SUMMARY.getMessage(), htmlContent, true, null, restTemplate,
						AUTH_SERVICE);
				emailSenderThread.start();
//				this.sendEmailWithAttachment(email, MessageConstant.ORDER_SUMMARY.getMessage(), htmlContent, true,
//						createPdfSupplier);

				createPdfSupplier.delete();

			} catch (IOException e) {
				e.printStackTrace();
			}
			designerEmail(byDesigner, orderId, userName,fullName,ordersdata);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	File createPdfSupplier(@RequestBody OrderDetailsEntity orderDetailsEntity) throws IOException {

		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();

		/* next, get the Template */
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		try {
			ve.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Template t = null;
		try {
			t = ve.getTemplate("templates/orderSummary.vm");
		} catch (Exception e) {
			e.printStackTrace();
		}

		VelocityContext context = new VelocityContext();
		context.put("orderDetailsEntity", orderDetailsEntity);
		StringWriter writer = new StringWriter();
		t.merge(context, writer);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		baos = generatePdf(writer.toString());

		try (OutputStream outputStream = new FileOutputStream("order-summary.pdf")) {
			baos.writeTo(outputStream);

		}

		return new File("order-summary.pdf");
	}

	private ByteArrayOutputStream generatePdf(String html) {

		PdfWriter pdfWriter = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// create a new document
		Document document = new Document();
		try {

			document = new Document();
			document.addAuthor("Divatt");
			document.addCreationDate();
			document.addProducer();
			document.addCreator("Divatt");
			document.addTitle("Divatt");
			document.setPageSize(PageSize.LETTER);

			PdfWriter.getInstance(document, baos);

			// open document
			document.open();

			XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
			xmlWorkerHelper.getDefaultCssResolver(true);
			StringReader stringReader = new StringReader(html);
			xmlWorkerHelper.parseXHtml(pdfWriter, document, stringReader);
			// close the document
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos;
	}

	private void sendEmailWithAttachment(String to, String subject, String body, Boolean enableHtml, File file) {

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(subject);
			helper.setFrom("no-reply@nitsolution.in");
			helper.setTo(to);
			helper.setText(body, enableHtml);
//			helper.addAttachment("order-summary", file);
			mailSender.send(message);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public void orderCancel(OrderSKUDetailsEntity skuDetailsEntity) {
		try {
			Long userId = skuDetailsEntity.getUserId();
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(userId));
			UserLoginEntity user = mongoOperations.findOne(query, UserLoginEntity.class);
			DesignerProfileEntity body = restTemplate.getForEntity(
					DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_BYID + skuDetailsEntity.getDesignerId(),
					DesignerProfileEntity.class).getBody();
			String designerName = body.getDesignerName();
			String designerEmail = body.getDesignerProfile().getEmail();
			String email = user.getEmail();
			String userName = user.getFirstName() + " " + user.getLastName();
			String images = skuDetailsEntity.getImages();
			String productName = skuDetailsEntity.getProductName();
			String size = skuDetailsEntity.getSize();
			String orderId = skuDetailsEntity.getOrderId();
			Long salesPrice = skuDetailsEntity.getSalesPrice();
			Object object = skuDetailsEntity.getOrderStatusDetails().getCancelFromUser().get("comment");
			Object object2 = skuDetailsEntity.getOrderStatusDetails().getCancelFromUser().get("reason");

			Map<String, Object> data = new HashMap<>();
			data.put("userName", userName);
			data.put("designerName", designerName);
			data.put("ProductImages", images);
			data.put("ProductName", productName);
			data.put("email", email);
			data.put("ProductSize", size);
			data.put("OrderId", orderId);
			data.put("comment", object);
			data.put("reason", object2);
			if (salesPrice == 0 || salesPrice == null) {
				Long mrp = skuDetailsEntity.getMrp();
				data.put("SalePrice", mrp);
			} else {
				data.put("SalePrice", salesPrice);
			}
			Context context = new Context();
			context.setVariables(data);
			String htmlContent = templateEngine.process("ordercancelUser.html", context);
			EmailSenderThread emailSenderThread = new EmailSenderThread(email, "Order Cancelled", htmlContent, true,
					null, restTemplate, AUTH_SERVICE);
			emailSenderThread.start();
			String htmlContent1 = templateEngine.process("ordercancelDesigner.html", context);
			EmailSenderThread emailSenderThread1 = new EmailSenderThread(designerEmail, "Order Cancelled by User",
					htmlContent1, true, null, restTemplate, AUTH_SERVICE);
			emailSenderThread1.start();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public void designerEmail(Set<Integer> set, String orderId, String userName,String fullName, List<OrderPlacedDTO> ordersdata) {
		for (Integer value : set) {
			List<OrderPlacedDTO> orders = new ArrayList<>();
			Map<String, Object> data = new HashMap<>();

			final DecimalFormat df = new DecimalFormat("0.00");
			Double mrp = 0.00;
			Double taxAmount = 0.00;
			Double total = 0.00;
			Double grandTotal = 0.00;
			Double totalMrp = 0.00;
			Double totalTax = 0.00;
			Double discount = 0.00;
			Double totalGiftWrapAmount = 0.00;

			String tmrp = null;
			String ttaxAmount = null;
			String ttotal = null;
			String ttotalMrp = null;
			String tgrandTotal = null;
			String ttotalTax = null;
			String tDiscount = null;

			String designerEmail = null;
			String designerName = null;
			String tgrossGrandTotal = null;
			String ttotalGiftWrapAmount = null;
			String displayName = null;

			List<OrderSKUDetailsEntity> findByOrderIdAndDesignerId = orderSKUDetailsRepo
					.findByOrderIdAndDesignerId(orderId, value);

			for (OrderSKUDetailsEntity orderSKUDetailsEntityRow : findByOrderIdAndDesignerId) {

				int designerId = orderSKUDetailsEntityRow.getDesignerId();
				orders.add(skuOrders(orderSKUDetailsEntityRow));
				taxAmount = taxAmount + Double.parseDouble(orderSKUDetailsEntityRow.getTaxAmount() + "" == null ? "0"
						: orderSKUDetailsEntityRow.getTaxAmount() + "");
				Double grossGrandTotal = 0.00;
				Double tgrossandTotal = 0.00;
				Double GiftWrapAmount = 0.00;
				for (OrderPlacedDTO order : orders) {
					grossGrandTotal = grossGrandTotal + Double.parseDouble(order.getMrp());
					tgrossandTotal = tgrossandTotal + Double.parseDouble(order.getTotal());
					tgrossGrandTotal = tgrossGrandTotal + order.getMrp() == null ? "0" : order.getMrp();
					if (orderSKUDetailsEntityRow.getGiftwrapStatus()) {
						totalGiftWrapAmount = totalGiftWrapAmount + Double
								.parseDouble(order.getGiftWrapAmount() == null ? "0" : order.getGiftWrapAmount());

					} else {
						totalGiftWrapAmount = totalGiftWrapAmount + Double.parseDouble("0" == null ? "0" : "0");
					}
				}

				totalTax = totalTax + Double.parseDouble(totalTax + "" == null ? "0" : totalTax + "");
				discount = discount + Double.parseDouble(orderSKUDetailsEntityRow.getDiscount() + "" == null ? "0"
						: orderSKUDetailsEntityRow.getDiscount() + "");

				tmrp = String.valueOf(df.format(mrp));
				ttaxAmount = String.valueOf(taxAmount);
				ttotal = String.valueOf(total);
				ttotalMrp = String.valueOf(totalMrp);
				ttotalTax = String.valueOf(totalTax);
				tgrandTotal = String.valueOf(tgrossandTotal);
				tgrossGrandTotal = String.valueOf(grossGrandTotal);
				tDiscount = String.valueOf(discount);
				ttotalGiftWrapAmount = String.valueOf(totalGiftWrapAmount);
				try {
					DesignerProfileEntity forEntity = restTemplate
							.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_BYID + designerId,
									DesignerProfileEntity.class)
							.getBody();
					designerEmail = forEntity.getDesignerProfile().getEmail();
					designerName = forEntity.getDesignerName();
					displayName = forEntity.getDesignerProfile().getDisplayName();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			data.put("orderId", orderId);
			data.put("userName", userName);
			data.put("data", orders);
			data.put("datas", ordersdata);
			data.put("tmrp", tmrp);
			data.put("ttotal", ttotal);
			data.put("ttaxAmount", taxAmount);
			data.put("ttotalMrp", ttotalMrp);
			data.put("ttotalTax", ttotalTax);
			data.put("tgrandTotal", tgrandTotal);
			data.put("tDiscount", tDiscount);
			data.put("totalGiftWrapAmount", totalGiftWrapAmount);
			data.put("tgrossGrandTotal", tgrossGrandTotal);
			data.put("displayName", displayName);
			data.put("fullName", fullName);
			Context context = new Context();
			context.setVariables(data);
			String htmlContent = templateEngine.process("orderPlacedDesigner.html", context);
			EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(designerEmail,
					MessageConstant.ORDER_RECEIVED.getMessage(), htmlContent, true, null, restTemplate, AUTH_SERVICE);
			emailSenderThreadDesigner.start();

		}
	}

	public void mailSend(OrderSKUDetailsEntity item, DesignerProfileEntity entity, String orderId, String productId,
			String orderItemStatus, Map<String, Object> data) {
		String itemStatus = item.getOrderItemStatus();
		SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT_TYPE.getMessage());
		String displayName = entity.getDesignerProfile().getDisplayName();
		Date dates = new Date();
		String format = formatter.format(dates);
		Long userId = item.getUserId();
		UserLoginEntity userById = userServiceImpl.getUserById(userId);
		String email = userById.getEmail();
		String firstName = userById.getFirstName();
		String productName = item.getProductName();
		Long salesPrice = item.getSalesPrice();
		Long mrp;
		if (salesPrice == 0 || salesPrice.equals(null)) {
			mrp = item.getMrp();
		} else {
			mrp = salesPrice;
		}
		String size = item.getSize();
		String images = item.getImages();
		Long units = item.getUnits();
		String email2 = entity.getDesignerProfile().getEmail();
		String colour = item.getColour();

		List<OrderPaymentEntity> findByOrderIdList = userOrderPaymentRepo.findByOrderIdList(orderId);

		if (findByOrderIdList.size() > 0) {

			String paymentMode = findByOrderIdList.get(0).getPaymentMode();
			OrderDetailsEntity orderDetailsEntity = orderDetailsRepo.findByOrderId(orderId).get(0);
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			JSONParser parser = new JSONParser();
			String replace = null;

			try {
				String json = ow.writeValueAsString(orderDetailsEntity.getShippingAddress());
				org.json.simple.JSONObject json1 = (org.json.simple.JSONObject) parser.parse(json);

				if (json1.get("address2") != null) {
					String address1 = json1.get("address1").toString();
					String address2 = json1.get("address2").toString();
					String country = json1.get("country").toString();
					String state = json1.get("state").toString();
					String city = json1.get("city").toString();
					String postalCode = json1.get("postalCode").toString();
					String landmark = json1.get("landmark").toString();
					String mobile = json1.get("mobile").toString();
					replace = (address1 + ", " + address2 + ", " + country + ", " + state + ", " + city + ", "
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
					replace = (address1 + ", " + country + ", " + state + ", " + city + ", " + postalCode + ", "
							+ landmark + ", " + mobile);
				}
			} catch (JsonProcessingException | ParseException e) {
				e.printStackTrace();
			}

			String orderDate = item.getCreatedOn();
			Date parse;
			try {
				parse = formatter.parse(orderDate);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(parse);
				Date time = calendar.getTime();
				DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				String format1 = dateFormat2.format(time);
				Double disc = orderDetailsEntity.getDiscount();
				String discount = decimalFormat.format(disc);
				Double taxAmount = orderDetailsEntity.getTaxAmount();
				String format2 = decimalFormat.format(taxAmount);
				Context context = new Context();
				context.setVariable("firstName", firstName);
				context.setVariable("productId", productId);
				context.setVariable("productName", productName);

				context.setVariable("discount", discount);
				context.setVariable("taxAmount", format2);
				context.setVariable("size", size);
				context.setVariable("displayName", displayName);
				context.setVariable("paymentMode", paymentMode);
				context.setVariable("shippingAddress", replace);
				context.setVariable("orderDate", format1);
				context.setVariable("orderId", orderId);
				context.setVariable("quantity", units);
				context.setVariable("colour", colour);
				context.setVariable("format3", mrp);
				if (item.getGiftwrapStatus()) {
					context.setVariable("giftWrapAmount", item.getGiftWrapAmount());
					double format3 = ((mrp - Double.parseDouble(format2)) + Double.parseDouble(discount)
							- item.getGiftWrapAmount());
					context.setVariable("mrp", format3);
				} else {
					context.setVariable("giftWrapAmount", 0.00);
					double format3 = (mrp - Double.parseDouble(format2)) + Double.parseDouble(discount);
					context.setVariable("mrp", format3);
				}
				String string = data.toString();
				String string2 = string.substring(1, string.toString().length() - 1).replaceAll("=", " : ");
				String substring = string2.replace(",", ",\n");

				context.setVariable("details", substring);
				if (orderItemStatus.equals("Orders")) {
					context.setVariable("orderItemStatus", "Verified");
				} else {
					context.setVariable("orderItemStatus", orderItemStatus);
				}

				context.setVariable("orderId", orderId);
				context.setVariable("productImage", images);
				if (orderItemStatus.equals("Orders")) {
					String htmlContent = templateEngine.process("statusChange.html", context);
					EmailSenderThread emailSenderThread = new EmailSenderThread(email,
							"Your Order Has been " + "Verified", htmlContent, true, null, restTemplate, AUTH_SERVICE);
					String htmlContentDesigner = templateEngine.process("statusChangeDesigner.html", context);
					EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(email2,
							"Your Product Has been " + "Verified", htmlContentDesigner, true, null, restTemplate,
							AUTH_SERVICE);
					emailSenderThreadDesigner.start();
					emailSenderThread.start();
				} else {
					String htmlContent = templateEngine.process("statusChange.html", context);
					EmailSenderThread emailSenderThread = new EmailSenderThread(email,
							"Your Order Has been " + orderItemStatus, htmlContent, true, null, restTemplate,
							AUTH_SERVICE);
					String htmlContentDesigner = templateEngine.process("statusChangeDesigner.html", context);
					EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(email2,
							"Your Order Has been " + orderItemStatus, htmlContentDesigner, true, null, restTemplate,
							AUTH_SERVICE);
					emailSenderThreadDesigner.start();
					emailSenderThread.start();
				}

			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public void mailReturnRequest(OrderSKUDetailsEntity item, String orderId, Integer productId,
			Map<String, Object> data) {
		try {
			Long userId = item.getUserId();
			UserLoginEntity userById = userServiceImpl.getUserById(userId);
			String email = userById.getEmail();
			String firstName = userById.getFirstName();
			DesignerProfileEntity designerDetails = restTemplate
					.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_BYID + item.getDesignerId(),
							DesignerProfileEntity.class)
					.getBody();
			Query query = new Query();
			query.addCriteria(Criteria.where("order_id").is(orderId).and("productId").is(productId));
			OrderSKUDetailsEntity skuDetailsEntity = mongoOperations.findOne(query, OrderSKUDetailsEntity.class);
			String designerName = designerDetails.getDesignerName();
			String designerEmail = designerDetails.getDesignerProfile().getEmail();
			String displayName = designerDetails.getDesignerProfile().getDisplayName();
			org.json.simple.JSONObject body = restTemplate.getForEntity(
					ADMIN_SERVICE + RestTemplateConstants.ADMIN_ROLE_NAME + MessageConstant.ADMIN_ROLES.getMessage(),
					org.json.simple.JSONObject.class).getBody();
			String adminMail = body.get("email").toString();
			String adminFirstName = body.get("firstName").toString();
			String adminLastName = body.get("lastName").toString();
			String adminName = adminFirstName + " " + adminLastName;
			String itemStatus = item.getOrderItemStatus();
			SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT_TYPE.getMessage());
			Date dates = new Date();
			String format = formatter.format(dates);
			String productName = item.getProductName();
			Long salesPrice = item.getSalesPrice();
			Long mrp;
			if (salesPrice == 0 || salesPrice.equals(null)) {
				mrp = item.getMrp();
			} else {
				mrp = salesPrice;
			}
			String size = item.getSize();
			String images = item.getImages();
			Long units = item.getUnits();
			String colour = item.getColour();
			List<OrderPaymentEntity> findByOrderIdList = userOrderPaymentRepo.findByOrderIdList(orderId);
			if (findByOrderIdList.size() > 0) {
				String paymentMode = findByOrderIdList.get(0).getPaymentMode();
				OrderDetailsEntity orderDetailsEntity = orderDetailsRepo.findByOrderId(orderId).get(0);
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				JSONParser parser = new JSONParser();
				String replace = null;
				try {
					String json = ow.writeValueAsString(orderDetailsEntity.getShippingAddress());
					org.json.simple.JSONObject json1 = (org.json.simple.JSONObject) parser.parse(json);
					if (json1.get("address2") != null) {
						String address1 = json1.get("address1").toString();
						String address2 = json1.get("address2").toString();
						String country = json1.get("country").toString();
						String state = json1.get("state").toString();
						String city = json1.get("city").toString();
						String postalCode = json1.get("postalCode").toString();
						String landmark = json1.get("landmark").toString();
						String mobile = json1.get("mobile").toString();
						replace = (address1 + ", " + address2 + ", " + country + ", " + state + ", " + city + ", "
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
						replace = (address1 + ", " + country + ", " + state + ", " + city + ", " + postalCode + ", "
								+ landmark + ", " + mobile);
					}
				} catch (JsonProcessingException | ParseException e) {
					e.printStackTrace();
				}
				String orderDate = item.getCreatedOn();
				Date parse;
				try {
					parse = formatter.parse(orderDate);

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(parse);
					Date time = calendar.getTime();
					DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
					DecimalFormat decimalFormat = new DecimalFormat("0.00");
					String format1 = dateFormat2.format(time);
					Double disc = orderDetailsEntity.getDiscount();
					String discount = decimalFormat.format(disc);
					Double taxAmount = orderDetailsEntity.getTaxAmount();
					String format2 = decimalFormat.format(taxAmount);
					Context context = new Context();
					context.setVariable("firstName", firstName);
					context.setVariable("productId", productId);
					context.setVariable("productName", productName);
					context.setVariable("designerName", designerName);
					context.setVariable("adminName", adminName);
					context.setVariable("discount", discount);
					context.setVariable("taxAmount", format2);
					context.setVariable("size", size);
					context.setVariable("displayName", displayName);
					context.setVariable("paymentMode", paymentMode);
					context.setVariable("shippingAddress", replace);
					context.setVariable("orderDate", format1);
					context.setVariable("orderId", orderId);
					context.setVariable("quantity", units);
					context.setVariable("colour", colour);
					context.setVariable("format3", mrp);
					if (item.getGiftwrapStatus()) {
						context.setVariable("giftWrapAmount", item.getGiftWrapAmount());
						double format3 = ((mrp - Double.parseDouble(format2)) + Double.parseDouble(discount)
								- item.getGiftWrapAmount());
						context.setVariable("mrp", format3);
					} else {
						context.setVariable("giftWrapAmount", 0.00);
						double format3 = (mrp - Double.parseDouble(format2)) + Double.parseDouble(discount);
						context.setVariable("mrp", format3);
					}
					String string = data.toString();
					LOGGER.info(string);
					String string2 = string.substring(1, string.toString().length() - 1).replaceAll("=", " ");
					String substring = string2.replace(",", ",\n");

					context.setVariable("details", substring);
					context.setVariable("orderId", orderId);
					context.setVariable("productImage", images);
					if (item.getOrderItemStatus().equals("returnRequest")) {
						String htmlContent = templateEngine.process("orderStatusUpdateUser.html", context);
						EmailSenderThread emailSenderThread = new EmailSenderThread(email, "Return request for an item",
								htmlContent, true, null, restTemplate, AUTH_SERVICE);
						String htmlContentDesigner = templateEngine.process("orderStatusUpdateDesigner.html", context);
						EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(designerEmail,
								"Received a return request for an item", htmlContentDesigner, true, null, restTemplate,
								AUTH_SERVICE);
						String htmlContentAdmin = templateEngine.process("orderStatusUpdateAdmin.html", context);
						EmailSenderThread emailSenderThreadAdmin = new EmailSenderThread(adminMail,
								"Received a return request for an item", htmlContentAdmin, true, null, restTemplate,
								AUTH_SERVICE);
						emailSenderThreadDesigner.start();
						emailSenderThread.start();
						emailSenderThreadAdmin.start();
					} else if (item.getOrderItemStatus().equals("returnRefund")) {
						String htmlContent = templateEngine.process("orderStatusUpdateUserAccepted.html", context);
						EmailSenderThread emailSenderThread = new EmailSenderThread(email,
								"Return request for an item has been accepted ", htmlContent, true, null, restTemplate,
								AUTH_SERVICE);
						String htmlContentDesigner = templateEngine.process("orderStatusUpdateDesignerAccepted.html",
								context);
						EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(designerEmail,
								"Received a return request for an item has been accepted", htmlContentDesigner, true,
								null, restTemplate, AUTH_SERVICE);
						String htmlContentAdmin = templateEngine.process("orderStatusUpdateAdminAccepted.html",
								context);
						EmailSenderThread emailSenderThreadAdmin = new EmailSenderThread(adminMail,
								"Received a return request for an item has been accepted", htmlContentAdmin, true, null,
								restTemplate, AUTH_SERVICE);
						emailSenderThreadDesigner.start();
						emailSenderThread.start();
						emailSenderThreadAdmin.start();
					} else if (item.getOrderItemStatus().equals("Rejected")) {
						String htmlContent = templateEngine.process("orderStatusUpdateUserRejected.html", context);
						EmailSenderThread emailSenderThread = new EmailSenderThread(email,
								"Return request for an item has been rejected ", htmlContent, true, null, restTemplate,
								AUTH_SERVICE);
						String htmlContentDesigner = templateEngine.process("orderStatusUpdateDesignerRejected.html",
								context);
						EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(designerEmail,
								"Received a return request for an item has been rejected", htmlContentDesigner, true,
								null, restTemplate, AUTH_SERVICE);
						String htmlContentAdmin = templateEngine.process("orderStatusUpdateAdminRejected.html",
								context);
						EmailSenderThread emailSenderThreadAdmin = new EmailSenderThread(adminMail,
								"Received a return request for an item has been rejected", htmlContentAdmin, true, null,
								restTemplate, AUTH_SERVICE);
						emailSenderThreadDesigner.start();
						emailSenderThread.start();
						emailSenderThreadAdmin.start();
					} else if (item.getOrderItemStatus().equals("Product shipped by user")) {

						if (!skuDetailsEntity.equals(item.getOrderItemStatus())) {
							if (skuDetailsEntity.getOrderItemStatus().equals("Return request approved")) {
								String htmlContent = templateEngine.process("orderStatusUpdateUserRejected.html",
										context);
								EmailSenderThread emailSenderThread = new EmailSenderThread(email,
										"Return request for an item has been rejected ", htmlContent, true, null,
										restTemplate, AUTH_SERVICE);
								String htmlContentDesigner = templateEngine
										.process("orderStatusUpdateDesignerRejected.html", context);
								EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(designerEmail,
										"Received a return request for an item has been rejected", htmlContentDesigner,
										true, null, restTemplate, AUTH_SERVICE);
								String htmlContentAdmin = templateEngine.process("orderStatusUpdateAdminRejected.html",
										context);
								EmailSenderThread emailSenderThreadAdmin = new EmailSenderThread(adminMail,
										"Received a return request for an item has been rejected", htmlContentAdmin,
										true, null, restTemplate, AUTH_SERVICE);
								emailSenderThreadDesigner.start();
								emailSenderThread.start();
								emailSenderThreadAdmin.start();
							}
						}
					}
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
	}
}
