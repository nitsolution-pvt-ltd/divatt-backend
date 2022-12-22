package com.divatt.user.entity.order;

public class HsnData {

	private Double sgst;

	private Double cgst;

	private Double igst;

	public HsnData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HsnData(Double sgst, Double cgst, Double igst) {
		super();
		this.sgst = sgst;
		this.cgst = cgst;
		this.igst = igst;
	}

	public Double getSgst() {
		return sgst;
	}

	public void setSgst(Double sgst) {
		this.sgst = sgst;
	}

	public Double getCgst() {
		return cgst;
	}

	public void setCgst(Double cgst) {
		this.cgst = cgst;
	}

	public Double getIgst() {
		return igst;
	}

	public void setIgst(Double igst) {
		this.igst = igst;
	}

	@Override
	public String toString() {
		return "HsnData [sgst=" + sgst + ", cgst=" + cgst + ", igst=" + igst + "]";
	}

}
