package com.divatt.user.utill;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.divatt.user.constant.MessageConstant;
import com.divatt.user.constant.RestTemplateConstant;
import com.divatt.user.designerProductEntity.DesignerProfileEntity;
import com.divatt.user.entity.OrderAndPaymentGlobalEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.helper.JwtUtil;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.OrderSKUDetailsRepo;
import com.divatt.user.repo.orderPaymenRepo.UserOrderPaymentRepo;
import com.divatt.user.serviceDTO.OrderPlacedDTO;
import com.divatt.user.services.SequenceGenerator;
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
	private RestTemplate restTemplate;

	@Autowired
	private JwtUtil jwtUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);

	public OrderPlacedDTO placedOrder(OrderDetailsEntity orderAndPaymentGlobalEntity) {

		OrderPlacedDTO dto = new OrderPlacedDTO();

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(orderAndPaymentGlobalEntity.getUserId()));
		UserLoginEntity userLoginEntity = mongoOperations.findOne(query, UserLoginEntity.class);
		String userName = userLoginEntity.getFirstName() + " " + userLoginEntity.getLastName();
		dto.setUserName(userName);
		dto.setOrderId(orderAndPaymentGlobalEntity.getOrderId());
		dto.setOrderDate(orderAndPaymentGlobalEntity.getOrderDate());
		dto.setBillAddress1(orderAndPaymentGlobalEntity.getBillingAddress().getAddress1());
		dto.setBillAddress2(orderAndPaymentGlobalEntity.getBillingAddress().getAddress2());
		dto.setBillCity(orderAndPaymentGlobalEntity.getBillingAddress().getCity());
		dto.setBillState(orderAndPaymentGlobalEntity.getBillingAddress().getState());
		dto.setBillPostalCode(orderAndPaymentGlobalEntity.getBillingAddress().getPostalCode());
//		String ship = orderAndPaymentGlobalEntity.getOrderDetailsEntity().getShippingAddress().toString();
//		LOGGER.info("DATA ********************* = {}", ship);
//		String shippingAddress = ship.substring(1, ship.toString().length() - 1).replaceAll("=", " : ");
//		LOGGER.info("DATA *********************|*********** = {}", shippingAddress);

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONParser parser = new JSONParser();

		try {
			String json = ow.writeValueAsString(orderAndPaymentGlobalEntity.getShippingAddress());
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

		List<OrderPlacedDTO> orders = new ArrayList<>();
		List<OrderPlacedDTO> ordersdata = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();

		final DecimalFormat df = new DecimalFormat("0.00");
		Double mrp = 0.00;
		Double taxAmount = 0.00;
		Double total = 0.00;
		Double grandTotal = 0.00;
		Double totalMrp = 0.00;
		Double totalTax = 0.00;

		String tmrp = null;
		String ttaxAmount = null;
		String ttotal = null;
		String ttotalMrp = null;
		String tgrandTotal = null;
		String ttotalTax = null;

		String designerEmail = null;
		String displayName = null;
		String designerName = null;
		String tgrossGrandTotal = null;

		OrderDetailsEntity orderDetailsEntity = orderDetailsRepo.findByOrderIds(orderPaymentEntity.getOrderId());
		List<OrderSKUDetailsEntity> orderSKUDetailsEntity = orderSKUDetailsRepo
				.findByOrderId(orderPaymentEntity.getOrderId());

		ordersdata.add(placedOrder(orderDetailsEntity));
		for (OrderSKUDetailsEntity orderSKUDetailsEntityRow : orderSKUDetailsEntity) {

			int designerId = orderSKUDetailsEntityRow.getDesignerId();
			orders.add(skuOrders(orderSKUDetailsEntityRow));
			taxAmount = taxAmount + Double.parseDouble(orderSKUDetailsEntityRow.getTaxAmount() + "" == null ? "0"
					: orderSKUDetailsEntityRow.getTaxAmount() + "");
			if (orderSKUDetailsEntityRow.getSalesPrice() == 0) {
				String mrp2 = orderSKUDetailsEntityRow.getMrp() + "";
				mrp = mrp + Double.parseDouble(mrp2 == null ? "0" : mrp2);
				totalMrp = totalMrp + Double.parseDouble(mrp + "" == null ? "0" : mrp + "");
				grandTotal = grandTotal + Double.parseDouble(
						orderSKUDetailsEntityRow.getMrp() == null ? "0" : orderSKUDetailsEntityRow.getMrp().toString());
			} else {
				String salesPrice = orderSKUDetailsEntityRow.getSalesPrice() + "";
				totalMrp = totalMrp + Double.parseDouble(mrp + "" == null ? "0" : mrp + "");
				grandTotal = grandTotal + Double.parseDouble(salesPrice == null ? "0" : salesPrice);
			}
			Double grossGrandTotal = 0.00;
			for (OrderPlacedDTO order : orders) {
				grossGrandTotal = grossGrandTotal + Double.parseDouble(order.getTotal());
			}
			totalTax = totalTax + Double.parseDouble(totalTax + "" == null ? "0" : totalTax + "");
			tmrp = String.valueOf(df.format(mrp));
			ttaxAmount = String.valueOf(taxAmount);
			ttotal = String.valueOf(total);
			ttotalMrp = String.valueOf(totalMrp);
			ttotalTax = String.valueOf(totalTax);
			tgrandTotal = String.valueOf(grandTotal);
			tgrossGrandTotal = String.valueOf(grossGrandTotal);
			try {
				DesignerProfileEntity forEntity = restTemplate
						.getForEntity(RestTemplateConstant.DESIGNER_BYID.getLink() + designerId,
								DesignerProfileEntity.class)
						.getBody();
				designerEmail = forEntity.getDesignerProfile().getEmail();
				designerName = forEntity.getDesignerName();
				displayName = forEntity.getDesignerProfile().getDisplayName();

				Query query = new Query();
				query.addCriteria(Criteria.where("id").is(orderDetailsEntity.getUserId()));
				UserLoginEntity userLoginEntity = mongoOperations.findOne(query, UserLoginEntity.class);

				String userName = userLoginEntity.getFirstName() + " " + userLoginEntity.getLastName();
				String email = userLoginEntity.getEmail();
				String orderId = orderDetailsEntity.getOrderId();
				data.put("displayName", displayName);
				data.put("orderId", orderId);
				data.put("userName", userName);
				data.put("data", orders);
				data.put("datas", ordersdata);
				data.put("tmrp", tmrp);
				data.put("ttotal", ttotal);
				data.put("ttaxAmount", ttaxAmount);
				data.put("ttotalMrp", ttotalMrp);
				data.put("ttotalTax", ttotalTax);
				data.put("tgrandTotal", tgrandTotal);
				data.put("tgrossGrandTotal", tgrossGrandTotal);
				Context context = new Context();
				context.setVariables(data);
				String htmlContent = templateEngine.process("orderPlaced.html", context);
				String htmlContent1 = templateEngine.process("orderPlacedDesigner.html", context);
				File createPdfSupplier;
				try {
					createPdfSupplier = createPdfSupplier(orderDetailsEntity);

					this.sendEmailWithAttachment(email, MessageConstant.ORDER_SUMMARY.getMessage(), htmlContent, true,
							createPdfSupplier);
					this.sendEmailWithAttachment(designerEmail, MessageConstant.ORDER_SUMMARY.getMessage(),
							htmlContent1 + MessageConstant.PRODUCT_PLACED.getMessage() + userLoginEntity.getFirstName()
									+ " " + userLoginEntity.getLastName(),
							true, createPdfSupplier);

					createPdfSupplier.delete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	File createPdfSupplier(@RequestBody OrderDetailsEntity orderDetailsEntity) throws IOException {
		System.out.println("ok");

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
			System.out.println(MessageConstant.PDF_GENERATED.getMessage());

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

}
