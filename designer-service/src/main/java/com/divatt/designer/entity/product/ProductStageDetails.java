package com.divatt.designer.entity.product;

public class ProductStageDetails {
	private String comment ;
	private String submitted_on ;
	private String submitted_by ;
	private String approved_on ;
	private String approved_by ;
	public ProductStageDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductStageDetails(String comment, String submitted_on, String submitted_by, String approved_on,
			String approved_by) {
		super();
		this.comment = comment;
		this.submitted_on = submitted_on;
		this.submitted_by = submitted_by;
		this.approved_on = approved_on;
		this.approved_by = approved_by;
	}
	@Override
	public String toString() {
		return "ProductStageDetails [comment=" + comment + ", submitted_on=" + submitted_on + ", submitted_by="
				+ submitted_by + ", approved_on=" + approved_on + ", approved_by=" + approved_by + "]";
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSubmitted_on() {
		return submitted_on;
	}
	public void setSubmitted_on(String submitted_on) {
		this.submitted_on = submitted_on;
	}
	public String getSubmitted_by() {
		return submitted_by;
	}
	public void setSubmitted_by(String submitted_by) {
		this.submitted_by = submitted_by;
	}
	public String getApproved_on() {
		return approved_on;
	}
	public void setApproved_on(String approved_on) {
		this.approved_on = approved_on;
	}
	public String getApproved_by() {
		return approved_by;
	}
	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}
	
	

}
