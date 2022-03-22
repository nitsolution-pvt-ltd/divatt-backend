package com.divatt.profile.contoller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.profile.entity.LoginEntity;
import com.divatt.profile.exception.CustomException;
import com.divatt.profile.repo.LoginRepository;

@RestController
@RequestMapping("/admin/profile")
public class ProfileContoller {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllProf() {
		
		return (ResponseEntity<?>)Optional.of(mongoOperations.find(query(where("is_deleted").is(false)), LoginEntity.class))
											.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
											.orElseThrow(()-> new CustomException("Data Not Found"));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProfById(@PathVariable("id") Long id) {
		try {
			return (ResponseEntity<?>)Optional.of(mongoOperations.find(query(where("_id").is(id).andOperator(where("is_deleted").is(false))), LoginEntity.class))
					.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
					.orElseThrow(()-> new RuntimeException("Data Not Found"));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProfById(@PathVariable("id") Long id) {
		loginRepository.save(loginRepository.findById(id).get());
		return (ResponseEntity<?>)Optional.of(loginRepository.findById(id))
											.map(e -> {
														LoginEntity loginEntity = e.get();
														loginEntity.setIs_deleted(true);
														e = Optional.of(loginEntity);
														loginRepository.save(loginEntity);
														return new ResponseEntity<>(e, HttpStatus.OK);
														})
											.orElseThrow(()-> new CustomException("Data Not Found"));
	}
}
