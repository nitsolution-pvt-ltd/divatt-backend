package com.divatt.user.helper;

import java.util.List;

public class ListResponseDTO {
	
	private List<String> orderList;

	public ListResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListResponseDTO(List<String> orderList) {
		super();
		this.orderList = orderList;
	}

	@Override
	public String toString() {
		return "ListResponseDTO [orderList=" + orderList + "]";
	}

	public List<String> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<String> orderList) {
		this.orderList = orderList;
	}
	
	
}
