package com.divatt.designer.utill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

import com.divatt.designer.entity.product.ImagesEntity;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.requestDTO.SearchingFilterDTO;

public class UtillClass {

	private UtillClass() {
		super();
	}
	public static SearchingFilterDTO searchingFilter
	(String designerId, String categoryId, String subCategoryId, String colour, Boolean cod,   
			Boolean customization, Boolean returnStatus,Boolean priceType, String maxPrice, String minPrice,String size) {
		try {
			//JSONObject jsonObject= new JSONObject();
			List<String> designerIdList= new ArrayList<String>(Arrays.asList(designerId.split(",")));
			List<String> categoryList= new ArrayList<String>(Arrays.asList(categoryId.split(",")));
			List<String> subCategoryList= new ArrayList<String>(Arrays.asList(subCategoryId.split(",")));
			List<String> colourList= new ArrayList<String>(Arrays.asList(colour.split(",")));
			List<String> sizeList= new ArrayList<String>(Arrays.asList(size.split(",")));
			return new SearchingFilterDTO
					(designerIdList,categoryList,subCategoryList,colourList,sizeList,
							cod,customization,returnStatus,priceType,maxPrice,minPrice);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	public static List<ProductMasterEntity> productListByCategory(List<ProductMasterEntity> filteredProduct,List<String> categoryIdList){
		return filteredProduct.stream()
				.filter(product->categoryIdList
						.stream()
						.anyMatch(categoryId->product.getCategoryId()==Integer.parseInt(categoryId)))
				.collect(Collectors.toList());
	}
	public static List<ProductMasterEntity> productListByDrsignerId(List<ProductMasterEntity> filteredProduct,List<String> designerIdList){
		return filteredProduct.stream()
				.filter(product->designerIdList.stream()
						.anyMatch(designerId->product.getDesignerId()==Integer.parseInt(designerId)))
				.collect(Collectors.toList());
	}
	public static List<ProductMasterEntity> productListColour(List<ProductMasterEntity> filteredProduct,List<String> colore){
		List<ImagesEntity> imagesEntities= new ArrayList<>();
		 filteredProduct
				.stream()
				.filter(product->
					Collections.addAll(imagesEntities, product.getImages())).close();
		 
	}
}
