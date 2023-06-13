package com.divatt.admin.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class PayOutDetails {
	@Field(name="payOutsId")
	List<String> payOutsId= new ArrayList<>();

	public PayOutDetails() {
		super();
	}

	public PayOutDetails(List<String> payOutsId) {
		super();
		this.payOutsId = payOutsId;
	}

	public List<String> getPayOutsId() {
		return payOutsId;
	}

	public void setPayOutsId(List<String> payOutsId) {
		this.payOutsId = payOutsId;
	}

	@Override
	public String toString() {
		return "PayOutDetails [payOutsId=" + payOutsId + "]";
	}
	
}
