package com.divatt.admin.servicesImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.divatt.admin.constant.MessageConstant;
import com.divatt.admin.entity.BannerEntity;
import com.divatt.admin.entity.ColourEntity;
import com.divatt.admin.entity.ColourMetaEntity;
import com.divatt.admin.entity.DesignerCategoryEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.AdminMDataRepo;
import com.divatt.admin.repo.BannerRepo;
import com.divatt.admin.repo.DesignerCategoryRepo;
import com.divatt.admin.services.AdminMService;
import com.divatt.admin.services.SequenceGenerator;


@Service
public class AdminMServiceImpl implements AdminMService{
	
	
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

	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminMServiceImpl.class);
	

	public List<ColourEntity> getColours() {
		LOGGER.info("Inside - AdminMServiceImplImpl.getColours()");
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
		LOGGER.info("Inside - AdminMServiceImplImpl.addColour()");
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
					return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.COLOUR_ADDED.getMessage(),200);
				}
			return new GlobalResponse(MessageConstant.FAILED.getMessage(), MessageConstant.COLOUR_NOT_ADDED.getMessage(), 404);
		}catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	

	public GlobalResponse updateColours(ColourEntity colourEntity, String name) {
		try {
			LOGGER.info("Inside - AdminMServiceImpl.updateColours()");
			
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
						return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.COLOUR_UPDATED.getMessage(), 200);
				}
		    }
			return new GlobalResponse(MessageConstant.FAILED.getMessage(), MessageConstant.COLOUR_NOT_UPDATED.getMessage(), 404);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
     public GlobalResponse deleteColours(String name) {
	   try {
		LOGGER.info("Inside - AdminMServiceImpl.deleteColours()");
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
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.COLOUR_INACTIVATED.getMessage(), 200);
		 }
		}
		return new GlobalResponse(MessageConstant.FAILED.getMessage(), MessageConstant.COLOUR_ACTIVATED.getMessage() ,404 ) ;
	} catch (Exception e) {
		throw new CustomException(e.getMessage());
	}
  }



     public GlobalResponse deleteColour(String name) {
    		try {
    			
    			Query query = new Query() ;
    			query.addCriteria(Criteria.where("metaKey").is("colors")) ;
    			ColourMetaEntity colourMetaEntity = mongoOperations.findOne(query, ColourMetaEntity.class) ;
    			List<ColourEntity> listColour = colourMetaEntity.getColors();
    			Boolean res = true;
    		    for(int i= 0 ; i< listColour.size() ; i++ ) {
    			if( listColour.get(i).getColorName().contentEquals(name)) {
    					//HttpEntity<String> request = new HttpEntity<>(headers);
    					//ResponseEntity<Boolean> response = this.restTemplate.getForEntity("https://localhost:9095/dev/designerProduct/getColour/"+ listColour.get(i).getColorValue(),Boolean.class);
    				//	ResponseEntity<Boolean> response1 = this.restTemplate.exchange("https://localhost:9095/dev/designerProduct/getColour/"+ listColour.get(i).getColorValue(),
    							//Boolean.class);
    					//res= response.getBody();
    					if(res) {
    						 listColour.remove(i);
    							//ColourMetaEntity colourMetaEntity = new ColourMetaEntity();
    							colourMetaEntity.setColors(listColour);
    							colourMetaEntity.setMetaKey("colors");
    							adminMDataRepo.save(colourMetaEntity);
    							return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.COLOUR_DELETED.getMessage(), 200);
    					 }
    				}
    			}
    			
    		 return new GlobalResponse(MessageConstant.FAILED.getMessage(), MessageConstant.COLOUR_NOT_DELETED.getMessage() ,404);
    		}
    		catch (Exception e) {
    		    throw new CustomException(e.getMessage()) ;
    		}
    	}





public ColourEntity getColour(String name) {
	try {
		LOGGER.info("Inside - AdminMServiceImpl.getColour()");
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
			LOGGER.info("Inside - AdminMServiceImpl.tblList()");
			
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
				throw new CustomException(MessageConstant.COLOUR_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse addBanner(BannerEntity bannerEntity) {
		try {
			
			LOGGER.info("Inside - AdminMServiceImpl.addBanner()");
			Query query = new Query();
			query.addCriteria(Criteria.where("title").is(bannerEntity.getTitle()));
			List<BannerEntity> findByBannerName = mongoOperations.find(query, BannerEntity.class);

			if (findByBannerName.size() >= 1) {
				throw new CustomException(MessageConstant.BANNER_ALREADY_EXIST.getMessage());
			} else {

				BannerEntity banEntity = new BannerEntity();
				banEntity.setId((long) sequenceGenerator.getNextSequence(BannerEntity.SEQUENCE_NAME));
				banEntity.setTitle(bannerEntity.getTitle());
				banEntity.setDescription(bannerEntity.getDescription());
				banEntity.setImage(bannerEntity.getImage());
				banEntity.setIsActive(true);
				banEntity.setIsDeleted(false);
				banEntity.setStartDate(bannerEntity.getStartDate());
				banEntity.setEndDate(bannerEntity.getEndDate());
				banEntity.setCreatedOn(new Date());

				bannerRepo.save(banEntity);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.BANNER_ADDED.getMessage(), 200);

			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse updateBanners(@Valid BannerEntity bannerEntity, Long id) {
		
		LOGGER.info("Inside - AdminMServiceImpl.updateBanners()");

		try {
			BannerEntity banEntity = bannerRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ID_NOT_EXIST.getMessage() + id));

			
			//BannerEntity updateEntity= new BannerEntity();
			
			
				
				if(banEntity.getIsDeleted().equals(false)) {
					banEntity.setId(id);
					banEntity.setImage(bannerEntity.getImage());
					banEntity.setTitle(bannerEntity.getTitle());
					banEntity.setCreatedOn(new Date());
					banEntity.setIsActive(true);
					banEntity.setIsDeleted(false);
					banEntity.setEndDate(bannerEntity.getEndDate());
					banEntity.setDescription(bannerEntity.getDescription());
					banEntity.setStartDate(bannerEntity.getStartDate());
					bannerRepo.save(banEntity);
					
					return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.BANNER_UPDATED.getMessage(), 200);
					
				}
				else {
					return new GlobalResponse(MessageConstant.FAILED_BANNER.getMessage(), MessageConstant.BANNER_NOT_UPDATED.getMessage(), 200);
				}
				
				
			
			
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse deleteBanner(Long id) {

		LOGGER.info("Inside - AdminMServiceImpl.deleteBanner()");		
		
		BannerEntity bannerEntity = bannerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ID_NOT_EXIST.getMessage() + id));

		if (bannerEntity.getIsDeleted().equals(false)) {

			bannerEntity.setIsDeleted(true);
			bannerRepo.save(bannerEntity);
            return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.BANNER_DELETED.getMessage(), 200);

		} else {

			return new GlobalResponse(MessageConstant.FAILED.getMessage(), MessageConstant.BANNER_NOT_DELETED.getMessage(), 404);
		}

	}
	
	
	
	 
	 public Map<String, Object> bannerListing(int page, int limit, String sort, String sortName, Boolean isDeleted,
	 		String keyword, Optional<String> sortBy) {
	 	
	 		try {
	 			Pageable pagingSort = null;
	 			
	 	
	 			if (sort.equals("ASC")) {
	 				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
	 			} else {
	 				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
	 			}
	 	
	 			Page<BannerEntity> findAll = null;
	 	
	 			if (keyword.isEmpty()) {
	 				findAll = bannerRepo.findByIsDeleted(isDeleted, pagingSort);
	 			} else {
	 				findAll = bannerRepo.Search(keyword, isDeleted, pagingSort);
	 	
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
	 				throw new CustomException(MessageConstant.BANNER_NOT_FOUND.getMessage());
	 			} else {
	 				return response;
	 			}
	 		} catch (Exception e) {
	 			throw new CustomException(e.getMessage());
	 		}
	 		}
	
	

	public GlobalResponse addDesignerCategory(DesignerCategoryEntity designerCategoryEntity) {
		
		LOGGER.info("Inside - AdminMServiceImpl.addDesignerCategory()");
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
					return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.DESIGNER_CATEGORY_ADDED.getMessage(), 200);
				}
			}
			return new GlobalResponse(MessageConstant.FAILED.getMessage(), MessageConstant.DESIGNER_CATEGORY_NOT_ADDED.getMessage(), 404);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public Object getDesignerService() {
		LOGGER.info("Inside - AdminMServiceImpl.getDesignerService()");
		
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
		LOGGER.info("Inside - AdminMServiceImpl.updateDesignerLevels()");
		
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
					return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.DESIGNER_CATEGORY_UPDATED.getMessage(), 200);
					}
				else {
					return new GlobalResponse(MessageConstant.FAILED.getMessage(), MessageConstant.DESIGNER_CATEGORY_NOT_UPDATED.getMessage(), 404);
					
				}
			}
			return null ;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	

	public GlobalResponse deleteDesignerLevels(String name) {
		LOGGER.info("Inside - AdminMServiceImpl.deleteDesignerLevels()");
		
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
					return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.DESIGNER_LEVEL_DELETED.getMessage(), 200);
				}
				else {
					return new GlobalResponse(MessageConstant.FAILED.getMessage(), MessageConstant.DESIGNER_LEVEL_NOT_EXIST.getMessage(), 404);
				}
			}
			return null;
		} catch (Exception e) {
			 throw new CustomException(e.getMessage());
		}
	}

	public Map<String ,String> getDesignerCategorybyname(String name) {
		LOGGER.info("Inside - AdminMServiceImpl.getDesignerCategorybyname()");
		
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


	public BannerEntity getBanner(Long id) {
LOGGER.info("Inside - AdminMServiceImpl.getBanner()");		
		
		BannerEntity bannerEntity = bannerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ID_NOT_EXIST.getMessage() + id));
		
		if(bannerEntity.getIsDeleted().equals(false)) {
			return bannerEntity;
		}
		else {
			return null ;
		}

		
	}


	public GlobalResponse changeBannerStatus(Long id) {
		
		BannerEntity bannerEntity = bannerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ID_NOT_EXIST.getMessage() + id));
		if(bannerEntity.getIsDeleted().equals(false) && bannerEntity.getIsActive().equals(true)) {
			
			bannerEntity.setIsActive(false);
			bannerRepo.save(bannerEntity);
			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.BANNER_INACTIVE.getMessage(), 200);
		}
		else {
			bannerEntity.setIsActive(true);
			bannerRepo.save(bannerEntity);
			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.BANNER_ACTIVE.getMessage(), 200);
		}
		
		
	}

}
