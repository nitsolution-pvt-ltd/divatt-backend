package com.divatt.user.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.divatt.user.config.JWTConfig;
import com.divatt.user.entity.BillingAddressEntity;
import com.divatt.user.entity.InvoiceEntity;
import com.divatt.user.entity.OrderInvoiceEntity;
import com.divatt.user.entity.OrderTrackingEntity;
import com.divatt.user.entity.ProductInvoice;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.entity.order.OrderStatusDetails;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.helper.ListResponseDTO;
import com.divatt.user.helper.PDFRunner;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.OrderInvoiceRepo;
import com.divatt.user.repo.OrderSKUDetailsRepo;
import com.divatt.user.repo.OrderTrackingRepo;
import com.divatt.user.repo.UserDesignerRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.repo.orderPaymenRepo.UserOrderPaymentRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.serviceDTO.CancelEmailJSON;
import com.divatt.user.serviceDTO.CancelationRequestDTO;
import com.divatt.user.serviceDTO.DeliveryDTO;
import com.divatt.user.serviceDTO.DesignerRequestDTO;
import com.divatt.user.serviceDTO.OrderItemStatusChange;
import com.divatt.user.serviceDTO.PackedDTO;
import com.divatt.user.serviceDTO.ShippedDTO;
import com.divatt.user.services.OrderAndPaymentService;
import com.divatt.user.services.SequenceGenerator;
import com.divatt.user.utill.EmailSenderThread;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import net.bytebuddy.asm.Advice.Return;
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

	@Value("${pdf.directory}")
	private String pdfDirectory;

	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;

	@Autowired
	private UserLoginRepo userloginRepo;

	protected String getRandomString() {
//		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
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
		String SALTCHARS = "1234567890";
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

		} catch (RazorpayException e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> postOrderPaymentService(OrderPaymentEntity orderPaymentEntity) {
		LOGGER.info("Inside - OrderAndPaymentService.postOrderPaymentService()");

		try {

			final RazorpayClient razorpayClient = new RazorpayClient(env.getProperty("key"),
					env.getProperty("secretKey"));
			LOGGER.info("Inside - OrderAndPaymentContoller.postOrderPaymentService() get data");

			String paymentIdFilter = null;
			ObjectMapper obj = new ObjectMapper();
			try {
				paymentIdFilter = obj.writeValueAsString(orderPaymentEntity.getPaymentDetails());
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			JsonNode OrderPayJson = new JsonNode(paymentIdFilter);

			Payment payment = razorpayClient.Payments
					.fetch(OrderPayJson.getObject().get("razorpay_payment_id").toString());

			String payStatus = "FAILED";
			if (payment.get("error_code").equals(null) && payment.get("error_reason").equals(null)
					&& payment.get("error_step").equals(null) && payment.get("status").equals("captured")) {
				payStatus = "COMPLETED";

			}
			List<OrderDetailsEntity> findOrderRow = orderDetailsRepo.findByOrderId(orderPaymentEntity.getOrderId());
			if (findOrderRow.size() <= 0) {
				throw new CustomException("Order not found");
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

			OrderPaymentEntity filterCatDetails = new OrderPaymentEntity();

			filterCatDetails.setId(sequenceGenerator.getNextSequence(OrderPaymentEntity.SEQUENCE_NAME));
			filterCatDetails.setOrderId(orderPaymentEntity.getOrderId());
			filterCatDetails.setPaymentMode(orderPaymentEntity.getPaymentMode());
			filterCatDetails.setPaymentDetails(orderPaymentEntity.getPaymentDetails());
			filterCatDetails.setPaymentResponse(map);
			filterCatDetails.setPaymentStatus(payStatus);
			filterCatDetails.setUserId(orderPaymentEntity.getUserId());
			filterCatDetails.setCreatedOn(new Date());

			userOrderPaymentRepo.save(filterCatDetails);
			return ResponseEntity.ok(mapPayId);
		} catch (RazorpayException e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> postOrderSKUService(OrderSKUDetailsEntity orderSKUDetailsEntityRow) {
		LOGGER.info("Inside - OrderAndPaymentService.postOrderSKUService()");

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
			orderSKUDetailsRepo.save(orderSKUDetailsEntityRow);

			return ResponseEntity.ok(null);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
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

			if (findAll.getSize() <= 1) {
				throw new CustomException("Payment not found!");
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
			LOGGER.info("inside orderSKUDetails" + orderSKUDetails.size());

			if (!orderStatus.isBlank() && !orderStatus.equals("All")) {
				List<String> collect = orderSKUDetails.stream().filter(e -> e.getOrderItemStatus().equals(orderStatus))
						.map(e -> e.getOrderId()).collect(Collectors.toList());
				findAll = orderDetailsRepo.findByOrderIdIn(collect, pagingSort);
				LOGGER.info("collect <><><><>" + collect);
			} else {
				findAll = orderDetailsRepo.findAll(pagingSort);
				LOGGER.info("hiiii");
//				LOGGER.info("inside no status" + findAll);
			}

//			LOGGER.info("Data for find ALL <><><><><><><><><> !!!!!! = {}",findAll.getContent());

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
			response.put("Cancelation", orderSKUDetailsRepo.findByOrderItemStatus("Request for cancelation").size());
			response.put("New", orderSKUDetailsRepo.findByOrderItemStatus("New").size());
			response.put("Packed", orderSKUDetailsRepo.findByOrderItemStatus("Packed").size());
			response.put("Shipped", orderSKUDetailsRepo.findByOrderItemStatus("Shipped").size());
			response.put("Delivered", orderSKUDetailsRepo.findByOrderItemStatus("Delivered").size());
			response.put("Return", orderSKUDetailsRepo.findByOrderItemStatus("Return").size());
			response.put("Active", orderSKUDetailsRepo.findByOrderItemStatus("Active").size());
			response.put("totalIteamStatus", orderSKUDetailsRepo.findByOrder(orderStatus).size());

			if (productId.size() <= 0) {
				Map<String, Integer> orderCount = getOrderCount(0, true);
				response.put("orderCount", orderCount);
				response.put("Error", "Order not found");
				return response;
			} else {
				LOGGER.info("USERNAME IN ELSE <><><><><><><><><> !!!! = {}",
						jwtconfig.extractUsername(token.substring(7)));
				if (!restTemplate.getForEntity(
						"https://localhost:8080/dev/auth/info/ADMIN/" + jwtconfig.extractUsername(token.substring(7)),
						Object.class).toString().isBlank()) {
					Map<String, Integer> orderCount = getOrderCount(0, true);
					response.put("orderCount", orderCount);
					return response;
				}
				return response;

			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getOrderDetailsService(String orderId) {
		try {

			List<OrderDetailsEntity> findById = this.orderDetailsRepo.findByOrderId(orderId);
			LOGGER.info(findById + " Inside FindBYid");
			if (findById.size() <= 0) {
				throw new CustomException("Order not found");
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
				LOGGER.info(OrderSKUDetailsRow + " Inside OrderSku");
				OrderSKUDetailsRow.forEach(D -> {
					LOGGER.info("Data in for each method" + D.getProductId());
					ObjectMapper objs = new ObjectMapper();
					String productIdFilters = null;
					LOGGER.info("Top of try catch");

					try {
						LOGGER.info(D.getProductId() + " inside productid");
						ResponseEntity<org.json.simple.JSONObject> productById = restTemplate.getForEntity(
								"https://localhost:8083/dev/designerProducts/productList/" + D.getProductId(),
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
						LOGGER.info(objectss + "Inside objectss");

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
			return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getUserOrderDetailsService(Integer userId) {

		try {
			List<OrderDetailsEntity> findById = this.orderDetailsRepo.findByUserIdOrderByIdDesc(userId);
			if (findById.size() <= 0) {
				throw new CustomException("Order not found");
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
								"https://localhost:8083/dev/designerProducts/productList/" + order.getProductId(),
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
			return ResponseEntity.ok(new Json(productId.toString()));

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
									SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
									SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
									SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
									SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
			response.put("Return", orderSKUDetailsRepo.findByOrderTotal(designerId, "Return").size());
			response.put("Active", orderSKUDetailsRepo.findByOrderTotal(designerId, "Active").size());
			response.put("cancelRequest",
					orderSKUDetailsRepo.findByOrderTotal(designerId, "Request for cancelation").size());
			response.put("Orders", orderSKUDetailsRepo.findByDesignerId(designerId).size());

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
				return new GlobalResponse("Error", "Order not found", 400);
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
				OrderDetailsEntity orderDetailsEntity = mongoTemplate.findOne(query, OrderDetailsEntity.class);

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

				if (findAll.getSize() <= 1) {
					throw new CustomException("Payment not found!");
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
				return new GlobalResponse("Success", "Order status updated", 200);
			} else {
				return new GlobalResponse("Error", "Order not found", 400);
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
				throw new CustomException("Order not found");
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
				throw new CustomException("Payment not found");
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

			filterCatDetails.setId(payId);
			filterCatDetails.setOrderId(OrderRow.get(0).getOrderId());
			filterCatDetails.setPaymentDetails(PayResponse);
			filterCatDetails.setPaymentResponse(map);
			filterCatDetails.setPaymentStatus(payStatus);
			filterCatDetails.setUserId(OrderRow.get(0).getUserId());
			filterCatDetails.setCreatedOn(new Date());

			OrderPaymentEntity data = userOrderPaymentRepo.save(filterCatDetails);

			return ResponseEntity.ok(data);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> postOrderTrackingService(OrderTrackingEntity orderTrackingEntity) {

		try {

			List<OrderTrackingEntity> OrderTrackingRow = orderTrackingRepo
					.findByTrackingIds(orderTrackingEntity.getTrackingId());

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

			OrderTrackingEntity data = orderTrackingRepo.save(filterCatDetails);

			return ResponseEntity.ok(new GlobalResponse("Success", "Tracking updated successfully", 200));

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

				OrderTrackingEntity data = orderTrackingRepo.save(filterCatDetails);

				return ResponseEntity.ok(new GlobalResponse("Success", "Tracking updated successfully", 200));

			} else {
				throw new CustomException("Something went to wrong! from order related");
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

	public GlobalResponse cancelOrderService(String refOrderId, Integer refProductId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("orderId").is(refOrderId).and("productId").is(refProductId));
			OrderSKUDetailsEntity skuDetailsEntity = mongoOperations.findOne(query, OrderSKUDetailsEntity.class);
			if (!skuDetailsEntity.getOrderItemStatus().equals("cancelled")) {
				skuDetailsEntity.setId(skuDetailsEntity.getId());
				skuDetailsEntity.setOrderItemStatus("cancelled");
				orderSKUDetailsRepo.save(skuDetailsEntity);
				Query query2 = new Query();
				query.addCriteria(Criteria.where("orderId").is(refOrderId));
				OrderDetailsEntity detailsEntity = mongoOperations.findOne(query2, OrderDetailsEntity.class);
				detailsEntity.setId(detailsEntity.getId());
				detailsEntity.setTotalAmount(detailsEntity.getTotalAmount() - skuDetailsEntity.getSalesPrice());
				detailsEntity.setTaxAmount(detailsEntity.getTaxAmount() - skuDetailsEntity.getTaxAmount());
				detailsEntity.setMrp(detailsEntity.getMrp() - skuDetailsEntity.getMrp());
				orderDetailsRepo.save(detailsEntity);
				return new GlobalResponse("Success", "Ordered product cancelled successfully", 200);
			} else {
				return new GlobalResponse("Error", "Product already cancelled", 400);
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
			String body = restTemplate.getForEntity("https://localhost:8083/dev/designer/designerIdList", String.class)
					.getBody();
			JSONArray jsonArray = new JSONArray(body);
			// System.out.println(jsonArray);
			ObjectMapper mapper = new ObjectMapper();
			for (int i = 0; i < jsonArray.length(); i++) {
				org.json.simple.JSONObject designerLoginEntity = mapper.readValue(jsonArray.get(i).toString(),
						org.json.simple.JSONObject.class);
				desiredDesingerIdList.add(Integer.parseInt(designerLoginEntity.get("dId").toString()));
				// System.out.println(designerLoginEntity.get("dId").toString());
			}
			// System.out.println(desiredDesingerIdList);
			int totalTax = 0;
			int totalAmount = 0;
			int totalGrossAmount = 0;
			for (int i = 0; i < desiredDesingerIdList.size(); i++) {
				List<ProductInvoice> productList = new ArrayList<>();

//				int a=0;a<orderSKUDetails.size();a++
				for (OrderSKUDetailsEntity a : orderSKUDetails) {
					// List<ProductInvoice> productList= new ArrayList<ProductInvoice>();
					if (a.getDesignerId() == desiredDesingerIdList.get(i)) {
						// System.out.println((orderSKUDetails.get(a).getProductId()));
						ProductInvoice invoice = new ProductInvoice();
						invoice.setGrossAmount(a.getMrp().intValue());
						invoice.setIgst(a.getTaxAmount().intValue());
						invoice.setProductDescription(a.getProductName());
						invoice.setProductSKUId(a.getProductSku());
						invoice.setQuantity(a.getUnits().toString());
						invoice.setWithTaxAmount(a.getSalesPrice().intValue());
						invoice.setProductSize(a.getSize());
						LOGGER.info(invoice.toString());
						productList.add(invoice);
						totalTax = totalTax + a.getTaxAmount().intValue();
						totalAmount = totalAmount + a.getSalesPrice().intValue();
						totalGrossAmount = totalGrossAmount + a.getMrp().intValue();
					}
				}
				LOGGER.info(" PDF " + productList);
				if (productList.size() > 0) {
					LOGGER.info("Rpoduct List data " + productList);
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
			// System.out.println(result);

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
						"https://localhost:9095/dev/designer/" + orderList.get(i).getDesignerId(), Object.class);
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
				throw new CustomException("Invoice already exist!");
			} else {
				OrderInvoiceEntity OrderLastRow = orderInvoiceRepo.findTopByOrderByIdDesc();

				String InvNumber = String.format("%014d", OrderLastRow.getId());
				orderInvoiceEntity.setId(sequenceGenerator.getNextSequence(OrderInvoiceEntity.SEQUENCE_NAME));
				orderInvoiceEntity.setInvoiceId("IV" + InvNumber);
				saveData = orderInvoiceRepo.save(orderInvoiceEntity);
			}
			return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Invoice added succesfully", 200));
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
				throw new CustomException("Invoice not found");
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
			return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Invoice updated succesfully", 200));
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
				throw new CustomException("Invoice not found!");
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
					.getForEntity("https://localhost:8080/dev/auth/info/DESIGNER/"
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
		// TODO Auto-generated method stub
		try {
			String designerEmail = jwtconfig.extractUsername(token.substring(7));
			LOGGER.info(designerEmail);
			String designerId = restTemplate
					.getForEntity("https://localhost:8080/dev/auth/info/DESIGNER/" + designerEmail,
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
			org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
			jsonObject.put("cancelComment", cancelationRequestDTO.getComment());
			jsonObject.put("cancelationTime", new Date());
			OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
			orderStatusDetails.setCancelOrderDetails(jsonObject);
			orderDetails.get(0).setOrderStatusDetails(orderStatusDetails);
			orderDetails.get(0).setOrderItemStatus("Request for cancelation");
			orderSKUDetailsRepo.saveAll(orderDetails);
			return new GlobalResponse("Success", "Cancelation request send successfully", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalResponse cancelApproval(String designerId, String orderId, String productId,
			CancelationRequestDTO cancelationRequestDTO) {
		try {
			List<OrderSKUDetailsEntity> orderDetails = orderSKUDetailsRepo
					.findByProductIdAndDesignerIdAndOrderIdAndOrderItemStatus(Integer.parseInt(productId),
							Integer.parseInt(designerId), orderId, "Request for cancelation");
//					.stream()
//					.filter(e->e.getDesignerId() == Long.parseLong(designerId))
//					.filter(e->e.getOrderId().equals(orderId))
//					.filter(e->e.getProductId()==Integer.parseInt(productId))
//					.filter(e->e.getOrderItemStatus().equals("Request for cancelation"))
//					.collect(Collectors.toList());
			String username = userloginRepo.findById(orderDetails.get(0).getUserId()).get().getFirstName();
			String userEmail = userloginRepo.findById(orderDetails.get(0).getUserId()).get().getEmail();
			DesignerRequestDTO designerResponse = restTemplate
					.getForEntity("https://localhost:8083/dev/designer/" + designerId, DesignerRequestDTO.class)
					.getBody();
			String designerName = designerResponse.getDesignerId().toString();
			String designerEmail = designerResponse.getDesignerProfile().get("email").toString();
			if (cancelationRequestDTO.getOrderStatus().equals("Accepted")) {

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
				cancelEmailJSON.setComment(orderDetails.get(0).getOrderStatusDetails().getCancelOrderDetails()
						.get("cancelComment").toString());
				LOGGER.info(cancelEmailJSON + "");
				data.put("data2", cancelEmailJSON);
				// data1.put("designerName", designerName);
				Context context = new Context();
				context.setVariables(data);
				// context.setVariables(data1);
				String htmlContent = templateEngine.process("ordercancel.html", context);
				EmailSenderThread emailSenderThread = new EmailSenderThread(userEmail,
						"Order cnacelled from designer side", htmlContent, true, null, restTemplate);
				emailSenderThread.start();
				return new GlobalResponse("Success", "Order cancelled successfully", 200);
			} else {
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
				cancelEmailJSON.setComment(cancelationRequestDTO.getComment());
				// LOGGER.info(cancelEmailJSON+"");
				data.put("data2", cancelEmailJSON);
				// data1.put("designerName", designerName);
				Context context = new Context();
				context.setVariables(data);
				// context.setVariables(data1);
				LOGGER.info(designerEmail);
				String htmlContent = templateEngine.process("ordercancelRejected.html", context);
				EmailSenderThread emailSenderThread = new EmailSenderThread(designerEmail, "Order cancelation rejected",
						htmlContent, true, null, restTemplate);
				emailSenderThread.start();
				return new GlobalResponse("Success", "Order Rejected successfully", 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalResponse itemStatusChange(String designerId, String orderId, String productId,
			org.json.simple.JSONObject statusChange, String orderItemStatus) {
		try {
			OrderSKUDetailsEntity item = orderSKUDetailsRepo.findByProductIdAndDesignerIdAndOrderId(
					Integer.parseInt(productId), Integer.parseInt(designerId), orderId).get(0);
			String itemStatus = item.getOrderItemStatus();

			if (orderItemStatus.equals("Orders")) {
				if (itemStatus.equals("New") || !itemStatus.equals(orderItemStatus)) {
					OrderSKUDetailsEntity orderDetails = orderSKUDetailsRepo.findByProductIdAndDesignerIdAndOrderId(
							Integer.parseInt(productId), Integer.parseInt(designerId), orderId).get(0);
					org.json.simple.JSONObject jsonObject3 = new org.json.simple.JSONObject();
					String string = statusChange.get("OrdersDTO").toString();
					LOGGER.info(string + "InsideObject");
					Gson gson = new Gson();
					org.json.simple.JSONObject fromJson = gson.fromJson(string, org.json.simple.JSONObject.class);
					try {
						OrderStatusDetails orderStatusDetails = orderDetails.getOrderStatusDetails();
						jsonObject3.put("withCustomization", fromJson.get("withCustomization"));
						jsonObject3.put("withDesignCustomization", fromJson.get("withDesignCustomization"));
						jsonObject3.put("ordersTime", new Date());
						orderStatusDetails.setOrdersDetails(jsonObject3);
						orderDetails.setOrderItemStatus(orderItemStatus);
						orderSKUDetailsRepo.save(orderDetails);
						LOGGER.info(jsonObject3 + "Inside");

					} catch (Exception e) {
						OrderStatusDetails orderStatusDetails = new OrderStatusDetails();

						jsonObject3.put("withCustomization", fromJson.get("withCustomization"));
						jsonObject3.put("withDesignCustomization", fromJson.get("withDesignCustomization"));
						jsonObject3.put("ordersTime", new Date());
						orderStatusDetails.setOrdersDetails(jsonObject3);
						orderDetails.setOrderStatusDetails(orderStatusDetails);
						orderDetails.setOrderItemStatus(orderItemStatus);
						orderSKUDetailsRepo.save(orderDetails);
						LOGGER.info(jsonObject3 + "Inside");

					}
				} else
					throw new CustomException("The Product is Already " + itemStatus);
			} else if (orderItemStatus.equals("Packed")) {
				if (itemStatus.equals("Orders") || !itemStatus.equals(orderItemStatus)) {
					LOGGER.info("Inside Packed");
					OrderSKUDetailsEntity orderDetails = orderSKUDetailsRepo.findByProductIdAndDesignerIdAndOrderId(
							Integer.parseInt(productId), Integer.parseInt(designerId), orderId).get(0);
					org.json.simple.JSONObject jsonObject2 = new org.json.simple.JSONObject();
					String string = statusChange.get("PackedDTO").toString();
					LOGGER.info(string + "InsideObject");
					Gson gson = new Gson();
					org.json.simple.JSONObject fromJson = gson.fromJson(string, org.json.simple.JSONObject.class);
					try {
						LOGGER.info("Inside Packed try ");
						OrderStatusDetails orderStatusDetails = orderDetails.getOrderStatusDetails();
						jsonObject2.put("packedCovered", fromJson.get("packedCovered"));
						jsonObject2.put("packingVideo", fromJson.get("packingVideo"));
						jsonObject2.put("orderPackedTime", new Date());
						orderDetails.setOrderItemStatus(orderItemStatus);
						orderStatusDetails.setPackedDetails(jsonObject2);
						orderSKUDetailsRepo.save(orderDetails);

					} catch (Exception e) {
						LOGGER.info("Inside Packed catch");
						OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
						LOGGER.info(statusChange.get("PackedDTO") + "Inside Packed");
						LOGGER.info(fromJson.toString());
						jsonObject2.put("packedCovered", fromJson.get("packedCovered"));
						jsonObject2.put("packingVideo", fromJson.get("packingVideo"));
						jsonObject2.put("orderPackedTime", new Date());
						orderStatusDetails.setPackedDetails(jsonObject2);
						orderDetails.setOrderStatusDetails(orderStatusDetails);
						orderDetails.setOrderItemStatus(orderItemStatus);
						orderSKUDetailsRepo.save(orderDetails);
						LOGGER.info(orderDetails + "Inside OrderDetails");

					}
				} else {
					throw new CustomException("The Product is Already " + itemStatus);

				}

			} else if (orderItemStatus.equals("Shipped")) {
				if (itemStatus.equals("Packed") || !itemStatus.equals(orderItemStatus)) {
					OrderSKUDetailsEntity orderDetails = orderSKUDetailsRepo.findByProductIdAndDesignerIdAndOrderId(
							Integer.parseInt(productId), Integer.parseInt(designerId), orderId).get(0);
					org.json.simple.JSONObject jsonObject1 = new org.json.simple.JSONObject();
					String string = statusChange.get("ShippedDTO").toString();
					LOGGER.info(string + "InsideObject");
					Gson gson = new Gson();
					org.json.simple.JSONObject fromJson = gson.fromJson(string, org.json.simple.JSONObject.class);
					try {
						OrderStatusDetails orderStatusDetails = orderDetails.getOrderStatusDetails();
						orderStatusDetails.setShippedDetails(jsonObject1);
						jsonObject1.put("courierName", fromJson.get("courierName"));
						jsonObject1.put("awbNumber", fromJson.get("awbNumber"));
						jsonObject1.put("orderShippedTime", new Date());
						orderStatusDetails.setShippedDetails(jsonObject1);
						orderDetails.setOrderItemStatus(orderItemStatus);
						orderSKUDetailsRepo.save(orderDetails);

					} catch (Exception e) {
						OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
						LOGGER.info(orderDetails + "Inside OrderDetails");
						LOGGER.info("Inside Shipped " + statusChange.get("ShippedDTO"));
						jsonObject1.put("courierName", fromJson.get("courierName"));
						jsonObject1.put("awbNumber", fromJson.get("awbNumber"));
						jsonObject1.put("orderShippedTime", new Date());
						orderStatusDetails.setShippedDetails(jsonObject1);
						orderDetails.setOrderStatusDetails(orderStatusDetails);
						orderDetails.setOrderItemStatus(orderItemStatus);
						orderSKUDetailsRepo.save(orderDetails);

					}
				} else {
					throw new CustomException("The Product is Already " + itemStatus);
				}
			} else if (orderItemStatus.equals("Delivered")) {
				if (itemStatus.equals("Shipped") || !itemStatus.equals(orderItemStatus)) {
					OrderSKUDetailsEntity orderDetails = orderSKUDetailsRepo.findByProductIdAndDesignerIdAndOrderId(
							Integer.parseInt(productId), Integer.parseInt(designerId), orderId).get(0);
					org.json.simple.JSONObject jsonObject4 = new org.json.simple.JSONObject();
					String string = statusChange.get("DeliveryDTO").toString();
					LOGGER.info(string + "InsideObject");
					Gson gson = new Gson();
					org.json.simple.JSONObject fromJson = gson.fromJson(string, org.json.simple.JSONObject.class);
					try {
						OrderStatusDetails orderStatusDetails = orderDetails.getOrderStatusDetails();
						jsonObject4.put("deliveredDate", fromJson.get("deliveredDate"));
						orderStatusDetails.setDeliveryDetails(jsonObject4);
						orderDetails.setOrderItemStatus(orderItemStatus);
						orderSKUDetailsRepo.save(orderDetails);

					} catch (Exception e) {
						OrderStatusDetails orderStatusDetails = new OrderStatusDetails();

						LOGGER.info(fromJson + "Inside Delivery");
						jsonObject4.put("deliveredDate", fromJson.get("deliveredDate"));
						orderStatusDetails.setDeliveryDetails(jsonObject4);
						orderDetails.setOrderStatusDetails(orderStatusDetails);
						orderDetails.setOrderItemStatus(orderItemStatus);
						orderSKUDetailsRepo.save(orderDetails);

					}
				} else {
					throw new CustomException("The Product is Already " + itemStatus);
				}
			}
			return new GlobalResponse("Sucess",
					"Item Status Changed " + itemStatus + " to " + orderItemStatus + " Sucessfully", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalResponse itemStatusChangefromAdmin(String token, String orderId, String productId,
			org.json.simple.JSONObject statusChange, String orderItemStatus) {
		try {
			LOGGER.info("Inside itemStatusChangefromAdmin");
			OrderSKUDetailsEntity item = orderSKUDetailsRepo
					.findByProductIdAndOrderId(Integer.parseInt(productId), orderId).get(0);
			String itemStatus = item.getOrderItemStatus();
			if (!itemStatus.equals("New")) {
				if (orderItemStatus.equals("Packed")) {
					if (itemStatus.equals("Orders") || !itemStatus.equals(orderItemStatus)) {
						LOGGER.info("Inside Packed");
						OrderSKUDetailsEntity orderDetails = orderSKUDetailsRepo
								.findByProductIdAndOrderId(Integer.parseInt(productId), orderId).get(0);
						org.json.simple.JSONObject jsonObject2 = new org.json.simple.JSONObject();
						String string = statusChange.get("PackedDTO").toString();
						LOGGER.info(string + "InsideObject");
						Gson gson = new Gson();
						org.json.simple.JSONObject fromJson = gson.fromJson(string, org.json.simple.JSONObject.class);
						try {
							LOGGER.info("Inside Packed try ");
							OrderStatusDetails orderStatusDetails = orderDetails.getOrderStatusDetails();
							jsonObject2.put("packedCovered", fromJson.get("packedCovered"));
							jsonObject2.put("packingVideo", fromJson.get("packingVideo"));
							jsonObject2.put("orderPackedTime", new Date());
							orderDetails.setOrderItemStatus(orderItemStatus);
							orderStatusDetails.setPackedDetails(jsonObject2);
							orderSKUDetailsRepo.save(orderDetails);

						} catch (Exception e) {
							LOGGER.info("Inside Packed catch");
							OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
							LOGGER.info(statusChange.get("PackedDTO") + "Inside Packed");
							LOGGER.info(fromJson.toString());
							jsonObject2.put("packedCovered", fromJson.get("packedCovered"));
							jsonObject2.put("packingVideo", fromJson.get("packingVideo"));
							jsonObject2.put("orderPackedTime", new Date());
							orderStatusDetails.setPackedDetails(jsonObject2);
							orderDetails.setOrderStatusDetails(orderStatusDetails);
							orderDetails.setOrderItemStatus(orderItemStatus);
							orderSKUDetailsRepo.save(orderDetails);
							LOGGER.info(orderDetails + "Inside OrderDetails");

						}
					} else {
						throw new CustomException("The Product is Already " + itemStatus);

					}

				} else if (orderItemStatus.equals("Shipped")) {
					if (itemStatus.equals("Packed") || !itemStatus.equals(orderItemStatus)) {
						OrderSKUDetailsEntity orderDetails = orderSKUDetailsRepo
								.findByProductIdAndOrderId(Integer.parseInt(productId), orderId).get(0);
						org.json.simple.JSONObject jsonObject1 = new org.json.simple.JSONObject();
						String string = statusChange.get("ShippedDTO").toString();
						LOGGER.info(string + "InsideObject");
						Gson gson = new Gson();
						org.json.simple.JSONObject fromJson = gson.fromJson(string, org.json.simple.JSONObject.class);
						try {
							OrderStatusDetails orderStatusDetails = orderDetails.getOrderStatusDetails();
							orderStatusDetails.setShippedDetails(jsonObject1);
							jsonObject1.put("courierName", fromJson.get("courierName"));
							jsonObject1.put("awbNumber", fromJson.get("awbNumber"));
							jsonObject1.put("orderShippedTime", new Date());
							orderStatusDetails.setShippedDetails(jsonObject1);
							orderDetails.setOrderItemStatus(orderItemStatus);
							orderSKUDetailsRepo.save(orderDetails);

						} catch (Exception e) {
							OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
							LOGGER.info(orderDetails + "Inside OrderDetails");
							LOGGER.info("Inside Shipped " + statusChange.get("ShippedDTO"));
							jsonObject1.put("courierName", fromJson.get("courierName"));
							jsonObject1.put("awbNumber", fromJson.get("awbNumber"));
							jsonObject1.put("orderShippedTime", new Date());
							orderStatusDetails.setShippedDetails(jsonObject1);
							orderDetails.setOrderStatusDetails(orderStatusDetails);
							orderDetails.setOrderItemStatus(orderItemStatus);
							orderSKUDetailsRepo.save(orderDetails);

						}
					} else {
						throw new CustomException("The Product is Already " + itemStatus);
					}
				} else if (orderItemStatus.equals("Delivered")) {
					if (itemStatus.equals("Shipped") || !itemStatus.equals(orderItemStatus)) {
						OrderSKUDetailsEntity orderDetails = orderSKUDetailsRepo
								.findByProductIdAndOrderId(Integer.parseInt(productId), orderId).get(0);
						org.json.simple.JSONObject jsonObject4 = new org.json.simple.JSONObject();
						String string = statusChange.get("DeliveryDTO").toString();
						LOGGER.info(string + "InsideObject");
						Gson gson = new Gson();
						org.json.simple.JSONObject fromJson = gson.fromJson(string, org.json.simple.JSONObject.class);
						try {
							OrderStatusDetails orderStatusDetails = orderDetails.getOrderStatusDetails();
							jsonObject4.put("deliveredDate", fromJson.get("deliveredDate"));
							orderStatusDetails.setDeliveryDetails(jsonObject4);
							orderDetails.setOrderItemStatus(orderItemStatus);
							orderSKUDetailsRepo.save(orderDetails);

						} catch (Exception e) {
							OrderStatusDetails orderStatusDetails = new OrderStatusDetails();

							LOGGER.info(fromJson + "Inside Delivery");
							jsonObject4.put("deliveredDate", fromJson.get("deliveredDate"));
							orderStatusDetails.setDeliveryDetails(jsonObject4);
							orderDetails.setOrderStatusDetails(orderStatusDetails);
							orderDetails.setOrderItemStatus(orderItemStatus);
							orderSKUDetailsRepo.save(orderDetails);

						}
					} else {
						throw new CustomException("The Product is Already " + itemStatus);
					}
				}
				return new GlobalResponse("Sucess",
						"Item Status Changed " + itemStatus + " to " + orderItemStatus + " Sucessfully", 200);
			} else
				throw new CustomException("Admin Can't Change this Status Now");
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
}
