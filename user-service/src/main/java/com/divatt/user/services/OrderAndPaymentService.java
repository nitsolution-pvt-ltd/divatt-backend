package com.divatt.user.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.divatt.user.entity.InvoiceEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.helper.PDFRunner;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.orderPaymenRepo.UserOrderPaymentRepo;
import com.divatt.user.response.GlobalResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import springfox.documentation.spring.web.json.Json;

@Service
public class OrderAndPaymentService{

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAndPaymentService.class);

	HttpResponse<String> response = null;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UserOrderPaymentRepo userOrderPaymentRepo;

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private Environment env;


	@Value("${pdf.directory}")
	private String pdfDirectory;
	
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
			RazorpayClient razorpayClient = new RazorpayClient(env.getProperty("key"), env.getProperty("secretKey"));
			JSONObject options = new JSONObject();

			options.put("amount", orderDetailsEntity.getTotalAmount());
			options.put("currency", "INR");
			options.put("receipt", "RC" + getRandomString());

			Order order = razorpayClient.Orders.create(options);

			return ResponseEntity.ok(new Json(order.toString()));

		} catch (RazorpayException e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> postOrderPaymentService(OrderPaymentEntity orderPaymentEntity) {
		LOGGER.info("Inside - OrderAndPaymentService.postOrderPaymentService()");

		try {

			RazorpayClient razorpayClient = new RazorpayClient(env.getProperty("key"), env.getProperty("secretKey"));

//			List<Payment> payments = razorpayClient.Payments.fetchAll();
			OrderPaymentEntity filterCatDetails = new OrderPaymentEntity();

			filterCatDetails.setId(sequenceGenerator.getNextSequence(OrderPaymentEntity.SEQUENCE_NAME));
			filterCatDetails.setOrderId(orderPaymentEntity.getOrderId());
			filterCatDetails.setPaymentMode(orderPaymentEntity.getPaymentMode());
			filterCatDetails.setPaymentDetails(orderPaymentEntity.getPaymentDetails());
			filterCatDetails.setPaymentResponse(orderPaymentEntity.getPaymentResponse());
			filterCatDetails.setPaymentStatus(orderPaymentEntity.getPaymentStatus());
			filterCatDetails.setUserId(orderPaymentEntity.getUserId());
			filterCatDetails.setCreatedOn(new Date());

			OrderPaymentEntity data=userOrderPaymentRepo.save(filterCatDetails);
			return ResponseEntity.ok(data);
		} catch (RazorpayException e) {
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
			Optional<String> sortBy) {
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

			if (keyword.isEmpty()) {
				findAll = orderDetailsRepo.findAll(pagingSort);
			} else {
				findAll = orderDetailsRepo.Search(keyword, pagingSort);

			}

			List<Object> productId = new ArrayList<>();

			findAll.forEach(e -> {
				ObjectMapper obj = new ObjectMapper();
				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}

				Optional<OrderPaymentEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByOrderId(e.getOrderId());

				String writeValueAsString = null;
				try {
					writeValueAsString = obj.writeValueAsString(OrderPaymentRow.get());
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
				JsonNode paymentJson = new JsonNode(writeValueAsString);

				JsonNode cartJN = new JsonNode(productIdFilter);
				JSONObject objects = cartJN.getObject();
				objects.put("paymentData", paymentJson.getObject());
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

			if (productId.size() <= 0) {
				throw new CustomException("Order not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getOrderDetailsService(String orderId) {
		try {
			List<OrderDetailsEntity> findById = this.orderDetailsRepo.findByOrderId(orderId);
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

				String writeValueAsString = null;
				try {
					writeValueAsString = obj.writeValueAsString(OrderPaymentRow.get());
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
				JsonNode paymentJson = new JsonNode(writeValueAsString);

				JsonNode cartJN = new JsonNode(productIdFilter);
				JSONObject objects = cartJN.getObject();
				objects.put("paymentData", paymentJson.getObject());
				productId.add(objects);

			});

			return ResponseEntity.ok(new Json(productId.get(0).toString()));
		} catch (Exception e2) {
			return ResponseEntity.ok(e2.getMessage());
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
				JsonNode pJN = new JsonNode(productIdFilter);
				JSONObject object = pJN.getObject();

				String writeValueAsString = null;
				try {
					writeValueAsString = obj.writeValueAsString(OrderPaymentRow.get());
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
				JsonNode paymentJson = new JsonNode(writeValueAsString);

				object.put("paymentData", paymentJson.getObject());
				productId.add(object);

			});
			return ResponseEntity.ok(new Json(productId.toString()));

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getDesigerOrders(int designerId, int page, int limit, String sort, String sortName,
			String keyword, Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentService.getOrders()");
		try {
			int CountData = (int) orderDetailsRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				System.out.println(limit);
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<OrderDetailsEntity> findAll = null;
			List<OrderDetailsEntity> findAlls = null;

			if (keyword.isEmpty()) {

				findAll = orderDetailsRepo.findDesigner(designerId, pagingSort);
				Query query = new Query();

				query.addCriteria(Criteria.where("products").elemMatch(Criteria.where("designerId").is(designerId)));
				query.fields().include("order_id").include("products.$");

				findAlls = mongoTemplate.find(query, OrderDetailsEntity.class);

			} else {
				findAll = orderDetailsRepo.Search(keyword, pagingSort);
			}

			List<Object> productId = new ArrayList<>();

			findAll.forEach(e -> {
				ObjectMapper obj = new ObjectMapper();
				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}

				Optional<OrderPaymentEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByOrderId(e.getOrderId());

				String writeValueAsString = null;
				try {
					writeValueAsString = obj.writeValueAsString(OrderPaymentRow.get());
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
				JsonNode paymentJson = new JsonNode(writeValueAsString);

				JsonNode cartJN = new JsonNode(productIdFilter);
				JSONObject objects = cartJN.getObject();
				objects.put("paymentData", paymentJson.getObject());
				productId.add(objects);

			});

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAlls);
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (productId.size() <= 0) {
				throw new CustomException("Order not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse invoiceGenarator(String orderId) {
		try {
			Query query= new Query();
			query.addCriteria(Criteria.where("order_id").is(orderId));
			OrderDetailsEntity detailsEntity= mongoOperations.findOne(query, OrderDetailsEntity.class);
			if(detailsEntity!=null) {
				// RestTemplate restTemplate= new RestTemplate();
				// ResponseEntity<UserLoginEntity> userLoginEntity=restTemplate.getForEntity("http://localhost:8080/dev/auth/info/USER/"+detailsEntity.getUserId(), UserLoginEntity.class);
				//ResponseEntity<UserLoginEntity> userLoginEntity=null;
				// System.out.println(userLoginEntity.getBody());
				 InvoiceEntity invoiceEntity= new InvoiceEntity();
				 invoiceEntity.setOrderDetailsEntity(detailsEntity);
				// invoiceEntity.setUserEntity(userLoginEntity.getBody());
				 PDFRunner pdfRunner= new PDFRunner(invoiceEntity);
				 pdfRunner.fun1();
				 return pdfRunner.pdfPath(orderId);
			}
			else {
				return new GlobalResponse("Error!!", "Order not found", 400);
			}
			}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public OrderDetailsEntity getOrderDetails(String orderId) {
		try {
			Query query= new Query();
			query.addCriteria(Criteria.where("order_id").is(orderId));
			OrderDetailsEntity orderDetailsEntity= mongoOperations.findOne(query, OrderDetailsEntity.class);
			return orderDetailsEntity;
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getProductDetails(String orderId, int page, int limit, String sort, String sortName,
			String keyword, Optional<String> sortBy) {
		try {
			try {
				Query query= new Query();
				query.addCriteria(Criteria.where("order_id").is(orderId));
				OrderDetailsEntity orderDetailsEntity= mongoTemplate.findOne(query, OrderDetailsEntity.class);
				int CountData =orderDetailsEntity.getProducts().size();
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
					findAll = orderDetailsRepo.findByOrderId(orderId,pagingSort);
				}
//				else {
//					findAll = orderDetailsRepo.SearchByOrderId(keyword,orderId,pagingSort);
//				}
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
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse orderUpdateService(OrderDetailsEntity orderDetailsEntity, String orderId) {
		try {
			Query query= new Query();
			query.addCriteria(Criteria.where("order_id").is(orderId));
			OrderDetailsEntity orderDetailsEntity2=mongoOperations.findOne(query, OrderDetailsEntity.class);
			System.out.println(orderDetailsEntity2);
			if(!orderDetailsEntity2.equals(null))
			{
				System.out.println(orderDetailsEntity);
				OrderDetailsEntity orderDetailsEntity1=orderDetailsRepo.findByOrderId(orderId).get(0);
				orderDetailsEntity1.setOrderId(orderDetailsRepo.findByOrderId(orderId).get(0).getOrderId());
				orderDetailsEntity1.setOrderStatus(orderDetailsEntity.getOrderStatus());
				orderDetailsRepo.save(orderDetailsEntity1);
				return new GlobalResponse("Success!!", "Order status updated", 200);
			}
			else {
				return new GlobalResponse("Error!!", "Order not found", 400);
			}
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	private enum ResourceType {
		FILE_SYSTEM,
		CLASSPATH
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
}
