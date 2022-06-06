package com.divatt.user.controller;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.user.entity.OrederAndPaymentGlobalEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.helper.JwtUtil;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.UserAddressRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.OrderAndPaymentService;
import com.divatt.user.services.SequenceGenerator;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import springfox.documentation.spring.web.json.Json;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;






@RestController
@RequestMapping("/userOrder")
public class OrderAndPaymentContoller {
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	private JwtUtil JwtUtil;

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	@Autowired
	private OrderAndPaymentService orderAndPaymentService;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private UserLoginRepo userLoginRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAndPaymentContoller.class);

	@PostMapping("/razorpay/create")
	public ResponseEntity<?> postRazorpayOrderCreate(@RequestHeader("Authorization") String token,
			@Valid @RequestBody OrderDetailsEntity orderDetailsEntity) {
		LOGGER.info("Inside - OrderAndPaymentContoller.postRazorpayOrderCreate()");

		try {
				String extractUsername = null;
				try {
					extractUsername = JwtUtil.extractUsername(token.substring(7));
				} catch (Exception e) {
					throw new CustomException("Unauthorized");
				}

			if (!userLoginRepo.findByEmail(extractUsername).isPresent()) {
				throw new CustomException("Unauthorized");
			}
			return this.orderAndPaymentService.postRazorpayOrderCreateService(orderDetailsEntity);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/payment/add")
	public void postOrderPaymentDetails(@RequestHeader("Authorization") String token,
			@Valid @RequestBody OrderPaymentEntity orderPaymentEntity) {
		LOGGER.info("Inside - OrderAndPaymentContoller.postOrderPaymentDetails()");

		try {
			String extractUsername = null;
			try {
				extractUsername = JwtUtil.extractUsername(token.substring(7));
			} catch (Exception e) {
				throw new CustomException("Unauthorized");
			}

			if (userLoginRepo.findByEmail(extractUsername).isPresent()) {
				this.orderAndPaymentService.postOrderPaymentService(orderPaymentEntity);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = { "/payment/list" }, method = RequestMethod.GET)
	public Map<String, Object> getOrderPaymentDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderPaymentDetails()");

		try {
			return this.orderAndPaymentService.getOrderPaymentService(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/add")
	public ResponseEntity<?> addOrder(@RequestHeader("Authorization") String token,
			@RequestBody OrederAndPaymentGlobalEntity orderAndPaymentGlobalEntity) {
		LOGGER.info("Inside - OrderAndPaymentContoller.addOrder()");

		try {
			Map<String, Object> map = new HashMap<>();
			String extractUsername = null;
			try {
				extractUsername = JwtUtil.extractUsername(token.substring(7));
			} catch (Exception e) {
				throw new CustomException("Unauthorized");
			}

			if (userLoginRepo.findByEmail(extractUsername).isPresent()) {

				OrderDetailsEntity orderDetailsEntity = orderAndPaymentGlobalEntity.getOrderDetailsEntity();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				String format = formatter.format(date);

				orderDetailsEntity.setId(sequenceGenerator.getNextSequence(OrderDetailsEntity.SEQUENCE_NAME));
				orderDetailsEntity.setOrderId("OR" + System.currentTimeMillis());
				orderDetailsEntity.setBillingAddress(orderDetailsEntity.getBillingAddress());
				orderDetailsEntity.setDiscount(orderDetailsEntity.getDiscount());
				orderDetailsEntity.setMrp(orderDetailsEntity.getMrp());
				orderDetailsEntity.setNetPrice(orderDetailsEntity.getNetPrice());
				orderDetailsEntity.setProducts(orderDetailsEntity.getProducts());
				orderDetailsEntity.setUserId(orderDetailsEntity.getUserId());
				orderDetailsEntity.setShippingAddress(orderDetailsEntity.getShippingAddress());
				orderDetailsEntity.setTaxAmount(orderDetailsEntity.getTaxAmount());
				orderDetailsEntity.setTotalAmount(orderDetailsEntity.getTotalAmount());
				orderDetailsEntity.setCreatedOn(format);
				OrderDetailsEntity OrderData = orderDetailsRepo.save(orderDetailsEntity);

				OrderPaymentEntity orderPaymentEntity = orderAndPaymentGlobalEntity.getOrderPaymentEntity();
				orderDetailsEntity.setId(sequenceGenerator.getNextSequence(OrderPaymentEntity.SEQUENCE_NAME));
				orderPaymentEntity.setOrderId(OrderData.getOrderId());
				orderPaymentEntity.setCreatedOn(new Date());
				postOrderPaymentDetails(token, orderPaymentEntity);

				map.put("orderId", OrderData.getOrderId());
				map.put("status", 200);
				map.put("message", "Order placed successfully");
				
				
				File createPdfSupplier = createPdfSupplier(orderDetailsEntity);
				sendEmailWithAttachment("soumendolui077@gmail.com","Invoice file", "Hi " + extractUsername + ""
						+ ",\n                           "
						+ " Your order created successfully. ",false,createPdfSupplier);
				
				createPdfSupplier.delete();
			}
			
			
			
			return ResponseEntity.ok(map);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getOrderDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderDetails()");

		try {
			return this.orderAndPaymentService.getOrders(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/getOrder/{orderId}")
	public ResponseEntity<?> getOrderDetails(@PathVariable() String orderId) {

		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderDetails()");

		try {
			return this.orderAndPaymentService.getOrderDetailsService(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/getUserOrder/{userId}")
	public ResponseEntity<?> getOrderDetailsByuserId(@PathVariable() Integer userId) {

		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderDetailsByuserId()");

		try {
			return this.orderAndPaymentService.getUserOrderDetailsService(userId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	

	@RequestMapping(value = { "/list/{designerId}" }, method = RequestMethod.GET)
	public Map<String, Object> getOrderByDesigner(@PathVariable int designerId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "") String keyword, @RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderByDesigner()");

		try {
			return this.orderAndPaymentService.getDesigerOrders(designerId, page, limit, sort, sortName, keyword,
					sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/invoice/{orderId}")
	public GlobalResponse invoiceGenarater(@PathVariable String orderId)
	{
		try {
			return this.orderAndPaymentService.invoiceGenarator(orderId);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	
	@PostMapping("/genpdf/order")
//	HttpEntity<byte[]> createPdfSupplier(@RequestBody Json supplierInvoiceStraching) throws IOException {
	File createPdfSupplier(@RequestBody OrderDetailsEntity orderDetailsEntity) throws IOException {
		System.out.println("ok");

		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		

		/* next, get the Template */
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		ve.init();
		Template t = ve.getTemplate("templates/invoice.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();
		context.put("orderDetailsEntity", orderDetailsEntity);
//		context.put("genDateTime", LocalDateTime.now().toString());
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		
		

		baos = generatePdf(writer.toString());
		
		
		try(OutputStream outputStream = new FileOutputStream("invoice.pdf")) {
			baos.writeTo(outputStream);
			
		}
		
		return new File("invoice.pdf");

//		HttpHeaders header = new HttpHeaders();
//	    header.setContentType(MediaType.APPLICATION_PDF);
//	    header.set(HttpHeaders.CONTENT_DISPOSITION,
//	                   "attachment; filename=" + "soumen");
//	    header.setContentLength(baos.toByteArray().length);

//	    return new HttpEntity<byte[]>(baos.toByteArray(), header);

	}

	
	
	
	public ByteArrayOutputStream generatePdf(String html) {

		String pdfFilePath = "";
		PdfWriter pdfWriter=null;

		// create a new document
		Document document = new Document();
		try {

			document = new Document();
			// document header attributes
			document.addAuthor("Kinns");
			document.addAuthor("Kinns123");
			document.addCreationDate();
			document.addProducer();
			document.addCreator("kinns123.github.io");
			document.addTitle("HTML to PDF using itext");
			document.setPageSize(PageSize.LETTER);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);

			// open document
			document.open();

			XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
			xmlWorkerHelper.getDefaultCssResolver(true);
			StringReader stringReader = new StringReader(html);
			xmlWorkerHelper.parseXHtml(pdfWriter, document, stringReader);
			// close the document
			document.close();
			System.out.println("PDF generated successfully");

			return baos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	public void sendEmailWithAttachment(String to, String subject, String body,Boolean enableHtml,File file) {

//		SimpleMailMessage message = new SimpleMailMessage();
//
//		message.setFrom("ulearn@co.in");
//		message.setTo(to);
//		message.setSubject(subject);
//		message.setText(body);
//		mailSender.send(message);
		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setSubject(subject);
			helper.setFrom("soumen.dolui@nitsolution.in");
			helper.setTo(to);
			helper.setText(body, enableHtml);
			helper.addAttachment("Invoice", file);
			mailSender.send(message);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
}
