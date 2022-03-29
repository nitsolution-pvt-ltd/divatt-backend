package com.divatt.productservice.entity;

public class standardSOH {
	private String sizeType;
	private Integer soh;
	private Integer oos;
	private Integer notify;
	public standardSOH() {
		super();
		// TODO Auto-generated constructor stub
	}
	public standardSOH(String sizeType, Integer soh, Integer oos, Integer notify) {
		super();
		this.sizeType = sizeType;
		this.soh = soh;
		this.oos = oos;
		this.notify = notify;
	}
	public String getSizeType() {
		return sizeType;
	}
	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}
	public Integer getSoh() {
		return soh;
	}
	public void setSoh(Integer soh) {
		this.soh = soh;
	}
	public Integer getOos() {
		return oos;
	}
	public void setOos(Integer oos) {
		this.oos = oos;
	}
	public Integer getNotify() {
		return notify;
	}
	public void setNotify(Integer notify) {
		this.notify = notify;
	}
	@Override
	public String toString() {
		return "standardSOH [sizeType=" + sizeType + ", soh=" + soh + ", oos=" + oos + ", notify=" + notify + "]";
	}
	
}
