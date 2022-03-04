package com.ecom.ecomMomgo.services;

import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.ecom.ecomMongo.model.Product;
import com.ecom.ecomMongo.repository.ProductRepository;

@Service
public class ProductServices {

	@Autowired
	private ProductRepository repository;
	
	
	
	public List<Product> saveProductService(List<Product> products)
	{
		
		//return repository.saveAll(products);
		 saveMongoOperations(products);
		 return products;
	}
	
	public Optional<Product> getProductById(Integer specid)
	{
		return repository.findById(specid);
	}
	
	public List<Product> getAllProducts()
	{
		return repository.findAll();
	}
	
	public List<Product> findProductsBySubCatId(int id){
		Query query=new Query();
		query.addCriteria(Criteria.where("prodRefId").is(id));
		return mongoTemplate.find(query, Product.class);
	}

	public List<Product> getProdctsBySearchCriteria(Map<String, Object> filterMap, Integer limit, Integer offset) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	


	public String saveMongoOperations(List<Product> products ) {
		for(Product product:products) {

		TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder().onField("prdDesc").onField("prdName").onField("color").onField("skuCode").build();
        mongoTemplate.indexOps(Product.class).ensureIndex(textIndex);
        mongoTemplate.save(product);
        
		}
		return "Saved sucessful";
		
		
	}

	public List<Product> findBySearch(String searchValue) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(searchValue);
		List<Product> products = mongoOperations.find(query(criteria), Product.class);
		return products;
	}
}
