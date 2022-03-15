package com.divatt.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divatt.category.Entity.CategoryEntity;
import com.divatt.category.Exception.CustomException;
import com.divatt.category.Repository.CategoryRepo;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;

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
	
	
	public String addCategory(CategoryEntity categoryData)
	{
		try
		{
			categoryRepo.save(categoryData);
			return "okk";
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}
}
