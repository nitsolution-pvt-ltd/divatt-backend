package com.divatt.admin.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.admin.entity.AdminModules;


public interface AdminModulesRepo extends MongoRepository<AdminModules, Long>{
	public Optional<AdminModules> findByRoleName(Long roleName);
}
