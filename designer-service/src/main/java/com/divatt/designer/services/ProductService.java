package com.divatt.designer.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.divatt.designer.entity.OrderSKUDetailsEntity;
import com.divatt.designer.entity.ProductEntity;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.response.GlobalResponce;

public interface ProductService {

	Map<String, Object> allList(int page, int limit, String sort, String sortName, Boolean isDeleted, String keyword,
			Optional<String> sortBy);

	Boolean getColourlist(String colourValue);

	GlobalResponce addData(@Valid ProductMasterEntity productEntity);

	ProductEntity productDetails(Integer productId);

	GlobalResponce changeStatus(Integer productId);

	GlobalResponce updateProduct(Integer productId, ProductMasterEntity productMasterEntity);

	GlobalResponce deleteProduct(Integer productId);

	Map<String, Object> getProductDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy);

	Map<String, Object> allWishlistProductData(List<Integer> list, Optional<String> sortBy, int page, String sort,
			String sortName, Boolean isDeleted, int limit);

	ResponseEntity<?> allCartProductData(List<Integer> list);

	Map<String, Object> designerIdListPage(Integer designerId, String status, Optional<String> sortBy, int page,
			String sort, String sortName, Boolean isDeleted, int limit, String keyword, Boolean isActive,
			String sortDateType);

	List<ProductMasterEntity> getApproval();

	ResponseEntity<?> getListProduct();

	Map<String, Object> getProductDetailsPerStatus(String status, int page, int limit, String sort, String sortName,
			Boolean isDeleted, String keyword, Optional<String> sortBy);

	Map<String, Object> getDesignerProductListService(Integer page, Integer limit, Optional<String> sortBy, String sort,
			String sortName, String keyword, Boolean isDeleted);

	Map<String, Object> getPerDesignerProductListService(Integer page, Integer limit, Optional<String> sortBy,
			String sort, String sortName, String keyword, Boolean isDeleted, Integer designerId);

	ResponseEntity<?> getPerDesignerProductService(Integer designerId);

	List<ProductMasterEntity> UserDesignerProductList(Integer designerId);

	ResponseEntity<?> ProductListByIdService(List<Integer> productIdList);

	GlobalResponce adminApproval(Integer productId, ProductMasterEntity masterEntity) throws IOException;

	GlobalResponce multiDelete(List<Integer> productIdList);

	GlobalResponce stockClearenceService(List<OrderSKUDetailsEntity> jsonObject);

	List<ProductMasterEntity> productListCategorySubcategory(String categoryName, String subcategoryName);

	List<ProductMasterEntity> viewProductByCategorySubcategoryService(String categoryName, String subCategoryName);

	Map<String, Object> getProductReminderService(Integer page, Integer limit, Optional<String> sortBy, String sort,
			String sortName, String keyword, Boolean isDeleted);

	GlobalResponce designerNotification();

	GlobalResponce stockRecovereService(OrderSKUDetailsEntity orderDetails);

	List<ProductMasterEntity> productSearching(String searchBy, String designerId, String categoryId,
			String subCategoryId, String colour, Boolean cod, Boolean customization, String priceType,
			Boolean returnStatus, String maxPrice, String minPrice, String size, Boolean giftWrap, String searchKey);

}
