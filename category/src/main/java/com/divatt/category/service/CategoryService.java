package com.divatt.category.service;

import java.util.List;
import java.util.Optional;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.divatt.category.Entity.CategoryEntity;
import com.divatt.category.Exception.CustomException;
import com.divatt.category.Repository.CategoryRepo;
import com.divatt.category.helper.CustomFunction;
import com.divatt.category.response.GlobalResponse;
import org.springframework.data.mongodb.*;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private CustomFunction customFunction;
	@Autowired
	private MongoTemplate mongoTemplate;
	public  List<CategoryEntity> listAllData()
	{
		try
		{
			return categoryRepo.findAll();
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
	
	public GlobalResponse addCategory(CategoryEntity categoryData)
	{
		try
		{
			if(categoryData.getCategoryId()!=null)
			{

				if(!categoryRepo.existsById(categoryData.getCategoryId()))
				{
					CategoryEntity validatedCategoryData=(CategoryEntity) customFunction.addCategoryFieldValidation(categoryData).getBody();
					categoryRepo.save(validatedCategoryData);
					return new GlobalResponse("Success", "Category Added Succesfully", 200);
				}
				else
				{
					return new GlobalResponse("Category Exist", "Category Id Already exists", 400);
				}
			}
			else
			{
				return new GlobalResponse("Field Required", "Category Id required", 400);
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponse deleteCategory(Long categoryId)
	{
		try
		{
			if(categoryRepo.existsById(categoryId))
			{
				Optional<CategoryEntity> prevoiusData=categoryRepo.findById(categoryId);
				if(customFunction.deleteCategory(categoryId, prevoiusData).getBody().equals("Deleted"))
				{
					return new GlobalResponse("Not found", "Already Deleted", 400);
				}
				else
				{
					return new GlobalResponse("Success", "Category Deleted Successfully", 200);
				}
			}
			else
			{
				return new GlobalResponse("Bad Request", "Category Id Does Nooot Exist", 400);
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
