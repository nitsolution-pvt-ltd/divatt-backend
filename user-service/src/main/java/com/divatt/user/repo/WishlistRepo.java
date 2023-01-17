package com.divatt.user.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.user.entity.WishlistEntity;

@Repository
public interface WishlistRepo extends MongoRepository<WishlistEntity, Integer> {

	Optional<WishlistEntity> findByProductIdAndUserId(Integer ProductId,Integer UserId);
	
	List<WishlistEntity> findByUserId(Integer UserId);

	@Query(value = "{ $or: [ { 'product_id' : {$regex:?0,$options:'i'} }, { 'user_id' : {$regex:?0,$options:'i'} } ]}")
	Page<WishlistEntity> Search(String sortKey, Pageable pageable);

	void deleteByProductIdAndUserId(Integer productId, Integer userId);
	
	List<WishlistEntity> findByAddedOn(Date addedOn);
	
	@Query("{addedOn:{$lt?0}}")
	List<WishlistEntity> findByAddedOnBefore1Week(Date addedOn);

}
