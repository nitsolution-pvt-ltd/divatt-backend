package com.divatt.productservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divatt.productservice.entity.ProductMasterEntity;
import com.divatt.productservice.exception.CustomException;
import com.divatt.productservice.repo.ProductRepository;
import com.divatt.productservice.response.GlobalResponce;
import com.google.common.base.Optional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private SequenceGenarator sequenceGenarator;
	public List<ProductMasterEntity> allList() {
		try
		{
			return productRepo.findAll();
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponce addData(ProductMasterEntity productData)
	{
		try
		{
			Optional<ProductMasterEntity> findByProductName=productRepo.findByProductName(productData.getProductName());
			if(findByProductName.isPresent())
			{
				return new GlobalResponce("ERROR", "Product Already Exist!", 400);
			}
			else
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
				//filterProductEntity.setProductId();
				filterProductEntity.setProductName(productData.getProductName());
				filterProductEntity.setPurchaseQuantity(productData.getPurchaseQuantity());
				filterProductEntity.setSpecifications(productData.getSpecifications());
				filterProductEntity.setStanderedSOH(productData.getStanderedSOH());
				filterProductEntity.setSubCategoryId(productData.getSubCategoryId());
				filterProductEntity.setSubmittedBy(productData.getSubmittedBy());
				filterProductEntity.setSubmittedOn(new Date());
				filterProductEntity.setTaxInclusive(productData.getTaxInclusive());
				//filterProductEntity.setUpdatedBy(productData.getUpdatedBy());
				//filterProductEntity.setUpdatedOn();
				productRepo.save(filterProductEntity);
				return new GlobalResponce("Success", "Product Added Successfully", 200);
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
