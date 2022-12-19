package com.divatt.user.helper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.divatt.user.entity.InvoiceEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.response.GlobalResponse;
import com.lowagie.text.DocumentException;

@Component
public class PDFRunner {
	
	
	@Autowired
	private TemplateEngine templateEngine;
	
	
//	@Value("${pdf.directory}")
//	private String pdfDirectory;
	
	@Autowired
	private InvoiceEntity invoiceEntity;
	@Autowired
	private OrderDetailsRepo detailsRepo;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	public PDFRunner(InvoiceEntity invoiceEntity) {
		super();
		this.invoiceEntity = invoiceEntity;
	}
	public PDFRunner() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void fun1() {
		
		InvoiceEntity thisInvoiceEntity=this.invoiceEntity;
		HashMap<String, Object> data= new HashMap<String, Object>();
		data.put("OrderDetails", thisInvoiceEntity.getOrderDetailsEntity());
//		generatePdfFile("invoice", data, "invoice.pdf");
	}
//	 public String generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
//		
//	        Context context = new Context();
//	        context.setVariables(data);	
//	        String htmlContent = templateEngine.process(templateName, context);
//	        try {
//	            FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + pdfFileName);
//	            ITextRenderer renderer = new ITextRenderer();
//	            renderer.setDocumentFromString(htmlContent);
//	            renderer.layout();
//	            renderer.createPDF(fileOutputStream, false);
//	            renderer.finishPDF();
//	            OrderDetailsEntity orderDetailsEntity= new OrderDetailsEntity();
//	            detailsRepo.save(orderDetailsEntity);
//	            return pdfDirectory + pdfFileName;
//	        } catch (FileNotFoundException e) {
//	            throw new CustomException(e.getMessage());
//	        } catch (DocumentException e) {
//	           throw new CustomException(e.getMessage());
//	        }
//	 }
	 
	 public GlobalResponse pdfPath(String orderId)
	 {
		 try {
			 Query query= new Query();
			 query.addCriteria(Criteria.where("order_id").is(orderId));
			 OrderDetailsEntity orderDetailsEntity=mongoOperations.findOne(query, OrderDetailsEntity.class);
			 return new GlobalResponse("Success!!", orderDetailsEntity.getOrderId() , 200);
		 }
		 catch(Exception e) {
			 throw new CustomException(e.getMessage());
		 }
	 }
}
