package com.divatt.admin.contoller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.AdminModule;
import com.divatt.admin.entity.AdminModules;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.LoginEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.AdminModulesRepo;
import com.divatt.admin.services.SequenceGenerator;



@RestController
@RequestMapping("/admin")
public class RoleAndPermission {
	
	@Autowired
	private AdminModulesRepo adminModulesRepo;
	
	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	Logger LOGGER = LoggerFactory.getLogger(RoleAndPermission.class);
	
	@GetMapping("/modules")
	public ResponseEntity<?> getModules(){
		LOGGER.info("Inside - RoleAndPermission.getModules()");
		try {
			return ResponseEntity.ok(Optional.of(adminModulesRepo.findAll()
					.stream()
					.filter(e -> e.getMetaKey()!=null && e.getMetaKey().equals("admin_modules"))
					.toList().get(0)).orElseThrow(()->new CustomException("No Data Found")));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
		
	}
	
	@GetMapping("/roles")
	public ResponseEntity<?> getRoles(){
		LOGGER.info("Inside - RoleAndPermission.getRole()");
		try {
		return ResponseEntity.ok(Optional.of(adminModulesRepo.findAll()
				.stream()
				.filter(e -> e.getMetaKey()!=null && e.getMetaKey().equals("ROLE"))
				.toList().get(0)).orElseThrow(()->new CustomException("No Data Found")));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/role/{name}")
	public ResponseEntity<?> getRole(@PathVariable("name") String name){
		LOGGER.info("Inside - RoleAndPermission.getRole()");
		try {
		List<AdminModules> orElseThrow = Optional.of(adminModulesRepo.findAll()
				.stream()
				.filter(e -> e.getMetaKey()!=null && e.getMetaKey().equals("ROLE") && e.getRoleName().equals(name))
				.toList())
				.orElseThrow(()->new CustomException("No Data Found"));
		if(orElseThrow.size()<1) 
			throw new CustomException("No Data Found");
		else
			return ResponseEntity.ok(orElseThrow.get(0));	
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@PostMapping("/role")
	public ResponseEntity<?> addRole(@RequestBody AdminModules adminModules){
		adminModules.setmId(sequenceGenerator.getNextSequence(AdminModules.SEQUENCE_NAME));
		adminModules.setMetaKey("ROLE");
		adminModulesRepo.save(adminModules);
		//adminModulesRepo.findByRoleName(adminModules.getRoleName()).ifPresentOrElse((value)->{throw new CustomException("This Role is Already Present");} , ()->{adminModulesRepo.save(adminModules);});
		return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Role Added Successfully",200));
	}

}
