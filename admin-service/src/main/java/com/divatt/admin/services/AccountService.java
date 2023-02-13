package com.divatt.admin.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.divatt.admin.entity.AccountEntity;
import com.divatt.admin.entity.GlobalResponse;

public interface AccountService {

	public Map<String, Object> getAccountDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, String designerReturn, String serviceCharge, String govtCharge, String userOrder, String ReturnStatus, 
			String settlement, int year, int month, String designerId, Optional<String> sortBy);

	public GlobalResponse postAccountDetails(AccountEntity accountEntity);

	public ResponseEntity<?> viewAccountDetails(long accountId);

	public GlobalResponse putAccountDetails(long accountId, @Valid AccountEntity accountEntity);
	
	public List<AccountEntity> excelReportService(String designerReturn, String serviceCharge, String govtCharge, String userOrder, String ReturnStatus, 
			String settlement, int year, int month, String designerId);

	public ResponseEntity<?> getDesignerInvoice(String orderId,Long designerId);

	public ResponseEntity<?> getTransactionsService(int page, int limit, String sort, String sortName,
			Boolean isDeleted, String keyword, String paymentStatus, Optional<String> sortBy);

}
