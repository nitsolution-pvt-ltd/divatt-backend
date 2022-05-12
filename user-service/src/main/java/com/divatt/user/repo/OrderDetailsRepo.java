package com.divatt.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.order.OrderDetailsEntity;

public interface OrderDetailsRepo extends MongoRepository<OrderDetailsEntity, Long>{

}
