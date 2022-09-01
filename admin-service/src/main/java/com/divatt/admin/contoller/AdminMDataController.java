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

import com.divatt.admin.entity.BannerEntity;
import com.divatt.admin.entity.ColourEntity;
import com.divatt.admin.entity.ColourMetaEntity;
import com.divatt.admin.entity.DesignerCategoryEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.AdminMService;


@RestController
@RequestMapping("/adminMData")
public class AdminMDataController {

	@Autowired
	private AdminMService adminMService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminMDataController.class);

	@GetMapping("/coloreList")
	public List<ColourEntity> colourList() {
		LOGGER.info("Inside - AdminMDataController.colourList()");
		try {
			return this.adminMService.getColours();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/addColour")
	public GlobalResponse addColore(@RequestBody ColourEntity colourEntity) {
		LOGGER.info("Inside - AdminMDataController.addColore()");
		
		try {
			return this.adminMService.addColour(colourEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	
	
	
	
	   
	  
	  
@PutMapping("/updateColours/{name}")
	  
	  public GlobalResponse updateColores(@RequestBody ColourEntity colourEntity , @PathVariable (value="name") String name)
	  {  
	LOGGER.info("Inside - AdminMDataController.updateColores()");
		  try {
			  return this.adminMService.updateColours(colourEntity,name); 
			  } 
		  
		  catch (Exception e) { 
			  throw new CustomException(e.getMessage());
	  
	  }
	  
	  }


@GetMapping("/getColour/{name}")

public ColourEntity getColore( @PathVariable (value="name") String name)
{  
	LOGGER.info("Inside - AdminMDataController.getColore()");
	  try {
		  return this.adminMService.getColour(name); 
		  } 
	  
	  catch (Exception e) { 
		  throw new CustomException(e.getMessage());

}

}
	  
 
 @PutMapping("/deleteColours/{name}")
 
 public GlobalResponse deleteColore( @PathVariable (value="name") String name)
 {  
	 LOGGER.info("Inside - AdminMDataController.deleteColore()");
	  try {
		  return this.adminMService.deleteColours(name); 
		  } 
	  
	  catch (Exception e) { 
		  throw new CustomException(e.getMessage());
 
 }
 
 
 }
 
 
@DeleteMapping("/deleteColour/{name}")
 
 public GlobalResponse deleteColour( @PathVariable (value="name") String name)
 {  
	LOGGER.info("Inside - AdminMDataController.deleteColour()");
	  try {
		  return this.adminMService.deleteColour(name); 
		  } 
	  
	  catch (Exception e) { 
		  throw new CustomException(e.getMessage());
 
 }
 
 
 }
 
		
		
		  
	

	@GetMapping("/tblList")
	public Map<String, Object> getColourDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "0") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "colors") String metaKey, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		
		LOGGER.info("Inside - AdminMDataController.getColourDetails()");
		
		try {
			return this.adminMService.tblList(page, metaKey, keyword, limit, sort, sortBy, sortName);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	
	

	
	
	

	
	
	
	

	@PostMapping("/addBanner")
	public GlobalResponse addBanner(@Valid @RequestBody BannerEntity bannerEntity) {
		LOGGER.info("Inside - AdminMDataController.addBanner()");
		try { 
			return this.adminMService.addBanner(bannerEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	
	
	@PostMapping("/updateBanner/{id}")
	public GlobalResponse updateBanner(@Valid @RequestBody BannerEntity bannerEntity,@PathVariable (value="id") Long id) {
		LOGGER.info("Inside - AdminMDataController.updateBanner()");
		try { 
			return this.adminMService.updateBanners(bannerEntity,id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	@GetMapping("/deleteBanner/{id}")
	public GlobalResponse deleteBanner(@PathVariable (value="id") Long id) {
		LOGGER.info("Inside - AdminMDataController.deleteBanner()");
		try { 
			return this.adminMService.deleteBanner(id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	

	@PutMapping("/addDesignerCategory")
	public GlobalResponse addDesignerCaregory(@RequestBody DesignerCategoryEntity designerCategoryEntity) {
		LOGGER.info("Inside - AdminMDataController.addDesignerCaregory()");
		
		try {
			return this.adminMService.addDesignerCategory(designerCategoryEntity);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	
	@PutMapping("/updateDesignerLevels/{name}")
	public GlobalResponse updateDesignerCaregorys(@RequestBody DesignerCategoryEntity designerCategoryEntity,@PathVariable (value="name") String name ) {
		LOGGER.info("Inside - AdminMDataController.updateDesignerCaregorys()");
		try {
			return this.adminMService.updateDesignerLevels(designerCategoryEntity,name);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	  @DeleteMapping("/deleteDesignerLevels/{name}") 
	  public GlobalResponse deleteDesignerCaregory(@PathVariable (value="name") String name) 
	  { 
		  LOGGER.info("Inside - AdminMDataController.deleteDesignerCaregory()");
		  try {
	            return this.adminMService.deleteDesignerLevels(name);
	          } 
		  catch(Exception e) {
	          throw new CustomException(e.getMessage());
	      }
       }
	 
	
	
	@GetMapping("/getDesignerCategory")
	public Object getDesignerList()
	{
		LOGGER.info("Inside - AdminMDataController.getDesignerList()");
		try {
			return this.adminMService.getDesignerService();
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	@GetMapping("/getDesignerCategory/{name}")
	public Map<String ,String> getDesignerCategorybyname(@PathVariable (value="name") String name)
	{
		LOGGER.info("Inside - AdminMDataController.getDesignerCategorybyname()");
		
		try {
			return this.adminMService.getDesignerCategorybyname(name);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	

	
}
