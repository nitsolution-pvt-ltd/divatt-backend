package com.divatt.admin.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.divatt.admin.entity.AccountEntity;

@Repository
public class AccountTemplateRepo {

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountTemplateRepo.class);

	public Page<AccountEntity> AccountSearchByKeywords(String keywords, Pageable pagingSort) {

		Query query = new Query();
		Query countQuery = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "datetime"));

		query.addCriteria(new Criteria().orOperator(Criteria.where("service_charge.date").regex(keywords),
				Criteria.where("service_charge.designer_invoice_id").regex(keywords),
				Criteria.where("service_charge.status").regex(keywords),
				Criteria.where("service_charge.remarks").regex(keywords),
				Criteria.where("designer_details.designer_name").regex(keywords),
				Criteria.where("designer_details.gst_in").regex(keywords),
				Criteria.where("designer_details.pan").regex(keywords),
				Criteria.where("designer_details.mobile").regex(keywords),
				Criteria.where("designer_details.address").regex(keywords),

				Criteria.where("order_details").elemMatch(Criteria.where("datetime").regex(keywords)),
				Criteria.where("order_details").elemMatch(Criteria.where("product_sku").regex(keywords)),
				Criteria.where("order_details").elemMatch(Criteria.where("size").regex(keywords)),
				Criteria.where("order_details").elemMatch(Criteria.where("tax_type").regex(keywords)),
				Criteria.where("order_details").elemMatch(Criteria.where("order_status").regex(keywords)),
				Criteria.where("order_details").elemMatch(Criteria.where("delivery_datetime").regex(keywords)),
				Criteria.where("order_details").elemMatch(Criteria.where("remarks").regex(keywords)),
				Criteria.where("order_details").elemMatch(Criteria.where("order_id").regex(keywords)),

				Criteria.where("govt_charge").elemMatch(Criteria.where("designer_invoice_id").regex(keywords)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("status").regex(keywords)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("datetime").regex(keywords)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("remarks").regex(keywords)),

				Criteria.where("designer_return_amount").elemMatch(Criteria.where("datetime").regex(keywords)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("status").regex(keywords)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("order_id").regex(keywords)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("product_sku").regex(keywords)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("size").regex(keywords)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("tax_type").regex(keywords)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("updated_datetime").regex(keywords)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("remarks").regex(keywords))

		));

		countQuery.with(pagingSort);
		long total = mongoTemplate.count(countQuery, AccountEntity.class);

		List<AccountEntity> find = mongoTemplate.find(query, AccountEntity.class);
		Page<AccountEntity> dataPageable = new PageImpl<AccountEntity>(find, pagingSort, total);

		return dataPageable;
	}

}
