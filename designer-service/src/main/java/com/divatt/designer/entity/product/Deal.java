package com.divatt.designer.entity.product;

public class Deal {
	private String deal_name;
	private String deal_type;
	private Integer deal_value;
	private String deal_start;
	private String deal_end;
	private Integer sale_prise;
	public Deal() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Deal(String deal_name, String deal_type, Integer deal_value, String deal_start, String deal_end,
			Integer sale_prise) {
		super();
		this.deal_name = deal_name;
		this.deal_type = deal_type;
		this.deal_value = deal_value;
		this.deal_start = deal_start;
		this.deal_end = deal_end;
		this.sale_prise = sale_prise;
	}
	@Override
	public String toString() {
		return "Deal [deal_name=" + deal_name + ", deal_type=" + deal_type + ", deal_value=" + deal_value
				+ ", deal_start=" + deal_start + ", deal_end=" + deal_end + ", sale_prise=" + sale_prise + "]";
	}
	public String getDeal_name() {
		return deal_name;
	}
	public void setDeal_name(String deal_name) {
		this.deal_name = deal_name;
	}
	public String getDeal_type() {
		return deal_type;
	}
	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}
	public Integer getDeal_value() {
		return deal_value;
	}
	public void setDeal_value(Integer deal_value) {
		this.deal_value = deal_value;
	}
	public String getDeal_start() {
		return deal_start;
	}
	public void setDeal_start(String deal_start) {
		this.deal_start = deal_start;
	}
	public String getDeal_end() {
		return deal_end;
	}
	public void setDeal_end(String deal_end) {
		this.deal_end = deal_end;
	}
	public Integer getSale_prise() {
		return sale_prise;
	}
	public void setSale_prise(Integer sale_prise) {
		this.sale_prise = sale_prise;
	}
	
	

}
