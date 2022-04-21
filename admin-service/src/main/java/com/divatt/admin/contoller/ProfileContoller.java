package com.divatt.admin.contoller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.LoginEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.LoginRepository;
import com.divatt.admin.services.SequenceGenerator;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

@RestController
@RequestMapping("/admin/profile")
public class ProfileContoller {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	Logger LOGGER = LoggerFactory.getLogger(ProfileContoller.class);
	
	
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getAll(			
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, 
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, 			
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - ProfileContoller.getAll()");

		try {		
			return this.getAdminProfDetails(page, limit, sort, sortName, isDeleted, keyword,
					sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/all")
	public List<LoginEntity> getAllProf() {
		LOGGER.info("Inside - ProfileContoller.getAllProf()");

		try {
			  List<LoginEntity> orElseThrow = Optional.of(mongoOperations.find(query(where("is_deleted").is(false)), LoginEntity.class))
					  .orElseThrow(()->new CustomException("Internal Server Error"));
			  if(orElseThrow.size()<1)
				  throw new CustomException("Data Not Found");
			  return orElseThrow;
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}									
											
	}
	
	@GetMapping("/{id}")
	public List<LoginEntity> getProfById(@PathVariable("id") Long id) {
		LOGGER.info("Inside - ProfileContoller.getProfById()");
		try {
			List<LoginEntity> orElseThrow = Optional.of(mongoOperations.find(query(where("_id").is(id).andOperator(where("is_deleted").is(false))), LoginEntity.class))
					.orElseThrow(()-> new RuntimeException("Internal Server Error"));
			 if(orElseThrow.size()<1)
				  throw new CustomException("Data Not Found");
			  return orElseThrow;
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	@PostMapping("/add")
	public ResponseEntity<?> addProfile(@Valid @RequestBody LoginEntity loginEntity,Errors error){
		LOGGER.info("Inside - ProfileContoller.addProfile()");
		try {		
				if (error.hasErrors()) {
					throw new CustomException("Check The Fields");
				}
			loginRepository.findByEmail(loginEntity.getEmail()).ifPresentOrElse((value)->{throw new CustomException("This Email is Already Present");} , ()->{});
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			loginEntity.setUid((long)sequenceGenerator.getNextSequence(LoginEntity.SEQUENCE_NAME));
			loginEntity.setRole(loginEntity.getRole());
			loginEntity.setRoleName(loginEntity.getRoleName().toUpperCase());
			loginEntity.setIs_active(true);
			loginEntity.setDeleted(false);
			loginEntity.setCreated_on(date.toString());
			loginEntity.setModified_on(date.toString());
			loginRepository.save(loginEntity);
			return new ResponseEntity<>(new GlobalResponse("SUCCESS","Added Successfully",200), HttpStatus.OK);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
		
		
	}
	@PutMapping("/update")
	public ResponseEntity<?> updateProfile(@Valid @RequestBody LoginEntity loginEntity,Errors error){
		LOGGER.info("Inside - ProfileContoller.updateProfile()");
		try {		
			
			if (error.hasErrors()) {
				throw new CustomException("Check The Fields");
			}
			if(loginEntity.getUid()==null ||  loginEntity.getUid().equals(""))
				throw new CustomException("Id is Null");
			if(!loginRepository.findByEmail(loginEntity.getEmail()).stream().anyMatch(e->e.getUid()==loginEntity.getUid()))
				throw new CustomException("This Email is Already Present");
			if(!mongoOperations.exists(query(where("uid").is(loginEntity.getUid())), LoginEntity.class)) {
				throw new CustomException("Id Not Found");
			}
//			loginRepository.findByRole(loginEntity.getRole()).ifPresentOrElse((value)->{throw new CustomException("This Role is Already Present");} , ()->{});
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			LoginEntity findById = loginRepository.findById(loginEntity.getUid()).get();
			loginEntity.setUid(findById.getUid());
			loginEntity.setIs_active(findById.isIs_active());
			loginEntity.setDeleted(findById.isDeleted());
			loginEntity.setCreated_on(findById.toString());
			loginEntity.setModified_on(date.toString());
			loginRepository.save(loginEntity);
			return new ResponseEntity<>(new GlobalResponse("SUCCESS","Updated Successfully",200), HttpStatus.OK);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProfById(@PathVariable("id") Long id) {
		LOGGER.info("Inside - ProfileContoller.deleteProfById()");
		try {
			if(mongoOperations.exists(query(where("uid").is(id)), LoginEntity.class)) {
				Optional.of(mongoOperations.findAndModify(query(where("uid").is(id)), new Update().set("is_deleted", true), LoginEntity.class))
						.orElseThrow(()-> new RuntimeException("Internal Server Error"));
				return new ResponseEntity<>(new GlobalResponse("SUCCESS","Deleted Successfully",200), HttpStatus.OK);
			}
			throw new CustomException("Id Not Found");
			
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> changeStatusById(@PathVariable("id") Long id) {
		LOGGER.info("Inside - ProfileContoller.changeStatusById()");
		try {
			if(mongoOperations.exists(query(where("uid").is(id)), LoginEntity.class)) {
				Optional.of(mongoOperations.findAndModify(query(where("uid").is(id)), new Update().set("is_active", false), LoginEntity.class))
						.orElseThrow(()-> new RuntimeException("Internal Server Error"));
				return new ResponseEntity<>(new GlobalResponse("SUCCESS","Status Changed Successfully",200), HttpStatus.OK);
			}
			throw new CustomException("Id Not Found");
			
		}catch(Exception e) {
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
				findAll = loginRepository.findByIsDeleted(isDeleted,pagingSort);
				

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
				throw new CustomException("Institute Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
//	@PostMapping("/test")
//	public ResponseEntity<?> addProfile(@Valid @RequestBody Json loginEntity,Errors error){
//		
//	}
	
	
	
}
