package com.divatt.admin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.json.simple.JSONObject;
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

import com.amazonaws.services.applicationdiscovery.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.divatt.admin.contoller.AdminMDataController;
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

	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminMService.class);
	

	public List<ColourEntity> getColours() {
		LOGGER.info("Inside - AdminMService.getColours()");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("metaKey").is("colors"));
			ColourMetaEntity coloreData = mongoOperations.findOne(query, ColourMetaEntity.class);
			List<ColourEntity> coloreEntity = coloreData.getColors();
			List<ColourEntity> filterColour = new ArrayList<ColourEntity>();
			for (int i = 0; i < coloreEntity.size(); i++) {
				if (coloreEntity.get(i).getIsActive().equals(true)) {
					filterColour.add(coloreEntity.get(i));
				}
			}
			return filterColour;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	
	public GlobalResponse addColour(ColourEntity colourEntity) {
		LOGGER.info("Inside - AdminMService.addColour()");
		try {
			Query query= new Query();
			query.addCriteria(Criteria.where("metaKey").is("colors"));
			ColourMetaEntity colourMetaEntity= mongoOperations.findOne(query, ColourMetaEntity.class);
			List<ColourEntity> colore=colourMetaEntity.getColors();
			List<ColourEntity> updated= new ArrayList<>();
			Boolean t = true ;
			for(int i = 0 ; i < colore.size() ; i++) {
					if(colore.get(i).getColorName().contentEquals(colourEntity.getColorName())) {
						
						t = false;
					}
				}
			
			if(t) {
					ColourEntity colourEntity1= new ColourEntity();
					colourEntity1.setColorName(colourEntity.getColorName());
					colourEntity1.setColorValue(colourEntity.getColorValue());
					colourEntity1.setIsActive(true);
					updated.add(colourEntity1);
					updated.addAll(colourMetaEntity.getColors());
					colourMetaEntity.setColors(updated);
					colourMetaEntity.setMetaKey("colors");
					colourMetaEntity.setId(colourMetaEntity.getId());
					adminMDataRepo.save(colourMetaEntity);
					return new GlobalResponse("Success", "colore added successfully",200);
				}
			return new GlobalResponse("Failed", "colore not added ",404);
		}catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	

	public GlobalResponse updateColours(ColourEntity colourEntity, String name) {
		try {
			LOGGER.info("Inside - AdminMService.updateColours()");
			
			Query query = new Query();
			query.addCriteria(Criteria.where("metaKey").is("colors"));
			ColourMetaEntity colour = mongoOperations.findOne(query, ColourMetaEntity.class);
			List<ColourEntity> colourEntities = new ArrayList<ColourEntity>();
			colourEntities.addAll(colour.getColors());
			ColourEntity colourEntity2 = new ColourEntity();
			colourEntity2.setColorName(colourEntity.getColorName());
			colourEntity2.setColorValue(colourEntity.getColorValue());
			colourEntity2.setIsActive(colourEntity.getIsActive());
			List<ColourEntity> entity = colour.getColors();
			for(int i = 0 ;i< entity.size() ; i++) {
					if( entity.get(i).getColorName().equals(name)) {
						colourEntities.set(i, colourEntity2);
						ColourMetaEntity coloEntity = new ColourMetaEntity();
						coloEntity.setId(colour.getId());
						coloEntity.setMetaKey(colour.getMetaKey());
						coloEntity.setColors(colourEntities);
						adminMDataRepo.save(coloEntity);
						return new GlobalResponse("Success", "colore updated successfully", 200);
				}
		    }
			return new GlobalResponse("Failed", "colore not updated ", 404);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
     public GlobalResponse deleteColours(String name) {
	   try {
		LOGGER.info("Inside - AdminMService.deleteColours()");
		Query query = new Query();
		query.addCriteria(Criteria.where("metaKey").is("colors"));
		ColourMetaEntity colour = mongoOperations.findOne(query, ColourMetaEntity.class);
		List<ColourEntity> colourEntities = new ArrayList<ColourEntity>();
		colourEntities.addAll(colour.getColors());
		List<ColourEntity> entity = colour.getColors();
		ColourEntity colourEntity2 = new ColourEntity();
		for(int i=0 ; i< entity.size() ; i++) {
			if(entity.get(i).getIsActive().equals(true) && entity.get(i).getColorName().equals(name)) {
				colourEntity2.setColorName(entity.get(i).getColorName());
				colourEntity2.setColorValue(entity.get(i).getColorValue());
				colourEntity2.setIsActive(false);
				colourEntities.set(i, colourEntity2);
				ColourMetaEntity coloEntity = new ColourMetaEntity();
				coloEntity.setId(colour.getId());
				coloEntity.setMetaKey(colour.getMetaKey());
				coloEntity.setColors(colourEntities);
				adminMDataRepo.save(coloEntity);
				return new GlobalResponse("Success", "Colour inActivated successfully", 200);
		 }
		}
		return new GlobalResponse("Failed", "Colour not inActivated ",404 ) ;
	} catch (Exception e) {
		throw new CustomException(e.getMessage());
	}
  }






     public GlobalResponse deleteColour(String name) {
	    try {
		
		
		
		List<ColourEntity> entity = colour.getColors();
		
		ColourEntity colourEntity2 = new ColourEntity();
		
		
		for(int i=0 ; i< entity.size() ; i++) {
			
			if(entity.get(i).getIsActive().equals(true) && entity.get(i).getColorName().equals(name)) {
				colourEntity2.setColorName(entity.get(i).getColorName());
				colourEntity2.setColorValue(entity.get(i).getColorValue());
				colourEntity2.setIsActive(false);
				
				
				colourEntities.set(i, colourEntity2);
				
				
				ColourMetaEntity coloEntity = new ColourMetaEntity();
				coloEntity.setId(colour.getId());
				coloEntity.setMetaKey(colour.getMetaKey());
				coloEntity.setColors(colourEntities);
				adminMDataRepo.save(coloEntity);
				return new GlobalResponse("Success!!", "Colore Delete Successfully", 200);
				
				
			}
			
			
		}
		
	}
	catch (Exception e) {
		// TODO: handle exception
		
		throw new CustomException(e.getMessage()) ;
	}
}





public ColourEntity getColour(String name) {
	try {
		LOGGER.info("Inside - AdminMService.getColour()");
		Query query = new Query();
		query.addCriteria(Criteria.where("metaKey").is("colors"));
		ColourMetaEntity colour = mongoOperations.findOne(query, ColourMetaEntity.class);
		List<ColourEntity> colourEntities = new ArrayList<ColourEntity>();
	    colourEntities.addAll(colour.getColors());
		for(int i=0 ; i< colourEntities.size() ; i++) {
			
			if(colourEntities.get(i).getColorName().equals(name)) {
		      return colourEntities.get(i) ;
	        }
		}
	    return null ;
	 } catch (Exception e) {
		throw new CustomException(e.getMessage());
	}
}



	
	




	public Map<String, Object> tblList(int page, String metakey, String keyword, int limit, String sort,
			Optional<String> sortBy, String sortName) {
		try {
			LOGGER.info("Inside - AdminMService.tblList()");
			
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
				throw new CustomException("Colour Not Found");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse addBanner(BannerEntity bannerEntity) {
		try {
			
			LOGGER.info("Inside - AdminMService.addBanner()");
			Query query = new Query();
			query.addCriteria(Criteria.where("title").is(bannerEntity.getTitle()));
			List<BannerEntity> findByBannerName = mongoOperations.find(query, BannerEntity.class);

			if (findByBannerName.size() >= 1) {
				throw new CustomException("Banner title already exist");
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

	public GlobalResponse updateBanners(@Valid BannerEntity bannerEntity, Long id) {
		
		LOGGER.info("Inside - AdminMService.updateBanners()");

		try {
			BannerEntity banEntity = bannerRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Id not exist :" + id));

			if (banEntity.getIsActive()) {
				banEntity.setId(id);
				banEntity.setTitle(bannerEntity.getTitle());
				banEntity.setDescription(bannerEntity.getDescription());
				banEntity.setImage(bannerEntity.getImage());
				banEntity.setIsActive(bannerEntity.getIsActive());
				banEntity.setIsDeleted(bannerEntity.getIsDeleted());
				banEntity.setStartDate(bannerEntity.getStartDate());
				banEntity.setEndDate(bannerEntity.getEndDate());
				banEntity.setCreatedOn(new Date());
				bannerRepo.save(banEntity);
				return new GlobalResponse("Success", "Banner updated", 200);

			} else {

				return new GlobalResponse("Failed", "Banner not update", 404);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse deleteBanner(Long id) {

		LOGGER.info("Inside - AdminMService.deleteBanner()");		
		
		BannerEntity bannerEntity = bannerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not exist" + id));

		if (bannerEntity.getIsActive() && bannerEntity.getIsDeleted().equals(false)) {

			bannerEntity.setIsActive(false);
			bannerEntity.setIsDeleted(false);
			bannerRepo.save(bannerEntity);
            return new GlobalResponse("success", "Banner Deleted", 200);

		} else {

			return new GlobalResponse("Failed", "Banner not deleted", 404);
		}

	}

	public GlobalResponse addDesignerCategory(DesignerCategoryEntity designerCategoryEntity) {
		
		LOGGER.info("Inside - AdminMService.addDesignerCategory()");
		try {
			Query query = new Query();
			List<String> updatedList = new ArrayList<>();
			query.addCriteria(Criteria.where("metakey").is("DESIGNER_LEVELS"));
			DesignerCategoryEntity designerEntity = mongoOperations.findOne(query, DesignerCategoryEntity.class);
			for (int i = 0; i < designerCategoryEntity.getDesignerLevels().size(); i++) {
				if (!designerEntity.getDesignerLevels().contains(designerCategoryEntity.getDesignerLevels().get(i))) {
					updatedList.add(designerCategoryEntity.getDesignerLevels().get(i));
					updatedList.addAll(designerEntity.getDesignerLevels());
					designerEntity.setDesignerLevels(updatedList);
					designerEntity.setMetakey("DESIGNER_LEVELS");
					System.out.println(designerEntity);
					designerCategoryRepo.save(designerEntity);
					return new GlobalResponse("Success", "designer category added successfully", 200);
				}
			}
			return new GlobalResponse("Failed", "designer category not added", 404);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public Object getDesignerService() {
		LOGGER.info("Inside - AdminMService.getDesignerService()");
		
		try {
			Query query = new Query();
			List<Object> response = new ArrayList<Object>();
			query.addCriteria(Criteria.where("id").is(101));
			JSONObject jsonObject1= new JSONObject();
			DesignerCategoryEntity designerCategoryEntity=mongoOperations.findOne(query, DesignerCategoryEntity.class);
			System.out.println(designerCategoryEntity);
			for (int i = 0; i < designerCategoryEntity.getDesignerLevels().size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Name", designerCategoryEntity.getDesignerLevels().get(i));
				// response.put(i,jsonObject);
				response.add(jsonObject);
			}
			jsonObject1.put("data", response);
			//designerCategoryEntity.getDesignerLevels();
			return jsonObject1;
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	
	
	public GlobalResponse updateDesignerLevels(DesignerCategoryEntity designerCategoryEntity,String name ) {
		LOGGER.info("Inside - AdminMService.updateDesignerLevels()");
		
		try {
			Query query = new Query();
			List<String> updatedList = new ArrayList<>();
			query.addCriteria(Criteria.where("metakey").is("DESIGNER_LEVELS"));
			DesignerCategoryEntity designerEntity = mongoOperations.findOne(query, DesignerCategoryEntity.class);
			updatedList.addAll(designerEntity.getDesignerLevels());
			List<String> list = designerEntity.getDesignerLevels() ;
			for (int i = 0; i < list.size(); i++) {
			if ( list.get(i).equals(name)) {
					updatedList.set(i, designerCategoryEntity.getDesignerLevels().get(0));
					designerEntity.setDesignerLevels(updatedList);
					designerEntity.setMetakey("DESIGNER_LEVELS");
					System.out.println(designerEntity);
					designerCategoryRepo.save(designerEntity);
					return new GlobalResponse("Success", "Designer category updated successfully", 200);
					}
				else {
					return new GlobalResponse("Failed", "Designerlevel not update, May not exist this level", 404);
					
				}
			}
			return null ;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	

	public GlobalResponse deleteDesignerLevels(String name) {
		LOGGER.info("Inside - AdminMService.deleteDesignerLevels()");
		
		try {
			
			Query query = new Query();
			List<String> updatedList = new ArrayList<>();
			query.addCriteria(Criteria.where("metakey").is("DESIGNER_LEVELS"));
			DesignerCategoryEntity designerEntity = mongoOperations.findOne(query, DesignerCategoryEntity.class);
			updatedList.addAll(designerEntity.getDesignerLevels());
			List<String> list = designerEntity.getDesignerLevels() ;
			for (int i = 0; i < list.size(); i++) {
				
				if ( list.get(i).equals(name)) {
					updatedList.remove(i);
					designerEntity.setDesignerLevels(updatedList);
					designerEntity.setMetakey("DESIGNER_LEVELS");
					System.out.println(designerEntity);
					designerCategoryRepo.save(designerEntity);
					return new GlobalResponse("Success", "Designerlevels deleted successfully", 200);
				}
				else {
					return new GlobalResponse("Failed", "Designerlevels not exist ", 404);
				}
			}
			return null;
		} catch (Exception e) {
			 throw new CustomException(e.getMessage());
		}
	}

	public Map<String ,String> getDesignerCategorybyname(String name) {
		LOGGER.info("Inside - AdminMService.getDesignerCategorybyname()");
		
		try {
			Query query = new Query();
			Map<String ,String> response = new HashMap<>();
			query.addCriteria(Criteria.where("id").is(101));
			DesignerCategoryEntity designerCategoryEntity = mongoOperations.findOne(query,DesignerCategoryEntity.class);
			System.out.println(designerCategoryEntity);
			
			for (int i = 0; i < designerCategoryEntity.getDesignerLevels().size(); i++) {
				if(designerCategoryEntity.getDesignerLevels().get(i).contentEquals(name)) {
					response.put("Name", designerCategoryEntity.getDesignerLevels().get(i));
				}
				
			}
			
			return response;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	

	



	
	

	
	
	
	
	
  
	



	

}
