package com.divatt.admin.contoller;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import static org.springframework.data.mongodb.core.query.Query.query;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.divatt.admin.helper.*;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.divatt.admin.entity.AdminModule;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.LoginEntity;
import com.divatt.admin.entity.category.SubCategoryEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.AdminModulesRepo;
import com.divatt.admin.repo.LoginRepository;
import com.divatt.admin.services.SequenceGenerator;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mongodb.BasicDBList;
import com.divatt.admin.services.*;

@RestController
@RequestMapping("/admin/profile")
public class ProfileContoller {

	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private AdminModulesRepo adminModulesRepo;
	
	@Autowired
	private JwtUtil JwtUtil;

	Logger LOGGER = LoggerFactory.getLogger(ProfileContoller.class);

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getAll(@RequestHeader("Authorization") String token,@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - ProfileContoller.getAll()");

		try {
			if(!checkPermission(token, "module7", "list"))
				throw new CustomException("Don't have list permission");
			return this.getAdminProfDetails(page, limit, sort, sortName, isDeleted, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/all")
	public List<LoginEntity> getAllProf(@RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - ProfileContoller.getAllProf()");

		try {
			if(!checkPermission(token, "module7", "list"))
				throw new CustomException("Don't have list permission");
			List<LoginEntity> orElseThrow = Optional
					.of(mongoOperations.find(query(where("is_deleted").is(false)), LoginEntity.class))
					.orElseThrow(() -> new CustomException("Internal Server Error"));
			if (orElseThrow.size() < 1)
				throw new CustomException("Data not found");
			return orElseThrow;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/{id}")
	public LoginEntity getProfById(@RequestHeader("Authorization") String token,@PathVariable("id") Long id) {
		LOGGER.info("Inside - ProfileContoller.getProfById()");
		try {
			if(!checkPermission(token, "module7", "list"))
				throw new CustomException("Don't have get permission");
			List<LoginEntity> orElseThrow = Optional.of(mongoOperations
					.find(query(where("_id").is(id).andOperator(where("is_deleted").is(false))), LoginEntity.class))
					.orElseThrow(() -> new RuntimeException("Internal Server Error"));
			if (orElseThrow.size() < 1)
				throw new CustomException("Data not found");
			return orElseThrow.get(0);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/add")
	public ResponseEntity<?> addProfile(@RequestHeader("Authorization") String token,@Valid @RequestBody LoginEntity loginEntity, Errors error) {
		LOGGER.info("Inside - ProfileContoller.addProfile()");
		try {
			if (error.hasErrors()) {
				throw new CustomException("Please check all input fields");
			}
			if(!checkPermission(token, "module7", "create"))
				throw new CustomException("Don't have create permission");
			Optional<LoginEntity> findByEmail = loginRepository.findByEmail(loginEntity.getEmail());
			if (findByEmail.isPresent()) {
				throw new CustomException("This email already present");
			}

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			loginEntity.setPassword(passwordEncoder.encode(loginEntity.getPassword()));
			loginEntity.setUid((long) sequenceGenerator.getNextSequence(LoginEntity.SEQUENCE_NAME));
			loginEntity.setRole(loginEntity.getRole());
			loginEntity.setRoleName(adminModulesRepo.findById(loginEntity.getRole()).get().getRoleName().toUpperCase());
			loginEntity.setActive(true);
			loginEntity.setDeleted(false);
			loginEntity.setCreatedOn(date.toString());
			loginEntity.setModifiedOn(date.toString());
			
			
			
			JsonObject jo = new JsonObject();
			jo.addProperty("senderMailId", loginEntity.getEmail());
			jo.addProperty("subject", "Successfully Registration");
			jo.addProperty("body", "Welcome " + loginEntity.getEmail() + ""
					+ ",\n                           "
					+ " Your account created successfully.Please login your account by bellow credentials "
					+ "\n Username:-  " + loginEntity.getEmail()
					+ "\n Password:-  " + loginEntity.getPassword()
					);
			jo.addProperty("enableHtml", false);
			try {
				Unirest.setTimeouts(0, 0);
				HttpResponse<String> response = Unirest.post("http://localhost:8080/dev/auth/sendMail")
						.header("Content-Type", "application/json").body(jo.toString()).asString();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			
			loginRepository.save(loginEntity);
					return new ResponseEntity<>(new GlobalResponse("SUCCESS", "Sub admin added successfully", 200),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	@PutMapping("/update")
	public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token,@Valid @RequestBody LoginEntity loginEntity, Errors error) {
		LOGGER.info("Inside - ProfileContoller.updateProfile()");
		try {

			if (error.hasErrors()) {
				throw new CustomException("Check The Fields");
			}
			if(!checkPermission(token, "module7", "update"))
				throw new CustomException("Don't have update permission");
			if (loginEntity.getUid() == null || loginEntity.getUid().equals(""))
				throw new CustomException("Id is Null");
			if (!loginRepository.findByEmail(loginEntity.getEmail()).stream()
					.anyMatch(e -> e.getUid() == loginEntity.getUid()))
				throw new CustomException("This Email is Already Present");
			if (!mongoOperations.exists(query(where("uid").is(loginEntity.getUid())), LoginEntity.class)) {
				throw new CustomException("Id Not Found");
			}
//			loginRepository.findByRole(loginEntity.getRole()).ifPresentOrElse((value)->{throw new CustomException("This Role is Already Present");} , ()->{});
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			LoginEntity findById = loginRepository.findById(loginEntity.getUid()).get();
			loginEntity.setUid(findById.getUid());
			loginEntity.setActive(findById.isActive());
			loginEntity.setDeleted(findById.isDeleted());
			loginEntity.setCreatedOn(findById.toString());
			loginEntity.setModifiedOn(date.toString());
			loginEntity.setRole(loginEntity.getRole());
			loginEntity.setRoleName(adminModulesRepo.findById(loginEntity.getRole()).get().getRoleName().toUpperCase());
			loginRepository.save(loginEntity);
			return new ResponseEntity<>(new GlobalResponse("SUCCESS", "Updated successfully", 200), HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProfById(@RequestHeader("Authorization") String token,@PathVariable("id") Long id) {
		LOGGER.info("Inside - ProfileContoller.deleteProfById()");
		try {
			if(!checkPermission(token, "module7", "delete"))
				throw new CustomException("Don't have delete permission");
			if (mongoOperations.exists(query(where("uid").is(id)), LoginEntity.class)) {
				Optional.of(mongoOperations.findAndModify(query(where("uid").is(id)),
						new Update().set("is_deleted", true), LoginEntity.class))
						.orElseThrow(() -> new RuntimeException("Internal Server Error"));
				return new ResponseEntity<>(new GlobalResponse("SUCCESS", "Deleted successfully", 200), HttpStatus.OK);
			}
			throw new CustomException("Id Not Found");

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/{id}/{status}")
	public ResponseEntity<?> changeStatusById(@RequestHeader("Authorization") String token,@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
		LOGGER.info("Inside - ProfileContoller.changeStatusById()");
		try {
			if(!checkPermission(token, "module7", "update"))
				throw new CustomException("Don't have update permission");
			if (mongoOperations.exists(query(where("uid").is(id)), LoginEntity.class)) {
				Optional.of(mongoOperations.findAndModify(query(where("uid").is(id)),
						new Update().set("is_active", status), LoginEntity.class))
						.orElseThrow(() -> new RuntimeException("Internal Server Error"));
				return new ResponseEntity<>(new GlobalResponse("SUCCESS", "Status changed successfully", 200),
						HttpStatus.OK);
			}
			throw new CustomException("Id Not Found");

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@PutMapping("/muldelete")
	public GlobalResponse subAdminMulDelete(@RequestHeader("Authorization") String token,@RequestBody() List<Integer> CateID) {
		LOGGER.info("Inside - ProfileContoller.subAdminMulDelete()");
		try {
			if(!checkPermission(token, "module7", "delete"))
				throw new CustomException("Don't have delete permission");
			if (!CateID.equals(null)){
				for (Integer CateIdRowId : CateID) {

					 Optional<LoginEntity> findById = loginRepository.findById(CateIdRowId);
					 LoginEntity filterCatDetails = findById.get();

					if (filterCatDetails.getUid() != null) {
						filterCatDetails.setDeleted(true);
						filterCatDetails.setCreatedOn(new Date().toLocaleString());
						loginRepository.save(filterCatDetails);
					}
				}
				return new GlobalResponse("SUCCESS", "Subadmin deleted successfully", 200);
			}else {
				throw new CustomException("Subadmin Id Not Found!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}
	

	public Map<String, Object> getAdminProfDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			int CountData = (int) loginRepository.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<LoginEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = loginRepository.findByIsDeleted(isDeleted, pagingSort);

			} else {
				findAll = loginRepository.Search(keyword, isDeleted, pagingSort);

			}

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

			if (findAll.getSize() <= 1) {
				throw new CustomException("Profile not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/checkPermission")
	Boolean checkPermission(String token ,String moduleName , String access) {
		System.out.println(token);
		String extractUsername = JwtUtil.extractUsername(token.substring(7));
		Long role = loginRepository.findByEmail(extractUsername).get().getRole();
		ArrayList<AdminModule> modules = adminModulesRepo.findById(role).get().getModules();
		Boolean haveAccess = false;
		for(AdminModule obj : modules) {
			if(obj.getModName().equals(moduleName)) {
				haveAccess = obj.getModPrivs().get(access);
			}
		}
//		if(!haveAccess)
//			throw new CustomException("Don't have access on this module");
		
		return haveAccess;
	}
	
	@GetMapping("/s3")
	public ResponseEntity<?> getFiles(){
		return ResponseEntity.ok(s3Service.listFiles());
	}
	@PostMapping("/s3/upload")
	public ResponseEntity<?> uploadFiles(@RequestPart(value = "file", required = false) MultipartFile file) throws IOException{
		return ResponseEntity.ok(s3Service.uploadFile(file.getOriginalFilename(),file.getBytes()));
	}
	
	

}
