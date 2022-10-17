package com.divatt.designer.utill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;

import com.divatt.designer.exception.CustomException;
import com.divatt.designer.requestDTO.SearchingFilterDTO;

public class UtillClass {

	private UtillClass() {
		super();
	}
	public static SearchingFilterDTO searchingFilter
	(String designerId, String categoryId, String subCategoryId, String colour, Boolean cod,   Boolean customization, Boolean returnStatus,Boolean priceType, String maxPrice, String minPrice,String size) {
		try {
			//JSONObject jsonObject= new JSONObject();
			List<String> designerIdList= new ArrayList<String>(Arrays.asList(designerId.split(",")));
			List<String> categoryList= new ArrayList<String>(Arrays.asList(categoryId.split(",")));
			List<String> subCategoryList= new ArrayList<String>(Arrays.asList(subCategoryId.split(",")));
			List<String> colourList= new ArrayList<String>(Arrays.asList(colour.split(",")));
			List<String> sizeList= new ArrayList<String>(Arrays.asList(size.split(",")));
			return new SearchingFilterDTO(designerIdList,categoryList,subCategoryList,colourList,sizeList,cod,customization,returnStatus,priceType,maxPrice,minPrice);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
