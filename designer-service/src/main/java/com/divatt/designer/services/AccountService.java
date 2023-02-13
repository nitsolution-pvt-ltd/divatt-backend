package com.divatt.designer.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.divatt.designer.entity.account.AccountEntity;



public interface AccountService {

	public ResponseEntity<?> getAccountDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, String designerReturn, String serviceCharge, String govtCharge, String userOrder, String ReturnStatus, Optional<String> sortBy,String settlement, int year,int month, String designerId);

	public ResponseEntity<?> postAccountDetails(@Valid AccountEntity accountEntity,String token);

	public ResponseEntity<?> viewAccountDetails(long accountId,String token);

	public ResponseEntity<?> putAccountDetails(long accountId, @Valid AccountEntity accountEntity,String token);

}
