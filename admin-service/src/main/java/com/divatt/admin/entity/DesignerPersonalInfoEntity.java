package com.divatt.admin.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.divatt.admin.DTO.BankDetails;


public class DesignerPersonalInfoEntity {
	
      private BankDetails bankDetails;

	public DesignerPersonalInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerPersonalInfoEntity(BankDetails bankDetails) {
		super();
		this.bankDetails = bankDetails;
	}

	public BankDetails getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}

	@Override
	public String toString() {
		return "DesignerPersonalInfoEntity [bankDetails=" + bankDetails + "]";
	}

	
}
