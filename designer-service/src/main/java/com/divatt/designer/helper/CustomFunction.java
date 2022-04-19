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
	
	
	private static final String baseURL="http://localhost:8083/dev/dbservice-product/";
	RestTemplate restTemplate= new  RestTemplate();
	public ProductMasterEntity filterDataEntity(ProductMasterEntity productData)
	{
		try
		{
			ProductMasterEntity filterProductEntity= new ProductMasterEntity();
			filterProductEntity.setProductId(sequenceGenarator.getNextSequence(ProductMasterEntity.SEQUENCE_NAME));
			filterProductEntity.setSKQCode(randomString.getAlphaNumericString(10));
			filterProductEntity.setAge(productData.getAge());
			filterProductEntity.setApprovedBy(productData.getApprovedBy());
			filterProductEntity.setCategoryId(productData.getCategoryId());
			filterProductEntity.setCod(productData.getCod());
			filterProductEntity.setComment(productData.getComment());
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
			filterProductEntity.setIsActive(true);
			filterProductEntity.setIsApprove(productData.getIsApprove());
			filterProductEntity.setTaxPercentage(productData.getTaxPercentage());
			filterProductEntity.setIsDeleted(false);
			filterProductEntity.setIsSubmitted(true);
			filterProductEntity.setPrice(productData.getPrice());
			filterProductEntity.setPriceType(productData.getPriceType());
			filterProductEntity.setProductDescription(productData.getProductDescription());
			filterProductEntity.setProductName(productData.getProductName());
			filterProductEntity.setPurchaseQuantity(productData.getPurchaseQuantity());
			filterProductEntity.setSpecifications(productData.getSpecifications());
			filterProductEntity.setStanderedSOH(productData.getStanderedSOH());
			filterProductEntity.setSubCategoryId(productData.getSubCategoryId());
			filterProductEntity.setSubmittedBy(productData.getSubmittedBy());
			filterProductEntity.setSubmittedOn(new Date());
			filterProductEntity.setTaxInclusive(productData.getTaxInclusive());
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
			filterProductEntity.setApprovedBy(productData.getApprovedBy());
			filterProductEntity.setCategoryId(productData.getCategoryId());
			filterProductEntity.setCod(productData.getCod());
			filterProductEntity.setComment(productData.getComment());
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
			filterProductEntity.setIsActive(true);
			filterProductEntity.setIsApprove(productData.getIsApprove());
			filterProductEntity.setTaxPercentage(productData.getTaxPercentage());
			filterProductEntity.setIsDeleted(false);
			filterProductEntity.setIsSubmitted(true);
			filterProductEntity.setPrice(productData.getPrice());
			filterProductEntity.setPriceType(productData.getPriceType());
			filterProductEntity.setProductDescription(productData.getProductDescription());
			filterProductEntity.setProductName(productData.getProductName());
			filterProductEntity.setPurchaseQuantity(productData.getPurchaseQuantity());
			filterProductEntity.setSpecifications(productData.getSpecifications());
			filterProductEntity.setStanderedSOH(productData.getStanderedSOH());
			filterProductEntity.setSubCategoryId(productData.getSubCategoryId());
			filterProductEntity.setSubmittedBy(productData.getSubmittedBy());
			filterProductEntity.setSubmittedOn(new Date());
			filterProductEntity.setTaxInclusive(productData.getTaxInclusive());
			return filterProductEntity;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
