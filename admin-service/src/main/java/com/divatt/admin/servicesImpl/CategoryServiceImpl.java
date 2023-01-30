package com.divatt.admin.servicesImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.divatt.admin.constant.MessageConstant;
import com.divatt.admin.entity.CategoryEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.SubCategoryEntity;
import com.divatt.admin.entity.UserCategoryResponse;
import com.divatt.admin.entity.UserResponseEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.CategoryRepo;
import com.divatt.admin.repo.SubCategoryRepo;
import com.divatt.admin.services.CategoryService;
import com.divatt.admin.services.SequenceGenerator;


@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private SubCategoryRepo subCategoryRepo;

	@Autowired
	private MongoOperations mongoOperations;

	public GlobalResponse postCategoryDetails(@RequestBody CategoryEntity categoryEntity) {
		LOGGER.info("Inside - CategoryServiceImpl.postCategoryDetails()");

		try {

			List<CategoryEntity> findByCategoryName = categoryRepo
					.findByCategoryNameAndIsDeleted(categoryEntity.getCategoryName(), false);
			if (findByCategoryName.size() >= 1) {
				throw new CustomException(MessageConstant.CATEGORY_ALREADY_EXIST.getMessage());
			} else {
				CategoryEntity filterCatDetails = new CategoryEntity();

				filterCatDetails.setId(sequenceGenerator.getNextSequence(CategoryEntity.SEQUENCE_NAME));
				filterCatDetails.setCategoryName(categoryEntity.getCategoryName());
				filterCatDetails.setCategoryDescription(categoryEntity.getCategoryDescription());
				filterCatDetails.setCategoryImage(categoryEntity.getCategoryImage());
				filterCatDetails.setCreatedBy(categoryEntity.getCreatedBy());
				filterCatDetails.setCreatedOn(new Date());
				filterCatDetails.setLevel(categoryEntity.getLevel());
				filterCatDetails.setParentId("0");
				filterCatDetails.setIsActive(true);
				filterCatDetails.setIsDeleted(false);

				categoryRepo.save(filterCatDetails);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.CATEGORY_ADDED.getMessage(), 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Object> getCategoryDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			int CountData = (int) categoryRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<CategoryEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = categoryRepo.findByIsDeletedAndParentId(isDeleted, "0", pagingSort);
			} else {
				findAll = categoryRepo.Search(keyword, isDeleted, "0", pagingSort);

			}

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() <= 0) {
				throw new CustomException(MessageConstant.CATEGORY_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Optional<CategoryEntity> viewCategoryDetails(Integer catId) {
		try {
			Optional<CategoryEntity> findById = this.categoryRepo.findById(catId);
			if (!(findById.isPresent())) {
				throw new CustomException(MessageConstant.CATEGORY_NOT_FOUND.getMessage());
			} else {
				return findById;
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse putCategoryDetailsService(CategoryEntity categoryEntity, Integer catId) {

		try {
			Optional<CategoryEntity> findByCategoryRow = categoryRepo.findById(catId);

			if (!findByCategoryRow.isPresent()) {
				throw new CustomException(MessageConstant.CATEGORY_NOT_FOUND.getMessage());
			} else {
				CategoryEntity filterCatDetails = findByCategoryRow.get();

				filterCatDetails.setCategoryName(categoryEntity.getCategoryName());
				filterCatDetails.setCategoryDescription(categoryEntity.getCategoryDescription());
				filterCatDetails.setCategoryImage(categoryEntity.getCategoryImage());
				filterCatDetails.setCreatedBy(categoryEntity.getCreatedBy());
				filterCatDetails.setCreatedOn(new Date());
				filterCatDetails.setLevel(categoryEntity.getLevel());
				filterCatDetails.setParentId("0");
				filterCatDetails.setIsActive(true);
				filterCatDetails.setIsDeleted(false);
				categoryRepo.save(filterCatDetails);

				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.CATEGORY_UPDATED.getMessage(), 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse putCategoryDeleteService(Integer CatId) {
		try {
			List<CategoryEntity> findByVerify = categoryRepo.VerifyCategory(false, CatId.toString());
			Optional<CategoryEntity> findById = categoryRepo.findById(CatId);

			CategoryEntity filterCatDetails = findById.get();

			if (!findByVerify.isEmpty()) {
				throw new CustomException(
						MessageConstant.CATEGORY_DELETE.getMessage());
			} else {
				if (!findById.isPresent()) {
					throw new CustomException(MessageConstant.CATEGORY_NOT_FOUND.getMessage());
				} else {
					filterCatDetails.setIsDeleted(true);
					filterCatDetails.setCreatedOn(new Date());
					categoryRepo.save(filterCatDetails);

					return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.CATEGORY_DELETED.getMessage(), 200);
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse putCategoryStatusService(Integer CatId) {
		try {

			Optional<CategoryEntity> findById = categoryRepo.findById(CatId);
			CategoryEntity filterCatDetails = findById.get();

			if (filterCatDetails.getId() == null) {
				throw new CustomException(MessageConstant.CATEGORY_NOT_FOUND.getMessage());

			} else {
				Boolean isStatus = null;
				String message = null;
				if (filterCatDetails.getIsActive() == false) {
					isStatus = true;
					message = "actived";
				} else {
					isStatus = false;
					message = "inactive";
				}

				filterCatDetails.setIsActive(isStatus);
				filterCatDetails.setCreatedOn(new Date());
				categoryRepo.save(filterCatDetails);

				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.STATUS.getMessage() + message + MessageConstant.SUCCESSFULLY.getMessage(), 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse putCategoryMulDeleteService(List<Integer> CateID) {
		try {
			String message = null;
			Integer status = 400;
			for (Integer CateIdRowId : CateID) {

				Optional<CategoryEntity> findById = categoryRepo.findById(CateIdRowId);
				List<CategoryEntity> findByVerify = categoryRepo.VerifyCategory(false, CateIdRowId.toString());

				CategoryEntity filterCatDetails = findById.get();

				if (filterCatDetails.getId() != null) {
					filterCatDetails.setIsDeleted(true);
					filterCatDetails.setCreatedOn(new Date());
					if (findByVerify.isEmpty()) {
						message = MessageConstant.CATEGORY_DELETED.getMessage();
						status = 200;
						categoryRepo.save(filterCatDetails);
					} else {
						message = MessageConstant.ALL_CATEGORY_NOT_DELETED.getMessage();
					}
				}
			}
			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), message, status);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<Integer> categoryVerification(List<Integer> categoryId) {
		try {
			List<Integer> verificationList = new ArrayList<Integer>();
			for (int i = 0; i < categoryId.size(); i++) {
				if (categoryRepo.existsById(categoryId.get(i))) {
					CategoryEntity viewCategoryDetails = viewCategoryDetails(categoryId.get(i)).get();
					if (viewCategoryDetails.getIsActive().equals(true)) {
						verificationList.add(categoryId.get(i));
					}
				}
			}
			System.out.println(verificationList);
			return verificationList;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<CategoryEntity> getAllCategoryDetails(Boolean isDeleted, Boolean Status) {
		try {

			List<CategoryEntity> findAll = categoryRepo.findByIsDeletedAndIsActiveAndParentId(isDeleted, Status, "0");

			if (findAll.isEmpty()) {
				throw new CustomException(MessageConstant.CATEGORY_NOT_FOUND.getMessage());
			} else {
				return findAll;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getCategoryListDetailsService(Boolean isDeleted, Boolean Status) {
		try {

			List<SubCategoryEntity> catList = subCategoryRepo
					.findByIsDeletedAndIsActiveAndParentId(isDeleted, Status, "0").stream().map(e -> {
						List<SubCategoryEntity> subCategoryEntity = subCategoryRepo
								.findByIsDeletedAndIsActiveAndParentId(isDeleted, Status, e.getId().toString());
						subCategoryEntity.forEach(x -> e.setSubCategory(x));
						return e;
					}).filter(e -> {
						if (e.getSubCategory() != null)
							return true;
						return false;
					}).collect(Collectors.toList());

			if (catList.isEmpty()) {
				throw new CustomException(MessageConstant.CATEGORY_NOT_FOUND.getMessage());
			} else {
				return ResponseEntity.ok(catList);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<UserCategoryResponse> viewByCategoryNameService() {
		try {

			List<UserCategoryResponse> categoryList = new ArrayList<UserCategoryResponse>();
			List<CategoryEntity> listOfCategory = categoryRepo.findByIsDeletedAndIsActiveAndParentId(false, true, "0");
			// System.out.println(listOfCategory);
			for (int i = 0; i < listOfCategory.size(); i++) {
				UserCategoryResponse categoryResponse = new UserCategoryResponse();
				categoryResponse.setId(listOfCategory.get(i).getId());
				// System.out.println(listOfCategory.get(i).getId());
				categoryResponse.setCategoryDescription(listOfCategory.get(i).getCategoryDescription());
				categoryResponse.setCategoryImage(listOfCategory.get(i).getCategoryImage());
				categoryResponse.setCategoryName(listOfCategory.get(i).getCategoryName());
				categoryResponse.setSubCategoryEntities(categoryRepo.findByIsDeletedAndIsActiveAndParentId(false, true,
						listOfCategory.get(i).getId().toString()));
				categoryList.add(categoryResponse);
				// categoryIdList.add(listOfCategory.get(i).getId());
			}

			return categoryList;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public UserResponseEntity viewByCategoryName(String categoryName, String subCategoryName) {
		try {
			Query query = new Query();
			Query query1 = new Query();
			// CategoryEntity categoryEntity= new CategoryEntity();
			UserResponseEntity responseEntity = new UserResponseEntity();
			query.addCriteria(Criteria.where("categoryName").is(categoryName));
			CategoryEntity categoryEntity = mongoOperations.findOne(query, CategoryEntity.class);

			if (subCategoryName.equals("All")) {
				SubCategoryEntity subCategoryEntity = mongoOperations.findOne(query, SubCategoryEntity.class);
				responseEntity.setCategoryEntity(categoryEntity);
				responseEntity.setSubCategoryEntity(subCategoryEntity);
				return responseEntity;
			}
			query1.addCriteria(Criteria.where("categoryName").is(subCategoryName).and("parentId")
					.is(categoryEntity.getId().toString()));
			SubCategoryEntity subCategoryEntity = mongoOperations.findOne(query1, SubCategoryEntity.class);
			responseEntity.setCategoryEntity(categoryEntity);
			responseEntity.setSubCategoryEntity(subCategoryEntity);
			return responseEntity;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	public List<CategoryEntity> getAllCategoryDetails(){
		try {
			List<CategoryEntity> categoryData = this.categoryRepo.findAll();
			return categoryData;
		}catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
