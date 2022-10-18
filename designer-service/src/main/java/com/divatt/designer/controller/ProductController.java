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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.divatt.designer.entity.OrderSKUDetailsEntity;
import com.divatt.designer.entity.ProductEntity;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.ProductService;
import com.divatt.designer.services.ProductServiceImp;

@RestController
@RequestMapping("/designerProduct")
public class ProductController implements ProductServiceImp {

	@Autowired
	private ProductService productService;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	
	@GetMapping("/test")
	public String test() {
		return "Hii";
	}

	@GetMapping("/allList")
	public Map<String, Object> allProductList(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {

		try {
			LOGGER.info("Inside- ProductController.allList()");
			return this.productService.allList(page, limit, sort, sortName, isDeleted, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	
	@GetMapping("/getColour/{colourValue}")
	public Boolean getColour(@PathVariable (value="colourValue") String colourValue) {
		
		
		try {
			LOGGER.info("Inside- ProductController.getColour()");
			return productService.getColourlist(colourValue);
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
	public ProductEntity viewProductDetails(@PathVariable Integer productId) {
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

	@PostMapping("/getWishlistProductList")
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
	
	@PostMapping("/getCartProductList")
	public ResponseEntity<?> CartProductList(@RequestBody JSONObject productIdList) {
		try {
			LOGGER.info("Inside-ProductController.productList()");
			
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
			

			return productService.allCartProductData(list);
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
			return this.productService.designerIdListPage(designerId, sortBy, page, sort, sortName, isDeleted, limit,
					keyword);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getApproval")
	public List<ProductMasterEntity> getApproval() {
		try {
			return this.productService.getApproval();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/userProductList")
	public ResponseEntity<?> userProductList() {
		try {
			return this.productService.getListProduct();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/listPerStatus")
	public Map<String, Object> getAllProductDetails(@RequestParam(defaultValue = "all") String status,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {

		try {
			LOGGER.info("Inside - designer -> ProductController.getProductDetailsPerStatus()");
			return this.productService.getProductDetailsPerStatus(status, page, limit, sort, sortName, isDeleted,
					keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getDesignerProductListUser")
	public Map<String, Object> getDesignerProductListUser(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "productId") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			LOGGER.info("Inside-ProductController.getDesignerProductListUser()");

			return productService.getDesignerProductListService(page, limit, sortBy, sort, sortName, keyword,
					isDeleted);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getPerDesignerProductListUser/{designerId}")
	public Map<String, Object> getPerDesignerProductListUser(@PathVariable Integer designerId,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "productId") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			LOGGER.info("Inside-ProductController.getDesignerProductListUser()");

			return productService.getPerDesignerProductListService(page, limit, sortBy, sort, sortName, keyword,
					isDeleted, designerId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/getPerDesignerProductUser/{designerId}")
	public ResponseEntity<?> getPerDesignerProductUser(@PathVariable Integer designerId,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "productId") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			LOGGER.info("Inside-ProductController.getPerDesignerProductUser()");

			return productService.getPerDesignerProductService(designerId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/UserDesignerProductList/{designerId}")
	public List<ProductMasterEntity>UserDesignerProductList (@PathVariable Integer designerId)
	{
		try
		{
			return this.productService.UserDesignerProductList(designerId);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/getProductListById")
	public ResponseEntity<?> getProductListById(@RequestBody List<Integer> productIdList) {
		try {
			LOGGER.info("Inside-ProductController.getProductListById()");
//			String productId = productIdList.get("productId").toString();
			
//			JSONParser jsonParser = new JSONParser();
//			Object object = (Object) jsonParser.parse(productId);
//			JSONArray jsonArray = (JSONArray) object;
//		
//
//			List<Integer> list = new ArrayList<Integer>();
//			for (int i = 0; i < jsonArray.size(); i++) {
//				Object object2 = jsonArray.get(i);
//				int a = Integer.parseInt(object2.toString());
//				list.add(a);
//			}
			
//			System.out.println(productIdList);
			return productService.ProductListByIdService(productIdList);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
			
		}

}
	@PutMapping("/approval/{productId}")
	public GlobalResponce approvaladmin(@PathVariable Integer productId,@RequestBody ProductMasterEntity masterEntity)
	{
		try {
			return this.productService.adminApproval(productId,masterEntity);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	

	@PutMapping("/multipleDelete")
	public GlobalResponce multipleDelete(@RequestBody List<Integer> productIdList)
	{
		try {
			return this.productService.multiDelete(productIdList);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/stockClearence")
	public GlobalResponce stockClearence(@RequestBody List<OrderSKUDetailsEntity> jsonObject)
	{
		try {
			return this.productService.stockClearenceService(jsonObject);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/productList/{categoryName}/{subCategoryName}")
	public List<ProductMasterEntity> productList(@PathVariable String  categoryName,@PathVariable String subcategoryName)
	{
		try {
			return this.productService.productListCategorySubcategory(categoryName, subcategoryName);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/viewProductByCategorySubcategory/{categoryName}/{subCategoryName}")
	public List<ProductMasterEntity> viewProductByCategorySubcategory(@PathVariable String categoryName, @PathVariable String subCategoryName){
		try {
			return this.productService.viewProductByCategorySubcategoryService(categoryName,subCategoryName);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	@GetMapping("/getProductReminder")
	public Map<String, Object> getProductReminder(
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "productId") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			LOGGER.info("Inside-ProductController.getProductReminder()");
//return null;
			return productService.getProductReminderService(page, limit, sortBy, sort, sortName, keyword,isDeleted);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	@GetMapping("/designerNotification")
	public GlobalResponce designerNotification() {
		try {
			return this.productService.designerNotification();
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@PutMapping("/stockRecoverService")
	public GlobalResponce stockRecoverService(@RequestBody OrderSKUDetailsEntity orderDetails) {
		try {
			return this.productService.stockRecovereService(orderDetails);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/searching")
	public List<ProductMasterEntity> productSearching(@RequestParam (defaultValue = "")  String searchBy,
													  @RequestParam(defaultValue = "") String designerId,
													  @RequestParam(defaultValue = "") String categoryId,
													  @RequestParam(defaultValue = "") String subCategoryId,
													  @RequestParam(defaultValue = "") String colour,
													  @RequestParam(defaultValue = "") Boolean cod,
													  @RequestParam(defaultValue = "") Boolean customization,
													  @RequestParam(defaultValue = "") Boolean priceType,
													  @RequestParam(defaultValue = "") Boolean returnStatus,
													  @RequestParam(defaultValue = "") String maxPrice,
													  @RequestParam(defaultValue = "") String minPrice,
													  @RequestParam(defaultValue = "") String size,
													  @RequestParam Boolean isFilter){
		try {
			LOGGER.info("Inside- ProductController.productSearching()");
			return this.productService.productSearching
					(searchBy,designerId,categoryId,subCategoryId,colour,cod,customization,priceType,returnStatus,maxPrice,minPrice,size,isFilter);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}