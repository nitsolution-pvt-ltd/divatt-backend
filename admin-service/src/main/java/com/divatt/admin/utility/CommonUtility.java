package com.divatt.admin.utility;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.divatt.admin.constant.MessageConstant;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);

	public static double duoble(float f) {

		DecimalFormat df = new DecimalFormat("0.00");
		Double valueOf = Double.valueOf(df.format(f));
		return valueOf;

	}

	public void mailSendAccount(AccountEntity accountEntity) {
		try {
			LoginEntity findByRoleName = loginRepository.findByRoleName(MessageConstant.ADMIN_ROLES.getMessage());
			String email = findByRoleName.getEmail();
//			String email="shantanukhamrui527@gmail.com";
			String firstName = findByRoleName.getFirstName();
			String lastName = findByRoleName.getLastName();
			String name = firstName + " " + lastName;
			Context context = new Context();
			context.setVariable("firstName", firstName);
			context.setVariable("name", name);
			String htmlContent = templateEngine.process("adminAccountUpdate.html", context);
			EmailSenderThread emailSenderThread = new EmailSenderThread(email, "Account updated", htmlContent, true,
					null, restTemplate);
			emailSenderThread.start();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public static PaymentCharges invoiceUpdateMapper(AccountEntity accountEntity) {
		PaymentCharges charges = new PaymentCharges();
		charges.setAdminCity(accountEntity.getAdmin_details().getCity());
		charges.setAdminCountry(accountEntity.getAdmin_details().getCountry());
		charges.setAdminGst(accountEntity.getAdmin_details().getGst_in());
		charges.setAdminPan(accountEntity.getAdmin_details().getPan());
		charges.setAdminPhone(accountEntity.getAdmin_details().getMobile());
		charges.setAdminPin(accountEntity.getAdmin_details().getPin());
		charges.setAdminState(accountEntity.getAdmin_details().getState());

		charges.setDesignerCity(accountEntity.getDesigner_details().getCity());
		charges.setDesignerCountry(accountEntity.getDesigner_details().getCountry());
		charges.setDesignerPan(accountEntity.getDesigner_details().getPan());
		charges.setDesignerState(accountEntity.getDesigner_details().getState());
		charges.setDesignerPin(accountEntity.getDesigner_details().getPin());
		charges.setDesignerPhone(accountEntity.getDesigner_details().getMobile());
		charges.setDesignerGst(accountEntity.getDesigner_details().getGst_in());
		charges.setDesignerName(accountEntity.getDesigner_details().getDesigner_name());
		charges.setCgst(accountEntity.getService_charge().getCgst() + "");
		charges.setIgst(accountEntity.getService_charge().getIgst() + "");
		charges.setSgst(accountEntity.getService_charge().getSgst() + "");
		charges.setDiscount(0 + "");
		charges.setProductName(accountEntity.getOrder_details().get(0).getProductName());
		charges.setGrossAmount(accountEntity.getOrder_details().get(0).getMrp() + "");
		charges.setHsnCode(accountEntity.getOrder_details().get(0).getHsn_code());
		charges.setTotal(accountEntity.getOrder_details().get(0).getSales_price()+"");
		return charges;
	}
	
	public static PaymentCharges invoiceUpdateMap(AccountEntity accountEntity) {
		PaymentCharges charges=new PaymentCharges();
		charges.setProductName(accountEntity.getOrder_details().get(0).getProductName());
		charges.setGrossAmount(accountEntity.getOrder_details().get(0).getMrp()+"");
		charges.setTotal(accountEntity.getOrder_details().get(0).getSales_price()+"");
		charges.setSgst(accountEntity.getService_charge().getSgst()+"");
		charges.setIgst(accountEntity.getService_charge().getIgst()+"");
		charges.setCgst(accountEntity.getService_charge().getCgst()+"");
		charges.setDiscount(0+"");
		return charges;
	}

}
