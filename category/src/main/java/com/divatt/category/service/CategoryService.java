package com.divatt.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divatt.category.Entity.CategoryEntity;
import com.divatt.category.Exception.CustomException;
import com.divatt.category.Repository.CategoryRepo;
import com.divatt.category.helper.CustomFunction;
import com.divatt.category.response.GlobalResponse;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private CustomFunction customFunction;

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
			if(categoryData.getCategoryId()!=0)
			{

				if(!categoryRepo.existsById(categoryData.getCategoryId()))
				{
					CategoryEntity validatedCategoryData=(CategoryEntity) customFunction.addCategoryFieldValidation(categoryData).getBody();
					categoryRepo.save(validatedCategoryData);
					return new GlobalResponse("Success", "Category Added Succesfully", 200);
				}
				else
				{
					return new GlobalResponse("Category Exist", "Category Id Already exists", 200);
				}
			}
			else
			{
				return new GlobalResponse("Field Required", "Category Id required", 200);
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
