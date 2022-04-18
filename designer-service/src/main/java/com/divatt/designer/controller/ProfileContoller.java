package com.divatt.designer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.entity.profile.DesignerLoginEntity;
import com.divatt.designer.entity.profile.DesignerProfile;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.DesignerLoginRepo;
import com.divatt.designer.repo.DesignerProfileRepo;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.SequenceGenerator;


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
		return ResponseEntity.ok(designerProfileRepo.findById(id));
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addDesigner(@RequestBody DesignerProfileEntity designerProfileEntity){
		try {
			designerLoginRepo.findByEmail(designerProfileEntity.getDesignerProfile().getEmail());
			if(designerLoginRepo.findByEmail(designerProfileEntity.getDesignerProfile().getEmail()).isPresent())
				throw new CustomException("Email Already Present");
			DesignerLoginEntity designerLoginEntity = new DesignerLoginEntity();
			designerLoginEntity.setUid(sequenceGenerator.getNextSequence(DesignerLoginEntity.SEQUENCE_NAME));
			designerLoginEntity.setEmail(designerProfileEntity.getDesignerProfile().getEmail());
			designerLoginEntity.setPassword(bCryptPasswordEncoder.encode(designerProfileEntity.getDesignerProfile().getPassword()));
			designerLoginEntity.setIsActive(true);
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
			return ResponseEntity.ok(new GlobalResponce("SUCCESS","Register Successfully",200));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}

}
