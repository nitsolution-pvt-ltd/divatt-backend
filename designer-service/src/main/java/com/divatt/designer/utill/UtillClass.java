package com.divatt.designer.utill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.divatt.designer.dto.SearchingFilterDTO;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.exception.CustomException;

public class UtillClass {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UtillClass.class);

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
	public static void productListColour(List<ProductMasterEntity> filteredProducts,List<String> colore){
		
		LOGGER.info("Inside - UtillClass.productListColour()");
		LOGGER.info("count of the data = {}",filteredProducts.size());
//		colore.stream()
//				.filter(color -> filteredProducts.stream()
//						.anyMatch(product -> Arrays.asList(product.getImages()).stream().anyMatch(image -> Optional
//								.ofNullable(image.getColour()).filter(image1 -> image1.equals("#"+color)).isPresent()))).forEach(System.out::println);
		
		filteredProducts.stream().filter(product -> colore.stream()
				.anyMatch(color -> Arrays.asList(product.getImages()).stream().anyMatch(image -> Optional
						.ofNullable(image.getColour()).filter(image1 -> image1.equals("#" + color)).isPresent())))
		.collect(Collectors.toList());
		
		List<String> color1 = colore.stream().map(color -> "#" + color).collect(Collectors.toList());
		
		long count = filteredProducts.stream()
				.filter(product -> Arrays.asList(product.getImages()).stream().anyMatch(
						image -> color1.stream().filter(colour2 -> colour2.equals(image.getColour())).count() > 0))
				.count();
	
	LOGGER.info("Count = {}", count);
		
//		Product.stream.filter(prod->
//        Prod.getimage.stream.filter(color->
//                  Colorarray.contain(color)
//        
//        )
//)
	}
	
//	public static void productListBySize(List<ProductMasterEntity> filteredProducts, ) {
//		
//	}
}
