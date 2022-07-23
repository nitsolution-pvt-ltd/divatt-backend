package com.divatt.admin.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.BannerEntity;


@Repository
public interface BannerRepo extends MongoRepository<BannerEntity, Long> {
	
	

}
