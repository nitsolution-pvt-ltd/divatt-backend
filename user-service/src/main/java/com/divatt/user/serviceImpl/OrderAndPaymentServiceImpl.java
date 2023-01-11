package com.divatt.user.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.divatt.user.config.JWTConfig;
import com.divatt.user.constant.MessageConstant;
import com.divatt.user.constant.RestTemplateConstant;
import com.divatt.user.designerProductEntity.DesignerProfileEntity;
import com.divatt.user.entity.BillingAddressEntity;
import com.divatt.user.entity.InvoiceEntity;
import com.divatt.user.entity.OrderInvoiceEntity;
import com.divatt.user.entity.OrderTrackingEntity;
import com.divatt.user.entity.ProductInvoice;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.order.HsnData;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.entity.order.OrderStatusDetails;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.helper.ListResponseDTO;
import com.divatt.user.helper.PDFRunner;
import com.divatt.user.helper.UtillUserService;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.OrderInvoiceRepo;
import com.divatt.user.repo.OrderSKUDetailsRepo;
import com.divatt.user.repo.OrderTrackingRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.repo.orderPaymenRepo.UserOrderPaymentRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.serviceDTO.CancelEmailJSON;
import com.divatt.user.serviceDTO.CancelationRequestApproveAndRejectDTO;
import com.divatt.user.serviceDTO.CancelationRequestDTO;
import com.divatt.user.serviceDTO.DesignerRequestDTO;
import com.divatt.user.serviceDTO.InvoiceUpdatedModel;
import com.divatt.user.services.OrderAndPaymentService;
import com.divatt.user.services.SequenceGenerator;
import com.divatt.user.utill.CommonUtility;
import com.divatt.user.utill.EmailSenderThread;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;

import springfox.documentation.spring.web.json.Json;

@Service
public class OrderAndPaymentServiceImpl implements OrderAndPaymentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAndPaymentServiceImpl.class);

	HttpResponse<String> response = null;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserOrderPaymentRepo userOrderPaymentRepo;

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	@Autowired
	private OrderTrackingRepo orderTrackingRepo;

	@Autowired
	private OrderSKUDetailsRepo orderSKUDetailsRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private MongoOperations mongoOperations;

//	@Autowired
//	private MeasurementRepo measurementRepo;

	@Autowired
	private Environment env;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private OrderInvoiceRepo orderInvoiceRepo;

	@Autowired
	private JWTConfig jwtconfig;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;

	@Value("${interfaceId}")
	private String interfaceId;

	@Autowired
	private UserLoginRepo userloginRepo;

	@Autowired
	private Gson gson;
	
	@Autowired
	private CommonUtility commonUtility;

	protected String getRandomString() {
//		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String SALTCHARS = MessageConstant.RANDOM_STRING.getMessage();
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 16) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	protected String getRandomStringInt() {
		String SALTCHARS = MessageConstant.RANDOM_STRING_INT.getMessage();
//		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 16) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	protected String getRandomNumber() {
//		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String SALTCHARS = MessageConstant.RANDOM_INT.getMessage();
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 16) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public ResponseEntity<?> postRazorpayOrderCreateService(OrderDetailsEntity orderDetailsEntity) {
		LOGGER.info("Inside - OrderAndPaymentService.postRazorpayOrderCreateService()");

		try {
			final RazorpayClient razorpayClient = new RazorpayClient(env.getProperty("key"),
					env.getProperty("secretKey"));
			JSONObject options = new JSONObject();

			options.put("amount", orderDetailsEntity.getTotalAmount());
			options.put("currency", "INR");
			options.put("receipt", "RC" + getRandomString());

			final Order order = razorpayClient.Orders.create(options);

			return ResponseEntity.ok(new Json(order.toString()));

		} catch (RazorpayException exe) {
			return new ResponseEntity<>(new Json(exe.getLocalizedMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<?> postOrderPaymentService(OrderPaymentEntity orderPaymentEntity) {
		LOGGER.info("Inside - OrderAndPaymentService.postOrderPaymentService()");

		try {

			final RazorpayClient razorpayClient = new RazorpayClient(env.getProperty("key"),
					env.getProperty("secretKey"));
			LOGGER.info("Inside - OrderAndPaymentContoller.postOrderPaymentService()");

			String paymentIdFilter = null;
			ObjectMapper obj = new ObjectMapper();
			try {
				paymentIdFilter = obj.writeValueAsString(orderPaymentEntity.getPaymentDetails());
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			LOGGER.info("Payment ID FILTER data = {}", paymentIdFilter);
			JsonNode OrderPayJson = new JsonNode(paymentIdFilter);

			Payment payment = razorpayClient.Payments
					.fetch(OrderPayJson.getObject().get("razorpay_payment_id").toString());
			LOGGER.info(OrderPayJson.getObject().get("razorpay_payment_id").toString() + "Inside Json");

			String payStatus = "FAILED";
			if (payment.get("error_code").equals(null) && payment.get("status").equals("captured")) {
				payStatus = "COMPLETED";

			} else if (!payment.get("error_code").equals(null) && !payment.get("error_reason").equals(null)
					&& !payment.get("error_step").equals(null) && payment.get("status").equals("failed")) {
				payStatus = "FAILED";
			}
			LOGGER.info("Payment STATUS = {}", payStatus);
			List<OrderDetailsEntity> findOrderRow = orderDetailsRepo.findByOrderId(orderPaymentEntity.getOrderId());
//			LOGGER.info("FInd by order id");
			LOGGER.info("Get order id from client end = {}", orderPaymentEntity.getOrderId());
			if (findOrderRow.size() <= 0) {
				throw new CustomException(MessageConstant.ORDER_NOT_FOUND.getMessage());
			}
			Map<String, Object> map = null;
			try {
				map = obj.readValue(payment.toString(), new TypeReference<Map<String, Object>>() {
				});
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			Map<String, String> mapPayId = new HashMap<>();
			mapPayId.put("OrderId", orderPaymentEntity.getOrderId());
			mapPayId.put("TransactionId", OrderPayJson.getObject().get("razorpay_payment_id").toString());
			LOGGER.info(OrderPayJson.getObject().get("razorpay_payment_id").toString() + "Inside Json");
			Optional<OrderPaymentEntity> findByOrderId = userOrderPaymentRepo
					.findByOrderId(orderPaymentEntity.getOrderId());
			SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT_TYPE.getMessage());
			Date date = new Date();
			String format = formatter.format(date);
			if (!findByOrderId.isPresent()) {
				OrderPaymentEntity filterCatDetails = new OrderPaymentEntity();
				filterCatDetails.setId(sequenceGenerator.getNextSequence(OrderPaymentEntity.SEQUENCE_NAME));
				filterCatDetails.setOrderId(orderPaymentEntity.getOrderId());
				filterCatDetails.setPaymentMode(orderPaymentEntity.getPaymentMode());
				filterCatDetails.setPaymentDetails(orderPaymentEntity.getPaymentDetails());
				filterCatDetails.setPaymentResponse(map);
				filterCatDetails.setPaymentStatus(payStatus);
				filterCatDetails.setUserId(orderPaymentEntity.getUserId());
				filterCatDetails.setCreatedOn(format);
				userOrderPaymentRepo.save(filterCatDetails);
				commonUtility.userOrder(orderPaymentEntity);
				return ResponseEntity.ok(mapPayId);
			} else
				throw new CustomException(MessageConstant.ORDER_ID_EXIST.getMessage());
		} catch (RazorpayException e) {
			return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (HttpStatusCodeException ex) {
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(),ex.getStatusCode());
		}catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<?> postOrderSKUService(OrderSKUDetailsEntity orderSKUDetailsEntityRow) {
		LOGGER.info("Inside - OrderAndPaymentService.postOrderSKUService()");

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT_TYPE.getMessage());
			Date date = new Date();
			String format = formatter.format(date);

			orderSKUDetailsEntityRow.setId(sequenceGenerator.getNextSequence(OrderSKUDetailsEntity.SEQUENCE_NAME));
			orderSKUDetailsEntityRow.setCreatedOn(format);
			orderSKUDetailsEntityRow.setCustomizationStatus(orderSKUDetailsEntityRow.getCustomizationStatus());
			if (orderSKUDetailsEntityRow.getCustomizationStatus()) {
				orderSKUDetailsEntityRow.setCustomObject(orderSKUDetailsEntityRow.getCustomObject());
				orderSKUDetailsEntityRow.setUserComment(orderSKUDetailsEntityRow.getUserComment());
			}
			orderSKUDetailsEntityRow.setGiftwrapStatus(orderSKUDetailsEntityRow.getGiftwrapStatus());
			if (orderSKUDetailsEntityRow.getGiftwrapStatus()) {
				orderSKUDetailsEntityRow.setGiftWrapObject(orderSKUDetailsEntityRow.getGiftWrapObject());
			}
			if (orderSKUDetailsEntityRow.getCustomizationStatus()) {
				orderSKUDetailsEntityRow.setCustomizationStatus(orderSKUDetailsEntityRow.getCustomizationStatus());
			} else {
				orderSKUDetailsEntityRow.setCustomizationStatus(false);
			}
			if (orderSKUDetailsEntityRow.getGiftwrapStatus()) {
				orderSKUDetailsEntityRow.setGiftwrapStatus(orderSKUDetailsEntityRow.getGiftwrapStatus());
			} else {
				orderSKUDetailsEntityRow.setGiftwrapStatus(false);
			}
			orderSKUDetailsEntityRow.setDesignerId(orderSKUDetailsEntityRow.getDesignerId());
			orderSKUDetailsEntityRow.setOrderItemStatus("New");

			try {
				ResponseEntity<org.json.simple.JSONObject> forEntity = restTemplate.getForEntity(
						RestTemplateConstant.DESIGNER_PRODUCT.getLink() + orderSKUDetailsEntityRow.getProductId(),
						org.json.simple.JSONObject.class);
				int shipmentTime = Integer.parseInt(forEntity.getBody().get("shipmentTime").toString());
				Object productSku = forEntity.getBody().get("sku");
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DATE, shipmentTime);
				String output = formatter.format(c.getTime());
				HsnData hsndata = new HsnData();
				hsndata.setCgst(orderSKUDetailsEntityRow.getHsnData().getCgst());
				hsndata.setIgst(orderSKUDetailsEntityRow.getHsnData().getIgst());
				hsndata.setSgst(orderSKUDetailsEntityRow.getHsnData().getSgst());
				orderSKUDetailsEntityRow.setHsnData(hsndata);
				orderSKUDetailsEntityRow.setShippingDate(output);
				orderSKUDetailsEntityRow.setProductSku(productSku.toString());
				orderSKUDetailsRepo.save(orderSKUDetailsEntityRow);
			} catch (Exception e) {
				throw new CustomException(e.getMessage());
			}
			return ResponseEntity.ok(null);
		} catch (HttpStatusCodeException ex) {
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(),ex.getStatusCode());
		}catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public Map<String, Object> getOrderPaymentService(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentService.getOrderPaymentService()");
		try {
			int CountData = (int) userOrderPaymentRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<OrderPaymentEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = userOrderPaymentRepo.findAll(pagingSort);
			} else {
				findAll = userOrderPaymentRepo.Search(keyword, pagingSort);

			}

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() <= 0) {
				throw new CustomException(MessageConstant.PAYMENT_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getOrders(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy, String token, String orderStatus) {
		LOGGER.info("Inside - OrderAndPaymentService.getOrders()");
		try {
			int CountData = (int) orderDetailsRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<OrderDetailsEntity> findAll = null;

			if (!orderStatus.equals("All")) {
				findAll = orderDetailsRepo.findOrderStatus(orderStatus, pagingSort);
			} else if (keyword.isEmpty()) {
				findAll = orderDetailsRepo.findAll(pagingSort);
			} else {
				findAll = orderDetailsRepo.Search(keyword, pagingSort);
			}

			List<OrderSKUDetailsEntity> orderSKUDetails = new ArrayList<>();
			orderSKUDetails = this.orderSKUDetailsRepo.findAll();

			if (!orderStatus.isBlank() && !orderStatus.equals("All")) {
				List<String> collect = orderSKUDetails.stream().filter(e -> e.getOrderItemStatus().equals(orderStatus))
						.map(e -> e.getOrderId()).collect(Collectors.toList());
				findAll = orderDetailsRepo.findByOrderIdIn(collect, pagingSort);
			} else {
				findAll = orderDetailsRepo.findAll(pagingSort);
			}

			List<Object> productId = new ArrayList<>();

			findAll.getContent().forEach(e -> {
				ObjectMapper obj = new ObjectMapper();
				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}

				Optional<OrderPaymentEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByOrderId(e.getOrderId());
				List<OrderSKUDetailsEntity> OrderSKUDetailsRow = this.orderSKUDetailsRepo.findByOrderId(e.getOrderId());

				String writeValueAsString = null;
				JSONObject payRow = null;
				if (!OrderPaymentRow.isEmpty()) {
					try {
						writeValueAsString = obj.writeValueAsString(OrderPaymentRow.get());
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					JsonNode paymentJson = new JsonNode(writeValueAsString);
					payRow = paymentJson.getObject();
				}
				String OrderSKUD = null;
				try {
					if (!orderStatus.isEmpty() && !orderStatus.equals("All")) {
						OrderSKUD = obj.writeValueAsString(
								orderSKUDetailsRepo.findByOrderIdAndOrderItemStatus(e.getOrderId(), orderStatus));
						LOGGER.info("Order SKU DATA <><><><><> {}", OrderSKUD);
					} else {
						OrderSKUD = obj.writeValueAsString(OrderSKUDetailsRow);
					}
					// OrderSKUD = obj.writeValueAsString(OrderSKUDetailsRow);
				} catch (JsonProcessingException e2) {
					e2.printStackTrace();
				}

				JsonNode OrderSKUDJson = new JsonNode(OrderSKUD);

				JsonNode cartJN = new JsonNode(productIdFilter);
				JSONObject objects = cartJN.getObject();
				objects.put("paymentData", payRow);
				objects.put("OrderSKUDetails", OrderSKUDJson.getArray());
				productId.add(objects);
//				LOGGER.info("Payment data <><><><><><><><><>{}",payRow);
//				LOGGER.info("Order SKUDetails data <><><><><><><><><>{}",OrderSKUDJson.getArray());
			});
			LOGGER.info("<><><><><><><><><><>!!!!!!!! = {}", productId.size());
			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}
			Map<String, Object> response = new HashMap<>();
			response.put("data", new Json(productId.toString()));
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());
			response.put("requestForCancelation",
					orderSKUDetailsRepo.findByOrderItemStatus("Request for cancelation").size());
			response.put("New", orderSKUDetailsRepo.findByOrderItemStatus("New").size());
			response.put("Packed", orderSKUDetailsRepo.findByOrderItemStatus("Packed").size());
			response.put("Shipped", orderSKUDetailsRepo.findByOrderItemStatus("Shipped").size());
			response.put("Delivered", orderSKUDetailsRepo.findByOrderItemStatus("Delivered").size());
			response.put("Return", orderSKUDetailsRepo.findByOrderItemStatus("Return").size());
			response.put("Active", orderSKUDetailsRepo.findByOrderItemStatus("Active").size());
			response.put("Cancelled", orderSKUDetailsRepo.findByOrderItemStatus("cancelled").size());
			response.put("totalIteamStatus", orderSKUDetailsRepo.findByOrder(orderStatus).size());

//			if (productId.size() <= 0) {
//				Map<String, Integer> orderCount = getOrderCount(0, true);
//				response.put("orderCount", orderCount);
//				response.put("Error", "Order not found");
//				return response;
//			} else {
//				LOGGER.info("USERNAME IN ELSE <><><><><><><><><> !!!! = {}",
//						jwtconfig.extractUsername(token.substring(7)));
//				if (!restTemplate.getForEntity(
//						"https://localhost:8080/dev/auth/info/ADMIN/" + jwtconfig.extractUsername(token.substring(7)),
//						Object.class).toString().isBlank()) {
//					Map<String, Integer> orderCount = getOrderCount(0, true);
//					response.put("orderCount", orderCount);
//					return response;
//				}
//			}
			return response;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getOrderDetailsService(String orderId) {
		try {

			List<OrderDetailsEntity> findById = this.orderDetailsRepo.findByOrderId(orderId);
			LOGGER.info(findById + " Inside FindBYid");
			if (findById.size() <= 0) {
				throw new CustomException(MessageConstant.ORDER_NOT_FOUND.getMessage());
			}
			List<Object> productId = new ArrayList<>();
			List<Object> productIds = new ArrayList<>();

			findById.forEach(e -> {
				ObjectMapper obj = new ObjectMapper();
				LOGGER.info("Data for order id: " + e.toString());
				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
					LOGGER.info(productIdFilter + "Inside ProductIdfilter");
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}

				Optional<OrderPaymentEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByOrderId(e.getOrderId());
				LOGGER.info(e.getOrderId() + "Inside OrderId");
				List<OrderSKUDetailsEntity> OrderSKUDetailsRow = this.orderSKUDetailsRepo.findByOrderId(e.getOrderId());
				LOGGER.info(OrderPaymentRow + " Inside PaymentRow");
				LOGGER.info(" Inside OrderSku" + OrderSKUDetailsRow);
				OrderSKUDetailsRow.forEach(D -> {
					LOGGER.info("Data in for each method" + D.getProductId());
					ObjectMapper objs = new ObjectMapper();
					String productIdFilters = null;
					LOGGER.info("Top of try catch");

					// "https://localhost:8083/dev/designerProducts/productList/"
					try {
						LOGGER.info(D.getProductId() + " inside productid");
						ResponseEntity<org.json.simple.JSONObject> productById = restTemplate.getForEntity(
								RestTemplateConstant.DESIGNER_PRODUCT.getLink() + D.getProductId(),
								org.json.simple.JSONObject.class);
						LOGGER.info("Dta after rest call = {} ", productById);
//						LOGGER.info("Inside rest call" + productById.getBody().get("hsnData"));
						D.setHsn(productById.getBody().get("hsnData"));
						LOGGER.info(productById.getBody().get("withGiftWrap") + "Inside gift wrap");
						productIdFilters = objs.writeValueAsString(D);
						Integer i = (int) (long) D.getUserId();
						LOGGER.info(i + "Inside i");
						LOGGER.info(D.getDesignerId() + "Inside DesignerId");
						LOGGER.info(D.getProductId() + "Inside ProductId");
						List<OrderTrackingEntity> findByIdTracking = this.orderTrackingRepo
								.findByOrderIdAndUserIdAndDesignerIdAndProductId(orderId, i, D.getDesignerId(),
										D.getProductId());
						LOGGER.info(findByIdTracking + "Inside Tracking");
						JsonNode cartJNs = new JsonNode(productIdFilters);

						JSONObject objectss = cartJNs.getObject();
						LOGGER.info(objectss + "Inside objectss");
						objectss.put("customization", productById.getBody().get("customization"));
						objectss.put("withGiftWrap", productById.getBody().get("giftWrap"));
						String orderId2 = D.getOrderId();
						List<OrderInvoiceEntity> invoiceId = getInvoiceByOrder(orderId2);
						if (invoiceId.size() > 0) {
							invoiceId.forEach(invoice -> {
								objectss.put("invoiceId", invoice.getInvoiceId());
							});
							LOGGER.info(objectss + "Inside objectss");
						} else {
							objectss.put("invoiceId", JSONObject.NULL);
						}
						if (findByIdTracking.size() > 0) {
							String writeValueAsStringd = null;
							try {
								writeValueAsStringd = objs.writeValueAsString(findByIdTracking.get(0));
							} catch (JsonProcessingException e1) {
								e1.printStackTrace();
							}

							JsonNode TrackingJson = new JsonNode(writeValueAsStringd);

							LOGGER.info(TrackingJson + "Inside TrackingJson");
							objectss.put("TrackingData", TrackingJson.getObject());
							LOGGER.info(TrackingJson.getObject() + "Inside Trackingjson 33");

						}
						productIds.add(objectss);
					} catch (JsonProcessingException e2) {
						e2.printStackTrace();
					}
				});

				String writeValueAsString = null;
				JSONObject payJson = null;
				if (!OrderPaymentRow.isEmpty()) {
					try {
						writeValueAsString = obj.writeValueAsString(OrderPaymentRow.get());
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}

					JsonNode paymentJson = new JsonNode(writeValueAsString);
					payJson = paymentJson.getObject();
				}
				JsonNode cartJN = new JsonNode(productIdFilter);
				JSONObject objects = cartJN.getObject();
				objects.put("paymentData", payJson);
				LOGGER.info(productIds.get(0).toString());
				objects.put("OrderSKUDetails", productIds);

				productId.add(objects);

			});
			return ResponseEntity.ok(new Json(productId.get(0).toString()));
		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("<Application name:{}>,<Request URL:{}>,<Response message:{}>,<Response code:{}>",
						"Designer Service", host + contextPath + "/userOrder/getOrder/" + orderId,
						ex.getResponseBodyAsString(), ex.getStatusCode());
			}
			return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
		} catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("<Application name:{}>,<Request URL:{}>,<Response message:{}>,<Response code:{}>",
						"Designer Service", host + contextPath + "/userOrder/getOrder/" + orderId,
						exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(exception.getLocalizedMessage());
		}
	}

	public Map<String, Object> getUserOrderDetailsService(Integer userId, int page, int limit, String sort,
			String sortName, String keyword, Optional<String> sortBy, String token) {

		try {
			int CountData = (int) orderDetailsRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<OrderDetailsEntity> findAll = null;
			if (keyword.isEmpty()) {
				findAll = orderDetailsRepo.findByUserId(userId, pagingSort);

			} else {
				// findAll = orderDetailsRepo.findByUserId(Long.parseLong(userId.toString()),
				// sort, pagingSort);
				findAll = orderDetailsRepo.findByUserIdAndKeyword(userId, keyword, pagingSort);
			}
			LOGGER.info("findAll<><><><>" + findAll.getContent());
			// List<OrderDetailsEntity> findById =
			// this.orderDetailsRepo.findByUserIdOrderByIdDesc(userId);
			List<OrderDetailsEntity> findById = findAll.getContent();
			if (findById.size() <= 0) {
				throw new CustomException(MessageConstant.ORDER_NOT_FOUND.getMessage());
			}
			List<Object> productId = new ArrayList<>();

			findById.forEach(e -> {
				ObjectMapper obj = new ObjectMapper();

				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
				Optional<OrderPaymentEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByOrderId(e.getOrderId());
				List<OrderSKUDetailsEntity> OrderSKUDetailsRow = this.orderSKUDetailsRepo.findByOrderId(e.getOrderId());

				// get HSN for product

				OrderSKUDetailsRow.forEach(order -> {
					try {
						ResponseEntity<org.json.simple.JSONObject> getProductByID = restTemplate.getForEntity(
								RestTemplateConstant.DESIGNER_PRODUCT.getLink() + order.getProductId(),
								org.json.simple.JSONObject.class);
						LOGGER.info(getProductByID.getBody().get("hsnData").toString());
						order.setHsn(getProductByID.getBody().get("hsnData"));
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				});

				String writeValueAsString = null;

				JsonNode pJN = new JsonNode(productIdFilter);
				JSONObject object = pJN.getObject();
				JsonNode paymentJson = null;
				JSONObject payJson = null;

				if (!OrderPaymentRow.isEmpty()) {
					try {
						writeValueAsString = obj.writeValueAsString(OrderPaymentRow.get());
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					paymentJson = new JsonNode(writeValueAsString);
					payJson = paymentJson.getObject();
				}

				String OrderSKUD = null;
				try {
					OrderSKUD = obj.writeValueAsString(OrderSKUDetailsRow);
				} catch (JsonProcessingException e2) {
					e2.printStackTrace();
				}

				JsonNode OrderSKUDJson = new JsonNode(OrderSKUD);

				object.put("paymentData", payJson);
				object.put("OrderSKUDetails", OrderSKUDJson.getArray());
				productId.add(object);

			});
			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}
			// return ResponseEntity.ok(new Json(productId.toString()));
			Map<String, Object> response = new HashMap<>();
			response.put("data", new Json(productId.toString()));
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());
			return response;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getDesigerOrders(int designerId, int page, int limit, String sort, String sortName,
			String keyword, Optional<String> sortBy, String orderItemStatus, String sortDateType, String startDate,
			String endDate) {
		LOGGER.info("Inside - OrderAndPaymentService.getOrders()");
		LOGGER.info("Designer id = {}", designerId);
//		String orderItemStatusValue = null;
		try {

			int CountData = (int) orderDetailsRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			if (!sortDateType.equals(null)) {

				if (sortDateType.equalsIgnoreCase("new")) {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, "createdOn");

				} else if (sortDateType.equalsIgnoreCase("old")) {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, "createdOn");

				}
			}
			Page<OrderDetailsEntity> findAll = null;
			List<OrderSKUDetailsEntity> OrderSKUDetailsData = new ArrayList<>();
			if (keyword != null || !"".equals(keyword)) {
				OrderSKUDetailsData = this.orderSKUDetailsRepo.findByDesignerId(designerId);
				LOGGER.info("SKU data is = {}", OrderSKUDetailsData);
			}
			List<Object> productId = new ArrayList<>();

			if (!orderItemStatus.isEmpty() && !orderItemStatus.equals("Orders")) {

				LOGGER.info("SKU DATA IS ={}", OrderSKUDetailsData);

				List<String> OrderId1 = OrderSKUDetailsData.stream()

						.filter(e -> e.getOrderItemStatus().equals(orderItemStatus))
						.filter(e -> !keyword.isBlank() ? e.getOrderId().startsWith(keyword.toUpperCase()) : true)
						.filter(e -> {
							if (!startDate.isBlank() && !endDate.isBlank()) {
								try {
									SimpleDateFormat dateFormat = new SimpleDateFormat(
											MessageConstant.DATA_TYPE_FORMAT.getMessage());
									SimpleDateFormat dateFormat2 = new SimpleDateFormat(
											MessageConstant.DATE_FORMAT_TYPE.getMessage());
									Date startdate = dateFormat.parse(startDate);
									Date enddate = dateFormat.parse(endDate);
									Date createDate = dateFormat2.parse(e.getCreatedOn());
									String format = dateFormat.format(createDate);
									Date createDateFormatted = dateFormat.parse(format);
									if ((createDateFormatted.after(startdate) || createDateFormatted.equals(startdate))
											&& createDateFormatted.before(enddate)
											|| createDateFormatted.equals(enddate)) {
										return true;
									} else {
										return false;
									}
								} catch (Exception e2) {
									throw new CustomException(e2.getMessage());
								}
							} else {
								return true;
							}
						}).map(c -> c.getOrderId()).collect(Collectors.toList());

				LOGGER.info("Order id = {}", OrderId1);
				findAll = orderDetailsRepo.findByOrderIdIn(OrderId1, pagingSort);

				LOGGER.info("Data for find ALL in if = {}", findAll.getContent());

			} else {
				List<String> OrderId = OrderSKUDetailsData.stream().filter(
						e -> !keyword.isBlank() ? e.getOrderId().toUpperCase().startsWith(keyword.toUpperCase()) : true)
						.filter(e -> {
							if (!startDate.isBlank() && !endDate.isBlank()) {
								try {
									SimpleDateFormat dateFormat = new SimpleDateFormat(
											MessageConstant.DATA_TYPE_FORMAT.getMessage());
									SimpleDateFormat dateFormat2 = new SimpleDateFormat(
											MessageConstant.DATE_FORMAT_TYPE.getMessage());
									Date startdate = dateFormat.parse(startDate);
									Date enddate = dateFormat.parse(endDate);
									Date createDate = dateFormat2.parse(e.getCreatedOn());
									String format = dateFormat.format(createDate);
									Date createDateFormatted = dateFormat.parse(format);
									if ((createDateFormatted.after(startdate) || createDateFormatted.equals(startdate))
											&& createDateFormatted.before(enddate)
											|| createDateFormatted.equals(enddate)) {
										return true;
									} else {
										return false;
									}
								} catch (Exception e2) {
									throw new CustomException(e2.getMessage());
								}
							} else {
								return true;
							}
						}).map(c -> c.getOrderId()).collect(Collectors.toList());
				findAll = orderDetailsRepo.findByOrderIdIn(OrderId, pagingSort);
				LOGGER.info("Data for find ALL in else = {}", findAll.getContent());
			}

			List<OrderDetailsEntity> content = findAll.getContent();
			LOGGER.info("Content data is = {}", content);
			content.forEach(e -> {
				ObjectMapper obj = new ObjectMapper();
				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}

				Optional<OrderPaymentEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByOrderId(e.getOrderId());

				List<OrderSKUDetailsEntity> OrderSKUDetailsRow = this.orderSKUDetailsRepo
						.findByOrderIdAndDesignerId(e.getOrderId(), designerId);
				LOGGER.info("value for SKU = {}", OrderSKUDetailsRow);
				JsonNode pJN = new JsonNode(productIdFilter);
				JSONObject object = pJN.getObject();
				String writeValueAsString = null;
				JSONObject payRow = null;
				if (!OrderPaymentRow.isEmpty()) {
					try {
						writeValueAsString = obj.writeValueAsString(OrderPaymentRow.get());
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					JsonNode paymentJson = new JsonNode(writeValueAsString);
					payRow = paymentJson.getObject();
				}
				String OrderSKUD = null;
				try {
					if (!orderItemStatus.isEmpty() && !orderItemStatus.equals("Orders")) {
						OrderSKUD = obj.writeValueAsString(
								orderSKUDetailsRepo.findByOrderIdAndDesignerIdAndorderItemStatus(e.getOrderId(),
										designerId, orderItemStatus));
					} else if (!orderItemStatus.isEmpty() && orderItemStatus.equals("Orders")) {
						OrderSKUD = obj.writeValueAsString(OrderSKUDetailsRow);
					} else {
						OrderSKUD = obj.writeValueAsString(OrderSKUDetailsRow);
					}

				} catch (JsonProcessingException e2) {
					e2.printStackTrace();
				}

				JsonNode OrderSKUDJson = new JsonNode(OrderSKUD);

				object.put("paymentData", payRow);
				object.put("OrderSKUDetails", OrderSKUDJson.getArray());
				productId.add(object);

			});

			LOGGER.info(productId.toString() + "inside ProductId");
			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();

			if (!productId.isEmpty()) {
				response.put("data", new Json(productId.toString()));
			} else {
				response.put("data", productId);
			}
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());
			response.put("New", orderSKUDetailsRepo.findByOrderTotal(designerId, "New").size());
			response.put("Packed", orderSKUDetailsRepo.findByOrderTotal(designerId, "Packed").size());
			response.put("Shipped", orderSKUDetailsRepo.findByOrderTotal(designerId, "Shipped").size());
			response.put("Delivered", orderSKUDetailsRepo.findByOrderTotal(designerId, "Delivered").size());
			response.put("Return", orderSKUDetailsRepo.findByOrderTotal(designerId, "returnRefund").size());
			response.put("Active", orderSKUDetailsRepo.findByOrderTotal(designerId, "Active").size());
			response.put("cancelRequest",
					orderSKUDetailsRepo.findByOrderTotal(designerId, "Request for cancelation").size());
			response.put("Orders", orderSKUDetailsRepo.findByDesignerId(designerId).size());
			response.put("Canceled", orderSKUDetailsRepo.findByOrderTotal(designerId, "cancelled").size());

			return response;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse invoiceGenarator(String orderId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("order_id").is(orderId));
			OrderDetailsEntity detailsEntity = mongoOperations.findOne(query, OrderDetailsEntity.class);
			if (detailsEntity != null) {

				InvoiceEntity invoiceEntity = new InvoiceEntity();
				invoiceEntity.setOrderDetailsEntity(detailsEntity);
				PDFRunner pdfRunner = new PDFRunner(invoiceEntity);
				pdfRunner.fun1();
				return pdfRunner.pdfPath(orderId);
			} else {
				return new GlobalResponse(MessageConstant.ERROR.getMessage(),
						MessageConstant.ORDER_NOT_FOUND.getMessage(), 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public OrderDetailsEntity getOrderDetails(String orderId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("order_id").is(orderId));
			OrderDetailsEntity orderDetailsEntity = mongoOperations.findOne(query, OrderDetailsEntity.class);
			return orderDetailsEntity;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getProductDetails(String orderId, int page, int limit, String sort, String sortName,
			String keyword, Optional<String> sortBy) {
		try {
			try {
				Query query = new Query();
				query.addCriteria(Criteria.where("order_id").is(orderId));
				mongoTemplate.findOne(query, OrderDetailsEntity.class);

				Pageable pagingSort = null;

				if (sort.equals("ASC")) {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
				} else {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
				}

				Page<OrderDetailsEntity> findAll = null;

				if (keyword.isEmpty()) {
					findAll = orderDetailsRepo.findByOrderId(orderId, pagingSort);
				}

				int totalPage = findAll.getTotalPages() - 1;
				if (totalPage < 0) {
					totalPage = 0;
				}

				Map<String, Object> response = new HashMap<>();
				response.put("data", findAll.getContent());
				response.put("currentPage", findAll.getNumber());
				response.put("total", findAll.getTotalElements());
				response.put("totalPage", totalPage);
				response.put("perPage", findAll.getSize());
				response.put("perPageElement", findAll.getNumberOfElements());

				if (findAll.getSize() <= 0) {
					throw new CustomException(MessageConstant.PAYMENT_NOT_FOUND.getMessage());
				} else {
					return response;
				}
			} catch (Exception e) {
				throw new CustomException(e.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse orderUpdateService(OrderSKUDetailsEntity orderSKUDetailsEntity, String orderId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("order_id").is(orderId));
			OrderSKUDetailsEntity orderSKUDetailsEntity2 = mongoOperations.findOne(query, OrderSKUDetailsEntity.class);
			if (!orderSKUDetailsEntity2.equals(null)) {
				OrderSKUDetailsEntity orderSKUDetailsEntity1 = orderSKUDetailsRepo.findByOrderId(orderId).get(0);
				orderSKUDetailsEntity1.setOrderId(orderDetailsRepo.findByOrderId(orderId).get(0).getOrderId());
				orderSKUDetailsEntity1.setOrderItemStatus(orderSKUDetailsEntity.getOrderItemStatus());
				orderSKUDetailsRepo.save(orderSKUDetailsEntity1);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_STATUS_UPDATED.getMessage(), 200);
			} else {
				return new GlobalResponse(MessageConstant.ERROR.getMessage(),
						MessageConstant.ORDER_NOT_FOUND.getMessage(), 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	private enum ResourceType {
		FILE_SYSTEM, CLASSPATH
	}

	private static final String FILE_DIRECTORY = "/var/files";

	public Resource getFileSystem(String filename, HttpServletResponse response) {
		return getResource(filename, response, ResourceType.FILE_SYSTEM);
	}

	public Resource getClassPathFile(String filename, HttpServletResponse response) {
		return getResource(filename, response, ResourceType.CLASSPATH);
	}

	private Resource getResource(String filename, HttpServletResponse response, ResourceType resourceType) {
		response.setContentType("text/csv; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setHeader("filename", filename);

		Resource resource = null;
		switch (resourceType) {
		case FILE_SYSTEM:
			resource = new FileSystemResource(FILE_DIRECTORY + filename);
			break;
		case CLASSPATH:
			resource = new ClassPathResource("data/" + filename);
			break;
		}

		return resource;
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity<?> postOrderHandleDetailsService(org.json.simple.JSONObject object) {
		LOGGER.info("Inside - OrderAndPaymentService.postOrderHandleDetailsService ");
		try {

			org.json.simple.JSONObject PayEntity = new org.json.simple.JSONObject((Map) object.get("entity"));

			final RazorpayClient razorpayClient = new RazorpayClient(env.getProperty("key"),
					env.getProperty("secretKey"));

			List<OrderDetailsEntity> OrderRow = orderDetailsRepo
					.findByRazorpayOrderId(PayEntity.get("order_id").toString());

			if (OrderRow.size() <= 0) {
				LOGGER.info("Order id not found in order table");
				throw new CustomException(MessageConstant.ORDER_NOT_FOUND.getMessage());
			}

			List<Payment> payments = razorpayClient.Orders.fetchPayments(PayEntity.get("order_id").toString());

			Payment payment = null;
			String payStatus = "FAILED";
			for (Payment pay : payments) {
				payment = razorpayClient.Payments.fetch(pay.get("id").toString());

				if (payment.get("error_code").equals(null) && payment.get("error_reason").equals(null)
						&& payment.get("error_step").equals(null) && payment.get("status").equals("captured")) {
					payStatus = "COMPLETED";
					break;
				}
			}

			if (payment.equals(null)) {
				LOGGER.info("Payment not found in order table");
				throw new CustomException(MessageConstant.PAYMENT_NOT_FOUND.getMessage());
			}

			ObjectMapper obj = new ObjectMapper();
			Map<String, Object> map = obj.readValue(payment.toString(), new TypeReference<Map<String, Object>>() {
			});

			Map<String, Object> PayResponse = new HashMap<>();
			PayResponse.put("razorpay_payment_id", PayEntity.get("id"));
			PayResponse.put("razorpay_order_id", PayEntity.get("order_id"));
			PayResponse.put("razorpay_signature", "");

			Optional<OrderPaymentEntity> PaymentRow = userOrderPaymentRepo.findPaymentId(PayEntity.get("id").toString(),
					PayEntity.get("order_id").toString());

			int payId = sequenceGenerator.getNextSequence(OrderPaymentEntity.SEQUENCE_NAME);
			OrderPaymentEntity filterCatDetails = null;

			if (PaymentRow.isEmpty()) {
				filterCatDetails = new OrderPaymentEntity();
			} else {
				filterCatDetails = PaymentRow.get();
				payId = PaymentRow.get().getId();
			}
			SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT_TYPE.getMessage());
			Date date = new Date();
			String format = formatter.format(date);

			filterCatDetails.setId(payId);
			filterCatDetails.setOrderId(OrderRow.get(0).getOrderId());
			filterCatDetails.setPaymentDetails(PayResponse);
			filterCatDetails.setPaymentResponse(map);
			filterCatDetails.setPaymentStatus(payStatus);
			filterCatDetails.setUserId(OrderRow.get(0).getUserId());
			filterCatDetails.setCreatedOn(format);

			OrderPaymentEntity data = userOrderPaymentRepo.save(filterCatDetails);

			return ResponseEntity.ok(data);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> postOrderTrackingService(OrderTrackingEntity orderTrackingEntity) {

		try {

			orderTrackingRepo.findByTrackingIds(orderTrackingEntity.getTrackingId());

//			if (OrderTrackingRow.size() <= 0){

			OrderTrackingEntity filterCatDetails = new OrderTrackingEntity();

			filterCatDetails.setId(sequenceGenerator.getNextSequence(OrderTrackingEntity.SEQUENCE_NAME));
			filterCatDetails.setOrderId(orderTrackingEntity.getOrderId());
			filterCatDetails.setDeliveredDate(orderTrackingEntity.getDeliveredDate());
			filterCatDetails.setDeliveryExpectedDate(orderTrackingEntity.getDeliveryExpectedDate());
			filterCatDetails.setDeliveryMode(orderTrackingEntity.getDeliveryMode());
			filterCatDetails.setDeliveryStatus(orderTrackingEntity.getDeliveryStatus());
			filterCatDetails.setDeliveryType(orderTrackingEntity.getDeliveryType());
			filterCatDetails.setProcuctSku(orderTrackingEntity.getProcuctSku());
			filterCatDetails.setProductId(orderTrackingEntity.getProductId());
			filterCatDetails.setTrackingHistory(orderTrackingEntity.getTrackingHistory());
			filterCatDetails.setTrackingUrl(orderTrackingEntity.getTrackingUrl());
			filterCatDetails.setTrackingId(getRandomStringInt());
			filterCatDetails.setUserId(orderTrackingEntity.getUserId());
			filterCatDetails.setDesignerId(orderTrackingEntity.getDesignerId());

			orderTrackingRepo.save(filterCatDetails);

			return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.TRACKING_UPDATED.getMessage(), 200));

//			} else {
//				throw new CustomException("Something went to wrong! from order related");
//			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> putOrderTrackingService(OrderTrackingEntity orderTrackingEntity, String trackingId) {

		try {

			Optional<OrderTrackingEntity> OrderTrackingRow = orderTrackingRepo.findByTrackingId(trackingId);

			if (!OrderTrackingRow.isEmpty()) {

				OrderTrackingEntity filterCatDetails = OrderTrackingRow.get();

				filterCatDetails.setOrderId(orderTrackingEntity.getOrderId());
				filterCatDetails.setDeliveredDate(orderTrackingEntity.getDeliveredDate());
				filterCatDetails.setDeliveryExpectedDate(orderTrackingEntity.getDeliveryExpectedDate());
				filterCatDetails.setDeliveryMode(orderTrackingEntity.getDeliveryMode());
				filterCatDetails.setDeliveryStatus(orderTrackingEntity.getDeliveryStatus());
				filterCatDetails.setDeliveryType(orderTrackingEntity.getDeliveryType());
				filterCatDetails.setProcuctSku(orderTrackingEntity.getProcuctSku());
				filterCatDetails.setProductId(orderTrackingEntity.getProductId());
				filterCatDetails.setTrackingHistory(orderTrackingEntity.getTrackingHistory());
				filterCatDetails.setTrackingUrl(orderTrackingEntity.getTrackingUrl());
				filterCatDetails.setTrackingId(trackingId);
				filterCatDetails.setUserId(orderTrackingEntity.getUserId());
				filterCatDetails.setDesignerId(orderTrackingEntity.getDesignerId());

				orderTrackingRepo.save(filterCatDetails);

				return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.TRACKING_UPDATED.getMessage(), 200));

			} else {
				throw new CustomException(MessageConstant.SOMETHING_WENT_WRONG.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> getOrderTrackingDetailsService(String orderId, int userId, int designerId) {
		try {
			Map<String, Object> map = new HashMap<>();
			List<OrderTrackingEntity> findById = null;

			if (orderId != null && (Integer) userId != 0 && (Integer) designerId != 0) {
				findById = this.orderTrackingRepo.findByOrderIdLDU(orderId, userId, designerId);
			} else {
				findById = this.orderTrackingRepo.findByOrderIdL(orderId);

			}
			List<Object> St = new ArrayList<>();
			if (findById.size() <= 0) {
				map.put("status", 200);
				map.put("data", St);
				map.put("message", "Order not found");
				return ResponseEntity.ok(map);
			}
			map.put("status", 200);
			map.put("data", findById);

			return ResponseEntity.ok(map);
		} catch (Exception e2) {
			return ResponseEntity.ok(e2.getMessage());
		}
	}

	public GlobalResponse orderStatusUpdateService(OrderSKUDetailsEntity orderSKUDetailsEntity, String refOrderId,
			Integer refProductId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("order_id").is(refOrderId).and("productId").is(refProductId));
			OrderSKUDetailsEntity skuDetailsEntity = mongoOperations.findOne(query, OrderSKUDetailsEntity.class);
			
			if(skuDetailsEntity.getOrderId().equals(null) && skuDetailsEntity.getOrderId().toString() == "") {
				throw new CustomException(MessageConstant.BAD_REQUEST.getMessage());
			}
			
			List<OrderPaymentEntity> findByOrderIdList = userOrderPaymentRepo.findByOrderIdList(refOrderId);
			List<OrderSKUDetailsEntity> findByOrderSKU = new ArrayList<>();
			JSONObject getPaymentData = null;
			
			if(findByOrderIdList.size() >0 ) {
				getPaymentData = new JSONObject(gson.toJson(findByOrderIdList.get(0).getPaymentDetails()));
				findByOrderSKU = orderSKUDetailsRepo.findByOrderId(refOrderId);
			}
			
			if (orderSKUDetailsEntity.getOrderItemStatus().equals("cancelled")) {
				skuDetailsEntity.setId(skuDetailsEntity.getId());
				skuDetailsEntity.setOrderItemStatus(orderSKUDetailsEntity.getOrderItemStatus());
				skuDetailsEntity.setOrderStatusDetails(orderSKUDetailsEntity.getOrderStatusDetails());
				orderSKUDetailsRepo.save(skuDetailsEntity);
				
				Query query2 = new Query();
				query.addCriteria(Criteria.where("orderId").is(refOrderId));
				OrderDetailsEntity detailsEntity = mongoOperations.findOne(query2, OrderDetailsEntity.class);
				detailsEntity.setId(detailsEntity.getId());
				detailsEntity.setTotalAmount(detailsEntity.getTotalAmount() - skuDetailsEntity.getSalesPrice());
				detailsEntity.setTaxAmount(detailsEntity.getTaxAmount() - skuDetailsEntity.getTaxAmount());
				detailsEntity.setMrp(detailsEntity.getMrp() - skuDetailsEntity.getMrp());
				orderDetailsRepo.save(detailsEntity);
				
				commonUtility.orderRefund(findByOrderSKU, orderSKUDetailsEntity, getPaymentData, findByOrderIdList);
				
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),MessageConstant.ORDER_CANCEL.getMessage(), 200);
			
			} else if (orderSKUDetailsEntity.getOrderItemStatus().equals("returnRequest")) {
				skuDetailsEntity.setId(skuDetailsEntity.getId());
				skuDetailsEntity.setOrderItemStatus(orderSKUDetailsEntity.getOrderItemStatus());
				skuDetailsEntity.setOrderStatusDetails(orderSKUDetailsEntity.getOrderStatusDetails());
				orderSKUDetailsRepo.save(skuDetailsEntity);

				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_REFUND_REQUEST.getMessage(), 200);
			} else if (orderSKUDetailsEntity.getOrderItemStatus().equals("returnRefund")) {
				skuDetailsEntity.setId(skuDetailsEntity.getId());
				skuDetailsEntity.setOrderItemStatus(orderSKUDetailsEntity.getOrderItemStatus());
				skuDetailsEntity.setOrderStatusDetails(orderSKUDetailsEntity.getOrderStatusDetails());
				orderSKUDetailsRepo.save(skuDetailsEntity);

				Query query2 = new Query();
				query.addCriteria(Criteria.where("orderId").is(refOrderId));
				OrderDetailsEntity detailsEntity = mongoOperations.findOne(query2, OrderDetailsEntity.class);
				detailsEntity.setId(detailsEntity.getId());
				detailsEntity.setTotalAmount(detailsEntity.getTotalAmount() - skuDetailsEntity.getSalesPrice());
				detailsEntity.setTaxAmount(detailsEntity.getTaxAmount() - skuDetailsEntity.getTaxAmount());
				detailsEntity.setMrp(detailsEntity.getMrp() - skuDetailsEntity.getMrp());
				orderDetailsRepo.save(detailsEntity);
				
				commonUtility.orderRefund(findByOrderSKU, orderSKUDetailsEntity, getPaymentData, findByOrderIdList);
				
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_REFUND_APPROVED.getMessage(), 200);
			} else {
				throw new CustomException(MessageConstant.BAD_REQUEST.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("all")
	public ResponseEntity<?> getOrderServiceByInvoiceId(String invoiceId) {
		try {
			Query query = new Query();
			Query query2 = new Query();
			Map<String, Object> data = new HashMap<String, Object>();
			List<Integer> desiredDesingerIdList = new ArrayList<Integer>();
			query.addCriteria(Criteria.where("invoiceId").is(invoiceId));
			OrderDetailsEntity orderDetailsEntity = mongoOperations.findOne(query, OrderDetailsEntity.class);
			BillingAddressEntity billAddressData = new BillingAddressEntity();
			billAddressData.setAddress1(orderDetailsEntity.getBillingAddress().getAddress1());
			billAddressData.setFullName(orderDetailsEntity.getBillingAddress().getFullName());
			billAddressData.setCountry(orderDetailsEntity.getBillingAddress().getCountry());
			billAddressData.setState(orderDetailsEntity.getBillingAddress().getState());
			billAddressData.setCity(orderDetailsEntity.getBillingAddress().getCity());
			billAddressData.setPostalCode(orderDetailsEntity.getBillingAddress().getPostalCode());
			billAddressData.setMobile(orderDetailsEntity.getBillingAddress().getMobile());
			query2.addCriteria(Criteria.where("orderId").is(orderDetailsEntity.getOrderId()));
			List<OrderSKUDetailsEntity> orderSKUDetails = mongoOperations.find(query2, OrderSKUDetailsEntity.class);
			String body = restTemplate.getForEntity(RestTemplateConstant.DESIGNER_IDLIST.getLink(), String.class)
					.getBody();
			JSONArray jsonArray = new JSONArray(body);
			ObjectMapper mapper = new ObjectMapper();
			for (int i = 0; i < jsonArray.length(); i++) {
				org.json.simple.JSONObject designerLoginEntity = mapper.readValue(jsonArray.get(i).toString(),
						org.json.simple.JSONObject.class);
				desiredDesingerIdList.add(Integer.parseInt(designerLoginEntity.get("dId").toString()));
			}
			int totalTax = 0;
			int totalAmount = 0;
			int totalGrossAmount = 0;
			for (int i = 0; i < desiredDesingerIdList.size(); i++) {
				List<ProductInvoice> productList = new ArrayList<>();

				for (OrderSKUDetailsEntity a : orderSKUDetails) {
					if (a.getDesignerId() == desiredDesingerIdList.get(i)) {
						ProductInvoice invoice = new ProductInvoice();
						invoice.setGrossAmount(a.getMrp().intValue());
						invoice.setIgst(a.getTaxAmount().intValue());
						invoice.setProductDescription(a.getProductName());
						invoice.setProductSKUId(a.getProductSku());
						invoice.setQuantity(a.getUnits().toString());
						invoice.setWithTaxAmount(a.getSalesPrice().intValue());
						invoice.setProductSize(a.getSize());
						productList.add(invoice);
						totalTax = totalTax + a.getTaxAmount().intValue();
						totalAmount = totalAmount + a.getSalesPrice().intValue();
						totalGrossAmount = totalGrossAmount + a.getMrp().intValue();
					}
				}
				if (productList.size() > 0) {
					data.put("data", productList);
				}
			}
			ProductInvoice invoice = new ProductInvoice();
			invoice.setGrossAmount(totalGrossAmount);
			invoice.setWithTaxAmount(totalAmount);
			invoice.setIgst(totalTax);
			Map<String, Object> data4 = new HashMap<>();
			data4.put("totalData", invoice);
			Map<String, Object> response = new HashMap<>();
			response.put("billAddressData", billAddressData);
			Context context = new Context();
			context.setVariables(response);
			context.setVariables(data);
			context.setVariables(data4);
			String htmlContent = templateEngine.process("invoiceUpdated.html", context);

			ByteArrayOutputStream target = new ByteArrayOutputStream();
			ConverterProperties converterProperties = new ConverterProperties();
			converterProperties.setBaseUri("http://localhost:8082");
			HtmlConverter.convertToPdf(htmlContent, target, converterProperties);
			byte[] bytes = target.toByteArray();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=" + "orderInvoiceUpdated.pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bytes);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrderInvoiceId(String invoiceId) {
		try {
			Query query = new Query();
			Query query2 = new Query();
			List<Object> resObjects = new ArrayList<Object>();
			Map<String, Object> response = new HashMap<String, Object>();
			query.addCriteria(Criteria.where("invoice_id").is(invoiceId));
			OrderDetailsEntity detailsEntity = mongoOperations.findOne(query, OrderDetailsEntity.class);
			response.put("OrderDetails", detailsEntity);
			System.out.println(detailsEntity.getOrderId());
			query2.addCriteria(Criteria.where("orderId").is(detailsEntity.getOrderId()));
			List<OrderSKUDetailsEntity> orderList = mongoOperations.find(query2, OrderSKUDetailsEntity.class);
			for (int i = 0; i < orderList.size(); i++) {
				ResponseEntity<Object> designerData = restTemplate.getForEntity(
						RestTemplateConstant.DESIGNER_BYID.getLink() + orderList.get(i).getDesignerId(), Object.class);
				org.json.simple.JSONObject object = new org.json.simple.JSONObject();
				object.put("ProductData", orderList.get(i));
				object.put("DesignerData", designerData.getBody());
				resObjects.add(object);
			}
			response.put("OrderSKUDetails", resObjects);
			return response;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> postOrderInvoiceService(OrderInvoiceEntity orderInvoiceEntity) {
		LOGGER.info("Inside - OrderAndPaymentService.postOrderInvoiceService()");
		OrderInvoiceEntity saveData = null;
		try {
			List<OrderInvoiceEntity> findByCategoryName = orderInvoiceRepo
					.findByInvoiceId(orderInvoiceEntity.getInvoiceId());
			if (findByCategoryName.size() > 0) {
				throw new CustomException(MessageConstant.INVOICE_EXIST.getMessage());
			} else {
				OrderInvoiceEntity OrderLastRow = orderInvoiceRepo.findTopByOrderByIdDesc();

				String InvNumber = String.format("%014d", OrderLastRow.getId());
				orderInvoiceEntity.setId(sequenceGenerator.getNextSequence(OrderInvoiceEntity.SEQUENCE_NAME));
				orderInvoiceEntity.setInvoiceId("IV" + InvNumber);
				saveData = orderInvoiceRepo.save(orderInvoiceEntity);
			}
			org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
			jsonObject.put("reason", MessageConstant.SUCCESS.getMessage());
			jsonObject.put("message", MessageConstant.INVOICE_ADDED.getMessage());
			jsonObject.put("invoiceId", orderInvoiceEntity.getInvoiceId());
			jsonObject.put("status", 200);
			return ResponseEntity.ok(jsonObject);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> putOrderInvoiceService(@PathVariable String invoiceId,
			@Valid OrderInvoiceEntity orderInvoiceEntity) {
		LOGGER.info("Inside - OrderAndPaymentService.putOrderInvoiceService()");
		try {
			List<OrderInvoiceEntity> findByInvId = orderInvoiceRepo.findByInvoiceId(invoiceId);
			if (findByInvId.size() <= 0) {
				throw new CustomException(MessageConstant.INVOICE_NOT_FOUND.getMessage());
			} else {
				OrderInvoiceEntity orderInvoiceRow = findByInvId.get(0);
				orderInvoiceRow.setInvoiceDatetime(orderInvoiceEntity.getInvoiceDatetime());
				orderInvoiceRow.setDesignerDetails(orderInvoiceEntity.getDesignerDetails());
				orderInvoiceRow.setOrderDatetime(orderInvoiceEntity.getOrderDatetime());
				orderInvoiceRow.setProductDetails(orderInvoiceEntity.getProductDetails());
				orderInvoiceRow.setOrderId(orderInvoiceEntity.getOrderId());
				orderInvoiceRow.setUserDetails(orderInvoiceEntity.getUserDetails());

				orderInvoiceRepo.save(orderInvoiceRow);
			}
			return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.INVOICE_UPDATED.getMessage(), 200));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getOrderInvoiceService(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentService.getOrderInvoiceService()");

		try {

			Pageable pagingSort = null;

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<OrderInvoiceEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = orderInvoiceRepo.findAll(pagingSort);
			} else {
				findAll = orderInvoiceRepo.SearchByKey(keyword, pagingSort);
			}
			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() <= 1) {
				throw new CustomException(MessageConstant.INVOICE_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Integer> getOrderCount(int designerId, Boolean adminstatus) {

		try {
			Map<String, Integer> countResponse = new HashMap<String, Integer>();
			List<String> orderIdList = new ArrayList<String>();
			if (adminstatus) {
//				List<OrderDetailsEntity> findByDesignerId = orderDetailsRepo.findAll();
//				findByDesignerId.stream().forEach(e -> {
//					if (!orderIdList.contains(e.getOrderId())) {
//						orderIdList.add(e.getOrderId());
//					}
//				});
				List<OrderDetailsEntity> getOrderDetailsData = orderDetailsRepo.findAll();
				getOrderDetailsData.stream().forEach(e -> {
					countResponse.put(e.getOrderStatus(), 0);
				});
				getOrderDetailsData.stream().forEach(e -> {
					try {
						int lastData = countResponse.get(e.getOrderStatus());
						countResponse.put(e.getOrderStatus(), lastData + 1);
					} catch (NullPointerException e1) {
						throw new CustomException(e1.getMessage());
					}
				});

				return countResponse;
			} else {
				List<OrderSKUDetailsEntity> findByDesignerId = orderSKUDetailsRepo.findByDesignerId(designerId);
				findByDesignerId.stream().forEach(e -> {
					if (!orderIdList.contains(e.getOrderId())) {
						orderIdList.add(e.getOrderId());
					}
				});
				List<OrderDetailsEntity> getOrderDetailsData = orderDetailsRepo.findByOrderIdIn(orderIdList);
				getOrderDetailsData.stream().forEach(e -> {
					countResponse.put(e.getDeliveryStatus(), 0);
				});
				getOrderDetailsData.stream().forEach(e -> {
					try {
						int lastData = countResponse.get(e.getDeliveryStatus());
						countResponse.put(e.getDeliveryStatus(), lastData + 1);
					} catch (NullPointerException e1) {
						throw new CustomException(e1.getMessage());
					}
				});

				return countResponse;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public OrderSKUDetailsEntity getOrderDetailsService(String orderId, String productId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("orderId").is(orderId).and("productId").is(productId));
			return mongoOperations.findOne(query, OrderSKUDetailsEntity.class);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public OrderSKUDetailsEntity getorderDetails(String orderId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("orderId").is(orderId));
			return mongoOperations.findOne(query, OrderSKUDetailsEntity.class);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public List<OrderDetailsEntity> getOrderListService(ListResponseDTO jsonObject) {
		try {
			List<String> orderIdList = jsonObject.getOrderList();
			return orderDetailsRepo.findByOrderIdIn(orderIdList);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public Object getDesignerSideOrderListService(String token, String orderStatus) {
		try {
			LOGGER.info(jwtconfig.extractUsername(token.substring(7)));
			org.json.simple.JSONObject designerObject = restTemplate
					.getForEntity(RestTemplateConstant.DESIGNER_DETAILS.getLink()
							+ jwtconfig.extractUsername(token.substring(7)), org.json.simple.JSONObject.class)
					.getBody();
			Integer designerId = Integer.parseInt(designerObject.get("designerId").toString());
			LOGGER.info(designerId + "");
			return orderSKUDetailsRepo.findByDesignerId(designerId).stream()
					.filter(designerOrder -> designerOrder.getOrderItemStatus().equals(orderStatus))
					.collect(Collectors.toList());
			// return orderStatusFiltered;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalResponse cancelOrderService(String orderId, String productId, String token,
			CancelationRequestDTO cancelationRequestDTO) {
		try {
			String designerEmail = jwtconfig.extractUsername(token.substring(7));
			LOGGER.info(designerEmail);
			String designerId = restTemplate
					.getForEntity(RestTemplateConstant.DESIGNER_DETAILS.getLink() + designerEmail,
							org.json.simple.JSONObject.class)
					.getBody().get("designerId").toString();
//			designerId=;
//			LOGGER.info(designerId);
			// return null;
			List<OrderSKUDetailsEntity> orderDetails = orderSKUDetailsRepo.findAll().stream()
					.filter(e -> e.getDesignerId() == Long.parseLong(designerId))
					.filter(e -> e.getOrderId().equals(orderId))
					.filter(e -> e.getProductId() == Integer.parseInt(productId)).collect(Collectors.toList());
			LOGGER.info(orderDetails + "");
			String orderItemStatus = orderDetails.get(0).getOrderItemStatus();
			LOGGER.info("orderItemStatus" + orderItemStatus);
			orderDetails.get(0).setStatus(orderItemStatus);
			String status = orderDetails.get(0).getStatus();
			LOGGER.info("status" + status);
			SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT_TYPE.getMessage());
			Date dates = new Date();
			String format = formatter.format(dates);
			if (!orderDetails.get(0).getOrderItemStatus().equals("New")) {
				org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
				jsonObject.put("cancelComment", cancelationRequestDTO.getComment());
				jsonObject.put("cancelationTime", format);
				OrderStatusDetails orderStatusDetails = orderDetails.get(0).getOrderStatusDetails();
				try {
					orderStatusDetails.setCancelOrderDetails(jsonObject);
					orderDetails.get(0).setOrderStatusDetails(orderStatusDetails);
					orderDetails.get(0).setOrderItemStatus("Request for cancelation");
					orderSKUDetailsRepo.saveAll(orderDetails);
				} catch (Exception e) {
					orderStatusDetails.setCancelOrderDetails(jsonObject);
					orderDetails.get(0).setOrderStatusDetails(orderStatusDetails);
					orderDetails.get(0).setOrderItemStatus("Request for cancelation");
					orderSKUDetailsRepo.saveAll(orderDetails);
				}
			} else {
				org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
				jsonObject.put("cancelComment", cancelationRequestDTO.getComment());
				jsonObject.put("cancelationTime", format);
				OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
				try {
					orderStatusDetails.setCancelOrderDetails(jsonObject);
					orderDetails.get(0).setOrderStatusDetails(orderStatusDetails);
					orderDetails.get(0).setOrderItemStatus("Request for cancelation");
					orderSKUDetailsRepo.saveAll(orderDetails);
				} catch (Exception e) {
					orderStatusDetails.setCancelOrderDetails(jsonObject);
					orderDetails.get(0).setOrderStatusDetails(orderStatusDetails);
					orderDetails.get(0).setOrderItemStatus("Request for cancelation");
					orderSKUDetailsRepo.saveAll(orderDetails);
				}
			}
			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.CANCELATION_REQUEST.getMessage(), 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalResponse cancelApproval(String designerId, String orderId, String productId,
			CancelationRequestApproveAndRejectDTO cancelationRequestApproveAndRejectDTO) {
		try {
			List<OrderSKUDetailsEntity> orderDetails = orderSKUDetailsRepo
					.findByProductIdAndDesignerIdAndOrderIdAndOrderItemStatus(Integer.parseInt(productId),
							Integer.parseInt(designerId), orderId, "Request for cancelation");
			LOGGER.info(orderDetails.size() + "");
//					.stream()
//					.filter(e->e.getDesignerId() == Long.parseLong(designerId))
//					.filter(e->e.getOrderId().equals(orderId))
//					.filter(e->e.getProductId()==Integer.parseInt(productId))
//					.filter(e->e.getOrderItemStatus().equals("Request for cancelation"))
//					.collect(Collectors.toList());
			String username = userloginRepo.findById(orderDetails.get(0).getUserId()).get().getFirstName();
			String userEmail = userloginRepo.findById(orderDetails.get(0).getUserId()).get().getEmail();
			DesignerRequestDTO designerResponse = restTemplate
					.getForEntity(RestTemplateConstant.DESIGNER_BYID.getLink() + designerId, DesignerRequestDTO.class)
					.getBody();
			String designerName = designerResponse.getDesignerId().toString();
			String designerEmail = designerResponse.getDesignerProfile().get("email").toString();
			SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT_TYPE.getMessage());
			Date dates = new Date();
			String format = formatter.format(dates);
			if (cancelationRequestApproveAndRejectDTO.getOrderStatus().equals("cancelled")) {

				// org.json.simple.JSONObject data2= new org.json.simple.JSONObject();
				Map<String, Object> data = new HashMap<String, Object>();
				// Map<String, Object> data1= new HashMap<String, Object>();
				CancelEmailJSON cancelEmailJSON = new CancelEmailJSON();
				cancelEmailJSON.setOrderId(orderDetails.get(0).getOrderId());
				cancelEmailJSON.setProductImages(orderDetails.get(0).getImages());
				cancelEmailJSON.setProductName(orderDetails.get(0).getProductName());
				cancelEmailJSON.setProductSize(orderDetails.get(0).getSize());
				cancelEmailJSON.setSalePrice(orderDetails.get(0).getSalesPrice() + "");
				cancelEmailJSON.setUserName(username);
				cancelEmailJSON.setDesignerName(designerName);
				cancelEmailJSON.setComment(cancelationRequestApproveAndRejectDTO.getComment());
				orderDetails.get(0).setOrderItemStatus("cancelled");
				// OrderStatusDetails details= new OrderStatusDetails();
				OrderStatusDetails details = orderDetails.get(0).getOrderStatusDetails();
				org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
				jsonObject.put("cancellationTime", format);
				jsonObject.put("adminCancellationComment", cancelationRequestApproveAndRejectDTO.getComment());
				try {

					details.setCancelRequestDetails(jsonObject);
					orderDetails.get(0).setOrderStatusDetails(details);
					orderSKUDetailsRepo.save(orderDetails.get(0));
				} catch (Exception e) {
					details.setCancelRequestDetails(jsonObject);
					orderDetails.get(0).setOrderStatusDetails(details);
					orderSKUDetailsRepo.save(orderDetails.get(0));
				}
				LOGGER.info(cancelEmailJSON + "");
				data.put("data2", cancelEmailJSON);
				// data1.put("designerName", designerName);
				Context context = new Context();
				context.setVariables(data);
				// context.setVariables(data1);
				String htmlContent = templateEngine.process("ordercancel.html", context);
				EmailSenderThread emailSenderThread = new EmailSenderThread(userEmail,
						MessageConstant.ORDER_CANCEL_FROM_DESIGNER.getMessage(), htmlContent, true, null, restTemplate);
				emailSenderThread.start();
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_CANCEL.getMessage(), 200);
			} else {
				Map<String, Object> data = new HashMap<String, Object>();
//				List<OrderSKUDetailsEntity> sKUdetailsData = orderSKUDetailsRepo
//						.findByProductIdAndDesignerIdAndOrderId(Integer.parseInt(productId),
//								Integer.parseInt(designerId), orderId);
				String orderItemStatus = orderDetails.get(0).getOrderItemStatus();
				LOGGER.info("orderItemStatus" + orderItemStatus);
//				sKUdetailsData.get(0).setStatus(orderItemStatus);
				String status = orderDetails.get(0).getStatus();
				LOGGER.info("status" + status);
				CancelEmailJSON cancelEmailJSON = new CancelEmailJSON();
				cancelEmailJSON.setOrderId(orderDetails.get(0).getOrderId());
				cancelEmailJSON.setProductImages(orderDetails.get(0).getImages());
				cancelEmailJSON.setProductName(orderDetails.get(0).getProductName());
				cancelEmailJSON.setProductSize(orderDetails.get(0).getSize());
				cancelEmailJSON.setSalePrice(orderDetails.get(0).getSalesPrice() + "");
				cancelEmailJSON.setUserName(username);
				cancelEmailJSON.setDesignerName(designerName);
				cancelEmailJSON.setComment(cancelationRequestApproveAndRejectDTO.getComment());
				orderDetails.get(0).setOrderItemStatus(status);
				OrderStatusDetails details = orderDetails.get(0).getOrderStatusDetails();
				org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
				jsonObject.put("rejectionTime", format);
				jsonObject.put("adminRejectionComment", cancelationRequestApproveAndRejectDTO.getComment());

				try {

					details.setCancelRequestDetails(jsonObject);
					orderDetails.get(0).setOrderStatusDetails(details);
					orderSKUDetailsRepo.save(orderDetails.get(0));
				} catch (Exception e) {
					details.setCancelRequestDetails(jsonObject);
					orderDetails.get(0).setOrderStatusDetails(details);
					orderSKUDetailsRepo.save(orderDetails.get(0));
				}
				orderSKUDetailsRepo.save(orderDetails.get(0));
				// LOGGER.info(cancelEmailJSON+"");
				data.put("data2", cancelEmailJSON);
				// data1.put("designerName", designerName);
				Context context = new Context();
				context.setVariables(data);
				// context.setVariables(data1);
				LOGGER.info(designerEmail);
				String htmlContent = templateEngine.process("ordercancelRejected.html", context);
				EmailSenderThread emailSenderThread = new EmailSenderThread(designerEmail,
						MessageConstant.ORDER_CANCELATION_REJECTED.getMessage(), htmlContent, true, null, restTemplate);
				emailSenderThread.start();

				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_CANCEL_REQUEST_REJECTED.getMessage(), 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("all")
	@Override
	public GlobalResponse itemStatusChange(String token, String orderId, String productId,
			org.json.simple.JSONObject statusChange, String orderItemStatus) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - OrderAndPaymentServiceImpl.itemStatusChange()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - OrderAndPaymentServiceImpl.itemStatusChange()");
		}
		try {
			String designerEmail = jwtconfig.extractUsername(token.substring(7));
			try {
				DesignerProfileEntity entity = restTemplate
						.getForEntity(RestTemplateConstant.DESIGNER_DETAILS.getLink() + designerEmail,
								DesignerProfileEntity.class)
						.getBody();
				String designerId = entity.getDesignerId().toString();
				String displayName = entity.getDesignerProfile().getDisplayName();
				OrderSKUDetailsEntity item1 = orderSKUDetailsRepo
						.findByProductIdAndOrderId(Integer.parseInt(productId), orderId).get(0);
				String designerId2 = item1.getDesignerId() + "";
				if (designerId.equals(designerId2)) {
					try {
						OrderSKUDetailsEntity item = orderSKUDetailsRepo.findByProductIdAndDesignerIdAndOrderId(
								Integer.parseInt(productId), Integer.parseInt(designerId), orderId).get(0);
						OrderStatusDetails orderStatusDetails = item.getOrderStatusDetails();
						String itemStatus = item.getOrderItemStatus();
						if (LOGGER.isInfoEnabled()) {
							LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
									interfaceId, host + contextPath + "/userOrder/itemStatusChange", gson.toJson(item),
									HttpStatus.OK);
						}
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
									interfaceId, host + contextPath + "/userOrder/itemStatusChange", gson.toJson(item),
									HttpStatus.OK);
						}
						SimpleDateFormat formatter = new SimpleDateFormat(
								MessageConstant.DATE_FORMAT_TYPE.getMessage());
						Date dates = new Date();
						String format = formatter.format(dates);
						if (orderItemStatus.equals("Orders")) {
							if (!itemStatus.equals(orderItemStatus)) {
								if (itemStatus.equals("New")) {
									OrderStatusDetails orderStatusDetails1 = new OrderStatusDetails();
									org.json.simple.JSONObject jsonObject3 = new org.json.simple.JSONObject();
									String string = statusChange.get("OrdersDTO").toString();
									org.json.simple.JSONObject fromJson = gson.fromJson(string,
											org.json.simple.JSONObject.class);
									if (fromJson.containsKey("withCustomization")
											|| fromJson.containsKey("withDesignCustomization")) {
										jsonObject3.put("withCustomization", fromJson.get("withCustomization"));
										jsonObject3.put("withDesignCustomization",
												fromJson.get("withDesignCustomization"));
										jsonObject3.put("ordersTime", format);
										item.setOrderItemStatus(orderItemStatus);
										if (item.getOrderStatusDetails() != null) {
											orderStatusDetails.setOrdersDetails(jsonObject3);
											orderSKUDetailsRepo.save(item);
										} else {
											orderStatusDetails1.setOrdersDetails(jsonObject3);
											item.setOrderStatusDetails(orderStatusDetails1);
											orderSKUDetailsRepo.save(item);
										}
									} else {
										jsonObject3.put("withCustomization", false);
										jsonObject3.put("withDesignCustomization", false);
										jsonObject3.put("ordersTime", format);
										item.setOrderItemStatus(orderItemStatus);
										if (item.getOrderStatusDetails() != null) {
											orderStatusDetails.setOrdersDetails(jsonObject3);
											orderSKUDetailsRepo.save(item);
										} else {
											orderStatusDetails1.setOrdersDetails(jsonObject3);
											item.setOrderStatusDetails(orderStatusDetails1);
											orderSKUDetailsRepo.save(item);
										}
									}
									if (LOGGER.isInfoEnabled()) {
										LOGGER.info(
												"Application name: {},Request URL: {},Response message: {},Response code: {}",
												interfaceId, host + contextPath + "/userOrder/itemStatusChange",
												"Success", HttpStatus.OK);
									}
									if (LOGGER.isDebugEnabled()) {
										LOGGER.debug(
												"Application name: {},Request URL: {},Response message: {},Response code: {}",
												interfaceId, host + contextPath + "/userOrder/itemStatusChange",
												gson.toJson(item), HttpStatus.OK);
									}
								} else
									throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());
							} else
								throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);
						} else if (orderItemStatus.equals("Packed")) {
							if (!itemStatus.equals(orderItemStatus)) {
								if (itemStatus.equals("Orders")) {
									org.json.simple.JSONObject jsonObject2 = new org.json.simple.JSONObject();
									String string = statusChange.get("PackedDTO").toString();
									org.json.simple.JSONObject fromJson = gson.fromJson(string,
											org.json.simple.JSONObject.class);
									if (fromJson.containsKey("packedCovered") || fromJson.containsKey("packingVideo")) {
										jsonObject2.put("packedCovered", fromJson.get("packedCovered"));
										jsonObject2.put("packingVideo", fromJson.get("packingVideo"));
										jsonObject2.put("orderPackedTime", format);
										item.setOrderItemStatus(orderItemStatus);
										orderStatusDetails.setPackedDetails(jsonObject2);
										item.setOrderStatusDetails(orderStatusDetails);
										orderSKUDetailsRepo.save(item);
									} else {
										jsonObject2.put("packedCovered", false);
										jsonObject2.put("packingVideo", false);
										jsonObject2.put("orderPackedTime", format);
										item.setOrderItemStatus(orderItemStatus);
										orderStatusDetails.setPackedDetails(jsonObject2);
										orderSKUDetailsRepo.save(item);
									}
								} else
									throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());
							} else
								throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);

						} else if (orderItemStatus.equals("Shipped")) {
							if (!itemStatus.equals(orderItemStatus)) {
								if (itemStatus.equals("Packed")) {
									org.json.simple.JSONObject jsonObject1 = new org.json.simple.JSONObject();
									String string = statusChange.get("ShippedDTO").toString();
									Object string1 = statusChange.get("ShippedDTO");
									String writeValueAsString = null;
									ObjectMapper objectMapper = new ObjectMapper();
									try {
										writeValueAsString = objectMapper.writeValueAsString(string1);
									} catch (Exception e) {
										e.printStackTrace();
									}
									JsonNode fromJson1 = new JsonNode(writeValueAsString);
									try {
										String courierName = fromJson1.getObject().get("courierName").toString();
										String awbNumber = fromJson1.getObject().get("awbNumber").toString();
										orderStatusDetails.setShippedDetails(jsonObject1);
										jsonObject1.put("courierName", courierName);
										jsonObject1.put("awbNumber", awbNumber);
										jsonObject1.put("orderShippedTime", format);
										orderStatusDetails.setShippedDetails(jsonObject1);
										item.setOrderItemStatus(orderItemStatus);
										orderSKUDetailsRepo.save(item);
									} catch (Exception e) {
										throw new CustomException("Please fill up the Required Fields");
									}
								} else
									throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());
							} else
								throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);

						} else if (orderItemStatus.equals("Delivered")) {
							if (!itemStatus.equals(orderItemStatus)) {
								if (itemStatus.equals("Shipped")) {
									org.json.simple.JSONObject jsonObject4 = new org.json.simple.JSONObject();
									String string = statusChange.get("DeliveryDTO").toString();
									org.json.simple.JSONObject fromJson = gson.fromJson(string,
											org.json.simple.JSONObject.class);
									try {
										String deliveredDate = (String) fromJson.get("deliveredDate");
										SimpleDateFormat dateFormat = new SimpleDateFormat(
												MessageConstant.DATA_TYPE_FORMAT.getMessage());
										DateFormat inputText = new SimpleDateFormat("yyyy-MM-dd");
										Date date = inputText.parse(deliveredDate);
										String format1 = dateFormat.format(date);

										jsonObject4.put("deliveredDate", format1);
										orderStatusDetails.setDeliveryDetails(jsonObject4);
										item.setOrderItemStatus(orderItemStatus);
										orderSKUDetailsRepo.save(item);
									} catch (Exception e) {
										throw new CustomException("Please fill up fields");
									}
								} else
									throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());

							} else
								throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);
						}

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
							System.out.println("orderDetailsEntity " + orderDetailsEntity.toString());
							Object shippingAddress = orderDetailsEntity.getShippingAddress();
							String substring2 = shippingAddress.toString()
									.substring(1, shippingAddress.toString().length() - 1).replaceAll("=", " : ");
							String replace = substring2.replace("address1 : ", "").replace("address2 : ", "")
									.replace("country : ", "").replace("state : ", "").replace("city : ", "")
									.replace("postalCode : ", "").replace("landmark : ", "").replace("fullName : ", "")
									.replace("email : ", "").replace("mobile : ", "");
							String orderDate = item.getCreatedOn();
							Date parse = formatter.parse(orderDate);
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
							context.setVariable("mrp", mrp);
							double format3 = (Double.parseDouble(format2) + mrp) - Double.parseDouble(discount);
							context.setVariable("format3", format3);
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
										"Your Order Has been " + "Verified", htmlContent, true, null, restTemplate);
								String htmlContentDesigner = templateEngine.process("statusChangeDesigner.html",
										context);
								EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(email2,
										"Your Product Has been " + "Verified", htmlContentDesigner, true, null,
										restTemplate);
								emailSenderThreadDesigner.start();
								emailSenderThread.start();
							} else {
								String htmlContent = templateEngine.process("statusChange.html", context);
								EmailSenderThread emailSenderThread = new EmailSenderThread(email,
										"Your Order Has been " + orderItemStatus, htmlContent, true, null,
										restTemplate);
								String htmlContentDesigner = templateEngine.process("statusChangeDesigner.html",
										context);
								EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(email2,
										"Your Product Has been " + orderItemStatus, htmlContentDesigner, true, null,
										restTemplate);
								emailSenderThreadDesigner.start();
								emailSenderThread.start();
							}
						}

						return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
								MessageConstant.ITEM_STATUS_CHANGE.getMessage() + itemStatus
										+ MessageConstant.TO.getMessage() + orderItemStatus
										+ MessageConstant.SUCCESSFULLY.getMessage(),
								HttpStatus.OK.value());

					} catch (Exception e) {
						throw new CustomException(e.getMessage());
					}
				} else {
					throw new CustomException("Invalid Token");
				}
			} catch (Exception e) {
				throw new CustomException(e.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalResponse itemStatusChangefromAdmin(String token, String orderId, String productId,
			org.json.simple.JSONObject statusChange, String orderItemStatus) {

		try {
			String extractUsername = jwtconfig.extractUsername(token.substring(7));
			try {
				String adminEmail = restTemplate
						.getForEntity(RestTemplateConstant.ADMIN_ROLE_NAME.getLink()
								+ MessageConstant.ADMIN_ROLES.getMessage(), org.json.simple.JSONObject.class)
						.getBody().get("email").toString();
				if (!extractUsername.equals(adminEmail)) {
					throw new CustomException(MessageConstant.UNAUTHORIZED.getMessage());
				}
			} catch (Exception e) {
				throw new CustomException(e.getLocalizedMessage());
			}
			OrderSKUDetailsEntity item = orderSKUDetailsRepo
					.findByProductIdAndOrderId(Integer.parseInt(productId), orderId).get(0);
			OrderStatusDetails orderStatusDetails = item.getOrderStatusDetails();
			String itemStatus = item.getOrderItemStatus();
			int designerId = item.getDesignerId();
			DesignerProfileEntity forEntity = restTemplate.getForEntity(
					RestTemplateConstant.DESIGNER_BYID.getLink() + designerId, DesignerProfileEntity.class).getBody();
			SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT_TYPE.getMessage());
			Date dates = new Date();
			String format = formatter.format(dates);
			if (!itemStatus.equals("New")) {
				if (itemStatus.equals(orderItemStatus)) {
					throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);
				} else if (orderItemStatus.equals("Packed")) {
					if (!itemStatus.equals(orderItemStatus)) {
						if (itemStatus.equals("Orders")) {
							org.json.simple.JSONObject jsonObject2 = new org.json.simple.JSONObject();
							String string = statusChange.get("PackedDTO").toString();
							org.json.simple.JSONObject fromJson = gson.fromJson(string,
									org.json.simple.JSONObject.class);
							if (fromJson.containsKey("packedCovered") || fromJson.containsKey("packingVideo")) {
								jsonObject2.put("packedCovered", fromJson.get("packedCovered"));
								jsonObject2.put("packingVideo", fromJson.get("packingVideo"));
								jsonObject2.put("orderPackedTime", format);
								item.setOrderItemStatus(orderItemStatus);
								orderStatusDetails.setPackedDetails(jsonObject2);
								item.setOrderStatusDetails(orderStatusDetails);
								orderSKUDetailsRepo.save(item);
							} else {
								jsonObject2.put("packedCovered", false);
								jsonObject2.put("packingVideo", false);
								jsonObject2.put("orderPackedTime", format);
								item.setOrderItemStatus(orderItemStatus);
								orderStatusDetails.setPackedDetails(jsonObject2);
								orderSKUDetailsRepo.save(item);
							}
						} else
							throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());
					} else
						throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);

				} else if (orderItemStatus.equals("Shipped")) {
					if (!itemStatus.equals(orderItemStatus)) {
						if (itemStatus.equals("Packed")) {
							org.json.simple.JSONObject jsonObject1 = new org.json.simple.JSONObject();
							statusChange.get("ShippedDTO").toString();
							Object string1 = statusChange.get("ShippedDTO");
							String writeValueAsString = null;
							ObjectMapper objectMapper = new ObjectMapper();
							try {
								writeValueAsString = objectMapper.writeValueAsString(string1);
							} catch (Exception e) {
								e.printStackTrace();
							}
							JsonNode fromJson1 = new JsonNode(writeValueAsString);
							try {
								String courierName = fromJson1.getObject().get("courierName").toString();
								String awbNumber = fromJson1.getObject().get("awbNumber").toString();
								orderStatusDetails.setShippedDetails(jsonObject1);
								jsonObject1.put("courierName", courierName);
								jsonObject1.put("awbNumber", awbNumber);
								jsonObject1.put("orderShippedTime", format);
								orderStatusDetails.setShippedDetails(jsonObject1);
								item.setOrderItemStatus(orderItemStatus);
								orderSKUDetailsRepo.save(item);
							} catch (Exception e) {
								throw new CustomException("Please fill up the Required Fields");
							}
						} else
							throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());
					} else
						throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);

				} else if (orderItemStatus.equals("Delivered")) {
					if (!itemStatus.equals(orderItemStatus)) {
						if (itemStatus.equals("Shipped")) {
							org.json.simple.JSONObject jsonObject4 = new org.json.simple.JSONObject();
							String string = statusChange.get("DeliveryDTO").toString();
							org.json.simple.JSONObject fromJson = gson.fromJson(string,
									org.json.simple.JSONObject.class);
							try {
								String deliveredDate = (String) fromJson.get("deliveredDate");
								SimpleDateFormat dateFormat = new SimpleDateFormat(
										MessageConstant.DATA_TYPE_FORMAT.getMessage());
								DateFormat inputText = new SimpleDateFormat("yyyy-MM-dd");
								Date date = inputText.parse(deliveredDate);
								String format1 = dateFormat.format(date);
								jsonObject4.put("deliveredDate", format1);
								orderStatusDetails.setDeliveryDetails(jsonObject4);
								item.setOrderItemStatus(orderItemStatus);
								orderSKUDetailsRepo.save(item);
							} catch (Exception e) {
								throw new CustomException("Please fill up fields");
							}
						} else
							throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());

					} else
						throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);
				}
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
				String colour = item.getColour();
				String email2 = forEntity.getDesignerProfile().getEmail();
				String displayName = forEntity.getDesignerProfile().getDisplayName();
				String city = forEntity.getDesignerProfile().getCity();
				String country = forEntity.getDesignerProfile().getCountry();
				String state = forEntity.getDesignerProfile().getState();
				List<OrderPaymentEntity> findByOrderIdList = userOrderPaymentRepo.findByOrderIdList(orderId);

				if (findByOrderIdList.size() > 0) {

					String paymentMode = findByOrderIdList.get(0).getPaymentMode();
					OrderDetailsEntity orderDetailsEntity = orderDetailsRepo.findByOrderId(orderId).get(0);
					Object shippingAddress = orderDetailsEntity.getShippingAddress();
					String substring2 = shippingAddress.toString().substring(1, shippingAddress.toString().length() - 1)
							.replaceAll("=", " : ");
					String replace = substring2.replace("address1 : ", "").replace("address2 : ", "")
							.replace("country : ", "").replace("state : ", "").replace("city : ", "")
							.replace("postalCode : ", "").replace("landmark : ", "").replace("fullName : ", "")
							.replace("email : ", "").replace("mobile : ", "");
					String orderDate = item.getCreatedOn();
					Date parse = formatter.parse(orderDate);
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
					context.setVariable("mrp", mrp);
					double format3 = (Double.parseDouble(format2) + mrp) - Double.parseDouble(discount);
					context.setVariable("format3", format3);
					context.setVariable("discount", discount);
					context.setVariable("taxAmount", format2);
					context.setVariable("mrp", mrp);
					context.setVariable("size", size);
					context.setVariable("displayName", displayName);
					context.setVariable("city", city);
					context.setVariable("country", country);
					context.setVariable("state", state);
					context.setVariable("paymentMode", paymentMode);
					context.setVariable("shippingAddress", replace);
					context.setVariable("orderDate", format1);
					context.setVariable("orderId", orderId);
					context.setVariable("quantity", units);
					context.setVariable("colour", colour);
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
								"Your Order Has been " + "Verified", htmlContent, true, null, restTemplate);
						String htmlContentDesigner = templateEngine.process("statusChangeDesigner.html", context);
						EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(email2,
								"Your Product Has been " + "Verified", htmlContentDesigner, true, null, restTemplate);
						emailSenderThreadDesigner.start();
						emailSenderThread.start();
					} else {
						String htmlContent = templateEngine.process("statusChange.html", context);
						EmailSenderThread emailSenderThread = new EmailSenderThread(email,
								"Your Order Has been " + orderItemStatus, htmlContent, true, null, restTemplate);
						String htmlContentDesigner = templateEngine.process("statusChangeDesigner.html", context);
						EmailSenderThread emailSenderThreadDesigner = new EmailSenderThread(email2,
								"Your Product Has been " + orderItemStatus, htmlContentDesigner, true, null,
								restTemplate);
						emailSenderThreadDesigner.start();
						emailSenderThread.start();
					}
				}
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ITEM_STATUS_CHANGE.getMessage() + itemStatus + MessageConstant.TO.getMessage()
								+ orderItemStatus + MessageConstant.SUCCESSFULLY.getMessage(),
						200);
			} else
				throw new CustomException(MessageConstant.ADMIN_CANNOT_CHANGE_STATUS_NOW.getMessage());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalResponse adminCancelation(String orderId, String productId, String token,
			CancelationRequestDTO cancelationRequestDTO) {
		try {
//			String extractUsername = jwtconfig.extractUsername(token.substring(7));
			OrderSKUDetailsEntity findByProductIdAndOrderId = this.orderSKUDetailsRepo
					.findByProductIdAndOrderId(Integer.parseInt(productId), orderId).get(0);
			String orderItemStatus = findByProductIdAndOrderId.getOrderItemStatus();
			if (!orderItemStatus.equals("Delivered")) {
				if (!orderItemStatus.equals("New")) {
					org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
					jsonObject.put("cancelComment", cancelationRequestDTO.getComment());
					jsonObject.put("cancelationTime", new Date());
					OrderStatusDetails orderStatusDetails = findByProductIdAndOrderId.getOrderStatusDetails();
					try {
						orderStatusDetails.setCancelOrderDetails(jsonObject);
						findByProductIdAndOrderId.setOrderStatusDetails(orderStatusDetails);
						findByProductIdAndOrderId.setOrderItemStatus(cancelationRequestDTO.getOrderStatus());
						this.orderSKUDetailsRepo.save(findByProductIdAndOrderId);
					} catch (Exception e) {
						orderStatusDetails.setCancelOrderDetails(jsonObject);
						findByProductIdAndOrderId.setOrderStatusDetails(orderStatusDetails);
						findByProductIdAndOrderId.setOrderItemStatus(cancelationRequestDTO.getOrderStatus());
						this.orderSKUDetailsRepo.save(findByProductIdAndOrderId);
					}
				} else {
					org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
					jsonObject.put("cancelComment", cancelationRequestDTO.getComment());
					jsonObject.put("cancelationTime", new Date());
					OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
					try {
						orderStatusDetails.setCancelOrderDetails(jsonObject);
						findByProductIdAndOrderId.setOrderStatusDetails(orderStatusDetails);
						findByProductIdAndOrderId.setOrderItemStatus(cancelationRequestDTO.getOrderStatus());
						this.orderSKUDetailsRepo.save(findByProductIdAndOrderId);
					} catch (Exception e) {
						orderStatusDetails.setCancelOrderDetails(jsonObject);
						findByProductIdAndOrderId.setOrderStatusDetails(orderStatusDetails);
						findByProductIdAndOrderId.setOrderItemStatus(cancelationRequestDTO.getOrderStatus());
						this.orderSKUDetailsRepo.save(findByProductIdAndOrderId);
					}
				}
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_CANCEL.getMessage(), 200);
			} else {
				throw new CustomException(MessageConstant.ORDER_DELIVERED.getMessage());
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getOrdersItemstatus(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy, String token, String orderItemStatus) {
		LOGGER.info("Inside - OrderAndPaymentService.getOrders()");
		try {
			int CountData = (int) orderSKUDetailsRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<OrderSKUDetailsEntity> findAll = null;

			if (!orderItemStatus.equals("All") && keyword.isEmpty()) {
				findAll = orderSKUDetailsRepo.findOrderStatus(orderItemStatus, pagingSort);
			} else if (keyword.isEmpty()) {
				findAll = orderSKUDetailsRepo.findAll(pagingSort);
			} else {
				findAll = orderSKUDetailsRepo.Searching(keyword, pagingSort);
			}

			LOGGER.info(findAll.getContent() + "Inside Findall");
			List<OrderSKUDetailsEntity> orderSKUDetails = new ArrayList<>();
			orderSKUDetails = this.orderSKUDetailsRepo.findAll();
			LOGGER.info("inside orderSKUDetails" + orderSKUDetails.size());

			List<Object> productId = new ArrayList<>();

			findAll.getContent().forEach(e -> {
				ObjectMapper obj = new ObjectMapper();
				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
				List<OrderInvoiceEntity> invoiceData = this.orderInvoiceRepo.findByOrder(e.getOrderId());
				ResponseEntity<DesignerProfileEntity> forEntity = restTemplate.getForEntity(
						RestTemplateConstant.DESIGNER_BYID.getLink() + e.getDesignerId(), DesignerProfileEntity.class);
				String designerName = forEntity.getBody().getDesignerName();
				Optional<OrderPaymentEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByOrderId(e.getOrderId());

				String writeValueAsString = null;
				JSONObject payRow = null;
				if (!OrderPaymentRow.isEmpty()) {
					try {
						writeValueAsString = obj.writeValueAsString(OrderPaymentRow.get());
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					JsonNode paymentJson = new JsonNode(writeValueAsString);
					payRow = paymentJson.getObject();
				}

				JsonNode cartJN = new JsonNode(productIdFilter);
				JSONObject objects = cartJN.getObject();
				if (invoiceData.size() > 0) {
					invoiceData.forEach(invoice -> {
						objects.put("invoiceId", invoice.getInvoiceId());
					});
				} else {
					objects.put("invoiceId", JSONObject.NULL);
				}
				objects.put("designerName", designerName);
				objects.put("paymentData", payRow);
				productId.add(objects);
			});
			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}
			Map<String, Object> response = new HashMap<>();
			response.put("data", new Json(productId.toString()));
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());
			response.put("requestForCancelation",
					orderSKUDetailsRepo.findByOrderItemStatus("Request for cancelation").size());
			response.put("New", orderSKUDetailsRepo.findByOrderItemStatus("New").size());
			response.put("Packed", orderSKUDetailsRepo.findByOrderItemStatus("Packed").size());
			response.put("Shipped", orderSKUDetailsRepo.findByOrderItemStatus("Shipped").size());
			response.put("Delivered", orderSKUDetailsRepo.findByOrderItemStatus("Delivered").size());
			response.put("Return", orderSKUDetailsRepo.findByOrderItemStatus("Return").size());
			response.put("Active", orderSKUDetailsRepo.findByOrderItemStatus("Active").size());
			response.put("Cancelled", orderSKUDetailsRepo.findByOrderItemStatus("cancelled").size());
			response.put("Orders", orderSKUDetailsRepo.findByOrderItemStatus("Orders").size());
			response.put("totalIteamStatus", orderSKUDetailsRepo.findByOrder(orderItemStatus).size());
			response.put("returnRequest", orderSKUDetailsRepo.findByOrderItemStatus("returnRequest").size());
			response.put("returnRefund", orderSKUDetailsRepo.findByOrderItemStatus("returnRefund").size());
			return response;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public Optional<OrderInvoiceEntity> getInvoiceByOrderId(String orderId) {
		try {
			return this.orderInvoiceRepo.findByOrderId(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public List<OrderInvoiceEntity> getInvoiceByOrder(String orderId) {
		try {
			return this.orderInvoiceRepo.findByOrder(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<byte[]> getOrderSummary(String orderId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("orderId").is(orderId));
			List<OrderInvoiceEntity> invoiceDataList = mongoOperations.find(query, OrderInvoiceEntity.class);
			List<String> keyList = new ArrayList<>();
			// LOGGER.info(invoiceDataList+"");
			LOGGER.info("Invoice Details data <><><><><> = {}", invoiceDataList);
			Map<String, List<InvoiceUpdatedModel>> responceData = new HashMap<>();
			List<String> invoiceIdList = new ArrayList<>();
			for (OrderInvoiceEntity invoiceEntity : invoiceDataList) {
				String gstNo = invoiceEntity.getDesignerDetails().getGSTIN();
				try {
					List<InvoiceUpdatedModel> productDetailsList = responceData.get(gstNo);
					Query query1 = new Query();
					query1.addCriteria(
							Criteria.where("designerId").is(invoiceEntity.getProductDetails().getDesignerId())
									.and("orderId").is(invoiceEntity.getOrderId()).and("productId")
									.is(invoiceEntity.getProductDetails().getProductId()));

					OrderSKUDetailsEntity detailsEntity = mongoOperations.findOne(query, OrderSKUDetailsEntity.class);
					invoiceIdList.add(invoiceEntity.getInvoiceId());
					productDetailsList.add(UtillUserService.invoiceMapperRestMap(invoiceEntity, detailsEntity));
//			List<OrderInvoiceEntity> invoiceDataList = mongoOperations.find(query, OrderInvoiceEntity.class);
//			List<String> keyList = new ArrayList<>();
//			 LOGGER.info(invoiceDataList+"");
//			Map<String, List<InvoiceUpdatedModel>> responceData = new HashMap<>();
//			for (OrderInvoiceEntity invoiceEntity : invoiceDataList) {
//				String gstNo = invoiceEntity.getDesignerDetails().getGSTIN();
//				try {
//					List<InvoiceUpdatedModel> productDetailsList = responceData.get(gstNo);
//					productDetailsList.add(UtillUserService.invoiceMapperRestMap(invoiceEntity,detailsEntity));
//					responceData.put(gstNo, productDetailsList);
				} catch (Exception e) {
					List<InvoiceUpdatedModel> productList = new ArrayList<>();
					keyList.add(gstNo);
					Query query2 = new Query();
					query2.addCriteria(
							Criteria.where("designerId").is(invoiceEntity.getProductDetails().getDesignerId())
									.and("orderId").is(invoiceEntity.getOrderId()).and("productId")
									.is(invoiceEntity.getProductDetails().getProductId()));
					OrderSKUDetailsEntity detailsEntity = mongoOperations.findOne(query, OrderSKUDetailsEntity.class);
					productList.add(UtillUserService.invoiceUpdatedModelMapper(invoiceEntity, detailsEntity));
					responceData.put(gstNo, productList);
				}
			}

			// mrpList.stream().collect(Collectors.summingInt(Integer::intValue));
			StringBuilder invoiceData = new StringBuilder();
			for (String key : keyList) {

				List<InvoiceUpdatedModel> invoiceUpdatedModels = responceData.get(key);
				LOGGER.info("invoiceUpdatedModels"+invoiceUpdatedModels);
				Double tCgst = 0.0;
				Double tSgst = 0.0;
				Double tDis = 0.0;
				Double tIgst = 0.0;
				Double tGross = 0.0;
				Double tTotal = 0.0;
				Double tMrp = 0.0;
				Double tTaxableValue = 0.0;
				int tQty = 0;

				String totalCgst = null;
				String totalSgst = null;
				String totalDiscount = null;
				String totalQunatuty = null;
				String totalIgst = null;
				String totalGross = null;
				String total = null;
				String displayName = null;
				String designerName = null;
				String taxableValue = null;

				invoiceUpdatedModels.stream().forEach(entity->{
					
					//LOGGER.info("taxableValue"+taxAmount);
					if(entity.getTotal().equals("0")) {
						if(entity.getDiscount().equals("0")) {
						entity.setTotal(entity.getMrp());
					}
					}
					
				});
				for (InvoiceUpdatedModel element : invoiceUpdatedModels) {
					String taxAmount = element.getTaxAmount();
					String total2 = element.getTotal();

					String taxValue=(Integer.parseInt(total2)-Integer.parseInt(taxAmount))+"";
					element.setTaxableValue(taxValue);
					element.setIgst(element.getIgst() == null ? "0" : element.getIgst());
					tCgst = tCgst + Double.parseDouble(element.getCgst() == null ? "0" : element.getCgst());
					tSgst = tSgst + Double.parseDouble(element.getSgst() == null ? "0" : element.getSgst());
					tDis = tDis + Double.parseDouble(
							Optional.ofNullable(element.getDiscount()).isEmpty() ? "0" : element.getDiscount());
					tQty = tQty + Integer.parseInt(element.getQty() == null ? "0" : element.getQty());
					tIgst = tIgst + Double.parseDouble(element.getIgst() == null ? "0" : element.getIgst());
					tGross = tGross
							+ Double.parseDouble(element.getGrossAmount() == null ? "0" : element.getGrossAmount());
					tTotal = tTotal + Double.parseDouble(element.getTotal() == null ? "0" : element.getTotal());
					tMrp= tMrp + Double.parseDouble(element.getMrp() == null ? "0" : element.getMrp());
					tTaxableValue = tTaxableValue + Double.parseDouble(element.getTaxableValue() == null ? "0" : element.getTaxableValue());
					totalCgst = String.valueOf(tCgst);
					totalSgst = String.valueOf(tSgst);
					totalDiscount = String.valueOf(tDis);
					totalQunatuty = String.valueOf(tQty);
					totalIgst = String.valueOf(tIgst);
					totalGross = String.valueOf(tGross);
					total = String.valueOf(tTotal);	
					taxableValue = String.valueOf(tTaxableValue);
//					orderInvoiceRepo.findByInvoiceId(element.getInvoiceId()).forEach(e -> {
//						DesignerProfileEntity forEntity = restTemplate.getForEntity(RestTemplateConstant.DESIGNER_BYID.getLink()+e.getProductDetails().getDesignerId(), DesignerProfileEntity.class).getBody();
//						displayName = forEntity.getDesignerProfile().getDisplayName();
//					});
					String invoiceId = element.getInvoiceId();
			    	String modifiedInvoiceId=invoiceId.substring(10,invoiceId.length());
					List<OrderInvoiceEntity> findByInvoiceId = this.orderInvoiceRepo.findByInvoiceId(modifiedInvoiceId);
					for (OrderInvoiceEntity e : findByInvoiceId) {
						DesignerProfileEntity forEntity = restTemplate.getForEntity(
								RestTemplateConstant.DESIGNER_BYID.getLink() + e.getProductDetails().getDesignerId(),
								DesignerProfileEntity.class).getBody();
						displayName = forEntity.getDesignerProfile().getDisplayName();
						designerName = forEntity.getDesignerName();
					}
				}
				LOGGER.info("invoiceUpdatedModels"+invoiceUpdatedModels);
				Map<String, Object> data = new HashMap<>();
				data.put("data", invoiceUpdatedModels);
				data.put("totalCgst", totalCgst);
				data.put("totalSgst", totalSgst);
				data.put("totalDiscount", totalDiscount);
				data.put("totalQunatuty", totalQunatuty);
				data.put("totalIgst", totalIgst);
				data.put("totalGross", totalGross);
				data.put("total", total);
				data.put("displayName", displayName);
				data.put("designerName", designerName);
				data.put("totalTaxableValue", taxableValue);
				Context context = new Context();
				context.setVariables(data);
				LOGGER.info("!!!@@@@ = {}", data);
				String htmlContent = templateEngine.process("new_invoice_User_test1.html", context);
				invoiceData.append(htmlContent);
			}

			// return invoiceData.toString();
			// return responceData;
			// Context context = new Context();
			// String htmlContent = templateEngine.process("new_invoice_User.html",
			// context);
			// String htmlContent = templateEngine.process("new_invoice_User_test.html",
			// context);
			ByteArrayOutputStream target = new ByteArrayOutputStream();
			ConverterProperties converterProperties = new ConverterProperties();
			// converterProperties.setBaseUri("https://localhost:8082");
			HtmlConverter.convertToPdf(invoiceData.toString(), target, converterProperties);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=" + "orderInvoiceUpdated.pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.body(target.toByteArray());

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public static ByteArrayOutputStream generatePdf(String html) {

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

	public ResponseEntity<?> getTransactionsService(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - OrderAndPaymentContoller.getOrderPaymentService()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - OrderAndPaymentContoller.getOrderPaymentService()");
		}

		try {
			int CountData = (int) userOrderPaymentRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<OrderPaymentEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = userOrderPaymentRepo.findAll(pagingSort);
			} else {
				findAll = userOrderPaymentRepo.Search(keyword, pagingSort);
			}

			if (findAll.getSize() <= 0) {
				Map<String, Object> mapObj = new HashMap<>();
				mapObj.put("status", 404);
				mapObj.put("reason", "Error");
				mapObj.put("message", MessageConstant.PAYMENT_NOT_FOUND.getMessage());
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Error: {}", MessageConstant.PAYMENT_NOT_FOUND.getMessage());
				}
				return new ResponseEntity<>(mapObj, HttpStatus.NOT_FOUND);
			}

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/userOrder/transactions", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/userOrder/transactions", "Success", HttpStatus.OK);
			}
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/userOrder/transactions", e.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}