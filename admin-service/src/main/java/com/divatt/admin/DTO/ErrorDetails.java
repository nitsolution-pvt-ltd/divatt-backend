package com.divatt.admin.DTO;

public class ErrorDetails {
	private String source;
	private String reason;
	private String description;
	private String code;
	private String step;
	private Metadata metadata;
	public ErrorDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ErrorDetails(String source, String reason, String description, String code, String step, Metadata metadata) {
		super();
		this.source = source;
		this.reason = reason;
		this.description = description;
		this.code = code;
		this.step = step;
		this.metadata = metadata;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	@Override
	public String toString() {
		return "ErrorDetails [source=" + source + ", reason=" + reason + ", description=" + description + ", code="
				+ code + ", step=" + step + ", metadata=" + metadata + "]";
	}
	

}
