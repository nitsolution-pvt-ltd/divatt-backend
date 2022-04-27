package com.divatt.designer.helper;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.exception.*;
import com.divatt.designer.repo.ProductRepository;
import com.divatt.designer.services.SequenceGenerator;

import io.swagger.models.HttpMethod;
import net.bytebuddy.utility.RandomString;

@Service
public class CustomFunction {

	
	@Autowired
	private SequenceGenerator sequenceGenarator;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CustomRandomString randomString;
	
	RestTemplate restTemplate= new  RestTemplate();
	public ProductMasterEntity filterDataEntity(ProductMasterEntity productData)
	{
		try
		{
			ProductMasterEntity filterProductEntity= new ProductMasterEntity();
			filterProductEntity.setProductId(sequenceGenarator.getNextSequence(ProductMasterEntity.SEQUENCE_NAME));
			filterProductEntity.setSKQCode(randomString.getAlphaNumericString(10));
			filterProductEntity.setAge(productData.getAge());
			filterProductEntity.setCategoryId(productData.getCategoryId());
			filterProductEntity.setCod(productData.getCod());
			//filterProductEntity.setComment(productData);
			filterProductEntity.setCreatedBy(productData.getCreatedBy());
			filterProductEntity.setCreatedOn(new Date());
			filterProductEntity.setCustomization(productData.getCustomization());
			filterProductEntity.setCustomizationSOH(productData.getCustomizationSOH());
			filterProductEntity.setDesignerId(productData.getDesignerId());
			filterProductEntity.setExtraSpecifications(productData.getExtraSpecifications());
			filterProductEntity.setGender(productData.getGender());
			filterProductEntity.setGiftWrapAmount(productData.getGiftWrapAmount());
			filterProductEntity.setGiftWrap(productData.getGiftWrap());
			filterProductEntity.setImages(productData.getImages());
			filterProductEntity.setIsActive(false);
			filterProductEntity.setTaxPercentage(productData.getTaxPercentage());
			filterProductEntity.setIsDeleted(false);
			filterProductEntity.setPrice(productData.getPrice());
			filterProductEntity.setPriceType(productData.getPriceType());
			filterProductEntity.setProductDescription(productData.getProductDescription());
			filterProductEntity.setProductName(productData.getProductName());
			filterProductEntity.setPurchaseQuantity(productData.getPurchaseQuantity());
			filterProductEntity.setSpecifications(productData.getSpecifications());
			filterProductEntity.setStanderedSOH(productData.getStanderedSOH());
			filterProductEntity.setSubCategoryId(productData.getSubCategoryId());
			filterProductEntity.setAdminStatusOn(new Date());
			filterProductEntity.setTaxInclusive(productData.getTaxInclusive());
			filterProductEntity.setAdminStatus("Pending");
			return filterProductEntity;
			//filterProductEntity.setUpdatedBy(productData.getUpdatedBy());
			//filterProductEntity.setUpdatedOn();
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public ProductMasterEntity updateFunction(ProductMasterEntity productData,Integer productId)
	{
		try
		{
			ProductMasterEntity filterProductEntity= new ProductMasterEntity();
			filterProductEntity.setProductId(productId);
			filterProductEntity.setSKQCode(productRepo.findById(productId).get().getSKQCode());
			filterProductEntity.setAge(productData.getAge());
			//filterProductEntity.setApprovedBy(productData.getApprovedBy());
			filterProductEntity.setComments(productData.getComments());
			filterProductEntity.setCategoryId(productData.getCategoryId());
			filterProductEntity.setCod(productData.getCod());
			filterProductEntity.setCreatedBy(productData.getCreatedBy());
			filterProductEntity.setCreatedOn(new Date());
			filterProductEntity.setCustomization(productData.getCustomization());
			filterProductEntity.setCustomizationSOH(productData.getCustomizationSOH());
			filterProductEntity.setDesignerId(productData.getDesignerId());
			filterProductEntity.setExtraSpecifications(productData.getExtraSpecifications());
			filterProductEntity.setGender(productData.getGender());
			filterProductEntity.setGiftWrapAmount(productData.getGiftWrapAmount());
			filterProductEntity.setGiftWrap(productData.getGiftWrap());
			filterProductEntity.setImages(productData.getImages());
			filterProductEntity.setTaxPercentage(productData.getTaxPercentage());
			filterProductEntity.setIsDeleted(false);
			filterProductEntity.setIsActive(productData.getIsActive());
			filterProductEntity.setAdminStatus(productData.getAdminStatus());
			filterProductEntity.setPrice(productData.getPrice());
			filterProductEntity.setPriceType(productData.getPriceType());
			filterProductEntity.setProductDescription(productData.getProductDescription());
			filterProductEntity.setProductName(productData.getProductName());
			filterProductEntity.setPurchaseQuantity(productData.getPurchaseQuantity());
			filterProductEntity.setSpecifications(productData.getSpecifications());
			filterProductEntity.setStanderedSOH(productData.getStanderedSOH());
			filterProductEntity.setSubCategoryId(productData.getSubCategoryId());
			filterProductEntity.setAdminStatusOn(productData.getAdminStatusOn());
			filterProductEntity.setTaxInclusive(productData.getTaxInclusive());
			filterProductEntity.setApprovedBy(productData.getApprovedBy());
			filterProductEntity.setApprovedOn(productData.getApprovedOn());
			filterProductEntity.setComments(productData.getComments());
			return filterProductEntity;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
