package com.divatt.admin.contoller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.divatt.admin.DTO.ContactDTO;
import com.divatt.admin.DTO.FundsDTO;
import com.divatt.admin.DTO.PayOutDTO;
import com.divatt.admin.DTO.RazorpayXPaymentDTO;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.ContactService;

@RestController
@RequestMapping("/payOutDetails")																																		
public class ContactController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired private ContactService contactService;
	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;

	@Value("${interfaceId}")
	private String interfaceId;
	
	
	@PostMapping("/contacts")
	public ResponseEntity<?> addcontacts( @RequestBody ContactDTO contactDTO){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactController.addcontacts()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactController.addcontacts()");
		}
	try {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
					host + contextPath + "/payOutDetails/contacts", "Success", HttpStatus.OK);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
					host + contextPath + "/payOutDetails/contacts", "Success", HttpStatus.OK);
		}
		return this.contactService.addcontactsService(contactDTO);
	}
	catch(Exception e)
	{
		throw new CustomException(e.getMessage());
	}
	}
	
	@PostMapping("/funds")
	public ResponseEntity<?> addFunds(@RequestParam Long designerId,@RequestBody FundsDTO fundsDto)
	{
		if (LOGGER.isInfoEnabled()) {
		LOGGER.info("Inside - ContactController.addFunds()");
	}
	if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Inside - ContactController.addFunds()");
	}
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/funds", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/funds", "Success", HttpStatus.OK);
			}
			return this.contactService.addFunds(designerId,fundsDto);		
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getContactsListById")
	public ResponseEntity<?>getContactListById(@RequestParam Long designerId)
	{
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactController.getContactListById()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactController.getContactListById()");
		}
		
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getContactsListById", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getContactsListById", "Success", HttpStatus.OK);
			}
			return this.contactService.getContactListById(designerId);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/getContactsList")
	public ResponseEntity<?>getContactList()
	{
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactController.getContactList()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactController.getContactList()");
		}
		
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getContactsList", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getContactsList", "Success", HttpStatus.OK);
			}
			return this.contactService.getContactList();
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/getFundsList")
	public ResponseEntity<?>getFundsList()
	{
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactController.getFundsList()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactController.getFundsList()");
		}
		
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getFundsList", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getFundsList", "Success", HttpStatus.OK);
			}
			return this.contactService.getFundsList();
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/getFundsListById")
	public ResponseEntity<?>getFundsListById(@RequestParam Long designerId)
	{
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactController.getFundsListById()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactController.getFundsListById()");
		}
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getFundsListById", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getFundsListById", "Success", HttpStatus.OK);
			}
			return this.contactService.getFundsListById(designerId);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
//	@GetMapping("/designerAccNo")
//	public ResponseEntity<?>getdesignerAccNo(@RequestParam Long designerId)
//	{
//		if (LOGGER.isInfoEnabled()) {
//			LOGGER.info("Inside - ContactController.getdesignerAccNo()");
//		}
//		if (LOGGER.isDebugEnabled()) {
//			LOGGER.debug("Inside - ContactController.getdesignerAccNo()");
//		}
//		try {
//			if (LOGGER.isInfoEnabled()) {
//				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
//						host + contextPath + "/payOutDetails/designerAccNo", "Success", HttpStatus.OK);
//			}
//			if (LOGGER.isDebugEnabled()) {
//				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
//						host + contextPath + "/payOutDetails/designerAccNo", "Success", HttpStatus.OK);
//			}
//			return this.contactService.getdesignerAccNo(designerId);
//		}
//		catch(Exception e)
//		{
//			throw new CustomException(e.getMessage());
//		}
//	}
	@PostMapping("/addPayOut")
	public ResponseEntity<?> addPayOut(@RequestHeader("Authorization") String token, @RequestBody PayOutDTO payOutDTO) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactController.addPayOut()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactController.addPayOut()");
		}
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/addPayOut", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/addPayOut", "Success", HttpStatus.OK);
			}
			return this.contactService.addPayOut(token, payOutDTO);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/payOutListById")
	public ResponseEntity<?> getPayOutListById(@RequestHeader("Authorization") String token,
			@RequestParam Long designerId, @RequestParam String orderId, @RequestParam int productId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactController.getPayOutListById()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactController.getPayOutListById()");
		}
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutListById", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutListById", "Success", HttpStatus.OK);
			}
			return this.contactService.getPayOutListById(token, designerId, orderId, productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/payOutList")
	public ResponseEntity<?> getPayOutList(@RequestHeader("Authorization") String token) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactController.getPayOutList()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactController.getPayOutList()");
		}
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutList", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutList", "Success", HttpStatus.OK);
			}
			return this.contactService.getPayOutList(token);
		} catch (Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
	@PostMapping("/razorpayX/handle")
	public ResponseEntity<?> postRazorpayXHandle(@RequestBody RazorpayXPaymentDTO xPaymentDTO){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactController.postRazorpayXHandle()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactController.postRazorpayXHandle()");
		}
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/razorpayX/handle", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/razorpayX/handle", "Success", HttpStatus.OK);
			}
			LOGGER.info("Recieved Request Body From Webhook in Controller: "+xPaymentDTO);
		    return this.contactService.postRazorpayXHandle(xPaymentDTO);
		} catch (Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}

}
