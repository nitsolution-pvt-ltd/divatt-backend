package com.divatt.admin.servicesImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

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

import com.divatt.admin.constant.MessageConstant;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.product.SpecificationEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.CategoryRepo;
import com.divatt.admin.repo.SpecificationRepo;
import com.divatt.admin.services.SequenceGenerator;
import com.divatt.admin.services.SpecificationService;

@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private SpecificationRepo specRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private MongoOperations mongoOperations;

	public GlobalResponse addSpecification(@Valid SpecificationEntity specificationEntity) {
		try {
			specificationEntity.setId(sequenceGenerator.getNextSequence(SpecificationEntity.SEQUENCE_NAME));
			specificationEntity.setIsActive(true);
			specificationEntity.setIsDeleted(false);
			specificationEntity.setAddonDate(new Date());
			specRepo.save(specificationEntity);
			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.SPECIFICATION_ADDED.getMessage(), 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<SpecificationEntity> listOfSpecification(Integer categoryId) {
		try {
			String categoryName = categoryRepo.findById(categoryId).get().getCategoryName();
			if (categoryName.toLowerCase().contains("women")) {
				categoryName = "Women";
			} else if (categoryName.toLowerCase().contains("men")) {
				categoryName = "Men";
			} else if (categoryName.toLowerCase().contains("girls")) {
				categoryName = "Girls";
			} else if (categoryName.toLowerCase().contains("boys")) {
				categoryName = "Boys";
			}  
			else {
				throw new CustomException(MessageConstant.INVALID_ID.getMessage());
			}
			Query query = new Query();
			query.addCriteria(Criteria.where("categoryName").is(categoryName).and("isActive").is(true));
			List<SpecificationEntity> listOfSpecificationData = mongoOperations.find(query, SpecificationEntity.class);
			Query query1 = new Query();
			query1.addCriteria(Criteria.where("categoryName").is("all").and("isActive").is(true));
			List<SpecificationEntity> allListOfSpecification = mongoOperations.find(query1, SpecificationEntity.class);
			List<SpecificationEntity> allSpeList = new ArrayList<SpecificationEntity>();
			allSpeList.addAll(allListOfSpecification);
			allSpeList.addAll(listOfSpecificationData);
			return allSpeList;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse updateSpec(SpecificationEntity specificationData, Integer specId) {
		try {
			if (specRepo.existsById(specId)) {
				specificationData.setId(specId);
				specificationData.setIsActive(specRepo.findById(specId).get().getIsActive());
				specificationData.setIsDeleted(specRepo.findById(specId).get().getIsDeleted());
				specificationData.setAddonDate(new Date());
				specRepo.save(specificationData);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.SPECIFICATION_UPDATED.getMessage(), 200);
			} else {
				throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse deleteSpec(Integer specId) {
		try {
			if (specRepo.existsById(specId)) {
				SpecificationEntity specificationEntity = specRepo.findById(specId).get();
				specificationEntity.setIsActive(false);
				specificationEntity.setIsDeleted(true);
				specRepo.save(specificationEntity);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.SPECIFICATION_DELETED.getMessage(), 200);
			} else {
				throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getAllSpec(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			int CountData = (int) specRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<SpecificationEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = specRepo.findByIsDeleted(isDeleted, pagingSort);
			} else {
				findAll = specRepo.Search(keyword, isDeleted, pagingSort);

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

			if (findAll.getSize() <= 1) {
				throw new CustomException(MessageConstant.CATEGORY_SPECIFICATION_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse activeSpecification(Integer specId) {
		try {
			SpecificationEntity specificationEntity = specRepo.findById(specId).get();
			if (specificationEntity != null) {
				if (specificationEntity.getIsActive().equals(false)) {
					specificationEntity.setIsActive(true);
					specRepo.save(specificationEntity);
					return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.SPECIFICATION_ACTIVED.getMessage(), 200);
				} else {
					specificationEntity.setIsActive(false);
					specRepo.save(specificationEntity);
					return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.SPECIFICATION_DEACTIVED.getMessage(), 200);
				}
			} else {
				return new GlobalResponse(MessageConstant.ERROR.getMessage(), MessageConstant.ID_NOT_EXIST.getMessage(), 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<SpecificationEntity> view(Integer specId) {
		try {
			if (specRepo.existsById(specId)) {
				return ResponseEntity.ok(specRepo.findById(specId).get());
			} else {
				return ResponseEntity.ok(null);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
