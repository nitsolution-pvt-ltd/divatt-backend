package com.divatt.admin.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.divatt.admin.entity.ColourEntity;
import com.divatt.admin.entity.ColourMetaEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.category.CategoryEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.AdminMDataRepo;

@Service
public class AdminMService {

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private AdminMDataRepo adminMDataRepo;
	
	public List<ColourEntity> getColour() {
		try
		{
			Query query= new Query();
			query.addCriteria(Criteria.where("metaKey").is("colors"));
			 ColourMetaEntity coloreData = mongoOperations.findOne(query, ColourMetaEntity.class);
			 List<ColourEntity> coloreEntity=coloreData.getColors();
			 List<ColourEntity>filterColour=new ArrayList<ColourEntity>();
			 for(int i=0;i<coloreEntity.size();i++)
			 {
				 if(coloreEntity.get(i).getIsActive().equals("true"))
				 {
					 filterColour.add(coloreEntity.get(i));
				 }
			 }
			return filterColour;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse addColour(ColourEntity colourEntity) {
		try
		{
			Query query= new Query();
			query.addCriteria(Criteria.where("meta_key").is("colors"));
			ColourMetaEntity colour = mongoOperations.findOne(query, ColourMetaEntity.class);
			List<ColourEntity> colourEntities= new ArrayList<ColourEntity>();
			colourEntities.addAll(colour.getColors());
			colourEntities.add(colourEntity);
			ColourMetaEntity coloEntity= new ColourMetaEntity();
			coloEntity.setId(colour.getId());
			coloEntity.setMetaKey(colour.getMetaKey());
			coloEntity.setColors(colourEntities);
			adminMDataRepo.save(coloEntity);
			return new GlobalResponse("Success!!", "Colore Added Successfully", 200);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> tblList(int page, String metakey, String keyword, int limit, String sort,
			Optional<String> sortBy, String sortName) {
		try
		{
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
				findAll = adminMDataRepo.findByMetaKey(metakey,"0", pagingSort);
			} else {
				findAll = adminMDataRepo.Search(keyword,"0", pagingSort);

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
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}

	
}
