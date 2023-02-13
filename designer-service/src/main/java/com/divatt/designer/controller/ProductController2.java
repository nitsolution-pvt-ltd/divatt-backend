package com.divatt.designer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.config.JWTConfig;
import com.divatt.designer.constant.MessageConstant;
import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.entity.profile.DesignerLoginEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.DesignerLoginRepo;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.ProductService2;

@RestController
@RequestMapping("/designerProducts")
public class ProductController2 {

	@Autowired
	private ProductService2 productService2;

	@Autowired
	private JWTConfig jwtConfig;

	@Autowired
	private DesignerLoginRepo designerLoginRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController2.class);

	@PostMapping("/addProduct")
	public GlobalResponce addProductData(@RequestBody ProductMasterEntity2 productMasterEntity2) {
		LOGGER.info("Inside - designer -> ProductController2.addProductData()");
		try {
			return this.productService2.addProductData(productMasterEntity2);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/updateProduct/{productId}")
	public GlobalResponce updateProduct(@RequestBody ProductMasterEntity2 productMasterEntity2,
			@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside - designer -> ProductController2.updateProduct()");
			return productService2.updateProduct(productMasterEntity2, productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/productList")
	public Map<String, Object> getAllProduct(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			LOGGER.info("Inside - designer -> ProductController2.getAllProduct()");
			return productService2.getAllProduct(page, limit, sort, sortName, isDeleted, keyword, sortBy);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	@GetMapping("/productList/{productId}")
	public ProductMasterEntity2 getProduct(@RequestHeader("Authorization") String token,
			@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController2.getProduct()");
			Optional<DesignerLoginEntity> findByEmail = designerLoginRepo
					.findByEmail(jwtConfig.extractUsername(token.substring(7)));
			if (findByEmail.isPresent()) {
				return productService2.getProduct(productId, findByEmail.get().getdId());
			} else {
				throw new CustomException(MessageConstant.UNAUTHORIZED.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	@GetMapping("/getProductDetailsallStatus")
	public Map<String, Object> getProductDetailsallStatus(@RequestParam(defaultValue = "all") String adminStatus,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			LOGGER.info("Inside - designer -> ProductController2.getProductDetailsallStatus()");
			return this.productService2.getProductDetailsallStatus(adminStatus, page, limit, sort, sortName, isDeleted,
					keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	@GetMapping("/getDesignerProductByDesignerId/{designerId}")
	public Map<String, Object> getDesignerProductByDesignerId(@PathVariable Integer designerId,
			@RequestParam(defaultValue = "live") String adminStatus,
			@RequestParam(defaultValue = "true") Boolean isActive, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy, @RequestParam(defaultValue = "") String sortDateType) {
		try {
			LOGGER.info("Inside - designer -> ProductController2.designerProductByDesignerId()");
			return this.productService2.getDesignerProductByDesignerId(designerId, adminStatus, isActive, page, limit,
					sort, sortName, isDeleted, keyword, sortBy, sortDateType);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	@PutMapping("/delete/{productId}")
	public GlobalResponce productDeleteByproductId(@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController2.productDeleteByproductId()");
			return this.productService2.productDeleteByproductId(productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	@PutMapping("/approvalUpdate/{productId}")
	public GlobalResponce adminApprovalUpdate(@PathVariable Integer productId,
			@RequestBody ProductMasterEntity2 entity2) {
		try {
			return this.productService2.adminApprovalUpdate(productId, entity2);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}


	@PutMapping("/changeAdminStatus/{productId}")
	public GlobalResponce changeAdminStatus(@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController.changeStatus()");
			return this.productService2.changeAdminStatus(productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	@GetMapping("/productListUser")
	public ResponseEntity<?> productListUser() {
		try {
			return this.productService2.productListUser();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/getWishlistProductList")
	public Map<String, Object> allWishlistProductData(@RequestBody JSONObject productIdList,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "productId") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			LOGGER.info("Inside-ProductController2.allWishlistProductData()");
			String productId = productIdList.get("productId").toString();
			int getLimit = (Integer) (productIdList.get("limit"));
			int getPage = (Integer) (productIdList.get("page"));
			JSONParser jsonParser = new JSONParser();
			Object object = (Object) jsonParser.parse(productId);
			JSONArray jsonArray = (JSONArray) object;
			int limit = jsonArray.size();
			int page = 0;

			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < jsonArray.size(); i++) {
				Object object2 = jsonArray.get(i);
				int a = Integer.parseInt(object2.toString());
				list.add(a);
			}
			if (getLimit != 0) {
				limit = getLimit;
			}
			if (getPage != 0) {
				page = getPage;
			}

			return productService2.allWishlistProductData(list, sortBy, page, sort, sortName, isDeleted, limit);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/getCartProductList")
	public ResponseEntity<?> CartProductList(@RequestBody JSONObject productIdList) {
		try {
			LOGGER.info("inside - ProductController2.CartProductList()");
			String productId = productIdList.get("productId").toString();
			JSONParser jsonParser = new JSONParser();
			Object object = (Object) jsonParser.parse(productId);
			JSONArray jsonArray = (JSONArray) object;

			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < jsonArray.size(); i++) {
				Object object2 = jsonArray.get(i);
				int a = Integer.parseInt(object2.toString());
				list.add(a);
			}
			return productService2.allCartProductData(list);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/searching")
	public Map<String, Object> productSearching(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName, @RequestParam Optional<String> sortBy,
			@RequestParam(defaultValue = "") String searchBy, @RequestParam(defaultValue = "") String designerId,
			@RequestParam(defaultValue = "") String categoryId, @RequestParam(defaultValue = "") String subCategoryId,
			@RequestParam(defaultValue = "") String colour, @RequestParam(defaultValue = "") Boolean cod,
			@RequestParam(defaultValue = "") Boolean customization, @RequestParam(defaultValue = "") String priceType,
			@RequestParam(defaultValue = "") Boolean returnStatus, @RequestParam(defaultValue = "-1") String maxPrice,
			@RequestParam(defaultValue = "-1") String minPrice, @RequestParam(defaultValue = "") String size,
			@RequestParam(defaultValue = "") Boolean giftWrap, @RequestParam(defaultValue = "") String searchKey,
			@RequestParam(defaultValue = "") String sortDateType, @RequestParam(defaultValue = "") String sortPrice,
			@RequestParam(defaultValue = "") String labelType) {
		try {
			LOGGER.info("Inside- ProductController.productSearching()");
			return this.productService2.productSearching(page, limit, sort, sortName, sortBy, searchBy, designerId,
					categoryId, subCategoryId, colour, cod, customization, priceType, returnStatus, maxPrice, minPrice,
					size, giftWrap, searchKey, sortDateType, sortPrice, labelType);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/productLists/{productId}")
	public ProductMasterEntity2 getProducts(@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController2.getProduct()");
			return productService2.getProducts(productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/productAdmin/{productId}")
	public ProductMasterEntity2 getProductsAdmin(@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController2.getProductsAdmin()");
			return productService2.getProductsAdmin(productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getBannerDetails/{categoryName}")
	public Map<String, Object> getBannerDetails(@PathVariable String categoryName,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "") String keyword, @RequestParam Optional<String> sortBy,
			@RequestParam(defaultValue = "") String searchBy, @RequestParam(defaultValue = "") String designerId,
			@RequestParam(defaultValue = "") String categoryId, @RequestParam(defaultValue = "") String subCategoryId,
			@RequestParam(defaultValue = "") String colour, @RequestParam(defaultValue = "") Boolean cod,
			@RequestParam(defaultValue = "") Boolean customization, @RequestParam(defaultValue = "") String priceType,
			@RequestParam(defaultValue = "") Boolean returnStatus, @RequestParam(defaultValue = "-1") String maxPrice,
			@RequestParam(defaultValue = "-1") String minPrice, @RequestParam(defaultValue = "") String size,
			@RequestParam(defaultValue = "") Boolean giftWrap, @RequestParam(defaultValue = "") String searchKey,
			@RequestParam(defaultValue = "") String sortDateType, @RequestParam(defaultValue = "") String sortPrice,
			@RequestParam(defaultValue = "") String labelType) {
		try {
			return this.productService2.getBannerDetails(categoryName, page, limit, sort, sortName, keyword, sortBy,
					searchBy, designerId, categoryId, subCategoryId, colour, cod, customization, priceType,
					returnStatus, maxPrice, minPrice, size, giftWrap, searchKey, sortDateType, sortPrice, labelType);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
