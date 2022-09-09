package com.divatt.admin.contoller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.hsnCode.HsnEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.HsnService;

@RestController
@RequestMapping("/hsn")
public class HsnController {
	
	@Autowired
	private HsnService hsnService ;
	private static final Logger LOGGER = LoggerFactory.getLogger(HsnController.class);
	
	
	@PostMapping("/add")
	public GlobalResponse postHsnDetails(@Valid @RequestBody HsnEntity hsnEntity) {
		LOGGER.info("Inside - HsnController.postHsnDetails()");

		try {
			 return this.hsnService.postHsnDetails(hsnEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		} 

	}
	
	
	
	
	
	@PutMapping("/update/{hsnCode}")
	public GlobalResponse updatetHsnDetailsByHsnCode(@Valid @RequestBody HsnEntity hsnEntity,@PathVariable (value="hsnCode") Integer hsnCode ) {
		LOGGER.info("Inside - HsnController.updatetHsnDetailsByHsnCode()");

		try {
			 return this.hsnService.updatetHsnDetailsByHsnCode(hsnEntity,hsnCode);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		} 

	}
	
	@GetMapping("/list")
	public Map<String, Object> getHsnDetails(			
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, 
			@RequestParam(defaultValue = "hsnCode") String sortName,
			@RequestParam(defaultValue = "false")  Boolean isDelete, 			
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy){
		
		LOGGER.info("Inside - HsnController.getHsnDetailse()");

		try {
			 return this.hsnService.getHsnDetails(page,limit,sort,sortName,isDelete,keyword,sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		} 
		
		
	}
	
	
	@GetMapping("/view/{hsnCode}")
	public Optional<HsnEntity> viewByHsnCode(@PathVariable (value="hsnCode") Integer hsnCode) {
		LOGGER.info("Inside - HsnController.viewByHsnCode()");

		try {
			 return this.hsnService.viewByHsnCode(hsnCode);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		} 

	}
	
	
	@PutMapping("/deleteHsnCode")
	public GlobalResponse deleteHsnCode(@RequestBody() HsnEntity hsnEntity) {
		LOGGER.info("Inside - HsnController.deleteHsnCode()");

		try {
			if(hsnEntity.getId()!=null) {
				return this.hsnService.deleteHsnCode(hsnEntity.getId());
			}
			else {
				throw new CustomException("Hsn not found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		} 

	}
	
	@PutMapping("/muldeleteHsnCode")
	public GlobalResponse muldeleteHsnCode(@RequestBody() List<Integer> hsnId) {
		LOGGER.info("Inside - HsnController.muldeleteHsnCode()");

		try {
			if(!hsnId.equals(null)) {
				return this.hsnService.muldeleteHsnCode(hsnId);
			}
			else {
				throw new CustomException("Hsn id not found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		} 

	}
	
	
	@PutMapping("/setStatus")
	public GlobalResponse setStatus(@RequestBody() HsnEntity hsnEntity) {
		LOGGER.info("Inside - HsnController.setStatus()");

		try {
			
			if(hsnEntity.getId() !=null) {
				return this.hsnService.setStatus(hsnEntity.getId());
			}
			else {
				throw new CustomException("Hsn Not Found");
			}
			 
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
//	@GetMapping("/getactiveHSNList")
//	public List<HsnEntity> getactiveHSNList(){
//		try {
//			return this.hsnService.getActiveHSNListService();
//		}catch(Exception e) {
//			throw new CustomException(e.getMessage());
//		}
//	}
	
	@GetMapping("/getactiveHSNList")
	public List<HsnEntity> getactiveHSNList(@RequestParam(defaultValue = "") String searchKeyword){
		try {
			return this.hsnService.getActiveHSNListService(searchKeyword);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
