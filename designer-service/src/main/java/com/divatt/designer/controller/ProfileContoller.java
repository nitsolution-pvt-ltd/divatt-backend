package com.divatt.designer.controller;

import java.net.URI;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.entity.profile.DesignerLoginEntity;
import com.divatt.designer.entity.profile.DesignerProfile;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.DesignerLoginRepo;
import com.divatt.designer.repo.DesignerProfileRepo;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.SequenceGenerator;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import springfox.documentation.spring.web.json.Json;


@RestController
@RequestMapping("/designer")
public class ProfileContoller {
	
	@Autowired
	private DesignerProfileRepo designerProfileRepo;
	
	@Autowired
	private DesignerLoginRepo designerLoginRepo;
	
	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getDesigner(@PathVariable Long id){
		try {
			return ResponseEntity.ok(designerProfileRepo.findById(id));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addDesigner(@Valid @RequestBody DesignerProfileEntity designerProfileEntity){
		try {
			designerLoginRepo.findByEmail(designerProfileEntity.getDesignerProfile().getEmail());
			if(designerLoginRepo.findByEmail(designerProfileEntity.getDesignerProfile().getEmail()).isPresent())
				throw new CustomException("Email Already Present");
			DesignerLoginEntity designerLoginEntity = new DesignerLoginEntity();
			designerLoginEntity.setUid(sequenceGenerator.getNextSequence(DesignerLoginEntity.SEQUENCE_NAME));
			designerLoginEntity.setEmail(designerProfileEntity.getDesignerProfile().getEmail());
			designerLoginEntity.setPassword(bCryptPasswordEncoder.encode(designerProfileEntity.getDesignerProfile().getPassword()));
			designerLoginEntity.setIsActive(false);
			designerLoginEntity.setIsDeleted(false);
			designerLoginEntity.setIsApproved(false);
			designerLoginEntity.setIsProfileCompleated(false);
			designerLoginEntity.setIsProfileSubmitted(false);
			if(designerLoginRepo.save(designerLoginEntity)!=null) {
				designerProfileEntity.setDesignerId(Long.parseLong(designerLoginEntity.getUid().toString()));
				designerProfileEntity.setdId((long)sequenceGenerator.getNextSequence(DesignerProfileEntity.SEQUENCE_NAME));
				DesignerProfile designerProfile = designerProfileEntity.getDesignerProfile();
				designerProfile.setPassword(bCryptPasswordEncoder.encode(designerProfileEntity.getDesignerProfile().getPassword()));
				designerProfileEntity.setDesignerProfile(designerProfile);
				designerProfileRepo.save(designerProfileEntity);	
			}
			JsonObject jo = new JsonObject();
			jo.addProperty("senderMailId", designerProfileEntity.getDesignerProfile().getEmail());
			jo.addProperty("subject", "Successfully Registration");
			jo.addProperty("body", "Welcome " + designerProfileEntity.getDesignerProfile().getEmail() + ""
					+ ",\n                           "
					+ " you have been register successfully.Please active your account by clicking the bellow link "+ URI.create("http://localhost:8083/dev/designer/redirect/"+Base64.getEncoder().encodeToString(designerLoginEntity.getEmail().toString().getBytes())) +" . We will verify your details and come back to you soon." );
			jo.addProperty("enableHtml", false);
			try {
				Unirest.setTimeouts(0, 0);
				HttpResponse<String> response = Unirest.post("http://192.168.29.23:8080/dev/auth/sendMail")
				  .header("Content-Type", "application/json")
				  .body(jo.toString())
				  .asString();
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
			return ResponseEntity.ok(new GlobalResponce("SUCCESS","Register Successfully",200));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateDesigner(@Valid @RequestBody DesignerLoginEntity designerLoginEntity){
		Optional<DesignerLoginEntity> findById = designerLoginRepo.findById(designerLoginEntity.getUid());
		if(!findById.isPresent())
			throw new CustomException("Designer Details Not Found");
		else {
			DesignerLoginEntity designerLoginEntityDB = findById.get();
			designerLoginEntityDB.setIsActive(designerLoginEntity.getIsActive());
			designerLoginEntityDB.setIsDeleted(designerLoginEntity.getIsDeleted());
			designerLoginEntityDB.setIsApproved(designerLoginEntity.getIsApproved());
			designerLoginEntityDB.setIsProfileCompleated(designerLoginEntity.getIsProfileCompleated());
			designerLoginEntityDB.setIsProfileSubmitted(designerLoginEntity.getIsProfileSubmitted());
			designerLoginRepo.save(designerLoginEntityDB);
		}
		return ResponseEntity.ok(new GlobalResponce("SUCCESS","Data Updated Successfully",200));
	}
	
	@GetMapping("/all/{field}/{value}")
	public ResponseEntity<?> getDesigner(@PathVariable("field") String field,@PathVariable("value") Boolean value){
		
		try {
			if(field.equals("approve")) {
				List<DesignerLoginEntity> findByIsApproved = designerLoginRepo.findByIsApproved(value);
				if(findByIsApproved.size()<1)
					throw new CustomException("All Designers Are Approved");
				return ResponseEntity.ok(findByIsApproved);
			}else if(field.equals("submit")) {
				List<DesignerLoginEntity> findByIsProfileSubmitted = designerLoginRepo.findByIsProfileSubmitted(value);
				if(findByIsProfileSubmitted.size()<1)
					throw new CustomException("All Designers Profiles Are Submitted");
				return ResponseEntity.ok(findByIsProfileSubmitted);
			}else {
				throw new CustomException("Path Not Found");
			}
			
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
		
	}
	
	@RequestMapping(value = "/redirect/{email}", method = RequestMethod.GET)
	public void method(HttpServletResponse httpServletResponse,@PathVariable("email") String email) {
		Optional<DesignerLoginEntity> findByEmail = designerLoginRepo.findByEmail(new String(Base64.getDecoder().decode(email)));
		if(findByEmail.isPresent()) {
			DesignerLoginEntity designerLoginEntity = findByEmail.get();
			designerLoginEntity.setIsActive(true);
			designerLoginRepo.save(designerLoginEntity);
		}
			
	    httpServletResponse.setHeader("Location", "http://localhost:8083/dev/swagger-ui/index.html");
	    httpServletResponse.setStatus(302);
	}

}
