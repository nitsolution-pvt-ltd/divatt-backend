package com.divatt.admin.contoller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.ColourEntity;
import com.divatt.admin.entity.ColourMetaEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.AdminMService;

@RestController
@RequestMapping("/adminMData")
public class AdminMDataController {

	@Autowired
	private AdminMService adminMService;
	
	@GetMapping("/coloreList")
	public List<ColourEntity> colourList()
	{
		try
		{
			return this.adminMService.getColour();
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@PutMapping("/addColour")
	public GlobalResponse addColore(@RequestBody ColourEntity colourEntity)
	{
		try {
			return this.adminMService.addColour(colourEntity);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/tblList")
	public Map<String, Object> getColourDetails(			
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, 
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "colors") String metaKey, 			
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy)
	{
		try {
			return this.adminMService.tblList(page,metaKey,keyword,limit,sort,sortBy,sortName);
		}
		catch(Exception e ) {
			throw new CustomException(e.getMessage());
		}
	}
}
