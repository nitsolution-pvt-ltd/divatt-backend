package com.divatt.designer.entity.product;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class ProductStageDetails {
	private String comment;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	private Date submittedOn;
	private String submittedBy;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")
	private Date approvedOn;
	private String approvedBy;

	public ProductStageDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductStageDetails(String comment, Date submittedOn, String submittedBy, Date approvedOn,
			String approvedBy) {
		super();
		this.comment = comment;
		this.submittedOn = submittedOn;
		this.submittedBy = submittedBy;
		this.approvedOn = approvedOn;
		this.approvedBy = approvedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public Date getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Override
	public String toString() {
		return "ProductStageDetails [comment=" + comment + ", submittedOn=" + submittedOn + ", submittedBy="
				+ submittedBy + ", approvedOn=" + approvedOn + ", approvedBy=" + approvedBy + "]";
	}

}
