package com.divatt.category.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.category.Entity.CategoryEntity;
import com.divatt.category.Exception.CustomException;
import com.divatt.category.response.GlobalResponse;
import com.divatt.category.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categoryList")
	public List<CategoryEntity> CategoryList()
	{
		try
		{
			return this.categoryService.listAllData();
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
//	@GetMapping("/main")
//	public String main()
//	{
//		return "Okk";
//	}
	@PostMapping("/addCategory")
	public GlobalResponse addCategory(@RequestBody CategoryEntity categoryData)
	{
		try
		{
			return this.categoryService.addCategory(categoryData);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@PutMapping("/deleteCategory/{categoryId}")
	public GlobalResponse deleteCategory(@PathVariable Long categoryId)
	{
		try
		{
			return categoryService.deleteCategory(categoryId);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
