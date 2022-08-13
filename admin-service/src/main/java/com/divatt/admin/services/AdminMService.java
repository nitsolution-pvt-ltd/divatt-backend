package com.divatt.admin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.divatt.admin.entity.BannerEntity;
import com.divatt.admin.entity.ColourEntity;
import com.divatt.admin.entity.ColourMetaEntity;
import com.divatt.admin.entity.DesignerCategoryEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.category.CategoryEntity;
import com.divatt.admin.entity.category.SubCategoryEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.AdminMDataRepo;
import com.divatt.admin.repo.BannerRepo;
import com.divatt.admin.repo.DesignerCategoryRepo;

@Service
public class AdminMService {

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private AdminMDataRepo adminMDataRepo;

	@Autowired
	private BannerRepo bannerRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Autowired
	private DesignerCategoryRepo designerCategoryRepo;

	public List<ColourEntity> getColour() {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("metaKey").is("colors"));
			ColourMetaEntity coloreData = mongoOperations.findOne(query, ColourMetaEntity.class);
			List<ColourEntity> coloreEntity = coloreData.getColors();
			List<ColourEntity> filterColour = new ArrayList<ColourEntity>();
			for (int i = 0; i < coloreEntity.size(); i++) {
				if (coloreEntity.get(i).getIsActive().equals("true")) {
					filterColour.add(coloreEntity.get(i));
				}
			}
			return filterColour;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse addColour(ColourEntity colourEntity) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("meta_key").is("colors"));
			ColourMetaEntity colour = mongoOperations.findOne(query, ColourMetaEntity.class);
			List<ColourEntity> colourEntities = new ArrayList<ColourEntity>();
			colourEntities.addAll(colour.getColors());
			colourEntities.add(colourEntity);
			ColourMetaEntity coloEntity = new ColourMetaEntity();
			coloEntity.setId(colour.getId());
			coloEntity.setMetaKey(colour.getMetaKey());
			coloEntity.setColors(colourEntities);
			adminMDataRepo.save(coloEntity);
			return new GlobalResponse("Success!!", "Colore Added Successfully", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> tblList(int page, String metakey, String keyword, int limit, String sort,
			Optional<String> sortBy, String sortName) {
		try {
			int CountData = (int) adminMDataRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<ColourMetaEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = adminMDataRepo.findByMetaKey(metakey, "0", pagingSort);
			} else {
				findAll = adminMDataRepo.Search(keyword, "0", pagingSort);

			}

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent().get(page).getColors());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() <= 1) {
				throw new CustomException("Colour Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse addBanner(BannerEntity bannerEntity) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("title").is(bannerEntity.getTitle()));
			List<BannerEntity> findByBannerName = mongoOperations.find(query, BannerEntity.class);

			if (findByBannerName.size() >= 1) {
				throw new CustomException("Banner title already exist!");
			} else {

				BannerEntity banEntity = new BannerEntity();
				banEntity.setId((long) sequenceGenerator.getNextSequence(BannerEntity.SEQUENCE_NAME));
				banEntity.setTitle(bannerEntity.getTitle());
				banEntity.setDescription(bannerEntity.getDescription());
				banEntity.setImage(bannerEntity.getImage());
				banEntity.setIsActive(bannerEntity.getIsActive());
				banEntity.setIsDeleted(bannerEntity.getIsDeleted());
				banEntity.setStartDate(bannerEntity.getStartDate());
				banEntity.setEndDate(bannerEntity.getEndDate());
				banEntity.setCreatedOn(new Date());

				bannerRepo.save(banEntity);
				return new GlobalResponse("Success", "Banner added successfully", 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse addDesignerCategory(DesignerCategoryEntity designerCategoryEntity) {
		try {
			Query query= new Query();
//			List<String>savedList= new ArrayList<String>();
			List<String>updatedList=new ArrayList<>();
			query.addCriteria(Criteria.where("metakey").is("DESIGNER_LEVELS"));
			DesignerCategoryEntity designerEntity= mongoOperations.findOne(query, DesignerCategoryEntity.class);
			for(int i=0;i<designerCategoryEntity.getDesignerLevels().size();i++) {
					if(!designerEntity.getDesignerLevels().contains(designerCategoryEntity.getDesignerLevels().get(i))) {
						updatedList.add(designerCategoryEntity.getDesignerLevels().get(i));
					}
			}
			updatedList.addAll(designerEntity.getDesignerLevels());
			designerEntity.setDesignerLevels(updatedList);
			designerEntity.setMetakey("DESIGNER_LEVELS");
			System.out.println(designerEntity);
			designerCategoryRepo.save(designerEntity);
			return new GlobalResponse("Success", "Designer category added successfully", 200);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public Object getDesignerService() {
		try {
			Query query= new Query();
			List<Object> response= new ArrayList<Object>();
			query.addCriteria(Criteria.where("id").is(101));
			JSONObject jsonObject1= new JSONObject();
			DesignerCategoryEntity designerCategoryEntity=mongoOperations.findOne(query, DesignerCategoryEntity.class);
			System.out.println(designerCategoryEntity);
			for(int i=0;i<designerCategoryEntity.getDesignerLevels().size();i++) {
				JSONObject jsonObject= new  JSONObject();
				jsonObject.put("Name", designerCategoryEntity.getDesignerLevels().get(i));
				//response.put(i,jsonObject);
				response.add(jsonObject);
			}
			jsonObject1.put("data", response);
			return jsonObject1;
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
