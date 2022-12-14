package com.divatt.admin.services;

import java.util.Map;
import java.util.Optional;

public interface AccountService {

	public Map<String, Object> getAccountDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy);

}
