package com.divatt.designer.controller;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.entity.profile.DesignerLogEntity;
import com.divatt.designer.entity.profile.DesignerLoginEntity;
import com.divatt.designer.entity.profile.DesignerPersonalInfoEntity;
import com.divatt.designer.entity.profile.DesignerProfile;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.entity.profile.SocialProfile;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.DesignerLogRepo;
import com.divatt.designer.repo.DesignerLoginRepo;
import com.divatt.designer.repo.DesignerPersonalInfoRepo;
import com.divatt.designer.repo.DesignerProfileRepo;
import com.divatt.designer.repo.ProductRepository;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.SequenceGenerator;
import com.google.gson.JsonObject;
import com.google.inject.internal.Errors;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import springfox.documentation.spring.web.json.Json;
import com.divatt.designer.repo.*;

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
	
	@Autowired
	private DesignerPersonalInfoRepo designerPersonalInfoRepo;
	
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	DesignerLogRepo designerLogRepo;

	@Autowired
	DatabaseSeqRepo databaseSeqRepo;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getDesigner(@PathVariable Long id) {
		try {
			Optional<DesignerProfileEntity> findById = designerProfileRepo.findBydesignerId(id);
			
			if(findById.isPresent());
			DesignerProfileEntity designerProfileEntity = findById.get();
			try {
				if(designerProfileEntity.getSocialProfile()==null)
					designerProfileEntity.setSocialProfile(new SocialProfile());
			}catch(Exception e) {
				designerProfileEntity.setSocialProfile(new SocialProfile());
			}
			try {
				DesignerLoginEntity designerLoginEntity = designerLoginRepo.findById(id).get();
				designerProfileEntity.setAccountStatus(designerLoginEntity.getAccountStatus());
				designerProfileEntity.setProfileStatus(designerLoginEntity.getProfileStatus());
				designerProfileEntity.setDesignerPersonalInfoEntity(designerPersonalInfoRepo.findByDesignerId(id).get());
			}catch(Exception e) {
				
			}
			
			return ResponseEntity.ok(designerProfileEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserDesigner(@PathVariable Long id) {
		try {
			Optional<DesignerLoginEntity> findById = designerLoginRepo.findById(id);
			if (!findById.isPresent())
				throw new CustomException("This designer profile is not completed");
			if(!findById.get().getProfileStatus().equals("COMPLETED"))
				throw new CustomException("This designer profile is not completed");
			
			DesignerLoginEntity designerLoginEntity = findById.get();
			designerLoginEntity.setDesignerProfileEntity(
					designerProfileRepo.findBydesignerId(Long.parseLong(designerLoginEntity.getdId().toString())).get());
			designerLoginEntity.setProductCount(productRepo.countByIsDeletedAndAdminStatusAndDesignerIdAndIsActive(false, "Approved", Long.parseLong(designerLoginEntity.getdId().toString()),true));
			
			
			return ResponseEntity.ok(designerLoginEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/add")
	public ResponseEntity<?> addDesigner(@Valid @RequestBody DesignerProfileEntity designerProfileEntity) {
		try {
			designerLoginRepo.findByEmail(designerProfileEntity.getDesignerProfile().getEmail());

				Unirest.setTimeouts(0, 0);
				JsonNode body = Unirest.get("http://localhost:8080/dev/auth/Present/"+designerProfileEntity.getDesignerProfile().getEmail())
				  .asJson().getBody();
				JSONObject jsObj = body.getObject();
				if((boolean) jsObj.get("isPresent"))
					throw new CustomException("Email already present");
			DesignerLoginEntity designerLoginEntity = new DesignerLoginEntity();

			designerLoginEntity.setdId((long) sequenceGenerator.getNextSequence(DesignerLoginEntity.SEQUENCE_NAME));
			designerLoginEntity.setEmail(designerProfileEntity.getDesignerProfile().getEmail());
			designerLoginEntity.setPassword(
					bCryptPasswordEncoder.encode(designerProfileEntity.getDesignerProfile().getPassword()));
			designerLoginEntity.setIsDeleted(false);
			designerLoginEntity.setProfileStatus("INACTIVE");
			if (designerLoginRepo.save(designerLoginEntity) != null) {
				designerProfileEntity.setDesignerId(Long.parseLong(designerLoginEntity.getdId().toString()));
				designerProfileEntity
						.setId((long) sequenceGenerator.getNextSequence(DesignerProfileEntity.SEQUENCE_NAME));
				DesignerProfile designerProfile = designerProfileEntity.getDesignerProfile();
				designerProfile.setPassword(
						bCryptPasswordEncoder.encode(designerProfileEntity.getDesignerProfile().getPassword()));
				designerProfileEntity.setDesignerProfile(designerProfile);
				designerProfileRepo.save(designerProfileEntity);
			}

			DesignerLogEntity designerLogEntity = new DesignerLogEntity();


			JsonObject jo = new JsonObject();
			jo.addProperty("senderMailId", designerProfileEntity.getDesignerProfile().getEmail());
			jo.addProperty("subject", "Successfully Registration");
			jo.addProperty("body", "Welcome " + designerProfileEntity.getDesignerProfile().getEmail() + ""
					+ ",\n                           "
					+ " you have been register successfully.Please active your account by clicking the bellow link "
					+ URI.create("http://65.1.190.195:8083/dev/designer/redirect/"
							+ Base64.getEncoder().encodeToString(designerLoginEntity.getEmail().toString().getBytes()))
					+ " . We will verify your details and come back to you soon.");
			jo.addProperty("enableHtml", false);
			try {
				Unirest.setTimeouts(0, 0);
				HttpResponse<String> response = Unirest.post("http://localhost:8080/dev/auth/sendMail")
						.header("Content-Type", "application/json").body(jo.toString()).asString();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			return ResponseEntity.ok(new GlobalResponce("SUCCESS", "Registered successfully", 200));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/update")
	public ResponseEntity<?> updateDesigner(@RequestBody DesignerLoginEntity designerLoginEntity) {
		
		Optional<DesignerLoginEntity> findById = designerLoginRepo.findById(designerLoginEntity.getdId());
		if (!findById.isPresent())
			throw new CustomException("Designer Details Not Found");
		else {
			DesignerLoginEntity designerLoginEntityDB = findById.get();
			if(designerLoginEntity.getProfileStatus().equals("REJECTED")) {
				designerLoginEntityDB.setAdminComment(designerLoginEntity.getAdminComment());
			}
	
			
			designerLoginEntityDB.setProfileStatus(designerLoginEntity.getProfileStatus());
			designerLoginEntityDB.setAccountStatus("ACTIVE");
			designerLoginEntityDB.setIsDeleted(designerLoginEntity.getIsDeleted());
			designerLoginRepo.save(designerLoginEntityDB);
		}
		return ResponseEntity.ok(new GlobalResponce("SUCCESS", "Updated successfully", 200));
	}

	@PutMapping("/profile/update")
	public ResponseEntity<?> updateDesignerProfile(@Valid @RequestBody DesignerProfileEntity designerProfileEntity) {
		try {
			@Valid
			DesignerPersonalInfoEntity designerPersonalInfoEntity = designerProfileEntity.getDesignerPersonalInfoEntity();	
				Optional<DesignerPersonalInfoEntity> findByDesignerId = designerPersonalInfoRepo.findByDesignerId(designerProfileEntity.getDesignerId());
				if(findByDesignerId.isPresent())
					designerPersonalInfoEntity.setId(findByDesignerId.get().getId());
				else
					designerPersonalInfoEntity.setId((long)sequenceGenerator.getNextSequence(DesignerPersonalInfoEntity.SEQUENCE_NAME));
				designerPersonalInfoEntity.setDesignerId(designerProfileEntity.getDesignerId());
				
				designerPersonalInfoRepo.save(designerPersonalInfoEntity);
		}catch(Exception e) {
			throw new CustomException("Please check the fields");
		}
		
		Optional<DesignerLoginEntity> findById = designerLoginRepo.findById(designerProfileEntity.getDesignerId());
		if (!findById.isPresent())
			throw new CustomException("Designer details not found");
		else {

			Optional<DesignerProfileEntity> findBydesignerId = designerProfileRepo
					.findBydesignerId(findById.get().getdId());
			if (!findBydesignerId.isPresent())
				throw new CustomException("Designer profile not found");

			DesignerProfile designerProfile = designerProfileEntity.getDesignerProfile();
			designerProfile.setEmail(findById.get().getEmail());
			designerProfile.setPassword(findById.get().getPassword());
			designerProfile.setProfilePic(designerProfileEntity.getDesignerProfile().getProfilePic());

			DesignerProfileEntity designerProfileEntityDB = findBydesignerId.get();
			
			designerProfileEntityDB.setBoutiqueProfile(designerProfileEntity.getBoutiqueProfile());
			designerProfileEntityDB.setDesignerProfile(designerProfile);
			designerProfileEntityDB.setSocialProfile(designerProfileEntity.getSocialProfile());

			designerProfileRepo.save(designerProfileEntityDB);
			DesignerLoginEntity designerLoginEntityDB = findById.get();
			designerLoginEntityDB.setProfileStatus("SUBMITTED");
			designerLoginRepo.save(designerLoginEntityDB);
			
			
			

		}

		return ResponseEntity.ok(new GlobalResponce("SUCCESS", "Updated successfully", 200));
	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted,
			@RequestParam(defaultValue = "APPROVE") String profileStatus,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {

		try {
			return this.getDesignerProfDetails(page, limit, sort, sortName, isDeleted, keyword, sortBy, profileStatus);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = "/redirect/{email}", method = RequestMethod.GET)
	public void method(HttpServletResponse httpServletResponse, @PathVariable("email") String email) {
		Optional<DesignerLoginEntity> findByEmail = designerLoginRepo
				.findByEmail(new String(Base64.getDecoder().decode(email)));
		if (findByEmail.isPresent()) {
			DesignerLoginEntity designerLoginEntity = findByEmail.get();
			if(designerLoginEntity.getProfileStatus().equals("INACTIVE"))
				designerLoginEntity.setProfileStatus("ACTIVE");
			designerLoginRepo.save(designerLoginEntity);
		}

		httpServletResponse.setHeader("Location", "http://65.1.190.195/admin/#/auth");
		httpServletResponse.setStatus(302);
	}

	@GetMapping("/userDesignerList")
	public ResponseEntity<?> userDesignertList() {
		try {
			long count = databaseSeqRepo.findById(DesignerLoginEntity.SEQUENCE_NAME).get().getSeq();
			Random rd = new Random();
			List<DesignerLoginEntity> designerLoginEntity = new ArrayList<>();
			List<DesignerLoginEntity> findAll = designerLoginRepo.findByIsDeletedAndProfileStatusAndAccountStatus(false,"COMPLETED","ACTIVE");

			if (findAll.size() <= 15) {
				designerLoginEntity=findAll;
				
			}else {
				Boolean flag = true;


				while (flag) {
					int nextInt = rd.nextInt((int) count);
					for (DesignerLoginEntity obj : findAll) {
						
						if (obj.getdId() == nextInt) {
							designerLoginEntity.add(obj);					  

						}
						if (designerLoginEntity.size() > 14)
							flag = false;
					}

				}

			}
			
						Stream<DesignerLoginEntity> map = designerLoginEntity.stream().map(e -> {
				try {
					e.setProductCount(productRepo.countByIsDeletedAndAdminStatusAndDesignerIdAndIsActive(false,"Approved",e.getdId(),true));
					e.setDesignerProfileEntity(
							designerProfileRepo.findBydesignerId(Long.parseLong(e.getdId().toString())).get());
				} catch (Exception o) {

				}

				return e;
			});
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getDesignerProfDetails(int page, int limit, String sort, String sortName,
			Boolean isDeleted,
			String keyword, Optional<String> sortBy,String profileStatus) {
		try {
			int CountData = (int) designerLoginRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<DesignerLoginEntity> findAll = null;

			if (keyword.isEmpty()) {
				
				findAll = designerLoginRepo.findByIsDeletedAndProfileStatus(isDeleted,profileStatus,pagingSort);
			} else {				
				findAll = designerLoginRepo.SearchByDeletedAndProfileStatus(keyword, isDeleted,profileStatus, pagingSort);				
				
			}

			if (findAll.getSize() <= 1)
				throw new CustomException("Designer not found!");

			findAll.map(e -> {
				try {
					e.setDesignerProfileEntity(
							designerProfileRepo.findBydesignerId(Long.parseLong(e.getdId().toString())).get());
				} catch (Exception o) {

				}

				return e;
			});

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());
			response.put("waitingForApproval", designerLoginRepo.findByProfileStatus("ACTIVE").size());
			response.put("waitingForSubmit", designerLoginRepo.findByProfileStatus("APPROVE").size());
			response.put("submitted", designerLoginRepo.findByProfileStatus("SUBMITTED").size());
			response.put("completed", designerLoginRepo.findByProfileStatus("COMPLETED").size());
			response.put("rejected", designerLoginRepo.findByProfileStatus("REJECTED").size());

			return response;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
