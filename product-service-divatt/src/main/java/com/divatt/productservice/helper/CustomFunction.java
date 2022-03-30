package com.divatt.productservice.helper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divatt.productservice.entity.ProductMasterEntity;
import com.divatt.productservice.exception.CustomException;
import com.divatt.productservice.service.SequenceGenarator;

@Service
public class CustomFunction {

	
	@Autowired
	private SequenceGenarator sequenceGenarator;
	
	public ProductMasterEntity filterDataEntity(ProductMasterEntity productData)
	{
		try
		{
			ProductMasterEntity filterProductEntity= new ProductMasterEntity();
			filterProductEntity.setProductId(sequenceGenarator.getNextSequence(ProductMasterEntity.SEQUENCE_NAME));
			filterProductEntity.setAge(productData.getAge());
			filterProductEntity.setApprovedBy(productData.getApprovedBy());
			filterProductEntity.setCategoryId(productData.getCategoryId());
			filterProductEntity.setCod(productData.getCod());
			filterProductEntity.setColour(productData.getColour());
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
}
