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
	
	
}
