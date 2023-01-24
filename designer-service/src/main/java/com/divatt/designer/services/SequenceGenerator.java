package com.divatt.designer.services;



import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.divatt.designer.entity.DatabaseSequence;





@Service
public class SequenceGenerator {
	
	@Autowired
	private MongoOperations mongoOperations;
	
	public long getCurrentSequence(String seqName) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(seqName));
		List<DatabaseSequence> find = mongoOperations.find(query, DatabaseSequence.class);
		long seq= 0L;
		if(find.size() > 0) {
		 seq = find.get(0).getSeq();
		}
		return seq;
	}
	
	public long generateSequence(String seqName) {
		DatabaseSequence counter =  mongoOperations.findAndModify(query(where("_id").is(seqName)),
	      new Update().inc("seq",1), options().returnNew(true).upsert(true),
	      DatabaseSequence.class);
	    return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}
	
	 public int getNextSequence(String seqName)
	    {
		 DatabaseSequence counter = mongoOperations.findAndModify(
	            query(where("_id").is(seqName)),
	            new Update().inc("seq",1),
	            options().returnNew(true).upsert(true),
	            DatabaseSequence.class);
	        return (int)counter.getSeq();
	    }
}
