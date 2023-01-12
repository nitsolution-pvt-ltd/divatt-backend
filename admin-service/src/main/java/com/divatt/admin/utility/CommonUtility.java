package com.divatt.admin.utility;

import java.text.DecimalFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.divatt.admin.constant.MessageConstant;
import com.divatt.admin.constant.RestTemplateConstant;
import com.divatt.admin.entity.AccountEntity;
import com.divatt.admin.entity.LoginEntity;
import com.divatt.admin.entity.PaymentCharges;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.helper.EmailSenderThread;
import com.divatt.admin.repo.LoginRepository;

@Component
public class CommonUtility {

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;

	@Value("${interfaceId}")
	private String interfaceId;

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);

	public static double duoble(float f) {

		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(f));

	}

	public void mailSendAccount(AccountEntity accountEntity) {
		try {
			LoginEntity findByRoleName = loginRepository.findByRoleName(MessageConstant.ADMIN_ROLES.getMessage());
			String email = findByRoleName.getEmail();
			String firstName = findByRoleName.getFirstName();
			String lastName = findByRoleName.getLastName();
			String name = firstName + " " + lastName;
			String gstIn = findByRoleName.getGstIn();
			String pan = findByRoleName.getPan();
			Context context = new Context();

			context.setVariable("name", name);
			context.setVariable("gstIn", gstIn);
			context.setVariable("pan", pan);
			context.setVariable("email", email);
			String htmlContent = templateEngine.process("adminAccountUpdate.html", context);
			EmailSenderThread emailSenderThread = new EmailSenderThread(email, "Account updated", htmlContent, true, null, restTemplate);
			emailSenderThread.start();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public PaymentCharges invoiceUpdateMapper(List<AccountEntity> accountEntity) {
		PaymentCharges charges = new PaymentCharges();
		charges.setAdminCity(accountEntity.get(0).getAdmin_details().getCity());
		charges.setAdminCountry(accountEntity.get(0).getAdmin_details().getCountry());
		charges.setAdminGst(accountEntity.get(0).getAdmin_details().getGst_in());
		charges.setAdminPan(accountEntity.get(0).getAdmin_details().getPan());
		charges.setAdminPhone(accountEntity.get(0).getAdmin_details().getMobile());
		charges.setAdminPin(accountEntity.get(0).getAdmin_details().getPin());
		charges.setAdminState(accountEntity.get(0).getAdmin_details().getState());

		charges.setDesignerCity(accountEntity.get(0).getDesigner_details().getCity());
		charges.setDesignerCountry(accountEntity.get(0).getDesigner_details().getCountry());
		charges.setDesignerPan(accountEntity.get(0).getDesigner_details().getPan());
		charges.setDesignerState(accountEntity.get(0).getDesigner_details().getState());
		charges.setDesignerPin(accountEntity.get(0).getDesigner_details().getPin());
		charges.setDesignerPhone(accountEntity.get(0).getDesigner_details().getMobile());
		charges.setDesignerGst(accountEntity.get(0).getDesigner_details().getGst_in());
		charges.setDesignerName(accountEntity.get(0).getDesigner_details().getDesigner_name());
		charges.setDisplayName(accountEntity.get(0).getDesigner_details().getDisplay_name());
		return charges;
	}

	public PaymentCharges invoiceUpdateMap(AccountEntity accountEntity) {
		PaymentCharges charges = new PaymentCharges();
		charges.setProductName(MessageConstant.DIVATT_CHARGES.getMessage());
		charges.setFee(accountEntity.getService_charge().getFee() + "");
		charges.setTotal(accountEntity.getService_charge().getTotal_amount() + "");
		charges.setSgst(accountEntity.getService_charge().getSgst() + "");
		charges.setIgst(accountEntity.getService_charge().getIgst() + "");
		charges.setCgst(accountEntity.getService_charge().getCgst() + "");
		charges.setRate(accountEntity.getService_charge().getRate() + "");
		charges.setTcs(accountEntity.getService_charge().getTcs() + "");
		charges.setTcsRate(accountEntity.getService_charge().getTcs_rate() + "");
		charges.setGrandTotal(accountEntity.getService_charge().getTotal_amount() + "");
		charges.setHsnCode(accountEntity.getOrder_details().get(0).getHsn_code());
		return charges;
	}
	

	public static double iNVValues(AccountEntity rowsAccount) {
		
		float iNVValuesAmount = 
				rowsAccount.getOrder_details().get(0).getSales_price()
				+ rowsAccount.getOrder_details().get(0).getHsn_cgst()
				+ rowsAccount.getOrder_details().get(0).getHsn_sgst()
				+ rowsAccount.getOrder_details().get(0).getHsn_igst()
				+ rowsAccount.getOrder_details().get(0).getGiftWrapAmount();
		
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(iNVValuesAmount));
	}

	public ResponseEntity<Object> getDesignerDetails(String token) {
		
		ResponseEntity<Object> getExchange = null;
		HttpHeaders header= new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("Authorization", token);
		HttpEntity<Object> httpEntity = new HttpEntity<>(header);
		
		try {
			String urlParam="designer/getDesignerToken";
			getExchange = restTemplate.exchange(RestTemplateConstant.DESIGNER_URL.getMessage()+urlParam,HttpMethod.GET, httpEntity,Object.class);
			
		} catch (HttpStatusCodeException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/excelReportDesigner/", ex.getResponseBodyAsByteArray(),
						ex.getRawStatusCode());
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/excelReportDesigner/", e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return getExchange;
		
	}
	
	

}
