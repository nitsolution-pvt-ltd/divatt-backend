package com.divatt.designer.entity.profile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class FundsBankAccount {
	
	@Field(name="fundsBankId")
	List<String> fundsBankId= new ArrayList<>();

	public FundsBankAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FundsBankAccount(List<String> fundsBankId) {
		super();
		this.fundsBankId = fundsBankId;
	}

	public List<String> getFundsBankId() {
		return fundsBankId;
	}

	public void setFundsBankId(List<String> fundsBankId) {
		this.fundsBankId = fundsBankId;
	}

	@Override
	public String toString() {
		return "FundsBankAccount [fundsBankId=" + fundsBankId + "]";
	}
	

}
