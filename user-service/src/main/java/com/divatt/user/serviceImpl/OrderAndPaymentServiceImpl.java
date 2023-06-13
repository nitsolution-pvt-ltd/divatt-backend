package com.divatt.user.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.DateFormat;
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

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.divatt.user.config.JWTConfig;
import com.divatt.user.constant.MessageConstant;
import com.divatt.user.constant.RestTemplateConstants;
import com.divatt.user.dto.CancelEmailJSON;
import com.divatt.user.dto.CancelationRequestApproveAndRejectDTO;
import com.divatt.user.dto.CancelationRequestDTO;
import com.divatt.user.dto.DesignerReceivedProductDTO;
import com.divatt.user.dto.DesignerRequestDTO;
import com.divatt.user.dto.DialogBoxDTO;
import com.divatt.user.dto.ForceReturnOnDTO;
import com.divatt.user.dto.ForceReturnOnDTO;
import com.divatt.user.dto.InvoiceUpdatedModel;
import com.divatt.user.dto.ReturnRejectedByAdminDTO;
import com.divatt.user.dto.ReturnRequestApproveDTO;
import com.divatt.user.dto.UserShippedProductDTO;
import com.divatt.user.entity.BillingAddressEntity;
import com.divatt.user.entity.InvoiceEntity;
import com.divatt.user.entity.OrderInvoiceEntity;
import com.divatt.user.entity.OrderTrackingEntity;
import com.divatt.user.entity.ProductInvoice;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.order.HsnData;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderPaymentEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.entity.order.OrderStatusDetails;
import com.divatt.user.entity.product.DesignerProfileEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.helper.ListResponseDTO;
import com.divatt.user.helper.PDFRunner;
import com.divatt.user.helper.UtillUserService;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.OrderInvoiceRepo;
import com.divatt.user.repo.OrderSKUDetailsRepo;
import com.divatt.user.repo.OrderTrackingRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.repo.UserOrderPaymentRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.OrderAndPaymentService;
import com.divatt.user.services.SequenceGenerator;
import com.divatt.user.utill.CommonUtility;
import com.divatt.user.utill.EmailSenderThread;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

	@Value("${DESIGNER}")
	private String DESIGNER_SERVICE;

	@Value("${AUTH}")
	private String AUTH_SERVICE;

	@Value("${ADMIN}")
	private String ADMIN_SERVICE;

	@Value("${USERS}")
	private String USER_SERVICE;

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

	@Autowired
	private ObjectMapper objectMapper;

	protected String getRandomString() {
		/*** String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; ***/
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
		/*** String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; ***/
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
		/*** String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; ***/
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

	@Override
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
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/razorpay/create", exe.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(new Json(exe.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/razorpay/create", e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
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
			JsonNode OrderPayJson = new JsonNode(paymentIdFilter);

			Payment payment = razorpayClient.Payments
					.fetch(OrderPayJson.getObject().get("razorpay_payment_id").toString());
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payment/add", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payment/add", gson.toJson(payment), HttpStatus.OK);
			}
			String payStatus = "FAILED";
			if (payment.get("error_code").equals(null) && payment.get("status").equals("captured")) {
				payStatus = "COMPLETED";

			} else if (!payment.get("error_code").equals(null) && !payment.get("error_reason").equals(null)
					&& !payment.get("error_step").equals(null) && payment.get("status").equals("failed")) {
				payStatus = "FAILED";
			}

			List<OrderDetailsEntity> findOrderRow = orderDetailsRepo.findByOrderId(orderPaymentEntity.getOrderId());
			if (findOrderRow.size() <= 0) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/payment/add", "Order details not found",
							HttpStatus.NOT_FOUND);
				}
				throw new CustomException(MessageConstant.ORDER_NOT_FOUND.getMessage());
			}

			Map<String, Object> map = null;
			try {
				map = obj.readValue(payment.toString(), new TypeReference<Map<String, Object>>() {
				});
			} catch (JsonProcessingException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/payment/add", e.getLocalizedMessage(),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			Map<String, String> mapPayId = new HashMap<>();
			mapPayId.put("OrderId", orderPaymentEntity.getOrderId());
			mapPayId.put("TransactionId", OrderPayJson.getObject().get("razorpay_payment_id").toString());

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
				filterCatDetails.setDateTime(orderPaymentEntity.getDateTime());
				filterCatDetails.setCreatedOn(format);
				userOrderPaymentRepo.save(filterCatDetails);
				commonUtility.userOrder(orderPaymentEntity);
				return ResponseEntity.ok(mapPayId);
			} else if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payment/add", MessageConstant.ORDER_ID_EXIST.getMessage(),
						HttpStatus.NOT_FOUND);
			}
			throw new CustomException(MessageConstant.ORDER_ID_EXIST.getMessage());
		} catch (RazorpayException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payment/add", e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payment/add", ex.getResponseBodyAsString(), ex.getStatusCode());
			}
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payment/add", e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public void postOrderSKUService(OrderSKUDetailsEntity orderSKUDetailsEntityRow) {
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
				ResponseEntity<org.json.simple.JSONObject> forEntity = restTemplate.getForEntity(DESIGNER_SERVICE
						+ RestTemplateConstants.DESIGNER_PRODUCT + orderSKUDetailsEntityRow.getProductId(),
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
		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/orderSKUDetails/add", ex.getResponseBodyAsString(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/orderSKUDetails/add", e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	}

	@Override
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

	@Override
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

			if (orderStatus != "" && !orderStatus.equals("All")) {
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
				if (OrderPaymentRow.isPresent()) {
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
					} else {
						OrderSKUD = obj.writeValueAsString(OrderSKUDetailsRow);
					}
				} catch (JsonProcessingException e2) {
					e2.printStackTrace();
				}

				JsonNode OrderSKUDJson = new JsonNode(OrderSKUD);

				JsonNode cartJN = new JsonNode(productIdFilter);
				JSONObject objects = cartJN.getObject();
				objects.put("paymentData", payRow);
				objects.put("OrderSKUDetails", OrderSKUDJson.getArray());
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
			response.put("totalIteamStatus", orderSKUDetailsRepo.findByOrder(orderStatus).size());

			return response;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> getOrdersDetailsService(String orderId,Integer productId,String size) {
		try {

			List<OrderDetailsEntity> findById = this.orderDetailsRepo.findByOrderId(orderId);
			if (findById.size() <= 0) {
				throw new CustomException(MessageConstant.ORDER_NOT_FOUND.getMessage());
			}
			List<Object> prodId = new ArrayList<>();
			List<Object> productIds = new ArrayList<>();

			findById.forEach(e -> {
				ObjectMapper obj = new ObjectMapper();
				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}

				Optional<OrderPaymentEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByOrderId(e.getOrderId());
				List<OrderSKUDetailsEntity> OrderSKUDetailsRow = new ArrayList<>();
				if (StringUtils.isEmpty(size) && productId == 0) {
					OrderSKUDetailsRow = this.orderSKUDetailsRepo.findByOrderId(e.getOrderId());
				} else {
					OrderSKUDetailsRow = this.orderSKUDetailsRepo.findByProductIdAndOrderIdAndSize(productId, e.getOrderId(), size);

				}
		
				OrderSKUDetailsRow.forEach(D -> {
					ObjectMapper objs = new ObjectMapper();
					String productIdFilters = null;
					try {
						ResponseEntity<org.json.simple.JSONObject> productById = restTemplate.getForEntity(
								DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_PRODUCT + D.getProductId(),
								org.json.simple.JSONObject.class);
						D.setHsn(productById.getBody().get("hsnData"));
						productIdFilters = objs.writeValueAsString(D);
						Integer i = (int) (long) D.getUserId();

						List<OrderTrackingEntity> findByIdTracking = this.orderTrackingRepo
								.findByOrderIdAndUserIdAndDesignerIdAndProductId(orderId, i, D.getDesignerId(),
										D.getProductId());
						JsonNode cartJNs = new JsonNode(productIdFilters);

						JSONObject objectss = cartJNs.getObject();
						objectss.put("customization", productById.getBody().get("customization"));
						objectss.put("withGiftWrap", productById.getBody().get("giftWrap"));
						objectss.put("designerProfile", productById.getBody().get("designerProfile"));
						String orderId2 = D.getOrderId();

						List<OrderInvoiceEntity> invoiceId = getInvoiceByOrder(orderId2);

						if (invoiceId.size() > 0) {
							invoiceId.forEach(invoice -> {
								objectss.put("invoiceId", invoice.getInvoiceId());
							});
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
							objectss.put("TrackingData", TrackingJson.getObject());
						}
						productIds.add(objectss);
					} catch (JsonProcessingException e2) {
						e2.printStackTrace();
					}
				});

				String writeValueAsString = null;
				JSONObject payJson = null;
				if (OrderPaymentRow.isPresent()) {
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
				objects.put("OrderSKUDetails", productIds);
				prodId.add(objects);
				
				
			});
			return ResponseEntity.ok(new Json(prodId.get(0).toString()));
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
	
	@Override
	public Map<String, Object> getUserOrderDetailsService(Integer userId, int page, int limit, String sort,
			String sortName, String keyword, Optional<String> sortBy, String token) {

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
				findAll = this.userOrderPaymentRepo.findByUserIdAndPaymentStatusNot(Long.parseLong(userId.toString()),
						"FAILED", pagingSort);
			} else {
				findAll = this.userOrderPaymentRepo.findByUserIdAndKeywordAndPaymentStatusNot(
						Long.parseLong(userId.toString()), keyword, "FAILED", pagingSort);
			}
			List<OrderPaymentEntity> findById = findAll.getContent();
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
				Optional<OrderDetailsEntity> ordersDetailsRow = this.orderDetailsRepo.findByOrderid(e.getOrderId());
				List<OrderSKUDetailsEntity> OrderSKUDetailsRow = this.orderSKUDetailsRepo.findByOrderId(e.getOrderId());
				OrderSKUDetailsRow.forEach(order -> {
					try {
						ResponseEntity<org.json.simple.JSONObject> getProductByID = restTemplate.getForEntity(
								DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_PRODUCT + order.getProductId(),
								org.json.simple.JSONObject.class);
						LOGGER.info("getProductByID" + getProductByID.getBody());
						order.setHsn(getProductByID.getBody().get("hsnData"));
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				});
				String writeValueAsString = null;
				JsonNode pJN = new JsonNode(productIdFilter);
				JSONObject object = pJN.getObject();
				JsonNode orderJson = null;
				JSONObject orderdetailsJson = null;
				if (ordersDetailsRow.isPresent()) {
					try {
						writeValueAsString = obj.writeValueAsString(ordersDetailsRow.get());
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					orderJson = new JsonNode(writeValueAsString);
					orderdetailsJson = orderJson.getObject();
				}
				String OrderSKUD = null;
				try {
					OrderSKUD = obj.writeValueAsString(OrderSKUDetailsRow);
				} catch (JsonProcessingException e2) {
					e2.printStackTrace();
				}

				JsonNode OrderSKUDJson = new JsonNode(OrderSKUD);
				orderdetailsJson.put("paymentData", object);
				orderdetailsJson.put("OrderSKUDetails", OrderSKUDJson.getArray());
				productId.add(orderdetailsJson);
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
			return response;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	////////for internal rest call
	@Override
	public Map<String, Object> getUserOrderDetailsService(Integer userId, int page, int limit, String sort,
			String sortName, String keyword, Optional<String> sortBy) {

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
				findAll = this.userOrderPaymentRepo.findByUserIdAndPaymentStatusNot(Long.parseLong(userId.toString()),
						"FAILED", pagingSort);
			} else {
				findAll = this.userOrderPaymentRepo.findByUserIdAndKeywordAndPaymentStatusNot(
						Long.parseLong(userId.toString()), keyword, "FAILED", pagingSort);
			}
			List<OrderPaymentEntity> findById = findAll.getContent();
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
				Optional<OrderDetailsEntity> ordersDetailsRow = this.orderDetailsRepo.findByOrderid(e.getOrderId());
				List<OrderSKUDetailsEntity> OrderSKUDetailsRow = this.orderSKUDetailsRepo.findByOrderId(e.getOrderId());
				OrderSKUDetailsRow.forEach(order -> {
					try {
						ResponseEntity<org.json.simple.JSONObject> getProductByID = restTemplate.getForEntity(
								DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_PRODUCT + order.getProductId(),
								org.json.simple.JSONObject.class);
						LOGGER.info("getProductByID" + getProductByID.getBody());
						order.setHsn(getProductByID.getBody().get("hsnData"));
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				});
				String writeValueAsString = null;
				JsonNode pJN = new JsonNode(productIdFilter);
				JSONObject object = pJN.getObject();
				JsonNode orderJson = null;
				JSONObject orderdetailsJson = null;
				if (ordersDetailsRow.isPresent()) {
					try {
						writeValueAsString = obj.writeValueAsString(ordersDetailsRow.get());
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					orderJson = new JsonNode(writeValueAsString);
					orderdetailsJson = orderJson.getObject();
				}
				String OrderSKUD = null;
				try {
					OrderSKUD = obj.writeValueAsString(OrderSKUDetailsRow);
				} catch (JsonProcessingException e2) {
					e2.printStackTrace();
				}

				JsonNode OrderSKUDJson = new JsonNode(OrderSKUD);
				orderdetailsJson.put("paymentData", object);
				orderdetailsJson.put("OrderSKUDetails", OrderSKUDJson.getArray());
				productId.add(orderdetailsJson);
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
			return response;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	
	
	
	@Override
	public Map<String, Object> getDesigerOrders(int designerId, int page, int limit, String sort, String sortName,
			String keyword, Optional<String> sortBy, String orderItemStatus, String sortDateType, String startDate,
			String endDate) {
		LOGGER.info("Inside - OrderAndPaymentService.getDesigerOrders()");
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
			if (!sortDateType.equals(null)) {

				if (sortDateType.equalsIgnoreCase("new")) {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, "id");

				} else if (sortDateType.equalsIgnoreCase("old")) {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, "id");

				}
			}
			List<OrderPaymentEntity> findByPaymentStatusNot = this.userOrderPaymentRepo
					.findByPaymentStatusNot("FAILED");
			Page<OrderPaymentEntity> findAll = null;
			List<OrderSKUDetailsEntity> OrderSKUDetailsData = new ArrayList<>();

			if (keyword != null || !"".equals(keyword)) {
				OrderSKUDetailsData = this.orderSKUDetailsRepo.findByDesignerId(designerId);
			}
			List<Object> productId = new ArrayList<>();

			if (!orderItemStatus.isEmpty() && !orderItemStatus.equals("Orders")) {

				List<String> OrderId1 = OrderSKUDetailsData.stream()

						.filter(e -> orderItemStatus.equals(e.getOrderItemStatus()))
						.filter(e -> keyword != "" ? e.getOrderId().startsWith(keyword.toUpperCase()) : true)
						.filter(e -> {
							if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
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

				findAll = userOrderPaymentRepo.findByOrderIdInAndPaymentStatusNot(OrderId1, "FAILED", pagingSort);

			} else {
				List<String> OrderId = OrderSKUDetailsData.stream().filter(
						e -> keyword != "" ? e.getOrderId().toUpperCase().startsWith(keyword.toUpperCase()) : true)
						.filter(e -> {
							if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
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
				findAll = userOrderPaymentRepo.findByOrderIdInAndPaymentStatusNot(OrderId, "FAILED", pagingSort);
			}
			List<OrderPaymentEntity> content = findAll.getContent();

			content.forEach(e -> {
				ObjectMapper obj = new ObjectMapper();
				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
				Optional<OrderDetailsEntity> ordersDetailsRow = this.orderDetailsRepo.findByOrderid(e.getOrderId());
				List<OrderSKUDetailsEntity> OrderSKUDetailsRow = this.orderSKUDetailsRepo
						.findByOrderIdAndDesignerId(e.getOrderId(), designerId);
				JsonNode pJN = new JsonNode(productIdFilter);
				JSONObject object = pJN.getObject();
				String writeValueAsString = null;
				JSONObject orderdetailsJson = null;
				if (ordersDetailsRow.isPresent()) {
					try {
						writeValueAsString = obj.writeValueAsString(ordersDetailsRow.get());
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					JsonNode orderJson = new JsonNode(writeValueAsString);
					orderdetailsJson = orderJson.getObject();
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
				orderdetailsJson.put("OrderSKUDetails", OrderSKUDJson.getArray());
				orderdetailsJson.put("paymentData", object);
				productId.add(orderdetailsJson);

			});
			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}
			Map<String, Object> response = new HashMap<>();
			List<OrderSKUDetailsEntity> dataByNew = this.orderSKUDetailsRepo.findByOrderTotal(designerId, "New");
			List<OrderSKUDetailsEntity> collectNew = findByPaymentStatusNot.stream()
					.flatMap(d -> dataByNew.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("New", collectNew.size());

			List<OrderSKUDetailsEntity> dataByPacked = this.orderSKUDetailsRepo.findByOrderTotal(designerId, "Packed");
			List<OrderSKUDetailsEntity> collectPacked = findByPaymentStatusNot.stream()
					.flatMap(d -> dataByPacked.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("Packed", collectPacked.size());

			List<OrderSKUDetailsEntity> dataByShipped = this.orderSKUDetailsRepo.findByOrderTotal(designerId,
					"Shipped");
			List<OrderSKUDetailsEntity> collectShipped = findByPaymentStatusNot.stream()
					.flatMap(d -> dataByShipped.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("Shipped", collectShipped.size());

			List<OrderSKUDetailsEntity> dataByDelivered = this.orderSKUDetailsRepo.findByOrderTotal(designerId,
					"Delivered");
			List<OrderSKUDetailsEntity> collectDelivered = findByPaymentStatusNot.stream()
					.flatMap(d -> dataByDelivered.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("Delivered", collectDelivered.size());

			List<OrderSKUDetailsEntity> dataByReturnRefund = this.orderSKUDetailsRepo.findByOrderTotal(designerId,
					"returnRefund");
			List<OrderSKUDetailsEntity> collectReturnRefund = findByPaymentStatusNot.stream()
					.flatMap(d -> dataByReturnRefund.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("Return", collectReturnRefund.size());

			List<OrderSKUDetailsEntity> dataByActive = this.orderSKUDetailsRepo.findByOrderTotal(designerId, "Active");
			List<OrderSKUDetailsEntity> collectActive = findByPaymentStatusNot.stream()
					.flatMap(d -> dataByActive.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("Active", collectActive.size());

			List<OrderSKUDetailsEntity> dataByRequestForCancelation = this.orderSKUDetailsRepo
					.findByOrderTotal(designerId, "Request for cancelation");
			List<OrderSKUDetailsEntity> collectRequestForCancelation = findByPaymentStatusNot.stream().flatMap(
					d -> dataByRequestForCancelation.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("cancelRequest", collectRequestForCancelation.size());

			List<OrderSKUDetailsEntity> dataByOrders = this.orderSKUDetailsRepo.findByDesignerId(designerId);
			List<OrderSKUDetailsEntity> collectOrders = findByPaymentStatusNot.stream()
					.flatMap(d -> dataByOrders.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("Orders", collectOrders.size());

			List<OrderSKUDetailsEntity> dataByCancelled = this.orderSKUDetailsRepo.findByOrderTotal(designerId,
					"cancelled");
			List<OrderSKUDetailsEntity> collectCancelled = findByPaymentStatusNot.stream()
					.flatMap(d -> dataByCancelled.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("Canceled", collectCancelled.size());

			List<OrderSKUDetailsEntity> dataByReturnRequest = this.orderSKUDetailsRepo.findByOrderTotal(designerId,
					"returnRequest");
			List<OrderSKUDetailsEntity> collectReturnRequest = findByPaymentStatusNot.stream()
					.flatMap(d -> dataByReturnRequest.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("returnRequest", collectReturnRequest.size());

			List<OrderSKUDetailsEntity> dataByRejected = this.orderSKUDetailsRepo.findByOrderTotal(designerId,
					"Rejected");
			List<OrderSKUDetailsEntity> collectRejected = findByPaymentStatusNot.stream()
					.flatMap(d -> dataByRejected.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("rejected", collectRejected.size());

			List<OrderSKUDetailsEntity> dataByRequestCancelation = this.orderSKUDetailsRepo.findByOrderTotal(designerId,
					"Request for cancelation");
			List<OrderSKUDetailsEntity> collectRequestCancelation = findByPaymentStatusNot.stream()
					.flatMap(
							d -> dataByRequestCancelation.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("requestCancelation", collectRequestCancelation.size());

			List<OrderSKUDetailsEntity> dataByReturnRequestApproved = this.orderSKUDetailsRepo
					.findByOrderTotal(designerId, "Return request approved");
			List<OrderSKUDetailsEntity> collectReturnRequestApproved = findByPaymentStatusNot.stream().flatMap(
					d -> dataByReturnRequestApproved.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("returnRequestApproved", collectReturnRequestApproved.size());

			List<OrderSKUDetailsEntity> dataByProductShippedByUser = this.orderSKUDetailsRepo
					.findByOrderTotal(designerId, "Product shipped by user");
			List<OrderSKUDetailsEntity> collectProductShippedByUser = findByPaymentStatusNot.stream().flatMap(
					d -> dataByProductShippedByUser.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("returnRequestApproved", collectProductShippedByUser.size());

			List<OrderSKUDetailsEntity> dataByProductReceivedFromUser = this.orderSKUDetailsRepo
					.findByOrderTotal(designerId, "Product received from user");
			List<OrderSKUDetailsEntity> collectProductReceivedFromUser = findByPaymentStatusNot.stream().flatMap(
					d -> dataByProductReceivedFromUser.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("productReceivedFromUser", collectProductReceivedFromUser.size());

			List<OrderSKUDetailsEntity> dataByReturnRejectedByAdmin = this.orderSKUDetailsRepo
					.findByOrderTotal(designerId, "Return rejected by admin");
			List<OrderSKUDetailsEntity> collectReturnRejectedByAdmin = findByPaymentStatusNot.stream().flatMap(
					d -> dataByReturnRejectedByAdmin.stream().filter(d1 -> d1.getOrderId().equals(d.getOrderId())))
					.collect(Collectors.toList());
			response.put("returnRejectedByAdmin", collectReturnRejectedByAdmin.size());

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
			return response;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
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
	@Override
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

			if (!PaymentRow.isPresent()) {
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

	@Override
	public ResponseEntity<?> postOrderTrackingService(OrderTrackingEntity orderTrackingEntity) {

		try {
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
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> putOrderTrackingService(OrderTrackingEntity orderTrackingEntity, String trackingId) {

		try {

			Optional<OrderTrackingEntity> OrderTrackingRow = orderTrackingRepo.findByTrackingId(trackingId);

			if (OrderTrackingRow.isPresent()) {

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

	@Override
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

	@Override
	public GlobalResponse orderStatusUpdateService(OrderSKUDetailsEntity orderSKUDetailsEntity, String refOrderId,
			Integer refProductId) {
		try {
			Map<String, Object> map = new HashMap<>();
			Query query = new Query();
			query.addCriteria(Criteria.where("order_id").is(refOrderId).and("productId").is(refProductId));
			OrderSKUDetailsEntity skuDetailsEntity = mongoOperations.findOne(query, OrderSKUDetailsEntity.class);

			if (skuDetailsEntity.getOrderId().equals(null) && skuDetailsEntity.getOrderId().toString() == "") {
				throw new CustomException(MessageConstant.BAD_REQUEST.getMessage());
			}

			List<OrderPaymentEntity> findByOrderIdList = userOrderPaymentRepo.findByOrderIdList(refOrderId);
			List<OrderSKUDetailsEntity> findByOrderSKU = new ArrayList<>();
			JSONObject getPaymentData = null;

			if (findByOrderIdList.size() > 0) {
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
				commonUtility.orderCancel(skuDetailsEntity);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_CANCEL.getMessage(), 200);

			} else if (orderSKUDetailsEntity.getOrderItemStatus().equals("Request for cancelation")) {
				skuDetailsEntity.setId(skuDetailsEntity.getId());
				skuDetailsEntity.setOrderItemStatus(orderSKUDetailsEntity.getOrderItemStatus());
				skuDetailsEntity.setOrderStatusDetails(orderSKUDetailsEntity.getOrderStatusDetails());
				orderSKUDetailsRepo.save(skuDetailsEntity);

				commonUtility.orderCancel(skuDetailsEntity);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_CANCEL.getMessage(), 200);

			} else if (orderSKUDetailsEntity.getOrderItemStatus().equals("returnRequest")) {
				skuDetailsEntity.setId(skuDetailsEntity.getId());
				skuDetailsEntity.setOrderItemStatus(orderSKUDetailsEntity.getOrderItemStatus());
				skuDetailsEntity.setOrderStatusDetails(orderSKUDetailsEntity.getOrderStatusDetails());
				orderSKUDetailsRepo.save(skuDetailsEntity);
				String comment = orderSKUDetailsEntity.getOrderStatusDetails().getReturnFromUser().get("comment")
						.toString();
				String reason = orderSKUDetailsEntity.getOrderStatusDetails().getReturnFromUser().get("reason")
						.toString();

				map.put(" for", reason + " and " + comment);
				commonUtility.mailReturnRequest(skuDetailsEntity, refOrderId, refProductId, map);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_REFUND_REQUEST.getMessage(), 200);
			} else if (orderSKUDetailsEntity.getOrderItemStatus().equals("returnRefund")) {
				skuDetailsEntity.setId(skuDetailsEntity.getId());
				skuDetailsEntity.setOrderItemStatus(orderSKUDetailsEntity.getOrderItemStatus());
				org.json.simple.JSONObject returnFromAdmin = orderSKUDetailsEntity.getOrderStatusDetails()
						.getReturnFromAdmin();
				skuDetailsEntity.getOrderStatusDetails().setReturnFromAdmin(returnFromAdmin);
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
				commonUtility.mailReturnRequest(skuDetailsEntity, refOrderId, refProductId, map);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_REFUND_APPROVED.getMessage(), 200);
			} else if (orderSKUDetailsEntity.getOrderItemStatus().equals("Rejected")) {
				skuDetailsEntity.setId(skuDetailsEntity.getId());
				skuDetailsEntity.setOrderItemStatus(orderSKUDetailsEntity.getOrderItemStatus());
				skuDetailsEntity.setOrderStatusDetails(orderSKUDetailsEntity.getOrderStatusDetails());
				orderSKUDetailsRepo.save(skuDetailsEntity);
				commonUtility.mailReturnRequest(skuDetailsEntity, refOrderId, refProductId, map);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_REFUND_REJECTED.getMessage(), 200);

			} else if (orderSKUDetailsEntity.getOrderItemStatus().equals("Product shipped by user")) {

				if (!skuDetailsEntity.equals(orderSKUDetailsEntity.getOrderItemStatus())) {
					if (skuDetailsEntity.getOrderItemStatus().equals("Return request approved")) {
						try {
							OrderStatusDetails orderStatusDetails = skuDetailsEntity.getOrderStatusDetails();
							orderStatusDetails.setUserShippedProduct(
									orderSKUDetailsEntity.getOrderStatusDetails().getUserShippedProduct());
							skuDetailsEntity.setOrderItemStatus(orderSKUDetailsEntity.getOrderItemStatus());
							orderSKUDetailsRepo.save(skuDetailsEntity);

							String comment = orderSKUDetailsEntity.getOrderStatusDetails().getUserShippedProduct()
									.getComments().toString();
							String courierName = orderSKUDetailsEntity.getOrderStatusDetails().getUserShippedProduct()
									.getCourierName().toString();
							String trackingNumber = orderSKUDetailsEntity.getOrderStatusDetails()
									.getUserShippedProduct().getTrakingNumber().toString();
							map.put(" for", comment + " and Courier Name is : " + courierName
									+ ", and Tracking Number is : " + trackingNumber);
							commonUtility.mailReturnRequest(orderSKUDetailsEntity, refOrderId, refProductId, map);
						} catch (Exception e) {
							throw new CustomException(MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
						}
					} else
						throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());
				} else {
					throw new CustomException(
							MessageConstant.PRODUCT_STATUS.getMessage() + orderSKUDetailsEntity.getOrderItemStatus());
				}
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.PRODUCT_SHIPPED_SUCCESSFULLY.getMessage(), 200);
			} else {
				throw new CustomException(MessageConstant.BAD_REQUEST.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("all")
	@Override
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
			String orderId = orderDetailsEntity.getOrderId();

			List<OrderSKUDetailsEntity> orderSKUDetails = mongoOperations.find(query2, OrderSKUDetailsEntity.class);

			String body = restTemplate
					.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_IDLIST, String.class).getBody();

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
			headers.add("Content-Disposition", "attachment; filename=" + orderId + ".pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bytes);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getOrderInvoiceId(String invoiceId) {
		try {
			Query query = new Query();
			Query query2 = new Query();
			List<Object> resObjects = new ArrayList<Object>();
			Map<String, Object> response = new HashMap<String, Object>();

			query.addCriteria(Criteria.where("invoice_id").is(invoiceId));
			OrderDetailsEntity detailsEntity = mongoOperations.findOne(query, OrderDetailsEntity.class);
			response.put("OrderDetails", detailsEntity);
			query2.addCriteria(Criteria.where("orderId").is(detailsEntity.getOrderId()));

			List<OrderSKUDetailsEntity> orderList = mongoOperations.find(query2, OrderSKUDetailsEntity.class);

			for (int i = 0; i < orderList.size(); i++) {
				ResponseEntity<Object> designerData = restTemplate.getForEntity(
						DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_BYID + orderList.get(i).getDesignerId(),
						Object.class);
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

	@Override
	public ResponseEntity<?> postOrderInvoiceService(OrderInvoiceEntity orderInvoiceEntity) {
		LOGGER.info("Inside - OrderAndPaymentService.postOrderInvoiceService()");

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
				orderInvoiceRepo.save(orderInvoiceEntity);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("reason", MessageConstant.SUCCESS.getMessage());
			map.put("message", MessageConstant.INVOICE_ADDED.getMessage());
			map.put("invoiceId", orderInvoiceEntity.getInvoiceId());
			map.put("status", 200);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
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

	@Override
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

	@Override
	public Map<String, Integer> getOrderCount(int designerId, Boolean adminstatus) {

		try {
			Map<String, Integer> countResponse = new HashMap<String, Integer>();
			List<String> orderIdList = new ArrayList<String>();

			if (adminstatus) {
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

	@Override
	public OrderSKUDetailsEntity getOrderDetailsService(String orderId, String productId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("orderId").is(orderId).and("productId").is(productId));
			return mongoOperations.findOne(query, OrderSKUDetailsEntity.class);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
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
					.getForEntity(AUTH_SERVICE + RestTemplateConstants.DESIGNER_DETAILS
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
			CancelationRequestDTO cancelationRequestDTO, String size) {
		try {
			String designerEmail = jwtconfig.extractUsername(token.substring(7));

			org.json.simple.JSONObject designerDetails = restTemplate
					.getForEntity(AUTH_SERVICE + RestTemplateConstants.DESIGNER_DETAILS + designerEmail,
							org.json.simple.JSONObject.class)
					.getBody();

			String designerId = designerDetails.get("designerId").toString();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(designerDetails);
			String firstName;
			String lastName;
			JSONParser parser = new JSONParser();
			try {
				org.json.simple.JSONObject json1 = (org.json.simple.JSONObject) parser.parse(json);
				org.json.simple.JSONObject json2 = (org.json.simple.JSONObject) json1.get("designerProfile");
				firstName = json2.get("firstName1").toString();
				lastName = json2.get("lastName1").toString();
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			List<OrderSKUDetailsEntity> orderDetails = orderSKUDetailsRepo.findAll().stream()
					.filter(e -> e.getDesignerId() == Long.parseLong(designerId))
					.filter(e -> e.getOrderId().equals(orderId))
					.filter(e -> e.getProductId() == Integer.parseInt(productId))
					.filter(e-> e.getSize().equals(size)).collect(Collectors.toList());
			String orderItemStatus = orderDetails.get(0).getOrderItemStatus();
			orderDetails.get(0).setStatus(orderItemStatus);

			SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT_TYPE.getMessage());
			Date dates = new Date();
			String format = formatter.format(dates);

			if (orderDetails.get(0).getOrderItemStatus().equals("cancelled")) {
				throw new CustomException(MessageConstant.PRODUCT_ALREADY_CANCEL.getMessage());
			} else if (!orderDetails.get(0).getOrderItemStatus().equals("New")
					&& !orderDetails.get(0).getOrderItemStatus().equals("Delivered")) {
				if (orderDetails.size() > 0) {
					OrderStatusDetails orderStatusDetails = orderDetails.get(0).getOrderStatusDetails();
					org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
					if (cancelationRequestDTO.getOrderStatus().equals("Request for cancelation")) {
						jsonObject.put("cancelComment", cancelationRequestDTO.getComment());
						jsonObject.put("cancelationTime", format);
						jsonObject.put("canceledBy", cancelationRequestDTO.getCanceledBy());
						jsonObject.put("updatedBy", cancelationRequestDTO.getUpdatedBy());
						orderStatusDetails.setCancelOrderDetails(jsonObject);
						orderDetails.get(0).setOrderStatusDetails(orderStatusDetails);
						orderDetails.get(0).setOrderItemStatus("Request for cancelation");
						orderSKUDetailsRepo.save(orderDetails.get(0));
					} else {
						throw new CustomException(MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
					}
				} else {
					throw new CustomException(MessageConstant.ORDER_NOT_FOUND.getMessage());
				}

			} else if (!orderDetails.get(0).getOrderItemStatus().equals("Delivered")) {
				OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
				org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
				if (cancelationRequestDTO.getOrderStatus().equals("Request for cancelation")) {
					jsonObject.put("cancelComment", cancelationRequestDTO.getComment());
					jsonObject.put("cancelationTime", format);
					jsonObject.put("canceledBy", cancelationRequestDTO.getCanceledBy());
					jsonObject.put("updatedBy", cancelationRequestDTO.getUpdatedBy());
					orderStatusDetails.setCancelOrderDetails(jsonObject);
					orderDetails.get(0).setOrderStatusDetails(orderStatusDetails);
					orderDetails.get(0).setOrderItemStatus("Request for cancelation");
					orderSKUDetailsRepo.save(orderDetails.get(0));
				} else {
					throw new CustomException(MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
				}
			} else {
				throw new CustomException(MessageConstant.PRODUCT_ALREADY_DELIVERED.getMessage());
			}
			try {
//				org.json.simple.JSONObject body = restTemplate
//						.getForEntity(ADMIN_SERVICE + RestTemplateConstants.ADMIN_ROLE_NAME
//								+ MessageConstant.ADMIN_ROLES.getMessage(), org.json.simple.JSONObject.class)
//						.getBody();
//				
				ResponseEntity<List<org.json.simple.JSONObject>> responseData = restTemplate.exchange(
						ADMIN_SERVICE + RestTemplateConstants.ADMIN_ROLE_NAME
								+ MessageConstant.ADMIN_ROLES.getMessage(),
						HttpMethod.GET, null, new ParameterizedTypeReference<List<org.json.simple.JSONObject>>() {
						});
				List<org.json.simple.JSONObject> loginData = responseData.getBody();
				String adminMail = loginData.get(0).get("email").toString();
				String designerName = firstName + " " + lastName;
				String adminFirstName = loginData.get(0).get("firstName").toString();
				String adminLastName = loginData.get(0).get("lastName").toString();
				String adminName = adminFirstName + " " + adminLastName;
				String orderId2 = orderDetails.get(0).getOrderId();
				Long userId = orderDetails.get(0).getUserId();
				int productId2 = orderDetails.get(0).getProductId();
				String productName = orderDetails.get(0).getProductName();
				String displayName = orderDetails.get(0).getDisplayName();
				String productImage = orderDetails.get(0).getImages();
				Long salesPrice = orderDetails.get(0).getSalesPrice();
				Long mrp = orderDetails.get(0).getMrp();
				String productSize = orderDetails.get(0).getSize();

				Map<String, Object> data = new HashMap<>();
				data.put("designerName", designerName);
				data.put("adminName", adminName);
				data.put("orderId", orderId2);
				data.put("userId", userId);
				data.put("productId2", productId2);
				data.put("productName", productName);
				data.put("displayName", displayName);
				data.put("productImage", productImage);
				data.put("productSize", productSize);

				if (salesPrice == 0 || salesPrice == null) {
					data.put("salesPrice", mrp);
				} else {
					data.put("salesPrice", salesPrice);
				}
				Context context = new Context();
				context.setVariables(data);
				String htmlContent = templateEngine.process("orderCancelRequestToAdmin.html", context);
				EmailSenderThread emailSenderThread = new EmailSenderThread(adminMail, "Request for Cancel Order",
						htmlContent, true, null, restTemplate, AUTH_SERVICE);
				emailSenderThread.start();
			} catch (Exception ex) {
				throw new CustomException(ex.getMessage());
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
			CancelationRequestApproveAndRejectDTO cancelationRequestApproveAndRejectDTO, String size) {
		try {
			List<OrderSKUDetailsEntity> orderDetails = orderSKUDetailsRepo
					.findByProductIdAndDesignerIdAndOrderIdAndOrderItemStatusAndSize(Integer.parseInt(productId),
							Integer.parseInt(designerId), orderId, "Request for cancelation", size);
			String username = userloginRepo.findById(orderDetails.get(0).getUserId()).get().getFirstName();
			LOGGER.info("username"+username);
			String userEmail = userloginRepo.findById(orderDetails.get(0).getUserId()).get().getEmail();
			DesignerRequestDTO designerResponse = restTemplate
					.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_BYID + designerId,
							DesignerRequestDTO.class)
					.getBody();
			String firstName = designerResponse.getDesignerProfile().get("firstName1").toString();
			String lastName = designerResponse.getDesignerProfile().get("lastName1").toString();
			String designerName1 = firstName + " " + lastName;
			String designerName = designerResponse.getDesignerProfile().get("displayName").toString();
			String designerEmail = designerResponse.getDesignerProfile().get("email").toString();
			SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT_TYPE.getMessage());
			Date dates = new Date();
			String format = formatter.format(dates);

			if (cancelationRequestApproveAndRejectDTO.getOrderStatus().equals("cancelled")) {

				Map<String, Object> data = new HashMap<String, Object>();
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
///
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
				data.put("data2", cancelEmailJSON);
				Context context = new Context();
				context.setVariables(data);
				String htmlContent = templateEngine.process("ordercancel.html", context);

				EmailSenderThread emailSenderThread = new EmailSenderThread(userEmail,
						MessageConstant.ORDER_CANCEL_FROM_DESIGNER.getMessage(), htmlContent, true, null, restTemplate,
						AUTH_SERVICE);
				emailSenderThread.start();
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ORDER_CANCEL.getMessage(), 200);
			} else {
				Map<String, Object> data = new HashMap<String, Object>();

				String status = orderDetails.get(0).getStatus();
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
				data.put("data2", cancelEmailJSON);
				Context context = new Context();
				context.setVariables(data);

				String htmlContent = templateEngine.process("ordercancelRejected.html", context);
				EmailSenderThread emailSenderThread = new EmailSenderThread(designerEmail,
						MessageConstant.ORDER_CANCELATION_REJECTED.getMessage(), htmlContent, true, null, restTemplate,
						AUTH_SERVICE);
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
			org.json.simple.JSONObject statusChange, String orderItemStatus,String size) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - OrderAndPaymentServiceImpl.itemStatusChange()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - OrderAndPaymentServiceImpl.itemStatusChange()");
		}
		try {
			String designerEmail = jwtconfig.extractUsername(token.substring(7));
			try {
				DesignerProfileEntity entity = new DesignerProfileEntity();
				try {
					entity = restTemplate
							.getForEntity(AUTH_SERVICE + RestTemplateConstants.DESIGNER_DETAILS + designerEmail,
									DesignerProfileEntity.class)
							.getBody();
				} catch (Exception e) {
					e.printStackTrace();
				}

				String designerId = entity.getDesignerId().toString();
				String displayName = entity.getDesignerProfile().getDisplayName();
				Map<String, Object> map = new HashMap<>();

				OrderSKUDetailsEntity item1 = orderSKUDetailsRepo
						.findByProductIdAndOrderIdAndSize(Integer.parseInt(productId), orderId,size).get(0);
				String designerId2 = item1.getDesignerId() + "";

				if (designerId.equals(designerId2)) {
					try {
						OrderSKUDetailsEntity item = orderSKUDetailsRepo.findByProductIdAndDesignerIdAndOrderIdAndSize(
								Integer.parseInt(productId), Integer.parseInt(designerId), orderId,size).get(0);
						OrderStatusDetails orderStatusDetails = item.getOrderStatusDetails();
						String itemStatus = item.getOrderItemStatus();
						if (LOGGER.isInfoEnabled()) {
							LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
									interfaceId, host + contextPath + "/userOrder/itemStatusChange", "Success",
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
						Context context = new Context();

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
										map.put("on ", format + ", with Customization and with Design Customization.");
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
//										map.put("with Customization", "No");
//										map.put("with DesignCustomization", "No");
//										map.put("Orders Time", format);
										map.put("on ",
												format + ", Customization: No and DesignCustomization: No.");
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
									commonUtility.mailSend(item, entity, orderId, productId, orderItemStatus, map);
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
										context.setVariables(jsonObject2);
										map.put("on  ", format
												+ " with Divatt Cover. For security we recorded the packing video");
										item.setOrderItemStatus(orderItemStatus);
										orderStatusDetails.setPackedDetails(jsonObject2);
										item.setOrderStatusDetails(orderStatusDetails);
										orderSKUDetailsRepo.save(item);
									} else {
										jsonObject2.put("packedCovered", false);
										jsonObject2.put("packingVideo", false);
										jsonObject2.put("orderPackedTime", format);
										context.setVariables(jsonObject2);
										item.setOrderItemStatus(orderItemStatus);
										orderStatusDetails.setPackedDetails(jsonObject2);
										orderSKUDetailsRepo.save(item);
									}
									commonUtility.mailSend(item, entity, orderId, productId, orderItemStatus, map);
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
										context.setVariables(jsonObject1);
										map.put("on  ", format + " by " + courierName
												+ " Courier and the AWB number is :" + awbNumber);
										item.setOrderItemStatus(orderItemStatus);
										orderSKUDetailsRepo.save(item);
									} catch (Exception e) {
										throw new CustomException(
												MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
									}
									commonUtility.mailSend(item, entity, orderId, productId, orderItemStatus, map);
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
										map.put("Delivered Date", format1);
										orderStatusDetails.setDeliveryDetails(jsonObject4);
										item.setOrderItemStatus(orderItemStatus);
										context.setVariables(jsonObject4);
										orderSKUDetailsRepo.save(item);
									} catch (Exception e) {
										throw new CustomException(
												MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
									}
									commonUtility.mailSend(item, entity, orderId, productId, orderItemStatus, map);
								} else
									throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());
							} else
								throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);
						} else if (orderItemStatus.equals("Product received from user")) {
							if (!itemStatus.equals(orderItemStatus)) {
								if (itemStatus.equals("Product shipped by user")) {
									Object string = statusChange.get("designerReceivedProduct");
									DesignerReceivedProductDTO fromJson = objectMapper.convertValue(string,
											DesignerReceivedProductDTO.class);
									try {
										orderStatusDetails.setDesignerReceivedProduct(fromJson);
										item.setOrderItemStatus(orderItemStatus);
										orderSKUDetailsRepo.save(item);
										commonUtility.mailSend(item, entity, orderId, productId, orderItemStatus, map);
									} catch (Exception e) {
										throw new CustomException(
												MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
									}
								} else
									throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());
							} else
								throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);
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
					throw new CustomException(MessageConstant.INVALID_TOKEN.getMessage());
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
	public GlobalResponse itemStatusChangefromAdmin(String token, String orderId, String productId, String size,
			org.json.simple.JSONObject statusChange, String orderItemStatus) {

		try {
			String extractUsername = jwtconfig.extractUsername(token.substring(7));
			Map<String, Object> map = new HashMap<>();
			try {
				
				ResponseEntity<List<org.json.simple.JSONObject>> responseData = restTemplate.exchange(
						ADMIN_SERVICE + RestTemplateConstants.ADMIN_ROLE_NAME
								+ MessageConstant.ADMIN_ROLES.getMessage(),
						HttpMethod.GET, null, new ParameterizedTypeReference<List<org.json.simple.JSONObject>>() {
						});
				List<org.json.simple.JSONObject> loginData = responseData.getBody();
//				String email2 = loginData.get(0).get("email").toString();
				
//				ResponseEntity<org.json.simple.JSONObject> forEntity = restTemplate.getForEntity(ADMIN_SERVICE
//						+ RestTemplateConstants.ADMIN_ROLE_NAME + MessageConstant.ADMIN_ROLES.getMessage(),
//						org.json.simple.JSONObject.class);
				String adminEmail = null;
				if (loginData.size() >0) {
					adminEmail = loginData.get(0).get("email").toString();
				}
				if (!extractUsername.equals(adminEmail)) {
					throw new CustomException(MessageConstant.UNAUTHORIZED.getMessage());
				}
			} catch (Exception e) {
				throw new CustomException(e.getLocalizedMessage());
			}

			OrderSKUDetailsEntity item = orderSKUDetailsRepo
					.findByProductIdAndOrderIdAndSize(Integer.parseInt(productId), orderId, size).get(0);
			OrderStatusDetails orderStatusDetails = item.getOrderStatusDetails();
			String itemStatus = item.getOrderItemStatus();
			int designerId = item.getDesignerId();

			DesignerProfileEntity forEntity = restTemplate
					.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_BYID + designerId,
							DesignerProfileEntity.class)
					.getBody();

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
								map.put("packedCovered", fromJson.get("packedCovered"));
								map.put("packingVideo", fromJson.get("packingVideo"));
								map.put("orderPackedTime", format);
								item.setOrderItemStatus(orderItemStatus);
								orderStatusDetails.setPackedDetails(jsonObject2);
								item.setOrderStatusDetails(orderStatusDetails);
								orderSKUDetailsRepo.save(item);
							} else {
								jsonObject2.put("packedCovered", false);
								jsonObject2.put("packingVideo", false);
								jsonObject2.put("orderPackedTime", format);
								map.put("packedCovered", false);
								map.put("packingVideo", false);
								map.put("orderPackedTime", format);
								item.setOrderItemStatus(orderItemStatus);
								orderStatusDetails.setPackedDetails(jsonObject2);
								orderSKUDetailsRepo.save(item);
							}
							commonUtility.mailSend(item, forEntity, orderId, productId, orderItemStatus, map);
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
								map.put("courierName", courierName);
								map.put("awbNumber", awbNumber);
								map.put("orderShippedTime", format);
								orderStatusDetails.setShippedDetails(jsonObject1);
								item.setOrderItemStatus(orderItemStatus);
								orderSKUDetailsRepo.save(item);
								commonUtility.mailSend(item, forEntity, orderId, productId, orderItemStatus, map);
							} catch (Exception e) {
								throw new CustomException(MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
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
								map.put("deliveredDate", format1);
								orderStatusDetails.setDeliveryDetails(jsonObject4);
								item.setOrderItemStatus(orderItemStatus);
								orderSKUDetailsRepo.save(item);
								commonUtility.mailSend(item, forEntity, orderId, productId, orderItemStatus, map);
							} catch (Exception e) {
								throw new CustomException(MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
							}
						} else
							throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());

					} else
						throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);
				} else if (orderItemStatus.equals("ForceReturnAdmin")) {
					if (itemStatus.equals("Delivered") && orderItemStatus.equals("ForceReturnAdmin")) {
						Object string = statusChange.get("forceReturnOnDTO");
						ForceReturnOnDTO fromJson = objectMapper.convertValue(string, ForceReturnOnDTO.class);
						try {
							orderStatusDetails.setForceReturnOnDTO(fromJson);
							item.setOrderStatusDetails(orderStatusDetails);
							item.setReturnAcceptable(true);
							orderSKUDetailsRepo.save(item);
							commonUtility.mailSend(item, forEntity, orderId, productId, orderItemStatus, map);
						} catch (Exception e) {
							throw new CustomException(MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
						}
					} else
						throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());
				} else if (orderItemStatus.equals("Return request approved")) {
					if (!itemStatus.equals(orderItemStatus)) {
						if (itemStatus.equals("Delivered") || itemStatus.equals("returnRequest")) {
							Object string = statusChange.get("ReturnRequestApproveDTO");
							ReturnRequestApproveDTO fromJson = objectMapper.convertValue(string,
									ReturnRequestApproveDTO.class);
							try {
								orderStatusDetails.setReturnRequestApprove(fromJson);
								item.setOrderItemStatus(orderItemStatus);
								orderSKUDetailsRepo.save(item);
								commonUtility.mailSend(item, forEntity, orderId, productId, orderItemStatus, map);
							} catch (Exception e) {
								throw new CustomException(MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
							}
						} else
							throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());

					} else
						throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);
				} else if (orderItemStatus.equals("Return rejected by admin")) {
					if (!itemStatus.equals(orderItemStatus)) {
						if (itemStatus.equals("returnRequest")) {
							Object string = statusChange.get("returnRejectedByAdmin");
							ReturnRejectedByAdminDTO fromJson = objectMapper.convertValue(string,
									ReturnRejectedByAdminDTO.class);
							try {
								orderStatusDetails.setReturnRejectedByAdmin(fromJson);
								item.setOrderItemStatus(orderItemStatus);
								orderSKUDetailsRepo.save(item);
								commonUtility.mailSend(item, forEntity, orderId, productId, orderItemStatus, map);
							} catch (Exception e) {
								throw new CustomException(MessageConstant.PLEASE_FILL_UP_REQUIRED_FIELDS.getMessage());
							}
						} else
							throw new CustomException(MessageConstant.YOU_CANNOT_SKIP_STATUS.getMessage());

					} else
						throw new CustomException(MessageConstant.PRODUCT_STATUS.getMessage() + itemStatus);
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
	public GlobalResponse adminCancelation(String orderId, String productId, String size, String token,
			CancelationRequestDTO cancelationRequestDTO) {
		try {
			OrderSKUDetailsEntity findByProductIdAndOrderId = this.orderSKUDetailsRepo
					.findByProductIdAndOrderIdAndSize(Integer.parseInt(productId), orderId, size).get(0);
			String orderItemStatus = findByProductIdAndOrderId.getOrderItemStatus();
			if (!orderItemStatus.equals("Delivered")) {
				SimpleDateFormat dateFormate = new SimpleDateFormat(MessageConstant.DATE_TIME_FORMAT_TYPE.getMessage());
				Date dates = new Date();
				String dateTimeformate = dateFormate.format(dates);

				if (!orderItemStatus.equals("New")) {

					org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
					jsonObject.put("cancelComment", cancelationRequestDTO.getComment());
					jsonObject.put("cancelationTime", dateTimeformate);
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
					jsonObject.put("cancelationTime", dateTimeformate);
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

	@Override
	public Map<String, Object> getOrdersItemstatus(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy, String token, String orderItemStatus) {
		LOGGER.info("Inside - OrderAndPaymentService.getOrdersItemstatus()");
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
//				findAll = orderSKUDetailsRepo.findOrderStatusAndSize(orderItemStatus,orderSKUDetailsRepo.s, pagingSort);

			} else if (keyword.isEmpty()) {
				findAll = orderSKUDetailsRepo.findAll(pagingSort);
			} else {
				findAll = orderSKUDetailsRepo.Searching(keyword, pagingSort);
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

				List<OrderInvoiceEntity> invoiceData = this.orderInvoiceRepo.findByOrder(e.getOrderId());
				ResponseEntity<DesignerProfileEntity> forEntity = restTemplate.getForEntity(
						DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_BYID + e.getDesignerId(),
						DesignerProfileEntity.class);
				String designerName = forEntity.getBody().getDesignerName();
				Optional<OrderPaymentEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByOrderId(e.getOrderId());

				String writeValueAsString = null;
				JSONObject payRow = null;
				if (OrderPaymentRow.isPresent()) {
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
			response.put("rejected", orderSKUDetailsRepo.findByOrderItemStatus("Rejected").size());
			response.put("returnRequestApproved",
					orderSKUDetailsRepo.findByOrderItemStatus("Return request approved").size());
			response.put("productShippedByUser",
					orderSKUDetailsRepo.findByOrderItemStatus("Product shipped by user").size());
			response.put("productReceivedFromUser",
					orderSKUDetailsRepo.findByOrderItemStatus("Product received from user").size());
			response.put("returnRejectedByAdmin",
					orderSKUDetailsRepo.findByOrderItemStatus("Return rejected by admin").size());
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
			StringBuilder invoiceData = new StringBuilder();
			for (String key : keyList) {

				List<InvoiceUpdatedModel> invoiceUpdatedModels = responceData.get(key);
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
				String digitalSignature=null;
				String taxableValue = null;

				invoiceUpdatedModels.stream().forEach(entity -> {

					if (entity.getTotal().equals("0")) {
						if (entity.getDiscount().equals("0")) {
							entity.setTotal(entity.getMrp());
						}
					}

				});
				for (InvoiceUpdatedModel element : invoiceUpdatedModels) {
					String taxAmount = element.getTaxAmount();
					String total2 = element.getTotal();

					String taxValue = (Integer.parseInt(total2) - Integer.parseInt(taxAmount)) + "";
					element.setTaxableValue(taxValue);
					element.setIgst(element.getIgst() == null ? "0" : element.getIgst());
					tCgst = tCgst + Double.parseDouble(element.getCgst() == null ? "0" : element.getCgst());
					tSgst = tSgst + Double.parseDouble(element.getSgst() == null ? "0" : element.getSgst());
					tDis = tDis + Double.parseDouble(
							Optional.ofNullable(element.getDiscount()).isPresent() ? "0" : element.getDiscount());
					tQty = tQty + Integer.parseInt(element.getQty() == null ? "0" : element.getQty());
					tIgst = tIgst + Double.parseDouble(element.getIgst() == null ? "0" : element.getIgst());
					tGross = tGross
							+ Double.parseDouble(element.getGrossAmount() == null ? "0" : element.getGrossAmount());
					tTotal = tTotal + Double.parseDouble(element.getTotal() == null ? "0" : element.getTotal());
					tMrp = tMrp + Double.parseDouble(element.getMrp() == null ? "0" : element.getMrp());
					tTaxableValue = tTaxableValue
							+ Double.parseDouble(element.getTaxableValue() == null ? "0" : element.getTaxableValue());
					totalCgst = String.valueOf(tCgst);
					totalSgst = String.valueOf(tSgst);
					totalDiscount = String.valueOf(tDis);
					totalQunatuty = String.valueOf(tQty);
					totalIgst = String.valueOf(tIgst);
					totalGross = String.valueOf(tGross);
					total = String.valueOf(tTotal);
					taxableValue = String.valueOf(tTaxableValue);
					String invoiceId = element.getInvoiceId();
					String modifiedInvoiceId = invoiceId.substring(10, invoiceId.length());

					List<OrderInvoiceEntity> findByInvoiceId = this.orderInvoiceRepo.findByInvoiceId(modifiedInvoiceId);

					for (OrderInvoiceEntity e : findByInvoiceId) {
						DesignerProfileEntity forEntity = restTemplate
								.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_BYID
										+ e.getProductDetails().getDesignerId(), DesignerProfileEntity.class)
								.getBody();
						displayName = forEntity.getDesignerProfile().getDisplayName();
						designerName = forEntity.getDesignerName();
						digitalSignature= forEntity.getDesignerProfile().getDigitalSignature();
						
						
					}
				}

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
				data.put("digitalSignature", digitalSignature);
				data.put("totalTaxableValue", taxableValue);
				Context context = new Context();
				context.setVariables(data);
				String htmlContent = templateEngine.process("new_invoice_User_test1.html", context);
				invoiceData.append(htmlContent);
			}

			ByteArrayOutputStream target = new ByteArrayOutputStream();
			ConverterProperties converterProperties = new ConverterProperties();
			HtmlConverter.convertToPdf(invoiceData.toString(), target, converterProperties);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=" + orderId + ".pdf");
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

	@Override
	public ResponseEntity<?> getTransactionsService(int page, int limit, String sort, String sortName, String keyword,
			String paymentStatus, Optional<String> sortBy) {

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

			if (!keyword.isEmpty()) {
				findAll = userOrderPaymentRepo.Search(keyword, pagingSort);
			} else if (!paymentStatus.isEmpty()) {
				findAll = userOrderPaymentRepo.findByPaymentStatus(paymentStatus, pagingSort);
			} else {
				findAll = userOrderPaymentRepo.findAll(pagingSort);
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

	@Override
	public ResponseEntity<?> updateDialogBox(String token, String orderId, Integer productId, String size,
			OrderSKUDetailsEntity skuDetailsEntity) {
		try {
			List<OrderSKUDetailsEntity> orderSKUDetailsEntity = this.orderSKUDetailsRepo
					.findByProductIdAndOrderIdAndSize(productId, orderId, size);

			if (orderSKUDetailsEntity.size() <= 0) {
				throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
			}
			if (orderSKUDetailsEntity.size() > 0) {
				if (orderSKUDetailsEntity.get(0).getOrderItemStatus().equals("New")) {
					List<DialogBoxDTO> dialogbox = skuDetailsEntity.getDialogbox();

					if (orderSKUDetailsEntity.size() > 0) {
						List<DialogBoxDTO> list = orderSKUDetailsEntity.get(0).getDialogbox();
						list.addAll(dialogbox);
						OrderSKUDetailsEntity detailsEntity = orderSKUDetailsEntity.get(0);
						detailsEntity.setDialogbox(list);
						orderSKUDetailsRepo.save(detailsEntity);
					} else {
						orderSKUDetailsEntity.get(0).setDialogbox(skuDetailsEntity.getDialogbox());
						orderSKUDetailsRepo.save(orderSKUDetailsEntity.get(0));
					}
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("reason", MessageConstant.SUCCESS.getMessage());
			map.put("message", MessageConstant.CHANGES_ADDED.getMessage());
			map.put("status", 200);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}