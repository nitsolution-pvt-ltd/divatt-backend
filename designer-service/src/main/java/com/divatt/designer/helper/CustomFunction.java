package com.divatt.designer.helper;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.entity.ProductEntity;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.entity.product.ProductStageDetails;
import com.divatt.designer.exception.*;
import com.divatt.designer.repo.ProductRepo2;
import com.divatt.designer.repo.ProductRepository;
import com.divatt.designer.services.ProductServiceImp2;
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

	@Autowired
	private ProductRepo2 productRepo2;

	RestTemplate restTemplate = new RestTemplate();
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomFunction.class);

	public ProductMasterEntity filterDataEntity(ProductMasterEntity productData) {

		try {
			ProductMasterEntity filterProductEntity = new ProductMasterEntity();
			filterProductEntity.setProductId(sequenceGenarator.getNextSequence(ProductMasterEntity.SEQUENCE_NAME));
			filterProductEntity.setSKQCode(randomString.getAlphaNumericString(10));
			filterProductEntity.setAge(productData.getAge());
			filterProductEntity.setCategoryId(productData.getCategoryId());
			filterProductEntity.setCod(productData.getCod());
			// filterProductEntity.setComment(productData);
			filterProductEntity.setCreatedBy(productData.getCreatedBy());
			filterProductEntity.setCreatedOn(new Date());
			filterProductEntity.setDesignerName(productData.getDesignerName());
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
			filterProductEntity.setHsnData(productData.getHsnData());
			return filterProductEntity;
			// filterProductEntity.setUpdatedBy(productData.getUpdatedBy());
			// filterProductEntity.setUpdatedOn();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProductMasterEntity updateFunction(ProductMasterEntity productData, Integer productId) {
		try {
			System.out.println(productData);
			ProductMasterEntity productEntity = productRepo.findById(productId).get();
			// System.out.println(productEntity);
			ProductMasterEntity filterProductEntity = new ProductMasterEntity();
			filterProductEntity.setProductId(productId);
			filterProductEntity.setSKQCode(productRepo.findById(productId).get().getSKQCode());
			filterProductEntity.setAge(productData.getAge());
			filterProductEntity.setDesignerName(productEntity.getDesignerName());
			// filterProductEntity.setApprovedBy(productData.getApprovedBy());
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
			filterProductEntity.setIsDeleted(productRepo.findById(productId).get().getIsDeleted());
			filterProductEntity.setIsActive(productRepo.findById(productId).get().getIsActive());
			filterProductEntity.setAdminStatus("Pending");
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
			filterProductEntity.setHsnData(productData.getHsnData());
			return filterProductEntity;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProductEntity productFilter(ProductMasterEntity productData) {
		try {
			ProductEntity filterProductEntity = new ProductEntity();
			filterProductEntity.setProductId(productData.getProductId());
			filterProductEntity.setSKQCode(productData.getSKQCode());
			filterProductEntity.setAge(productData.getAge());
			filterProductEntity.setDesignerName(productData.getDesignerName());
			// filterProductEntity.setApprovedBy(productData.getApprovedBy());
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
			filterProductEntity.setIsDeleted(productData.getIsDeleted());
			filterProductEntity.setIsActive(productData.getIsActive());
			filterProductEntity.setAdminStatus("Pending");
			filterProductEntity.setPrice(productData.getPrice());
			filterProductEntity.setPriceType(productData.getPriceType());
			filterProductEntity.setProductDescription(productData.getProductDescription());
			filterProductEntity.setProductName(productData.getProductName());
			filterProductEntity.setPurchaseQuantity(productData.getPurchaseQuantity());
			filterProductEntity.setSpecifications(productData.getSpecifications());
			filterProductEntity.setStanderedSOH(productData.getStanderedSOH());
//			filterProductEntity.setStanderedSOH(productData.getStanderedSOH());
			filterProductEntity.setSubCategoryId(productData.getSubCategoryId());
			filterProductEntity.setAdminStatusOn(productData.getAdminStatusOn());
			filterProductEntity.setTaxInclusive(productData.getTaxInclusive());
			filterProductEntity.setApprovedBy(productData.getApprovedBy());
			filterProductEntity.setApprovedOn(productData.getApprovedOn());
			filterProductEntity.setComments(productData.getComments());
			filterProductEntity.setHsnData(productData.getHsnData());
			return filterProductEntity;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProductMasterEntity2 updateProductData(ProductMasterEntity2 productMasterEntity2, Integer productId) {
		try {

			LOGGER.info("inside updateproductdata");
			ProductMasterEntity2 masterEntity2 = productRepo2.findById(productId).get();
			LOGGER.info("inside updateproductdata master" + masterEntity2);
			ProductMasterEntity2 updateMasterEntity = new ProductMasterEntity2();
			ProductStageDetails productStageDetails = new ProductStageDetails();
			updateMasterEntity.setProductId(productId);
			updateMasterEntity.setSku(productMasterEntity2.getSku());
			updateMasterEntity.setDesignerId(productMasterEntity2.getDesignerId());
			updateMasterEntity.setCategoryId(productMasterEntity2.getCategoryId());
			updateMasterEntity.setSubCategoryId(productMasterEntity2.getSubCategoryId());
			updateMasterEntity.setPurchaseMinQuantity(productMasterEntity2.getPurchaseMinQuantity());
			updateMasterEntity.setPurchaseMaxQuantity(productMasterEntity2.getPurchaseMaxQuantity());
			updateMasterEntity.setHsnCode(productMasterEntity2.getHsnCode());
			updateMasterEntity.setProductDetails(productMasterEntity2.getProductDetails());
			updateMasterEntity.setDesignCustomizationFeatures(productMasterEntity2.getDesignCustomizationFeatures());
			updateMasterEntity.setWithCustomization(productMasterEntity2.getWithCustomization());
			updateMasterEntity.setWithDesignCustomization(productMasterEntity2.getWithDesignCustomization());
			updateMasterEntity.setWithGiftWrap(productMasterEntity2.getWithGiftWrap());
			updateMasterEntity.setReturnAcceptable(productMasterEntity2.getReturnAcceptable());
			updateMasterEntity.setCancelAcceptable(productMasterEntity2.getCancelAcceptable());
			updateMasterEntity.setCod(productMasterEntity2.getCod());
			updateMasterEntity.setPriceType(productMasterEntity2.getPriceType());
			updateMasterEntity.setColour(productMasterEntity2.getColour());
			updateMasterEntity.setSizes(productMasterEntity2.getSizes());
			updateMasterEntity.setSoh(productMasterEntity2.getSoh());
			updateMasterEntity.setOos(productMasterEntity2.getOos());
			updateMasterEntity.setNotify(productMasterEntity2.getNotify());
			updateMasterEntity.setPriceCode(productMasterEntity2.getPriceCode());
			updateMasterEntity.setMrp(productMasterEntity2.getMrp());
			updateMasterEntity.setDeal(productMasterEntity2.getDeal());
			updateMasterEntity.setadminStatus("Pending");
			updateMasterEntity.setImage(productMasterEntity2.getImage());
			updateMasterEntity.setGiftWrapAmount(productMasterEntity2.getGiftWrapAmount());
			updateMasterEntity.setExtraSpecifications(productMasterEntity2.getExtraSpecifications());
			updateMasterEntity.setProductWeight(productMasterEntity2.getProductWeight());
			updateMasterEntity.setShipmentTime(productMasterEntity2.getShipmentTime());
//			updateMasterEntity.setDeal(productMasterEntity2.getDeal());
			updateMasterEntity.setIsActive(true);
			updateMasterEntity.setIsDeleted(false);

			// Product stage
			productStageDetails.setSubmittedBy(productMasterEntity2.getProductStageDetails().getSubmittedBy());
			productStageDetails.setSubmittedOn(productMasterEntity2.getProductStageDetails().getSubmittedOn());
			productStageDetails.setApprovedBy(productMasterEntity2.getProductStageDetails().getApprovedBy());
			productStageDetails.setApprovedOn(productMasterEntity2.getProductStageDetails().getApprovedOn());
			productStageDetails.setComment(productMasterEntity2.getProductStageDetails().getComment());

			updateMasterEntity.setProductStage(productMasterEntity2.getProductStage());
			updateMasterEntity.setProductStageDetails(productStageDetails);

			LOGGER.info(updateMasterEntity.toString());
			return updateMasterEntity;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProductMasterEntity2 addProductMasterData(ProductMasterEntity2 productMasterEntity2) {

		try {
			ProductMasterEntity2 filterProductEntity = new ProductMasterEntity2();
			ProductStageDetails productStageDetails = new ProductStageDetails();
			filterProductEntity.setProductId(sequenceGenarator.getNextSequence(ProductMasterEntity2.SEQUENCE_NAME));
			filterProductEntity.setSku(randomString.getAlphaNumericString(10));
			filterProductEntity.setDesignerId(productMasterEntity2.getDesignerId());
			filterProductEntity.setCategoryId(productMasterEntity2.getCategoryId());
			filterProductEntity.setSubCategoryId(productMasterEntity2.getSubCategoryId());
			filterProductEntity.setPurchaseMinQuantity(productMasterEntity2.getPurchaseMinQuantity());
			filterProductEntity.setPurchaseMaxQuantity(productMasterEntity2.getPurchaseMaxQuantity());
			filterProductEntity.setHsnCode(productMasterEntity2.getHsnCode());
			filterProductEntity.setProductDetails(productMasterEntity2.getProductDetails());
			filterProductEntity.setDesignCustomizationFeatures(productMasterEntity2.getDesignCustomizationFeatures());
			filterProductEntity.setWithCustomization(productMasterEntity2.getWithCustomization());
			filterProductEntity.setWithDesignCustomization(productMasterEntity2.getWithDesignCustomization());
			filterProductEntity.setWithGiftWrap(productMasterEntity2.getWithGiftWrap());
			filterProductEntity.setReturnAcceptable(productMasterEntity2.getReturnAcceptable());
			filterProductEntity.setCancelAcceptable(productMasterEntity2.getCancelAcceptable());
			filterProductEntity.setCod(productMasterEntity2.getCod());
			filterProductEntity.setPriceType(productMasterEntity2.getPriceType());
			filterProductEntity.setColour(productMasterEntity2.getColour());
			filterProductEntity.setSizes(productMasterEntity2.getSizes());
			filterProductEntity.setSoh(productMasterEntity2.getSoh());
			filterProductEntity.setOos(productMasterEntity2.getOos());
			filterProductEntity.setNotify(productMasterEntity2.getNotify());
			filterProductEntity.setPriceCode(productMasterEntity2.getPriceCode());
			filterProductEntity.setMrp(productMasterEntity2.getMrp());
			filterProductEntity.setDeal(productMasterEntity2.getDeal());
			filterProductEntity.setGiftWrapAmount(productMasterEntity2.getGiftWrapAmount());
			filterProductEntity.setExtraSpecifications(productMasterEntity2.getExtraSpecifications());
			filterProductEntity.setProductWeight(productMasterEntity2.getProductWeight());
			filterProductEntity.setShipmentTime(productMasterEntity2.getShipmentTime());
			filterProductEntity.setImage(productMasterEntity2.getImage());
			filterProductEntity.setIsActive(true);
			filterProductEntity.setIsDeleted(false);
			filterProductEntity.setCreatedOn(new Date());
			filterProductEntity.setCreatedBy(productMasterEntity2.getCreatedBy());
			filterProductEntity.setUpdatedOn(productMasterEntity2.getUpdatedOn());
			filterProductEntity.setUpdatedBy(productMasterEntity2.getUpdatedBy());
			filterProductEntity.setadminStatus("Pending");
			
			// Product stage
			productStageDetails.setSubmittedBy(productMasterEntity2.getProductStageDetails().getSubmittedBy());
			productStageDetails.setSubmittedOn(new Date());
			productStageDetails.setApprovedBy(productMasterEntity2.getProductStageDetails().getApprovedBy());
			productStageDetails.setApprovedOn(productMasterEntity2.getProductStageDetails().getApprovedOn());
			productStageDetails.setComment(productMasterEntity2.getProductStageDetails().getComment());
			
			filterProductEntity.setProductStage("new");
			filterProductEntity.setProductStageDetails(productStageDetails);
			return filterProductEntity;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
