package com.divatt.designer.helper;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.constant.MessageConstant;
import com.divatt.designer.entity.ProductEntity;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.entity.product.ProductStageDetails;
import com.divatt.designer.entity.profile.BoutiqueProfile;
import com.divatt.designer.entity.profile.DesignerLoginEntity;
import com.divatt.designer.entity.profile.DesignerProfile;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.DesignerProfileRepo;
import com.divatt.designer.repo.ProductRepo2;
import com.divatt.designer.repo.ProductRepository;
import com.divatt.designer.services.SequenceGenerator;

@Service
public class CustomFunction {

	@Autowired
	private SequenceGenerator sequenceGenarator;

	@Autowired
	private ProductRepository productRepo;

//	@Autowired
//	private CustomRandomString randomString;

	@Autowired
	private ProductRepo2 productRepo2;

	@Autowired
	private DesignerProfileRepo designerProfileRepo;

	ProductMasterEntity2 productMasterEntity2;

	RestTemplate restTemplate = new RestTemplate();
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomFunction.class);

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
	private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

	public String toSlug(String input) {
		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		slug = EDGESDHASHES.matcher(slug).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH);
	}

	public ProductMasterEntity filterDataEntity(ProductMasterEntity productData) {

		try {
			ProductMasterEntity filterProductEntity = new ProductMasterEntity();
			filterProductEntity.setProductId(sequenceGenarator.getNextSequence(ProductMasterEntity.SEQUENCE_NAME));
			filterProductEntity.setSKQCode(CustomRandomString.getAlphaNumericString(10));
			filterProductEntity.setAge(productData.getAge());
			filterProductEntity.setCategoryId(productData.getCategoryId());
			filterProductEntity.setCod(productData.getCod());
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
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProductMasterEntity updateFunction(ProductMasterEntity productData, Integer productId) {
		try {
			ProductMasterEntity productEntity = productRepo.findById(productId).get();
			ProductMasterEntity filterProductEntity = new ProductMasterEntity();
			filterProductEntity.setProductId(productId);
			filterProductEntity.setSKQCode(productRepo.findById(productId).get().getSKQCode());
			filterProductEntity.setAge(productData.getAge());
			filterProductEntity.setDesignerName(productEntity.getDesignerName());
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

			String slug = this.toSlug(productMasterEntity2.getProductDetails().getProductName());
			String alphaNumericString = CustomRandomString.getAlphaNumericString(10);
			ProductMasterEntity2 updateMasterEntity = productRepo2.findById(productId).get();

//			ProductMasterEntity2 updateMasterEntity = new ProductMasterEntity2();
			ProductStageDetails productStageDetails = new ProductStageDetails();
//			updateMasterEntity.setProductId(productId);
//			updateMasterEntity.setSku(updateMasterEntity.getSku());
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
			if (updateMasterEntity.getAdminStatus().equals("Pending")) {
				updateMasterEntity.setAdminStatus("Pending");
			} else if (updateMasterEntity.getAdminStatus().equals("Approved")) {
				updateMasterEntity.setAdminStatus("Approved");
			} else {
				updateMasterEntity.setAdminStatus("Pending");
			}

			updateMasterEntity.setWeightUnit(productMasterEntity2.getWeightUnit());
			updateMasterEntity.setImages(productMasterEntity2.getImages());
			updateMasterEntity.setGiftWrapAmount(productMasterEntity2.getGiftWrapAmount());
			updateMasterEntity.setExtraSpecifications(productMasterEntity2.getExtraSpecifications());
			updateMasterEntity.setProductWeight(productMasterEntity2.getProductWeight());
			updateMasterEntity.setShipmentTime(productMasterEntity2.getShipmentTime());
			updateMasterEntity.setDeal(productMasterEntity2.getDeal());
			updateMasterEntity.setIsActive(true);
			updateMasterEntity.setIsDeleted(false);
			updateMasterEntity.setSlug(slug + "-itm" + alphaNumericString.toLowerCase());

			// Product stage
			productStageDetails.setSubmittedBy(productMasterEntity2.getProductStageDetails().getSubmittedBy());
			productStageDetails.setSubmittedOn(productMasterEntity2.getProductStageDetails().getSubmittedOn());
			productStageDetails.setApprovedBy(productMasterEntity2.getProductStageDetails().getApprovedBy());
			productStageDetails.setApprovedOn(productMasterEntity2.getProductStageDetails().getApprovedOn());
			productStageDetails.setComment(productMasterEntity2.getProductStageDetails().getComment());

			updateMasterEntity.setProductStage(productMasterEntity2.getProductStage());
			updateMasterEntity.setProductStageDetails(productStageDetails);

			return updateMasterEntity;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProductMasterEntity2 addProductMasterData(ProductMasterEntity2 filterProductEntity) {

		try {
			String slug = this.toSlug(filterProductEntity.getProductDetails().getProductName());
			String alphaNumericString = CustomRandomString.getAlphaNumericString(10);
//			ProductMasterEntity2 filterProductEntity = new ProductMasterEntity2();
			ProductStageDetails productStageDetails = new ProductStageDetails();
			filterProductEntity.setProductId(sequenceGenarator.getNextSequence(ProductMasterEntity2.SEQUENCE_NAME));
			filterProductEntity.setSku(alphaNumericString);
//			filterProductEntity.setDesignerId(filterProductEntity.getDesignerId());
//			filterProductEntity.setCategoryId(filterProductEntity.getCategoryId());
//			filterProductEntity.setSubCategoryId(productMasterEntity2.getSubCategoryId());
//			filterProductEntity.setPurchaseMinQuantity(productMasterEntity2.getPurchaseMinQuantity());
//			filterProductEntity.setPurchaseMaxQuantity(productMasterEntity2.getPurchaseMaxQuantity());
//			filterProductEntity.setHsnCode(productMasterEntity2.getHsnCode());
//			filterProductEntity.setProductDetails(productMasterEntity2.getProductDetails());
//			filterProductEntity.setDesignCustomizationFeatures(productMasterEntity2.getDesignCustomizationFeatures());
//			filterProductEntity.setWithCustomization(productMasterEntity2.getWithCustomization());
//			filterProductEntity.setWithDesignCustomization(productMasterEntity2.getWithDesignCustomization());
//			filterProductEntity.setWithGiftWrap(productMasterEntity2.getWithGiftWrap());
//			filterProductEntity.setReturnAcceptable(productMasterEntity2.getReturnAcceptable());
//			filterProductEntity.setCancelAcceptable(productMasterEntity2.getCancelAcceptable());
//			filterProductEntity.setCod(productMasterEntity2.getCod());
//			filterProductEntity.setPriceType(productMasterEntity2.getPriceType());
//			filterProductEntity.setColour(productMasterEntity2.getColour());
//			filterProductEntity.setSizes(productMasterEntity2.getSizes());
//			filterProductEntity.setSoh(productMasterEntity2.getSoh());
//			filterProductEntity.setOos(productMasterEntity2.getOos());
//			filterProductEntity.setNotify(productMasterEntity2.getNotify());
//			filterProductEntity.setPriceCode(productMasterEntity2.getPriceCode());
//			filterProductEntity.setMrp(productMasterEntity2.getMrp());
//			filterProductEntity.setDeal(productMasterEntity2.getDeal());
//			filterProductEntity.setGiftWrapAmount(productMasterEntity2.getGiftWrapAmount());
//			filterProductEntity.setExtraSpecifications(productMasterEntity2.getExtraSpecifications());
//			filterProductEntity.setProductWeight(productMasterEntity2.getProductWeight());
//			filterProductEntity.setShipmentTime(productMasterEntity2.getShipmentTime());
//			filterProductEntity.setImages(productMasterEntity2.getImages());
//			filterProductEntity.setWeightUnit(productMasterEntity2.getWeightUnit());
			filterProductEntity.setIsActive(true);
			filterProductEntity.setIsDeleted(false);
			filterProductEntity.setCreatedOn(new Date());
//			filterProductEntity.setCreatedBy(productMasterEntity2.getCreatedBy());
//			filterProductEntity.setUpdatedOn(productMasterEntity2.getUpdatedOn());
//			filterProductEntity.setUpdatedBy(productMasterEntity2.getUpdatedBy());
			filterProductEntity.setAdminStatus("Pending");
			filterProductEntity.setSlug(slug + "-itm" + alphaNumericString.toLowerCase());

			// Product stage
			productStageDetails.setSubmittedBy(filterProductEntity.getProductStageDetails().getSubmittedBy());
			productStageDetails.setSubmittedOn(new Date());
			productStageDetails.setApprovedBy(filterProductEntity.getProductStageDetails().getApprovedBy());
			productStageDetails.setApprovedOn(filterProductEntity.getProductStageDetails().getApprovedOn());
			productStageDetails.setComment(filterProductEntity.getProductStageDetails().getComment());

			filterProductEntity.setProductStage("new");
			filterProductEntity.setProductStageDetails(productStageDetails);

			return filterProductEntity;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public DesignerProfileEntity designerProfileEntity(DesignerLoginEntity designerLoginEntity) {
		try {

			Long getdId = designerLoginEntity.getdId();
			DesignerProfileEntity body = designerProfileRepo.findBydesignerId(getdId).get();
			DesignerProfileEntity designerProfileEntity = new DesignerProfileEntity();
			DesignerProfile designerProfile = new DesignerProfile();
			BoutiqueProfile boutiqueProfile = new BoutiqueProfile();

			designerProfileEntity.setId(body.getId());
			designerProfileEntity.setAccountStatus(body.getAccountStatus());
			designerProfileEntity.setBoutiqueProfile(body.getBoutiqueProfile());
			designerProfileEntity.setDesignerId(body.getDesignerId());
			designerProfileEntity.setDesignerPersonalInfoEntity(body.getDesignerPersonalInfoEntity());
			designerProfileEntity.setDesignerLevel(body.getDesignerLevel());
			designerProfileEntity.setFollowerCount(body.getFollowerCount());
			designerProfileEntity.setIsDeleted(designerLoginEntity.getIsDeleted());
			designerProfileEntity.setProductCount(body.getProductCount());
			designerProfileEntity.setMenChartData(body.getMenChartData());
			designerProfileEntity.setWomenChartData(body.getWomenChartData());
			designerProfileEntity.setDesignerCurrentStatus(body.getDesignerCurrentStatus());
			designerProfileEntity.setDesignerName(body.getDesignerName());
			designerProfileEntity.setProfileStatus(designerLoginEntity.getProfileStatus());
			designerProfileEntity.setSocialProfile(body.getSocialProfile());
			designerProfileEntity.setIsProfileCompleted(designerLoginEntity.getIsProfileCompleted());
			designerProfileEntity.setUid(designerLoginEntity.getUid());

			designerProfile.setDesignerCategory(designerLoginEntity.getDesignerCategory());
			designerProfile.setDisplayName(designerLoginEntity.getDisplayName());
			designerProfile.setAltMobileNo(body.getDesignerProfile().getAltMobileNo());
			designerProfile.setCity(body.getDesignerProfile().getCity());
			designerProfile.setCountry(body.getDesignerProfile().getCountry());
			designerProfile.setDob(body.getDesignerProfile().getDob());
			if ((designerLoginEntity.getProfileStatus().equals("SUBMITTED")
					|| designerLoginEntity.getProfileStatus().equals("COMPLETED")
					|| designerLoginEntity.getProfileStatus().equals("SAVED"))) {
				boutiqueProfile
						.setGSTIN(designerLoginEntity.getDesignerProfileEntity().getBoutiqueProfile().getGSTIN());
			} else {
				boutiqueProfile.setGSTIN(body.getBoutiqueProfile().getGSTIN());
			}
			boutiqueProfile.setBoutiqueName(body.getBoutiqueProfile().getBoutiqueName());
			boutiqueProfile.setExperience(body.getBoutiqueProfile().getExperience());
			boutiqueProfile.setFirmName(body.getBoutiqueProfile().getFirmName());
//			boutiqueProfile.setOperatingCity(body.getBoutiqueProfile().getOperatingCity());
			boutiqueProfile.setArea(body.getBoutiqueProfile().getArea());
			boutiqueProfile.setProfessionalCategory(body.getBoutiqueProfile().getProfessionalCategory());
			boutiqueProfile.setYearOfOperation(body.getBoutiqueProfile().getYearOfOperation());
			if ((designerLoginEntity.getProfileStatus().equals("SUBMITTED")
					|| designerLoginEntity.getProfileStatus().equals("COMPLETED")
					|| designerLoginEntity.getProfileStatus().equals("SAVED"))) {
				designerProfile.setDigitalSignature(
						designerLoginEntity.getDesignerProfileEntity().getDesignerProfile().getDigitalSignature());
			} else {
				designerProfile.setDigitalSignature(body.getDesignerProfile().getDigitalSignature());
			}
			designerProfile.setEmail(body.getDesignerProfile().getEmail());
			designerProfile.setFirstName1(body.getDesignerProfile().getFirstName1());
			designerProfile.setLastName1(body.getDesignerProfile().getLastName1());
			designerProfile.setFirstName2(body.getDesignerProfile().getFirstName2());
			designerProfile.setLastName2(body.getDesignerProfile().getLastName2());
			designerProfile.setGender(body.getDesignerProfile().getGender());
			designerProfile.setMobileNo(body.getDesignerProfile().getMobileNo());
			designerProfile.setPassword(body.getDesignerProfile().getPassword());
			designerProfile.setProfilePic(body.getDesignerProfile().getProfilePic());
			designerProfile.setPinCode(body.getDesignerProfile().getPinCode());
			designerProfile.setState(body.getDesignerProfile().getState());
			designerProfile.setUid(body.getDesignerProfile().getUid());

			designerProfileEntity.setDesignerProfile(designerProfile);
			designerProfileEntity.setBoutiqueProfile(boutiqueProfile);

			return designerProfileEntity;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ProductMasterEntity2 setProduDetails(ProductMasterEntity2 productMasterEntity2, Integer qty) {
		try {
			this.productMasterEntity2 = new ProductMasterEntity2();
			this.productMasterEntity2.setProductId(productMasterEntity2.getProductId());
			this.productMasterEntity2.setSku(productMasterEntity2.getSku());
			this.productMasterEntity2.setDesignerId(productMasterEntity2.getDesignerId());
			this.productMasterEntity2.setCategoryId(productMasterEntity2.getCategoryId());
			this.productMasterEntity2.setSubCategoryId(productMasterEntity2.getSubCategoryId());
			this.productMasterEntity2.setPurchaseMaxQuantity(productMasterEntity2.getPurchaseMaxQuantity());
			this.productMasterEntity2.setPurchaseMinQuantity(productMasterEntity2.getPurchaseMinQuantity());
			this.productMasterEntity2.setHsnCode(productMasterEntity2.getHsnCode());
			this.productMasterEntity2.setProductDetails(productMasterEntity2.getProductDetails());
			this.productMasterEntity2.setWithCustomization(productMasterEntity2.getWithCustomization());
			this.productMasterEntity2.setWithDesignCustomization(productMasterEntity2.getWithDesignCustomization());
			this.productMasterEntity2.setWithGiftWrap(productMasterEntity2.getWithGiftWrap());
			this.productMasterEntity2.setReturnAcceptable(productMasterEntity2.getReturnAcceptable());
			this.productMasterEntity2.setCancelAcceptable(productMasterEntity2.getCancelAcceptable());
			this.productMasterEntity2.setCod(productMasterEntity2.getCod());
			this.productMasterEntity2.setPriceType(productMasterEntity2.getPriceType());
			this.productMasterEntity2.setColour(productMasterEntity2.getColour());
			this.productMasterEntity2.setSizes(productMasterEntity2.getSizes());
			if (productMasterEntity2.getSoh() == 0) {
				throw new CustomException(MessageConstant.PRODUCT_IS_OUT_OF_STOCK.getMessage());
			} else {
				this.productMasterEntity2.setSoh(productMasterEntity2.getSoh() - qty);
				this.productMasterEntity2.setOos(productMasterEntity2.getOos() + qty);
			}
			this.productMasterEntity2.setNotify(productMasterEntity2.getNotify());
			this.productMasterEntity2.setPriceCode(productMasterEntity2.getPriceCode());
			this.productMasterEntity2.setMrp(productMasterEntity2.getMrp());
			this.productMasterEntity2.setWeightUnit(productMasterEntity2.getWeightUnit());
			this.productMasterEntity2.setAdminStatus(productMasterEntity2.getAdminStatus());
			this.productMasterEntity2.setDeal(productMasterEntity2.getDeal());
			this.productMasterEntity2.setGiftWrapAmount(productMasterEntity2.getGiftWrapAmount());
			this.productMasterEntity2.setExtraSpecifications(productMasterEntity2.getExtraSpecifications());
			this.productMasterEntity2.setProductWeight(productMasterEntity2.getProductWeight());
			this.productMasterEntity2.setShipmentTime(productMasterEntity2.getShipmentTime());
			this.productMasterEntity2.setImages(productMasterEntity2.getImages());
			this.productMasterEntity2.setIsActive(productMasterEntity2.getIsActive());
			this.productMasterEntity2.setIsDeleted(productMasterEntity2.getIsDeleted());
			this.productMasterEntity2.setProductStage(productMasterEntity2.getProductStage());
			this.productMasterEntity2.setProductStageDetails(productMasterEntity2.getProductStageDetails());
			this.productMasterEntity2.setCreatedOn(productMasterEntity2.getCreatedOn());
			this.productMasterEntity2
					.setDesignCustomizationFeatures(productMasterEntity2.getDesignCustomizationFeatures());

			return this.productMasterEntity2;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<ProductMasterEntity2> filterProduct(List<ProductMasterEntity2> unFilterList, String searchBy,
			String designerId, String categoryId, String subCategoryId, String colour, Boolean cod,
			Boolean customization, String priceType, Boolean returnStatus, String maxPrice, String minPrice,
			String size, Boolean giftWrap, String searchKey, String sortDateType, String sortPrice, String labelType) {
		List<ProductMasterEntity2> list = unFilterList.stream()
				.filter(product -> cod != null ? product.getCod() == cod : true)
				.filter(product -> customization != null ? product.getWithCustomization() == customization : true)
				.filter(product -> !priceType.equals("") ? product.getPriceType().equals(priceType) : true)
				.filter(product -> giftWrap != null ? product.getWithGiftWrap() == giftWrap
						: true)
				.filter(product -> !maxPrice.equals("-1") ? product.getMrp() <= Long.parseLong(maxPrice) : true)
				.filter(product -> !minPrice.equals("-1") ? product.getMrp() >= Long.parseLong(minPrice) : true)
				.filter(product -> !size.equals("") ? Arrays.asList(size.split(",")).stream()
						.anyMatch(s -> product.getSizes().stream().anyMatch(sizee -> sizee.equals(s))) : true)
				// .filter(product -> !colour.equals("")
				// ? Arrays.asList(colour.split(",")).stream()
				// .anyMatch(color -> Arrays.asList(product.getImages()).stream()
				// .anyMatch(image -> Optional.ofNullable(image.getLarge())
				// .filter(image1 -> image1.equals("#" + color)).isPresent()))
				// : true)
				.filter(product -> !colour.equals("")
						? Arrays.asList(colour.split(",")).stream().anyMatch(color -> product.getColour().equals(color))
						: true)
				.filter(product -> !categoryId.equals("")
						? Arrays.asList(categoryId.split(",")).stream().anyMatch(
								category -> category.equals(product.getCategoryId().toString()))
						: true)
				.filter(product -> !subCategoryId.equals("") ? Arrays.asList(subCategoryId.split(",")).stream()
						.anyMatch(subCategory -> subCategory.equals(product.getSubCategoryId().toString())) : true)
				.filter(product -> !designerId.equals("") ? Arrays.asList(designerId.split(",")).stream()
						.anyMatch(dId -> Integer.parseInt(dId) == product.getDesignerId()) : true)
				.filter(e->e.getSoh()!=0)
				.filter(product-> product.getIsActive().equals(true))
				.collect(Collectors.toList());

		if (sortDateType.equalsIgnoreCase("new")) {
			Collections.sort(list, Comparator.comparing(ProductMasterEntity2::getCreatedOn).reversed());
		} else if (sortDateType.equalsIgnoreCase("old")) {
			Collections.sort(list, Comparator.comparing(ProductMasterEntity2::getCreatedOn));
		}
		if (sortPrice.equalsIgnoreCase("lowToHigh")) {
			Collections.sort(list,
					Comparator.comparing(product -> product.getDeal().getSalePrice() == null ? product.getMrp()
							: product.getDeal().getSalePrice()));
		} else if (sortPrice.equalsIgnoreCase("highToLow")) {
			Collections.sort(list,
					Comparator.comparing(product -> product.getDeal().getSalePrice() == null ? product.getMrp()
							: product.getDeal().getSalePrice()));
			Collections.reverse(list);
		}
		if(labelType.equals("")) {
		list.forEach(element -> {
			DesignerProfile designerProfile = designerProfileRepo
					.findBydesignerId(Long.parseLong(element.getDesignerId().toString())).get().getDesignerProfile();
			if (designerProfile.getDesignerCategory().toLowerCase().equals(MessageConstant.POP.getMessage())) {
				element.setDesignerProfile(designerProfile);
			}
		});
		}else {
			list.forEach(element -> {
				DesignerProfile designerProfile = designerProfileRepo
						.findBydesignerId(Long.parseLong(element.getDesignerId().toString())).get().getDesignerProfile();
					element.setDesignerProfile(designerProfile);
			});
		}
		list.removeIf(element -> element.getDesignerProfile() == null);
        list.removeIf(e-> e.getDesignerProfile().getDesignerCategory().equals("Neo"));
		return list;
	}
}
