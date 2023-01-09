package com.divatt.admin.repo;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.AccountEntity;
import com.divatt.admin.entity.AccountMapEntity;
import com.divatt.admin.entity.DesignerReturnAmount;
import com.divatt.admin.entity.GovtCharge;
import com.divatt.admin.entity.OrderDetails;

@Repository
public class AccountTemplateRepo {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
//	private Gson gson;
	
	private static final String constants = "i";

//	private static final Logger LOGGER = LoggerFactory.getLogger(AccountTemplateRepo.class);

	public Page<AccountEntity> AccountSearchByKeywords(String keywords, Pageable pagingSort) {

		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "datetime"));

		query.addCriteria(new Criteria().orOperator(
				Criteria.where("designer_details.designer_name").regex(keywords,constants),
				Criteria.where("designer_details.display_name").regex(keywords,constants),
				Criteria.where("designer_details.email").regex(keywords,constants),
				Criteria.where("designer_details.gst_in").regex(keywords,constants),
				Criteria.where("designer_details.pan").regex(keywords,constants),
				Criteria.where("designer_details.mobile").regex(keywords,constants),
				Criteria.where("designer_details.address").regex(keywords,constants),
				Criteria.where("designer_details.city").regex(keywords,constants),
				Criteria.where("designer_details.state").regex(keywords,constants),
				Criteria.where("designer_details.pin").regex(keywords,constants),
				Criteria.where("designer_details.country").regex(keywords,constants),
				
				Criteria.where("service_charge.date").regex(keywords,constants),
				Criteria.where("service_charge.designer_invoice_id").regex(keywords,constants),
				Criteria.where("service_charge.status").regex(keywords,constants),
				Criteria.where("service_charge.remarks").regex(keywords,constants),
				Criteria.where("service_charge.date").regex(keywords,constants),
				Criteria.where("service_charge.fee").regex(keywords,constants),
				Criteria.where("service_charge.cgst").regex(keywords,constants),
				Criteria.where("service_charge.sgst").regex(keywords,constants),
				Criteria.where("service_charge.igst").regex(keywords,constants),
				Criteria.where("service_charge.tcs").regex(keywords,constants),
				Criteria.where("service_charge.total_tax").regex(keywords,constants),
				Criteria.where("service_charge.total_amount").regex(keywords,constants),
				Criteria.where("service_charge.units").regex(keywords,constants),
				Criteria.where("service_charge.updated_datetime").regex(keywords,constants),
				Criteria.where("service_charge.rate").regex(keywords,constants),

				Criteria.where("order_details").elemMatch(Criteria.where("datetime").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("product_sku").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("size").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("tax_type").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("order_status").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("delivery_datetime").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("remarks").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("order_id").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("hsn_code").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("payment_mode").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("discount").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("sales_price").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("invoice_id").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("hsn_amount").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("hsn_rate").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("hsn_cgst").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("hsn_sgst").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("hsn_igst").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("total_tax").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("order_status").regex(keywords,constants)),
				Criteria.where("order_details").elemMatch(Criteria.where("mrp").regex(keywords,constants)),

				Criteria.where("govt_charge").elemMatch(Criteria.where("designer_invoice_id").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("status").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("datetime").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("remarks").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("fee").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("cgst").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("sgst").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("igst").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("tcs").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("total_tax").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("total_amount").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("units").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("updated_datetime").regex(keywords,constants)),
				Criteria.where("govt_charge").elemMatch(Criteria.where("rate").regex(keywords,constants)),

				Criteria.where("designer_return_amount").elemMatch(Criteria.where("datetime").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("status").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("order_id").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("product_sku").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("size").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("tax_type").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("updated_datetime").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("remarks").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("mrp").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("discount").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("sales_price").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("hsn_rate").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("hsn_code").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("hsn_amount").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("hsn_cgst").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("hsn_sgst").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("hsn_igst").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("tcs").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("total_tax_amount").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("total_amount_received").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("net_payable_designer").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("payment_datetime").regex(keywords,constants)),
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("basic_amount").regex(keywords,constants))

		));
		final List<AccountEntity> find = mongoTemplate.find(query, AccountEntity.class);
		int startOfPage = pagingSort.getPageNumber() * pagingSort.getPageSize();
		int endOfPage = Math.min(startOfPage + pagingSort.getPageSize(), find.size());
		List<AccountEntity> subList = startOfPage >= endOfPage ? new ArrayList<>()
				: find.subList(startOfPage, endOfPage);
		return new PageImpl<AccountEntity>(subList, pagingSort, find.size());
	}

	public AccountEntity update(long accountId, AccountEntity findByRows) {

		AccountEntity findOne = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(accountId)),
				AccountEntity.class);

		findOne.setDatetime(findByRows.getDatetime());
		findOne.setFilter_date(findByRows.getFilter_date());

		findOne.getService_charge().setCgst(findByRows.getService_charge().getCgst());
		findOne.getService_charge().setSgst(findByRows.getService_charge().getSgst());
		findOne.getService_charge().setIgst(findByRows.getService_charge().getIgst());
		findOne.getService_charge().setFee(findByRows.getService_charge().getFee());
		findOne.getService_charge().setTcs(findByRows.getService_charge().getTcs());
		findOne.getService_charge().setTotal_amount(findByRows.getService_charge().getTotal_amount());
		findOne.getService_charge().setTotal_tax(findByRows.getService_charge().getTotal_tax());
		findOne.getService_charge().setRate(findByRows.getService_charge().getRate());
		findOne.getService_charge().setTcs_rate(findByRows.getService_charge().getTcs_rate());
		findOne.getService_charge().setUnits(findByRows.getService_charge().getUnits());
		findOne.getService_charge().setDate(findByRows.getService_charge().getDate());
		findOne.getService_charge().setDesigner_invoice_id(findByRows.getService_charge().getDesigner_invoice_id());
		findOne.getService_charge().setRemarks(findByRows.getService_charge().getRemarks());
		findOne.getService_charge().setStatus(findByRows.getService_charge().getStatus());
		findOne.getService_charge().setUpdated_by(findByRows.getService_charge().getUpdated_by());
		findOne.getService_charge().setUpdated_datetime(findByRows.getService_charge().getUpdated_datetime());

		findOne.getAdmin_details().setAddress(findByRows.getAdmin_details().getAddress());
		findOne.getAdmin_details().setGst_in(findByRows.getAdmin_details().getGst_in());
		findOne.getAdmin_details().setMobile(findByRows.getAdmin_details().getMobile());
		findOne.getAdmin_details().setName(findByRows.getAdmin_details().getName());
		findOne.getAdmin_details().setPan(findByRows.getAdmin_details().getPan());
		findOne.getAdmin_details().setAdmin_id(findByRows.getAdmin_details().getAdmin_id());
		findOne.getAdmin_details().setEmail(findByRows.getAdmin_details().getEmail());
		findOne.getAdmin_details().setCity(findByRows.getAdmin_details().getCity());
		findOne.getAdmin_details().setCountry(findByRows.getAdmin_details().getCountry());
		findOne.getAdmin_details().setPin(findByRows.getAdmin_details().getPin());
		findOne.getAdmin_details().setState(findByRows.getAdmin_details().getState());

		findOne.getDesigner_details().setAddress(findByRows.getDesigner_details().getAddress());
		findOne.getDesigner_details().setGst_in(findByRows.getDesigner_details().getGst_in());
		findOne.getDesigner_details().setMobile(findByRows.getDesigner_details().getMobile());
		findOne.getDesigner_details().setDesigner_name(findByRows.getDesigner_details().getDesigner_name());
		findOne.getDesigner_details().setPan(findByRows.getDesigner_details().getPan());
		findOne.getDesigner_details().setDesigner_id(findByRows.getDesigner_details().getDesigner_id());
		findOne.getDesigner_details().setEmail(findByRows.getDesigner_details().getEmail());
		findOne.getDesigner_details().setDesigner_name(findByRows.getDesigner_details().getDesigner_name());
		findOne.getDesigner_details().setDisplay_name(findByRows.getDesigner_details().getDisplay_name());
		findOne.getDesigner_details().setCity(findByRows.getDesigner_details().getCity());
		findOne.getDesigner_details().setCountry(findByRows.getDesigner_details().getCountry());
		findOne.getDesigner_details().setPin(findByRows.getDesigner_details().getPin());
		findOne.getDesigner_details().setState(findByRows.getDesigner_details().getState());

		GovtCharge govtCharge = new GovtCharge();
		ArrayList<GovtCharge> govtChargeList = new ArrayList<>();

		findByRows.getGovt_charge().forEach(value -> {
			govtCharge.setCgst(value.getCgst());
			govtCharge.setSgst(value.getSgst());
			govtCharge.setIgst(value.getIgst());
			govtCharge.setFee(value.getFee());
			govtCharge.setTcs(value.getTcs());
			govtCharge.setTotal_amount(value.getTotal_amount());
			govtCharge.setTotal_tax(value.getTotal_tax());
			govtCharge.setRate(value.getRate());
			govtCharge.setTcs_rate(value.getTcs_rate());
			govtCharge.setUnits(value.getUnits());
			govtCharge.setDatetime(value.getDatetime());
			govtCharge.setDesigner_invoice_id(value.getDesigner_invoice_id());
			govtCharge.setDatetime(value.getDatetime());
			govtCharge.setRemarks(value.getRemarks());
			govtCharge.setStatus(value.getStatus());
			govtCharge.setUpdated_by(value.getUpdated_by());
			govtCharge.setUpdated_datetime(value.getUpdated_datetime());

			govtChargeList.add(govtCharge);
		});
		findOne.setGovt_charge(govtChargeList);

		OrderDetails orderDetails = new OrderDetails();
		ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();

		findByRows.getOrder_details().forEach(value -> {

			orderDetails.setDatetime(value.getDatetime());
			orderDetails.setDelivery_datetime(value.getDelivery_datetime());
			orderDetails.setInvoice_id(value.getInvoice_id());
			orderDetails.setOrder_id(value.getOrder_id());
			orderDetails.setOrder_status(value.getOrder_status());
			orderDetails.setProduct_sku(value.getProduct_sku());
			orderDetails.setRemarks(value.getRemarks());
			orderDetails.setSize(value.getSize());
			orderDetails.setTax_type(value.getTax_type());
			orderDetails.setDesigner_id(value.getDesigner_id());
			orderDetails.setDiscount(value.getDiscount());
			orderDetails.setHsn_code(value.getHsn_code());
			orderDetails.setHsn_amount(value.getHsn_amount());
			orderDetails.setHsn_cgst(value.getHsn_cgst());
			orderDetails.setHsn_igst(value.getHsn_igst());
			orderDetails.setHsn_sgst(value.getHsn_sgst());
			orderDetails.setHsn_rate(value.getHsn_rate());
			orderDetails.setMrp(value.getMrp());
			orderDetails.setProduct_id(value.getProduct_id());
			orderDetails.setSales_price(value.getSales_price());
			orderDetails.setTotal_tax(value.getTotal_tax());
			orderDetails.setUnits(value.getUnits());
			orderDetails.setUser_id(value.getUser_id());
			orderDetails.setImage(value.getImage());
			orderDetails.setGiftWrapAmount(value.getGiftWrapAmount());
			
			orderDetailsList.add(orderDetails);
		});
		findOne.setOrder_details(orderDetailsList);

		DesignerReturnAmount designerReturnAmount = new DesignerReturnAmount();
		ArrayList<DesignerReturnAmount> DesignerReturnAmountList = new ArrayList<>();

		findByRows.getDesigner_return_amount().forEach(value -> {

			designerReturnAmount.setDatetime(value.getDatetime());
			designerReturnAmount.setOrder_id(value.getOrder_id());
			designerReturnAmount.setProduct_sku(value.getProduct_sku());
			designerReturnAmount.setRemarks(value.getRemarks());
			designerReturnAmount.setSize(value.getSize());
			designerReturnAmount.setStatus(value.getStatus());
			designerReturnAmount.setTax_type(value.getTax_type());
			designerReturnAmount.setUpdated_by(value.getUpdated_by());
			designerReturnAmount.setUpdated_datetime(value.getUpdated_datetime());
			designerReturnAmount.setDesigner_id(value.getDesigner_id());
			designerReturnAmount.setDiscount(value.getDiscount());
			designerReturnAmount.setHsn_code(value.getHsn_code());
			designerReturnAmount.setHsn_amount(value.getHsn_amount());
			designerReturnAmount.setHsn_cgst(value.getHsn_cgst());
			designerReturnAmount.setHsn_igst(value.getHsn_igst());
			designerReturnAmount.setHsn_sgst(value.getHsn_sgst());
			designerReturnAmount.setHsn_rate(value.getHsn_rate());
			designerReturnAmount.setMrp(value.getMrp());
			designerReturnAmount.setNet_payable_designer(value.getNet_payable_designer());
			designerReturnAmount.setProduct_id(value.getProduct_id());
			designerReturnAmount.setSales_price(value.getSales_price());
			designerReturnAmount.setTcs(value.getTcs());
			designerReturnAmount.setTotal_amount_received(value.getTotal_amount_received());
			designerReturnAmount.setTotal_tax_amount(value.getTotal_tax_amount());
			designerReturnAmount.setUnits(value.getUnits());
			designerReturnAmount.setPayment_datetime(value.getPayment_datetime());
			designerReturnAmount.setBasic_amount(value.getBasic_amount());

			DesignerReturnAmountList.add(designerReturnAmount);
		});
		findOne.setDesigner_return_amount(DesignerReturnAmountList);

		return mongoTemplate.save(findOne);

	}

	public List<AccountMapEntity> getServiceFee(String settlement, int year, int month) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		MatchOperation match = null;
		LocalDate startDate = null;
		LocalDate endDate =null;
		
		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0) {
			yearMonth = YearMonth.of(year, month);
			startDate = yearMonth.atDay(1);
			endDate = yearMonth.atEndOfMonth();
		}
		if (year != 0 && month != 0 && !settlement.isEmpty()) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;

			match = Aggregation.match(new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString())
							.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(1).toString())
							.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
		} else if (year != 0 && month != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else if (year != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else {
			match = Aggregation.match(new Criteria());
		}

		GroupOperation mapCondition = Aggregation.group().sum("service_charge.fee").as("serviceFee");
		Aggregation aggregations = Aggregation.newAggregation(match, mapCondition);
		final AggregationResults<AccountMapEntity> results = mongoTemplate.aggregate(aggregations, AccountEntity.class,
				AccountMapEntity.class);
		return results.getMappedResults();
	}
	
	public List<AccountMapEntity> getServicGst(String settlement, int year, int month) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		MatchOperation match = null;
		LocalDate startDate = null;
		LocalDate endDate =null;
		
		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0) {
			yearMonth = YearMonth.of(year, month);
			startDate = yearMonth.atDay(1);
			endDate = yearMonth.atEndOfMonth();
		}
		if (year != 0 && month != 0 && !settlement.isEmpty()) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;

			match = Aggregation.match(new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString())
							.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(1).toString())
							.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
		} else if (year != 0 && month != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else if (year != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else {
			match = Aggregation.match(new Criteria());
		}

		GroupOperation mapCondition = Aggregation.group().sum("service_charge.igst").as("serviceGst");
		Aggregation aggregations = Aggregation.newAggregation(match, mapCondition);
		final AggregationResults<AccountMapEntity> results = mongoTemplate.aggregate(aggregations, AccountEntity.class,
				AccountMapEntity.class);
		return results.getMappedResults();
	}

	public List<AccountMapEntity> getGstAmount(String settlement, int year, int month) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		MatchOperation match = null;
		LocalDate startDate = null;
		LocalDate endDate =null;
		
		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0) {
			yearMonth = YearMonth.of(year, month);
			startDate = yearMonth.atDay(1);
			endDate = yearMonth.atEndOfMonth();
		}
		if (year != 0 && month != 0 && !settlement.isEmpty()) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;

			match = Aggregation.match(new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString())
							.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(1).toString())
							.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
		} else if (year != 0 && month != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else if (year != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else {
			match = Aggregation.match(new Criteria());
		}
		
		AggregationOperation unwind = Aggregation.unwind("order_details");
		GroupOperation mapCondition = Aggregation.group().sum("order_details.hsn_amount").as("gstAmount");
		Aggregation aggregations = Aggregation.newAggregation(match, unwind, mapCondition);
		final AggregationResults<AccountMapEntity> results = mongoTemplate.aggregate(aggregations, AccountEntity.class,
				AccountMapEntity.class);
		return results.getMappedResults();
	}

	public List<AccountMapEntity> getGovtChargeAmount(String settlement, int year, int month) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		MatchOperation match = null;
		LocalDate startDate = null;
		LocalDate endDate =null;
		
		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0) {
			yearMonth = YearMonth.of(year, month);
			startDate = yearMonth.atDay(1);
			endDate = yearMonth.atEndOfMonth();
		}
		if (year != 0 && month != 0 && !settlement.isEmpty()) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;

			match = Aggregation.match(new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString())
							.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(1).toString())
							.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
		} else if (year != 0 && month != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else if (year != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else {
			match = Aggregation.match(new Criteria());
		}

		AggregationOperation unwind = Aggregation.unwind("govt_charge");
		GroupOperation mapCondition = Aggregation.group().sum("govt_charge.total_amount").as("govtGstAmount");
		Aggregation aggregations = Aggregation.newAggregation(match, unwind, mapCondition);
		final AggregationResults<AccountMapEntity> results = mongoTemplate.aggregate(aggregations, AccountEntity.class,
				AccountMapEntity.class);
		return results.getMappedResults();
	}

	public List<AccountMapEntity> getDesignerGstAmount(String settlement, int year, int month) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		MatchOperation match = null;

		LocalDate startDate = null;
		LocalDate endDate =null;
		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0) {
			yearMonth = YearMonth.of(year, month);
			startDate = yearMonth.atDay(1);
			endDate = yearMonth.atEndOfMonth();
		}
		if (year != 0 && month != 0 && !settlement.isEmpty()) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;

			match = Aggregation.match(new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString())
							.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(1).toString())
							.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
		} else if (year != 0 && month != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else if (year != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else {
			match = Aggregation.match(new Criteria());
		}

		AggregationOperation unwind = Aggregation.unwind("designer_return_amount");
		GroupOperation mapCondition = Aggregation.group().sum("designer_return_amount.total_tax_amount")
				.as("designerGstAmount");
		Aggregation aggregations = Aggregation.newAggregation(match, unwind, mapCondition);
		final AggregationResults<AccountMapEntity> results = mongoTemplate.aggregate(aggregations, AccountEntity.class,
				AccountMapEntity.class);
		return results.getMappedResults();
	}

	public List<AccountMapEntity> getTcsAmount(String settlement, int year, int month) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		MatchOperation match = null;
		
		LocalDate startDate = null;
		LocalDate endDate =null;
		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0) {
			yearMonth = YearMonth.of(year, month);
			startDate = yearMonth.atDay(1);
			endDate = yearMonth.atEndOfMonth();
		}
		if (year != 0 && month != 0 && !settlement.isEmpty()) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;

			match = Aggregation.match(new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString())
							.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(1).toString())
							.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
		} else if (year != 0 && month != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else if (year != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else {
			match = Aggregation.match(new Criteria());
		}

		AggregationOperation unwind = Aggregation.unwind("designer_return_amount");
		GroupOperation mapCondition = Aggregation.group().sum("designer_return_amount.tcs").as("tcs");
		Aggregation aggregations = Aggregation.newAggregation(match, unwind, mapCondition);
		final AggregationResults<AccountMapEntity> results = mongoTemplate.aggregate(aggregations, AccountEntity.class,
				AccountMapEntity.class);
		return results.getMappedResults();
	}

	public List<AccountMapEntity> getTotalAmount(String settlement, int year, int month) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		MatchOperation match = null;

		LocalDate startDate = null;
		LocalDate endDate =null;
		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0) {
			yearMonth = YearMonth.of(year, month);
			startDate = yearMonth.atDay(1);
			endDate = yearMonth.atEndOfMonth();
		}
		if (year != 0 && month != 0 && !settlement.isEmpty()) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;

			match = Aggregation.match(new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString())
							.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(1).toString())
							.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
		} else if (year != 0 && month != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else if (year != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else {
			match = Aggregation.match(new Criteria());
		}

		AggregationOperation unwind = Aggregation.unwind("designer_return_amount");
		GroupOperation mapCondition = Aggregation.group().sum("designer_return_amount.total_amount_received")
				.as("totalAmount");
		Aggregation aggregations = Aggregation.newAggregation(match, unwind, mapCondition);
		final AggregationResults<AccountMapEntity> results = mongoTemplate.aggregate(aggregations, AccountEntity.class,
				AccountMapEntity.class);
		return results.getMappedResults();
	}

	public List<AccountMapEntity> getPayableAmount(String settlement, int year, int month) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		MatchOperation match = null;

		LocalDate startDate = null;
		LocalDate endDate =null;
		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0) {
			yearMonth = YearMonth.of(year, month);
			startDate = yearMonth.atDay(1);
			endDate = yearMonth.atEndOfMonth();
		}
		if (year != 0 && month != 0 && !settlement.isEmpty()) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;

			match = Aggregation.match(new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString())
					.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(1).toString())
							.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
		} else if (year != 0 && month != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else if (year != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else {
			match = Aggregation.match(new Criteria());
		}

		AggregationOperation unwind = Aggregation.unwind("designer_return_amount");
		GroupOperation mapCondition = Aggregation.group().sum("designer_return_amount.net_payable_designer")
				.as("payableAmount");
		Aggregation aggregations = Aggregation.newAggregation(match, unwind, mapCondition);
		final AggregationResults<AccountMapEntity> results = mongoTemplate.aggregate(aggregations, AccountEntity.class,
				AccountMapEntity.class);
		return results.getMappedResults();
	}

	public List<AccountMapEntity> getPendingAmount(String settlement, int year, int month) {
		
		MatchOperation match = Aggregation.match(new Criteria().andOperator(
				Criteria.where("designer_return_amount").elemMatch(Criteria.where("status").is("NOT RETURN"))));

		AggregationOperation unwind = Aggregation.unwind("designer_return_amount");
		GroupOperation mapCondition = Aggregation.group().sum("designer_return_amount.net_payable_designer").as("pendingAmount");
		Aggregation aggregations = Aggregation.newAggregation(match, unwind, mapCondition);
		final AggregationResults<AccountMapEntity> results = mongoTemplate.aggregate(aggregations, AccountEntity.class,
				AccountMapEntity.class);
		return results.getMappedResults();
	}

	public List<AccountMapEntity> getBasicAmount(String settlement, int year, int month) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		MatchOperation match = null;

		LocalDate startDate = null;
		LocalDate endDate =null;
		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0) {
			yearMonth = YearMonth.of(year, month);
			startDate = yearMonth.atDay(1);
			endDate = yearMonth.atEndOfMonth();
		}
		if (year != 0 && month != 0 && !settlement.isEmpty()) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;

			match = Aggregation.match(new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString())
							.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(1).toString())
							.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
		} else if (year != 0 && month != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else if (year != 0) {
			match = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
		} else {
			match = Aggregation.match(new Criteria());
		}

		AggregationOperation unwind = Aggregation.unwind("designer_return_amount");
		GroupOperation mapCondition = Aggregation.group().sum("designer_return_amount.basic_amount").as("basicAmount");

		Aggregation aggregations = Aggregation.newAggregation(match, unwind, mapCondition);
		final AggregationResults<AccountMapEntity> results = mongoTemplate.aggregate(aggregations, AccountEntity.class,
				AccountMapEntity.class);
		return results.getMappedResults();
	}

	public Page<AccountEntity> getAccountData(String designerReturn, String serviceCharge, String govtCharge,
			String userOrder, String ReturnStatus, String settlement, int year, int month, String designerId,
			Pageable pagingSort) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		LocalDate startDate = null;
		LocalDate endDate =null;

		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0) {
			yearMonth = YearMonth.of(year, month);
			startDate = yearMonth.atDay(1);
			endDate = yearMonth.atEndOfMonth();
		}
		if (year != 0 && month != 0 && !settlement.equals(null)) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;
		}

		MatchOperation filterByCondition = null;

		if (ReturnStatus.equals("NotPaid") && !ReturnStatus.isEmpty()) {
			filterByCondition = Aggregation.match(new Criteria().orOperator(
					Criteria.where("designer_return_amount").elemMatch(Criteria.where("status").is("NOT RETURN"))));
		} else if (ReturnStatus.equals("Paid") && !ReturnStatus.isEmpty()) {
			filterByCondition = Aggregation
					.match(Criteria.where("designer_return_amount").elemMatch(Criteria.where("status").is("RETURN")));
		} else {
			if (settlement.equals("firstSettlement") && year != 0 && month != 0) {
				filterByCondition = Aggregation.match(
						new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString()).andOperator(
								Criteria.where("filter_date").gte(yearMonth.atDay(1).toString()).andOperator(
										Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
			} else if (settlement.equals("secondSettlement") && year != 0 && month != 0) {
				filterByCondition = Aggregation
						.match(new Criteria().andOperator(Criteria.where("filter_date").lte(today.toString())
								.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(dayDivide).toString())
										.andOperator(Criteria.where("filter_date")
												.lte(yearMonth.atDay(lengthOfMonth).toString())))));
			} else if (year != 0 && month != 0) {
				filterByCondition = Aggregation
						.match(new Criteria()
								.andOperator(Criteria.where("filter_date").gte(startDate.toString())
								.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
			} else if (year != 0) {
				filterByCondition = Aggregation
					.match(new Criteria()
							.andOperator(Criteria.where("filter_date").gte(startDate.toString())
							.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
			}else {
				filterByCondition = Aggregation.match(new Criteria());
			}
		}

		if (designerReturn != "" && !designerReturn.isEmpty()) {
			filterByCondition = Aggregation.match(Criteria.where("designer_return_amount")
					.elemMatch(Criteria.where("status").is(designerReturn.trim())));
		}
		if (serviceCharge != "" && !serviceCharge.isEmpty()) {
			filterByCondition = Aggregation.match(Criteria.where("service_charge.status").is(serviceCharge.trim()));
		}
		if (govtCharge != "" && !govtCharge.isEmpty()) {
			filterByCondition = Aggregation
					.match(Criteria.where("govt_charge").elemMatch(Criteria.where("status").is(govtCharge.trim())));
		}
		if (userOrder != "" && !userOrder.isEmpty()) {
			filterByCondition = Aggregation.match(
					Criteria.where("order_details").elemMatch(Criteria.where("order_status").is(userOrder.trim())));
		}
		if (designerId != "" && !designerId.isEmpty()) {
			filterByCondition = Aggregation
					.match(Criteria.where("designer_details.designer_id").is(Long.parseLong(designerId.trim())));
		}
		SortOperation sortByIdDesc = Aggregation.sort(pagingSort.getSort());

		Aggregation aggregations = Aggregation.newAggregation(filterByCondition, sortByIdDesc);
		final AggregationResults<AccountEntity> results = mongoTemplate.aggregate(aggregations,
				mongoTemplate.getCollectionName(AccountEntity.class), AccountEntity.class);

		int startOfPage = pagingSort.getPageNumber() * pagingSort.getPageSize();
		int endOfPage = Math.min(startOfPage + pagingSort.getPageSize(), results.getMappedResults().size());
		List<AccountEntity> subList = startOfPage >= endOfPage ? new ArrayList<>()
				: results.getMappedResults().subList(startOfPage, endOfPage);
		return new PageImpl<AccountEntity>(subList, pagingSort, results.getMappedResults().size());

	}

	public List<AccountEntity> getAccountReport(String designerReturn, String serviceCharge, String govtCharge,
			String userOrder, String ReturnStatus, String settlement, int year, int month, String designerId) {

		LocalDate today = LocalDate.now();
		int dayDivide = 0;
		int lengthOfMonth = 0;
		YearMonth yearMonth = null;
		LocalDate startDate = null;
		LocalDate endDate =null;
		
		if(year !=0) {
			startDate = LocalDate.of(year, Month.JANUARY, 1);
			endDate = LocalDate.of(year, Month.DECEMBER, 31);
		}
		if (year != 0 && month != 0 && !settlement.equals(null)) {
			yearMonth = YearMonth.of(year, month);
			lengthOfMonth = yearMonth.lengthOfMonth();
			dayDivide = lengthOfMonth / 2;
		}

		MatchOperation filterByCondition = null;

		if (ReturnStatus.equals("NotPaid") && !ReturnStatus.isEmpty()) {
			filterByCondition = Aggregation.match(new Criteria()
					.orOperator(Criteria.where("designer_return_amount").elemMatch(Criteria.where("status").is("NOT RETURN"))));
		} else if (ReturnStatus.equals("Paid") && !ReturnStatus.isEmpty()) {
			filterByCondition = Aggregation
					.match(Criteria.where("designer_return_amount").elemMatch(Criteria.where("status").is("RETURN")));
		} else {
			if (settlement.equals("firstSettlement") && year != 0 && month != 0) {
				filterByCondition = Aggregation.match(new Criteria()
								.andOperator(Criteria.where("filter_date").lte(today.toString())
								.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(1).toString())
								.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(dayDivide).toString())))));
			} else if (settlement.equals("secondSettlement") && year != 0 && month != 0) {
				filterByCondition = Aggregation
						.match(new Criteria()
								.andOperator(Criteria.where("filter_date").lte(today.toString())
								.andOperator(Criteria.where("filter_date").gte(yearMonth.atDay(dayDivide).toString())
								.andOperator(Criteria.where("filter_date").lte(yearMonth.atDay(lengthOfMonth).toString())))));
			}else if (year != 0 && month != 0) {
				filterByCondition = Aggregation
						.match(new Criteria()
								.andOperator(Criteria.where("filter_date").gte(startDate.toString())
								.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
			} else if (year != 0) {
					filterByCondition = Aggregation
						.match(new Criteria()
								.andOperator(Criteria.where("filter_date").gte(startDate.toString())
								.andOperator(Criteria.where("filter_date").lte(endDate.toString()))));
			} else {
				filterByCondition = Aggregation.match(new Criteria());
			}
		}

		if (designerReturn != "" && !designerReturn.isEmpty()) {
			filterByCondition = Aggregation.match(Criteria.where("designer_return_amount")
					.elemMatch(Criteria.where("status").is(designerReturn.trim())));
		}
		if (serviceCharge != "" && !serviceCharge.isEmpty()) {
			filterByCondition = Aggregation.match(Criteria.where("service_charge.status").is(serviceCharge.trim()));
		}
		if (govtCharge != "" && !govtCharge.isEmpty()) {
			filterByCondition = Aggregation
					.match(Criteria.where("govt_charge").elemMatch(Criteria.where("status").is(govtCharge.trim())));
		}
		if (userOrder != "" && !userOrder.isEmpty()) {
			filterByCondition = Aggregation.match(
					Criteria.where("order_details").elemMatch(Criteria.where("order_status").is(userOrder.trim())));
		}
		if (designerId != "" && !designerId.isEmpty()) {
			filterByCondition = Aggregation
					.match(Criteria.where("designer_details.designer_id").is(Long.parseLong(designerId.trim())));
		}
		SortOperation sortByIdDesc = Aggregation.sort(Direction.DESC, "_id");

		Aggregation aggregations = Aggregation.newAggregation(filterByCondition, sortByIdDesc);
		final AggregationResults<AccountEntity> results = mongoTemplate.aggregate(aggregations,
				mongoTemplate.getCollectionName(AccountEntity.class), AccountEntity.class);

		return results.getMappedResults();
	}

	public List<AccountEntity> getOrder(String orderId, Long designerId) {

		MatchOperation filterByCondition = Aggregation
						.match(Criteria.where("order_details").elemMatch(Criteria.where("order_id").is(orderId))
						.andOperator(Criteria.where("order_details").elemMatch(Criteria.where("designer_id").is(designerId))));

		Aggregation aggregations = Aggregation.newAggregation(filterByCondition);
		final AggregationResults<AccountEntity> results = mongoTemplate.aggregate(aggregations,
				mongoTemplate.getCollectionName(AccountEntity.class), AccountEntity.class);
		return results.getMappedResults();

	}

}
