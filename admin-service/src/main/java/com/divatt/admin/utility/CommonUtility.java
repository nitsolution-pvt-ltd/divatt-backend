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
//			String email = findByRoleName.getEmail();
			String email="shantanukhamrui527@gmail.com";
			String firstName = findByRoleName.getFirstName();
			String lastName = findByRoleName.getLastName();
			String name=firstName +" " +lastName;
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

}
