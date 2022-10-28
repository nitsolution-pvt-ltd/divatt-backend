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

			LOGGER.info(updateMasterEntity.toString());
			return updateMasterEntity;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
