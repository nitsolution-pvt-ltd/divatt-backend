package com.divatt.admin.contoller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.divatt.admin.entity.AccountEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.LoginEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.helper.JwtUtil;
import com.divatt.admin.repo.LoginRepository;
import com.divatt.admin.services.AccountService;
import com.divatt.admin.utility.AccountExcelExporter;
import com.divatt.admin.utility.CommonUtility;
import com.divatt.admin.utility.DesignerAccountExcelExporter;


@RestController
@RequestMapping("/account")
public class AccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;

	@Value("${interfaceId}")
	private String interfaceId;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private CommonUtility commonUtility;
	
	
	@PostMapping("/add")
	public GlobalResponse postAccountDetails(@Valid @RequestBody AccountEntity accountEntity) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.postAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.postAccountDetails()");
		}

		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/add", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/add", "Success", HttpStatus.OK);
			}
			return this.accountService.postAccountDetails(accountEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/view/{accountId}")
	public ResponseEntity<?> viewAccountDetails(@PathVariable() long accountId) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.viewAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.viewAccountDetails()");
		}

		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/view/" + accountId, "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/view/" + accountId, "Success", HttpStatus.OK);
			}
			return this.accountService.viewAccountDetails(accountId);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/view/" + accountId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/update/{accountId}")
	public GlobalResponse putAccountDetails(@Valid @RequestBody AccountEntity accountEntity,
			@PathVariable() Integer accountId) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.putAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.putAccountDetails()");
		}

		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/update/" + accountId, "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/update/" + accountId, "Success", HttpStatus.OK);
			}
			return this.accountService.putAccountDetails(accountId, accountEntity);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/update/" + accountId, e.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getAccountDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "_id") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") String designerReturn,
			@RequestParam(defaultValue = "") String serviceCharge, @RequestParam(defaultValue = "") String govtCharge,
			@RequestParam(defaultValue = "") String userOrder, @RequestParam(defaultValue = "") String ReturnStatus,
			@RequestParam(defaultValue = "") String settlement, @RequestParam(defaultValue = "0") int year,
			@RequestParam(defaultValue = "0") int month, @RequestParam Optional<String> sortBy,
			@RequestParam(defaultValue = "") String designerId) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.getAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.getAccountDetails()");
		}

		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/list", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/list", "Success", HttpStatus.OK);
			}
			return this.accountService.getAccountDetails(page, limit, sort, sortName, isDeleted, keyword,
					designerReturn, serviceCharge, govtCharge, userOrder, ReturnStatus, settlement, year, month,
					designerId, sortBy);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/list", e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/excelReport")
	public void excelReport(@RequestHeader("Authorization") String token,@RequestParam(defaultValue = "") String userType,
			HttpServletResponse response, @RequestParam(defaultValue = "") String designerReturn,
			@RequestParam(defaultValue = "") String serviceCharge, @RequestParam(defaultValue = "") String govtCharge,
			@RequestParam(defaultValue = "") String userOrder, @RequestParam(defaultValue = "") String ReturnStatus,
			@RequestParam(defaultValue = "") String settlement, @RequestParam(defaultValue = "0") int year,
			@RequestParam(defaultValue = "0") int month, @RequestParam(defaultValue = "") String designerId) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.excelReport()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.excelReport()");
		}

		try {
			String extractUsername = jwtUtil.extractUsername(token.substring(7));
			final Optional<LoginEntity> findByEmail = loginRepository.findByEmail(extractUsername);
			
			if(findByEmail.orElse(null) != null && !token.substring(7).isEmpty()) {
					
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
							host + contextPath + "/account/excelReport", "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
							host + contextPath + "/account/excelReport", "Success", HttpStatus.OK);
				}
				response.setContentType("application/octet-stream");
				DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
				String currentDateTime = dateFormatter.format(new Date());
	
				String headerKey = "Content-Disposition";
				String headerValue = "attachment; filename=Divatt_payments_report_" + currentDateTime + ".xlsx";
				response.setHeader(headerKey, headerValue);
	
				List<AccountEntity> listUsers = accountService.excelReportService(designerReturn, serviceCharge, govtCharge,
						userOrder, ReturnStatus, settlement, year, month, designerId);
				AccountExcelExporter excelExporter = new AccountExcelExporter(listUsers);
				excelExporter.export(response);
				
			}else {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
							host + contextPath + "/account/excelReport","Unauthorized", HttpStatus.UNAUTHORIZED);
				}
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/excelReport", e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@GetMapping("/excelReportDesigner")
	public void excelReportDesiger(@RequestHeader("Authorization") String token,
			HttpServletResponse response, @RequestParam(defaultValue = "") String designerReturn,
			@RequestParam(defaultValue = "") String serviceCharge, @RequestParam(defaultValue = "") String govtCharge,
			@RequestParam(defaultValue = "") String userOrder, @RequestParam(defaultValue = "") String ReturnStatus,
			@RequestParam(defaultValue = "") String settlement, @RequestParam(defaultValue = "0") int year,
			@RequestParam(defaultValue = "0") int month, @RequestParam(defaultValue = "") String designerId) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.excelReportDesiger()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.excelReportDesiger()");
		}

		try {
			ResponseEntity<Object> designerDetails = commonUtility.getDesignerDetails(token);
			
			if(!designerDetails.equals(null)) {
					
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
							host + contextPath + "/account/excelReportDesigner", "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
							host + contextPath + "/account/excelReportDesigner", "Success", HttpStatus.OK);
				}
				
				response.setContentType("application/octet-stream");
				DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
				String currentDateTime = dateFormatter.format(new Date());
	
				String headerKey = "Content-Disposition";
				String headerValue = "attachment; filename=Divatt_payments_report_" + currentDateTime + ".xlsx";
				response.setHeader(headerKey, headerValue);
	
				List<AccountEntity> listUsers = accountService.excelReportService(designerReturn, serviceCharge, govtCharge,
						userOrder, ReturnStatus, settlement, year, month, designerId);
				DesignerAccountExcelExporter excelExporter = new DesignerAccountExcelExporter(listUsers);
				excelExporter.export(response);
				
			}else {
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
								host + contextPath + "/account/excelReportDesigner","Unauthorized", HttpStatus.UNAUTHORIZED);
					}
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/excelReportDesigner", e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@GetMapping("/getDesignerInvoice/{orderId}/{designerId}")
	public ResponseEntity<?> getDesignerInvoice(@PathVariable String orderId,@PathVariable Long designerId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.getDesignerInvoice()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.getDesignerInvoice()");
		}
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/getDesignerInvoice/"+orderId+"/"+designerId, "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/getDesignerInvoice/"+orderId+"/"+designerId, "Success", HttpStatus.OK);
			}
			return this.accountService.getDesignerInvoice(orderId, designerId);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/getDesignerInvoice/"+orderId+"/"+designerId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = { "/transactions" }, method = RequestMethod.GET)
	public ResponseEntity<?> getTransactions(@RequestHeader("Authorization") String token, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "_id") String sortName,@RequestParam(defaultValue = "false") Boolean isDeleted, 
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "") String paymentStatus,
			@RequestParam Optional<String> sortBy) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.getTransactions()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.getTransactions()");
		}

		try {
			String extractUsername = jwtUtil.extractUsername(token.substring(7));
			final Optional<LoginEntity> findByEmail = loginRepository.findByEmail(extractUsername);
			
			if(findByEmail.orElse(null) == null) {
				Map<String, Object> mapObj= new HashMap<>();
				mapObj.put("status", 401);
				mapObj.put("reason", "Error");
				mapObj.put("message", "Unauthorized");
				
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
							host + contextPath + "/account/transactions","Unauthorized", HttpStatus.UNAUTHORIZED);
				}
				return new ResponseEntity<>(mapObj, HttpStatus.UNAUTHORIZED);
			}
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/transactions", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/transactions", "Success", HttpStatus.OK);
			}
			return this.accountService.getTransactionsService(page, limit, sort, sortName, isDeleted, keyword, paymentStatus, sortBy);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/transactions", e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


}
