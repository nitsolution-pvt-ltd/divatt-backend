package com.divatt.user.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.divatt.user.constant.MessageConstant;
import com.divatt.user.dto.CancelationRequestApproveAndRejectDTO;
import com.divatt.user.dto.CancelationRequestDTO;
import com.divatt.user.entity.OrderAndPaymentGlobalEntity;
import com.divatt.user.entity.OrderInvoiceEntity;
import com.divatt.user.entity.OrderTrackingEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderPaymentEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.helper.JwtUtil;
import com.divatt.user.helper.ListResponseDTO;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.OrderAndPaymentService;
import com.divatt.user.services.SequenceGenerator;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.razorpay.RazorpayException;

@Validated
@RestController
@RequestMapping("/userOrder")
public class OrderAndPaymentContoller {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	@Autowired
	private OrderAndPaymentService orderAndPaymentService;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private UserLoginRepo userLoginRepo;

	@Autowired
	private MongoOperations mongoOperations;

	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;

	@Value("${interfaceId}")
	private String interfaceId;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAndPaymentContoller.class);

	@PostMapping("/razorpay/create")
	public ResponseEntity<?> postRazorpayOrderCreate(@RequestHeader("Authorization") String token,
			@Valid @RequestBody OrderDetailsEntity orderDetailsEntity) {
		LOGGER.info("Inside - OrderAndPaymentContoller.postRazorpayOrderCreate()");

		try {
			String extractUsername = jwtUtil.extractUsername(token.substring(7));
			if (!userLoginRepo.findByEmail(extractUsername).isPresent()) {
				return new ResponseEntity<>(MessageConstant.UNAUTHORIZED.getMessage(), HttpStatus.UNAUTHORIZED);
			}
			return orderAndPaymentService.postRazorpayOrderCreateService(orderDetailsEntity);
		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/razorpay/create", ex.getResponseBodyAsString(), ex.getStatusCode());
			}
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/razorpay/create", e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/payment/add")
	public ResponseEntity<?> postOrderPaymentDetails(@RequestHeader("Authorization") String token,
			@RequestBody OrderPaymentEntity orderPaymentEntity) {
		LOGGER.info("Inside - OrderAndPaymentContoller.postOrderPaymentDetails()");

		try {
			String extractUsername = jwtUtil.extractUsername(token.substring(7));
			if (!userLoginRepo.findByEmail(extractUsername).isPresent()) {
				return new ResponseEntity<>(MessageConstant.UNAUTHORIZED.getMessage(), HttpStatus.UNAUTHORIZED);
			}
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payment/add", "Success", HttpStatus.OK);
			}
			return orderAndPaymentService.postOrderPaymentService(orderPaymentEntity);

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

	@PostMapping("/orderSKUDetails/add")
	public void postOrderSKUDetails(@RequestHeader("Authorization") String token,
			@Valid @RequestBody OrderSKUDetailsEntity orderSKUDetailsEntity) {
		LOGGER.info("Inside - OrderAndPaymentContoller.postOrderSKUDetails()");

		try {
			String extractUsername = jwtUtil.extractUsername(token.substring(7));
			if (userLoginRepo.findByEmail(extractUsername).isPresent()) {
				orderAndPaymentService.postOrderSKUService(orderSKUDetailsEntity);
			}
		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/orderSKUDetails/add", ex.getResponseBodyAsString(), ex.getStatusCode());
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/orderSKUDetails/add", e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/razorpay/handle")
	public ResponseEntity<?> postOrderHandle(@RequestBody org.json.simple.JSONObject orderHandleDetails) {
		LOGGER.info("Inside - OrderAndPaymentContoller.postOrderHandle()");

		try {

			org.json.simple.JSONObject paymentE = new org.json.simple.JSONObject(
					(Map) orderHandleDetails.get("payload"));
			org.json.simple.JSONObject PayEntity = new org.json.simple.JSONObject((Map) paymentE.get("payment"));

			return orderAndPaymentService.postOrderHandleDetailsService(PayEntity);
		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/razorpay/handle", ex.getResponseBodyAsString(), ex.getStatusCode());
			}
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/razorpay/handle", e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = { "/payment/list" }, method = RequestMethod.GET)
	public Map<String, Object> getOrderPaymentDetails(@RequestHeader("Authorization") String token,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "") String keyword, @RequestParam Optional<String> sortBy)
			throws RazorpayException {
		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderPaymentDetails()");
		try {
			return orderAndPaymentService.getOrderPaymentService(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payment/list", e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/add")
	public ResponseEntity<?> addOrder(@RequestHeader("Authorization") String token,
			@RequestBody OrderAndPaymentGlobalEntity orderAndPaymentGlobalEntity) {
		LOGGER.info("Inside - OrderAndPaymentContoller.addOrder()");

		try {
			Map<String, Object> map = new HashMap<>();
			String extractUsername = jwtUtil.extractUsername(token.substring(7));

			if (userLoginRepo.findByEmail(extractUsername).isPresent()) {

				OrderDetailsEntity orderDetailsEntity = orderAndPaymentGlobalEntity.getOrderDetailsEntity();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

				Date date = new Date();
				String format = formatter.format(date);
				String formatDate = formatter.format(date);

				orderAndPaymentGlobalEntity.getOrderDetailsEntity()
						.setId(sequenceGenerator.getNextSequence(OrderDetailsEntity.SEQUENCE_NAME));
				orderAndPaymentGlobalEntity.getOrderDetailsEntity().setOrderId("OR" + System.currentTimeMillis());
				orderAndPaymentGlobalEntity.getOrderDetailsEntity().setOrderDate(formatDate);
				orderAndPaymentGlobalEntity.getOrderDetailsEntity().setCreatedOn(format);
				orderDetailsEntity.setNetPrice(orderAndPaymentGlobalEntity.getOrderDetailsEntity().getNetPrice());
				LOGGER.info("Store data into tbl_order_details");
				OrderDetailsEntity OrderData = orderDetailsRepo
						.save(orderAndPaymentGlobalEntity.getOrderDetailsEntity());

				List<OrderSKUDetailsEntity> orderSKUDetailsEntity = orderAndPaymentGlobalEntity
						.getOrderSKUDetailsEntity();

				for (OrderSKUDetailsEntity orderSKUDetailsEntityRow : orderSKUDetailsEntity) {

					orderSKUDetailsEntityRow
							.setId(sequenceGenerator.getNextSequence(OrderSKUDetailsEntity.SEQUENCE_NAME));
					orderSKUDetailsEntityRow.setOrderId(OrderData.getOrderId());
					orderSKUDetailsEntityRow.setCreatedOn(format);
					this.postOrderSKUDetails(token, orderSKUDetailsEntityRow);
				}
				map.put("orderId", OrderData.getOrderId());
				map.put("status", 200);
				map.put("message", MessageConstant.ORDER_PLACED.getMessage());
			}
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Order Status", "Order placed successfully");
			}
			return ResponseEntity.ok(map);

		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/add", ex.getResponseBodyAsString(), ex.getStatusCode());
			}
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/add", e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getOrderDetails(@RequestHeader("Authorization") String token,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "") String keyword, @RequestParam Optional<String> sortBy,
			@RequestParam(defaultValue = "All") String orderStatus) {
		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderDetails()For Admin side listing");

		try {
			return orderAndPaymentService.getOrders(page, limit, sort, sortName, keyword, sortBy, token, orderStatus);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/list", e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/getOrder/{orderId}")
	public ResponseEntity<?> getOrdersDetails(@PathVariable() String orderId,
			@RequestParam(defaultValue = "0") Integer productId, @RequestParam(defaultValue = "") String size) {

		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderDetails()");

		try {
			return orderAndPaymentService.getOrdersDetailsService(orderId, productId, size);
		} catch (HttpStatusCodeException ex) {
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("getUserOrder/{userId}")
	public Map<String, Object> getOrderDetailsByuserId(@RequestHeader(name ="Authorization", defaultValue = "Bearer ") String token,
			@PathVariable() Integer userId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {

		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderDetailsByuserId()");

		try {
			String extractUsername = jwtUtil.extractUsername(token.substring(7));
			if (!userLoginRepo.findByEmail(extractUsername).isPresent())
				throw new CustomException(MessageConstant.UNAUTHORIZED.getMessage());
			return orderAndPaymentService.getUserOrderDetailsService(userId, page, limit, sort, sortName, keyword,
					sortBy, token);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/getUserOrder/" + userId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/updateOrder/{orderId}")
	public GlobalResponse updateOrder(@RequestBody OrderSKUDetailsEntity orderSKUDetailsEntity,
			@PathVariable String orderId) {
		try {
			return this.orderAndPaymentService.orderUpdateService(orderSKUDetailsEntity, orderId);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/updateOrder/" + orderId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(e.getMessage());
		}
	}

	@RequestMapping(value = { "/list/{designerId}" }, method = RequestMethod.GET)
	public Map<String, Object> getOrderByDesigner(@RequestHeader("Authorization") String token,
			@PathVariable int designerId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "id") String sortName, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy, @RequestParam(defaultValue = "") String orderItemStatus,
			@RequestParam(defaultValue = "") String sortDateType, @RequestParam(defaultValue = "") String startDate,
			@RequestParam(defaultValue = "") String endDate) {
		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderByDesigner() for Designer side listing");

		try {
			return orderAndPaymentService.getDesigerOrders(designerId, page, limit, sort, sortName, keyword, sortBy,
					orderItemStatus, sortDateType, startDate, endDate);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/list/" + designerId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/genpdf/order")
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
			System.out.println(MessageConstant.PDF_GENERATED.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos;
	}

	@GetMapping("/orderProductDetails/{orderId}")
	public Map<String, Object> getOrderproductDetails(@RequestHeader("Authorization") String token,
			@PathVariable String orderId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			return orderAndPaymentService.getProductDetails(orderId, page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/orderProductDetails/" + orderId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/invoice/{orderId1}")
	File invGenarator(@PathVariable String orderId1) throws IOException {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(orderId1));
		OrderDetailsEntity orderDetailsEntity = mongoOperations.findOne(query, OrderDetailsEntity.class);
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

	@GetMapping(value = "/api/files/system/{filename}", produces = "text/csv; charset=utf-8")
	@ResponseStatus(HttpStatus.OK)
	public Resource getFileFromFileSystem(@PathVariable String filename, HttpServletResponse response) {
		return orderAndPaymentService.getFileSystem(filename, response);
	}

	@GetMapping(value = "/api/files/classpath/{filename}", produces = "text/csv; charset=utf-8")
	@ResponseStatus(HttpStatus.OK)
	public Resource getFileFromClasspath(@PathVariable String filename, HttpServletResponse response) {
		return orderAndPaymentService.getClassPathFile(filename, response);
	}

	@PostMapping("/track/add")
	public ResponseEntity<?> postOrderTracking(@Valid @RequestBody OrderTrackingEntity orderTrackingEntity) {
		LOGGER.info("Inside - OrderAndPaymentContoller.postOrderTracking()");

		try {
			return orderAndPaymentService.postOrderTrackingService(orderTrackingEntity);
		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/track/add", ex.getResponseBodyAsString(), ex.getStatusCode());
			}
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/track/add", e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/track/update/{trackingId}")
	public ResponseEntity<?> putOrderTracking(@Valid @RequestBody OrderTrackingEntity orderTrackingEntity,
			@PathVariable() String trackingId) {
		LOGGER.info("Inside - OrderAndPaymentContoller.putOrderTracking()");

		try {
			return orderAndPaymentService.putOrderTrackingService(orderTrackingEntity, trackingId);
		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/track/update/" + trackingId, ex.getResponseBodyAsString(),
						ex.getStatusCode());
			}
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/track/update/" + trackingId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/getTracking/{orderId}")
	public ResponseEntity<?> getOrderTrackingDetails(@PathVariable() String orderId,
			@RequestParam(defaultValue = "0") int userId, @RequestParam(defaultValue = "0") int designerId) {

		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderTrackingDetails()");

		try {
			return orderAndPaymentService.getOrderTrackingDetailsService(orderId, userId, designerId);
		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/track/getTracking/" + orderId, ex.getResponseBodyAsString(),
						ex.getStatusCode());
			}
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/track/getTracking/" + orderId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/orderStatusUpdate/{orderId}/{productId}")
	public GlobalResponse orderStatusUpdate(@RequestBody OrderSKUDetailsEntity orderSKUDetailsEntity,
			@PathVariable String orderId, @PathVariable Integer productId) {
		try {
			return orderAndPaymentService.orderStatusUpdateService(orderSKUDetailsEntity, orderId, productId);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/orderStatusUpdate/" + orderId + "/" + productId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getOrderByInvoiceId/{invoiceId}")
	public ResponseEntity<?> getOrderByInvoiceId(@PathVariable String invoiceId) {
		try {
			return orderAndPaymentService.getOrderServiceByInvoiceId(invoiceId);
		} catch (HttpStatusCodeException ex) {
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getPdfByOrderId/{id}")
	public ResponseEntity<byte[]> getPdfByOrderId(@PathVariable String id) throws IOException {

		List<OrderDetailsEntity> findByOrderId = orderDetailsRepo.findByOrderId(id);

		OrderDetailsEntity orderDetailsEntity = findByOrderId.get(0);
		File createPdfSupplier = createPdfSupplier(orderDetailsEntity);

		FileInputStream fl = new FileInputStream(createPdfSupplier);
		byte[] arr = new byte[(int) createPdfSupplier.length()];
		fl.read(arr);
		fl.close();

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		// String filename = "Order.pdf";

		headers.add("content-disposition", "inline;filename=" + id + ".pdf");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(arr, headers, HttpStatus.OK);
		return response;
	}

	@GetMapping("/exelSheet")
	public ResponseEntity<InputStreamSource> exelGen(@RequestParam String startDate, @RequestParam String endDate) {
		try {
			int flag = 0;
			List<OrderDetailsEntity> sortingList = new ArrayList<OrderDetailsEntity>();
			List<OrderDetailsEntity> orderDetailsList = orderDetailsRepo.findAll();
			for (OrderDetailsEntity data : orderDetailsList) {
				if (data.getOrderDate().equals(startDate)) {
					flag++;
				}
				if (data.getOrderDate().equals(endDate)) {
					flag = 0;
				}
				if (flag != 0) {
					sortingList.add(data);
				}
			}
			String[] colum = { "InvoiceId", "DelivaryDate", "OrderDate", "OrderId", "OrderStatus", "DelivaryStatus",
					"TotalAmount", "MRP Value", "Total Taxamount" };
			try (Workbook workbook = new XSSFWorkbook();
					ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();) {
				Sheet sheet = workbook.createSheet("SampleData");
				Font createFont = workbook.createFont();
				createFont.setBold(true);
				createFont.setColor(IndexedColors.BLUE.getIndex());

				CellStyle headerFont = workbook.createCellStyle();
				headerFont.setFont(createFont);
				Row headerRow = sheet.createRow(0);
				for (int col = 0; col < colum.length; col++) {
					Cell cell = headerRow.createCell(col);
					cell.setCellValue(colum[col]);
					cell.setCellStyle(headerFont);
				}
				int rowIndex = 1;
				List<OrderDetailsEntity> orderList = sortingList;
				for (OrderDetailsEntity detailsEntity : orderList) {
					Row row = sheet.createRow(rowIndex++);
					row.createCell(0).setCellValue(detailsEntity.getOrderId());
					row.createCell(1).setCellValue(detailsEntity.getDeliveryDate());
					row.createCell(2).setCellValue(detailsEntity.getOrderDate());
					row.createCell(3).setCellValue(detailsEntity.getOrderId());
					row.createCell(4).setCellValue(detailsEntity.getOrderStatus());
					row.createCell(5).setCellValue(detailsEntity.getDeliveryStatus());
					row.createCell(6).setCellValue(detailsEntity.getTotalAmount());
					row.createCell(7).setCellValue(detailsEntity.getMrp());
					row.createCell(8).setCellValue(detailsEntity.getTaxAmount());
				}
				workbook.write(arrayOutputStream);
				ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition", "attachment; filename=data.xlsx");
				return ResponseEntity.ok().headers(headers).body(new InputStreamResource(arrayInputStream));
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/invoices/add")
	public ResponseEntity<?> postOrderInvoiceDetails(@Valid @RequestBody OrderInvoiceEntity orderInvoiceEntity,
			@RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - OrderAndPaymentContoller.postOrderInvoiceDetails()");

		try {
			return orderAndPaymentService.postOrderInvoiceService(orderInvoiceEntity);

		} catch (HttpStatusCodeException ex) {
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/invoices/update/{invoiceId}")
	public ResponseEntity<?> putOrderInvoiceDetails(@Valid @RequestBody OrderInvoiceEntity orderInvoiceEntity,
			@PathVariable String invoiceId, @RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - OrderAndPaymentContoller.putOrderInvoiceDetails()");

		try {
			return orderAndPaymentService.putOrderInvoiceService(invoiceId, orderInvoiceEntity);
		} catch (HttpStatusCodeException ex) {
			return new ResponseEntity<>(ex.getResponseBodyAsByteArray(), ex.getStatusCode());
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/invoices/list")
	public Map<String, Object> getOrderInvoiceDetails(@RequestHeader("Authorization") String token,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "_id") String sortName,
			@RequestParam(defaultValue = "") String keyword, @RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderInvoiceDetails()");
		try {
			return orderAndPaymentService.getOrderInvoiceService(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/designerOrderCount/{designerId}")
	public Map<String, Integer> getDesignerOrderCount(@PathVariable int designerId,
			@RequestParam(defaultValue = "false") Boolean adminStatus) {
		try {
			return orderAndPaymentService.getOrderCount(designerId, adminStatus);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/orderDetails")
	public OrderSKUDetailsEntity getOrderDetailsUpdated(@RequestParam String orderId, @RequestParam String productId) {
		try {
			return orderAndPaymentService.getOrderDetailsService(orderId, productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/orderDetails/{orderId}")
	public OrderSKUDetailsEntity getOrderdetails(@PathVariable String orderId) {
		try {
			return this.orderAndPaymentService.getorderDetails(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/getOrderList")
	public List<OrderDetailsEntity> getOrderListAPI(@RequestBody ListResponseDTO jsonObject) {
		try {
			return this.orderAndPaymentService.getOrderListService(jsonObject);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/designerSideOrderList")
	public Object designerSideOrderList(@RequestHeader("Authorization") String token,
			@RequestParam(defaultValue = "") String orderStatus) {
		try {
			LOGGER.info(orderStatus);
			return this.orderAndPaymentService.getDesignerSideOrderListService(token, orderStatus);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/cancelOrder")
	public GlobalResponse cancellOrder(@RequestParam String orderId, @RequestParam String productId,
			@RequestHeader("Authorization") String token, @RequestBody CancelationRequestDTO cancelationRequestDTO,
			@RequestParam String size) {
		try {
			return orderAndPaymentService.cancelOrderService(orderId, productId, token, cancelationRequestDTO, size);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/adminApprovalCancelation")
	public GlobalResponse cancelationApproval(@RequestParam String designerId, @RequestParam String orderId,
			@RequestParam String productId,
			@RequestBody CancelationRequestApproveAndRejectDTO cancelationRequestApproveAndRejectDTO,
			@RequestParam String size) {

		try {
			return orderAndPaymentService.cancelApproval(designerId, orderId, productId,
					cancelationRequestApproveAndRejectDTO, size);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/itemStatusChange")
	public GlobalResponse itemStatusChange(@RequestHeader("Authorization") String token, @RequestParam String orderId,
			@RequestParam String productId, @RequestBody JSONObject statusChange, @RequestParam String orderItemStatus,
			@RequestParam String size) {
		try {
			return orderAndPaymentService.itemStatusChange(token, orderId, productId, statusChange, orderItemStatus,
					size);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/itemStatusChangefromAdmin")
	public GlobalResponse itemStatusChangefromAdmin(@RequestHeader("Authorization") String token,
			@RequestParam String orderId, @RequestParam String productId, @RequestParam String size, @RequestBody JSONObject statusChange,
			@RequestParam String orderItemStatus) {
		try {
			return orderAndPaymentService.itemStatusChangefromAdmin(token, orderId, productId, size, statusChange,
					orderItemStatus);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/adminCancelation")
	public GlobalResponse adminCancelation(@RequestParam String orderId, @RequestParam String productId, @RequestParam String size,
			@RequestHeader("Authorization") String token, @RequestBody CancelationRequestDTO cancelationRequestDTO) {
		try {
			return this.orderAndPaymentService.adminCancelation(orderId, productId, size, token, cancelationRequestDTO);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/skuList")
	public Map<String, Object> skuList(@RequestHeader("Authorization") String token,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "") String keyword, @RequestParam Optional<String> sortBy,
			@RequestParam(defaultValue = "All") String orderItemStatus) {
		LOGGER.info("Inside - OrderAndPaymentContoller.skuList() For Admin side listing");

		try {
			return orderAndPaymentService.getOrdersItemstatus(page, limit, sort, sortName, keyword, sortBy, token,
					orderItemStatus);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/getInvoiceByOrderId/{orderId}")
	public Optional<OrderInvoiceEntity> getByOrderId(@PathVariable String orderId) {
		try {
			return this.orderAndPaymentService.getInvoiceByOrderId(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getOrderSummary/{orderId}")
	public ResponseEntity<byte[]> getOrderSummary(@PathVariable String orderId) {
		try {
			return this.orderAndPaymentService.getOrderSummary(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/transactions")
	public ResponseEntity<?> getTransactions(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName, @RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") String paymentStatus, @RequestParam Optional<String> sortBy) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - OrderAndPaymentContoller.getTransactions()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - OrderAndPaymentContoller.getTransactions()");
		}

		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/userOrder/transactions", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/userOrder/transactions", "Success", HttpStatus.OK);
			}
			return orderAndPaymentService.getTransactionsService(page, limit, sort, sortName, keyword, paymentStatus,
					sortBy);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/userOrder/transactions", e.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/updateDialogBox")
	public ResponseEntity<?> updateDialogBox(@RequestHeader("Authorization") String token,
			@RequestParam(defaultValue = "") String orderId, @RequestParam(defaultValue = "") Integer productId,
			@RequestParam(defaultValue = "") String size, @RequestBody OrderSKUDetailsEntity skuDetailsEntity) {
		try {
			return this.orderAndPaymentService.updateDialogBox(token, orderId, productId, size, skuDetailsEntity);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}