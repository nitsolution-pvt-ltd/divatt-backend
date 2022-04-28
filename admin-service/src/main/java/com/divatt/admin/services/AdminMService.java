package com.divatt.admin.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.divatt.admin.entity.ColourEntity;
import com.divatt.admin.entity.ColourMetaEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.AdminMDataRepo;

@Service
public class AdminMService {

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private AdminMDataRepo adminMDataRepo;
	
	public ColourMetaEntity getColour() {
		try
		{
			Query query= new Query();
			query.addCriteria(Criteria.where("meta_key").is("colors"));
			return mongoOperations.findOne(query, ColourMetaEntity.class);
			//return coloreData;
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
			coloEntity.setMeta_key(colour.getMeta_key());
			coloEntity.setColors(colourEntities);
			adminMDataRepo.save(coloEntity);
			return new GlobalResponse("Success!!", "Colore Added Successfully", 200);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}

	
}
