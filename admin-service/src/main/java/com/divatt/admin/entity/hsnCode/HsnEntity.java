package com.divatt.admin.entity.hsnCode;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "tbl_hsn_code")
public class HsnEntity {

	@Id
	private Integer id;

	@Transient
	public static final String SEQUENCE_NAME = "tbl_hsn_code";

	@Field(name = "hsn_code")
	@NotNull(message = "hsnCode is requried")
	private Integer hsnCode;

	@NotNull(message = "description is requried")
	private String description;

	@Field(name = "tax_value")
	private Double taxValue;

	@Field(name = "SGST")

	private Double sgst;
	@Field(name = "CGST")
	private Double cgst;
	@Field(name = "IGST")
	private Double igst;

	@Field(name = "is_active")
	private Boolean isActive = true;
	@Field(name = "is_delete")
	private Boolean isDelete = false;

	private Float cess;

	private Float rate;

	private String rateVision;

	private String effectiveDate;

	public HsnEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HsnEntity(Integer id, @NotNull(message = "hsnCode is requried") Integer hsnCode,
			@NotNull(message = "description is requried") String description, Double taxValue, Double sgst, Double cgst,
			Double igst, Boolean isActive, Boolean isDelete, Float cess, Float rate, String rateVision,
			String effectiveDate) {
		super();
		this.id = id;
		this.hsnCode = hsnCode;
		this.description = description;
		this.taxValue = taxValue;
		this.sgst = sgst;
		this.cgst = cgst;
		this.igst = igst;
		this.isActive = isActive;
		this.isDelete = isDelete;
		this.cess = cess;
		this.rate = rate;
		this.rateVision = rateVision;
		this.effectiveDate = effectiveDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(Integer hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Float getCess() {
		return cess;
	}

	public void setCess(Float cess) {
		this.cess = cess;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getRateVision() {
		return rateVision;
	}

	public void setRateVision(String rateVision) {
		this.rateVision = rateVision;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	@Override
	public String toString() {
		return "HsnEntity [id=" + id + ", hsnCode=" + hsnCode + ", description=" + description + ", taxValue="
				+ taxValue + ", sgst=" + sgst + ", cgst=" + cgst + ", igst=" + igst + ", isActive=" + isActive
				+ ", isDelete=" + isDelete + ", cess=" + cess + ", rate=" + rate + ", rateVision=" + rateVision
				+ ", effectiveDate=" + effectiveDate + "]";
	}

	}