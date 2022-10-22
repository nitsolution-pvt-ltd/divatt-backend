package com.divatt.user.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.divatt.user.entity.OrderInvoiceEntity;
import com.divatt.user.entity.OrderTrackingEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.helper.ListResponseDTO;
import com.divatt.user.response.GlobalResponse;

public interface OrderAndPaymentService {

	public ResponseEntity<?> postRazorpayOrderCreateService(OrderDetailsEntity orderDetailsEntity);

	public ResponseEntity<?> postOrderPaymentService(OrderPaymentEntity orderPaymentEntity);

	public ResponseEntity<?> postOrderSKUService(OrderSKUDetailsEntity orderSKUDetailsEntityRow);

	public Map<String, Object> getOrderPaymentService(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy);

	public Map<String, Object> getOrders(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy, String token);

	public ResponseEntity<?> getOrderDetailsService(String orderId);

	public ResponseEntity<?> getUserOrderDetailsService(Integer userId);

	public Map<String, Object> getDesigerOrders(int designerId, int page, int limit, String sort, String sortName,
			String keyword, Optional<String> sortBy);

	public GlobalResponse invoiceGenarator(String orderId);

	public OrderDetailsEntity getOrderDetails(String orderId);

	public Map<String, Object> getProductDetails(String orderId, int page, int limit, String sort, String sortName,
			String keyword, Optional<String> sortBy);

	public GlobalResponse orderUpdateService(OrderSKUDetailsEntity orderDetailsEntity, String orderId);

	public ResponseEntity<?> postOrderHandleDetailsService(org.json.simple.JSONObject object);

	public ResponseEntity<?> postOrderTrackingService(OrderTrackingEntity orderTrackingEntity);

	public ResponseEntity<?> putOrderTrackingService(OrderTrackingEntity orderTrackingEntity, String trackingId);

	public ResponseEntity<?> getOrderTrackingDetailsService(String orderId, int userId, int designerId);

	public GlobalResponse cancelOrderService(String refOrderId, Integer refProductId);

	public ResponseEntity<?> getOrderServiceByInvoiceId(String invoiceId);

	public Map<String, Object> getOrderInvoiceId(String invoiceId);

	public ResponseEntity<?> postOrderInvoiceService(OrderInvoiceEntity orderInvoiceEntity);

	public ResponseEntity<?> putOrderInvoiceService(@PathVariable String invoiceId,
			@Valid OrderInvoiceEntity orderInvoiceEntity);

	public Map<String, Object> getOrderInvoiceService(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy);

	public Map<String, Integer> getOrderCount(int designerId, Boolean adminStatus);

	public OrderSKUDetailsEntity getOrderDetailsService(String orderId, String productId);

	public Resource getFileSystem(String filename, HttpServletResponse response);

	public Resource getClassPathFile(String filename, HttpServletResponse response);

	public OrderSKUDetailsEntity getorderDetails(String orderId);

	public List<OrderDetailsEntity> getOrderListService(ListResponseDTO jsonObject);

	public List<OrderDetailsEntity> findByOrderId(String orderId);

   

    public Optional<OrderPaymentEntity> getgetorderByid1(String orderId);

    public Page<OrderDetailsEntity> findByOrderIds(int page, int limit, String sort, String orderId,
            Boolean isDeleted, Optional<String> sortBy);

	
   

}
