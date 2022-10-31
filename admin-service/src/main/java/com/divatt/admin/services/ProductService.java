package com.divatt.admin.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.divatt.admin.entity.GlobalResponse;

public interface ProductService {

	public GlobalResponse productApproval(int productId, int designerId, List<Object> commString, String approvedBy,String adminStatus);

	public List<JSONObject> getReportSheet(Date startDate, Date endDate);

	public Map<String, Object> getProductDetails();

}
