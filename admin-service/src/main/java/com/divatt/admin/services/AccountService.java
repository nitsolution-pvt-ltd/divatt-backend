package com.divatt.admin.services;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.divatt.admin.entity.AccountEntity;
import com.divatt.admin.entity.GlobalResponse;

public interface AccountService {

	public Map<String, Object> getAccountDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy);

	public GlobalResponse postAccountDetails(@Valid AccountEntity accountEntity);

}
