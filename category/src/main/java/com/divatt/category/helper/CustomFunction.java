package com.divatt.category.helper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.divatt.category.Entity.CategoryEntity;
import com.divatt.category.Repository.CategoryRepo;
@Service
public class CustomFunction {

	@Autowired
	private CategoryRepo categoryRepo;
	public ResponseEntity<?> addCategoryFieldValidation(CategoryEntity newCategoryData)
	{
		if(newCategoryData.getCategoryId()!=null
		   && newCategoryData.getCategory_name()!=null
		   && newCategoryData.getCategory_description()!=null
		   && newCategoryData.getCategory_image()!=null
		   && newCategoryData.getCreated_by()!=null
		   && newCategoryData.getParent_id()!=null)
		{
			newCategoryData.setIs_Active(true);
			newCategoryData.setCreated_on(System.currentTimeMillis());
			newCategoryData.setIs_deleted(false);
			return ResponseEntity.ok(newCategoryData);
		}
		else
		{
			return null;
		}
	}
	public ResponseEntity<?> deleteCategory(Long categotyId, Optional<CategoryEntity> prevoiusData)
	{
		CategoryEntity _category=prevoiusData.get();
		if(_category.getIs_deleted().equals(true))
		{
			return ResponseEntity.ok("Deleted");
		}
		else
		{
			_category.setIs_deleted(true);
			_category.setIs_Active(false);
			categoryRepo.save(_category);
			return ResponseEntity.ok("Success");
		}
	}
}
