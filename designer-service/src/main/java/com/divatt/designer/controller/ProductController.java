package com.divatt.designer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.entity.ListProduct;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.ProductService;
import com.divatt.designer.services.ProductServiceImp;

@RestController
@RequestMapping("/product")
public class ProductController implements ProductServiceImp {

	@Autowired
	private ProductService productService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@GetMapping("/allList")
	public List<ListProduct> allProductList() {

		try {
			LOGGER.info("Inside- ProductController.allList()");
			return this.productService.allList();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/add")
	public GlobalResponce add(@Valid @RequestBody ProductMasterEntity productEntity) {
		try {
			LOGGER.info("Inside- ProductController.add()");
			return productService.addData(productEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/view/{productId}")
	public Optional<?> viewProductDetails(@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController.viewProductDetails()");
			return productService.productDetails(productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/status/{productId}")
	public GlobalResponce changeStatus(@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController.changeStatus()");
			return this.productService.changeStatus(productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/update/{productId}")
	public GlobalResponce updateProductData(@RequestBody ProductMasterEntity productMasterEntity,
			@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController.updateProductData()");
			return this.productService.updateProduct(productId, productMasterEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/delete/{productId}")
	public GlobalResponce productDelete(@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController.productDelete()");
			return this.productService.deleteProduct(productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/list")
	public Map<String, Object> getAllProductDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {

		try {
			LOGGER.info("Inside - ProductController.getAllProductDetails()");
			return this.productService.getProductDetails(page, limit, sort, sortName, isDeleted, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/getProductList")
	public Map<String, Object> productList(@RequestBody JSONObject productIdList,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "productId") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			LOGGER.info("Inside-ProductController.productList()");
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

			return productService.allWishlistProductData(list, sortBy, page, sort, sortName, isDeleted, limit);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/designerProductList/{designerId}")
	public Map<String, Object> designerProductList(@PathVariable Integer designerId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			// List<ProductMasterEntity>list= productService.designerIdList(designerId);
			return this.productService.designerIdListPage(designerId, sortBy, page, sort, sortName, isDeleted, limit,
					keyword);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}