package com.divatt.admin.servicesImpl;

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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.hsnCode.HsnEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.HsnRepo;
import com.divatt.admin.services.HsnService;
import com.divatt.admin.services.SequenceGenerator;

@Service
public class HsnServiceImpl implements HsnService {

	@Autowired
	private HsnRepo hsnRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private MongoOperations mongoOperations;

	private static final Logger LOGGER = LoggerFactory.getLogger(HsnServiceImpl.class);

	public GlobalResponse postHsnDetails(@RequestBody HsnEntity hsnEntity) {

		LOGGER.info("Inside - HsnServiceImpl.postHsnDetails()");

		try {

			Optional<HsnEntity> hsnlist = hsnRepo.findByhsnCode(hsnEntity.getHsnCode());

			if (hsnlist.isPresent()) {

				HsnEntity entity = hsnRepo.findByHsn(hsnEntity.getHsnCode());
				if (entity.getIsDelete().equals(true)) {
					throw new CustomException("HsnCode already used");
				} else {
					throw new CustomException("HsnCode already exist");
				}

			} else {
				HsnEntity hsn = new HsnEntity();
				hsn.setId(sequenceGenerator.getNextSequence(HsnEntity.SEQUENCE_NAME));
				hsn.setHsnCode(hsnEntity.getHsnCode());
				hsn.setDescription(hsnEntity.getDescription());
				hsn.setTaxValue(hsnEntity.getTaxValue());
				hsn.setSgst(hsnEntity.getTaxValue() / 2);
				hsn.setCgst(hsnEntity.getTaxValue() / 2);
				hsn.setIgst(hsnEntity.getTaxValue());

				hsnRepo.save(hsn);

				return new GlobalResponse("SUCCESS", "Hsn added succesfully", 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse updatetHsnDetailsByHsnCode(@RequestBody HsnEntity hsnEntity, Integer hsnCode) {

		LOGGER.info("Inside - HsnServiceImpl.updatetHsnDetailsByHsnCode()");

		try {
			Optional<HsnEntity> hsnlist = hsnRepo.findByhsnCode(hsnCode);
			if (hsnlist.isPresent()) {
				HsnEntity entity = hsnRepo.findByHsn(hsnCode);

				if (entity.getIsDelete().equals(false)) {
					entity.setId(entity.getId());

					entity.setHsnCode(hsnEntity.getHsnCode());
					entity.setDescription(hsnEntity.getDescription());
					entity.setTaxValue(hsnEntity.getTaxValue());
					entity.setSgst(hsnEntity.getTaxValue() / 2);
					entity.setCgst(hsnEntity.getTaxValue() / 2);
					entity.setIgst(hsnEntity.getTaxValue());
					entity.setIsActive(true);
					entity.setIsDelete(false);
					hsnRepo.save(entity);
					return new GlobalResponse("SUCCESS", "Hsn update succesfully", 200);

				} else {
					return new GlobalResponse("Failed", "This hsnCode already delete", 404);
				}

			} else {
				throw new CustomException("HsnCode not exist");
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getHsnDetails(int page, int limit, String sort, String sortName, Boolean isDelete,
			String keyword, Optional<String> sortBy) {

		LOGGER.info("Inside - HsnServiceImpl.getHsnDetails()");

		try {
			int CountData = (int) hsnRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit).withSort(Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit).withSort(Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<HsnEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = hsnRepo.findByIsDelete(isDelete, "0", pagingSort);
			} else {
				findAll = hsnRepo.Search(keyword, isDelete, "0", pagingSort);

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
				throw new CustomException("Hsn Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Optional<HsnEntity> viewByHsnCode(Integer hsnCode) {
		LOGGER.info("Inside - HsnServiceImpl.viewByHsnCode()");

		try {

			Optional<HsnEntity> hsnlist = hsnRepo.findByhsnCode(hsnCode);

			if (hsnlist.isPresent()) {
				return hsnlist;
			} else {
				throw new CustomException("HsnCode not found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse deleteHsnCode(Integer id) {

		LOGGER.info("Inside- HsnServiceImpl.deleteHsnCode()");
		try {

			Optional<HsnEntity> hsnlist = hsnRepo.findById(id);
			if (hsnlist.isPresent()) {
				HsnEntity entity = hsnRepo.findById(id).get();
				if (entity.getIsDelete().equals(false)) {
					entity.setIsDelete(true);
					hsnRepo.save(entity);
					return new GlobalResponse("SUCCESS", "Hsn delete succesfully", 200);
				} else {
					return new GlobalResponse("Failed", "Hsn already deleted", 404);
				}
			} else {

				return new GlobalResponse("Failed", "HsnCode not found", 404);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse muldeleteHsnCode(List<Integer> hsnId) {
		try {

			for (Integer id : hsnId) {
				HsnEntity entity = hsnRepo.findById(id).get();
				entity.setIsDelete(true);
				hsnRepo.save(entity);
			}

			return new GlobalResponse("SUCCESS", "hsnCode deleted successfully", 200);
		}

		catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse setStatus(Integer id) {
		LOGGER.info("Inside- HsnServiceImpl.setstatus()");
		try {

			HsnEntity entity = hsnRepo.findById(id).get();

			if (entity.getIsActive().equals(true)) {

				entity.setIsActive(false);
				hsnRepo.save(entity);
				return new GlobalResponse("Success", "Hsn active successfully", 200);
			} else {
				entity.setIsActive(true);
				hsnRepo.save(entity);
				return new GlobalResponse("Success", "Hsn deactive successfully", 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<HsnEntity> getActiveHSNListService(String searchKeyword) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("isActive").is(true).and("isDelete").is(false));
			List<HsnEntity> hsnList = mongoOperations.find(query, HsnEntity.class);
			if (searchKeyword.isEmpty()) {
				return hsnList;
			} else {
				List<HsnEntity> descriptionFiltered = hsnList.stream()
						.filter(e -> searchKeyword.equals(e.getDescription())).collect(Collectors.toList());
				if (descriptionFiltered.isEmpty()) {
					List<HsnEntity> hsnCodeFiltered = hsnList.stream()
							.filter(e -> searchKeyword.equals(e.getHsnCode() + "")).collect(Collectors.toList());
					if (hsnCodeFiltered.isEmpty()) {
						List<HsnEntity> taxValueFiltered = hsnList.stream()
								.filter(e -> searchKeyword.equals(e.getTaxValue() + "")).collect(Collectors.toList());
						if (taxValueFiltered.isEmpty()) {
							throw new CustomException("No data found");
						}
						return taxValueFiltered;
					}
					return hsnCodeFiltered;
				}
				return descriptionFiltered;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
