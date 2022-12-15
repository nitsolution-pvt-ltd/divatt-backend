package com.divatt.admin.entity;

public class DesignerDetails {

	public long designer_id;
	public String designer_name;
	public String gst_in;
	public String pan;
	public String mobile;
	public String address;
	
	public DesignerDetails() {
		super();
	}

	public DesignerDetails(long designer_id, String designer_name, String gst_in, String pan, String mobile,
			String address) {
		super();
		this.designer_id = designer_id;
		this.designer_name = designer_name;
		this.gst_in = gst_in;
		this.pan = pan;
		this.mobile = mobile;
		this.address = address;
	}

	public long getDesigner_id() {
		return designer_id;
	}

	public void setDesigner_id(long designer_id) {
		this.designer_id = designer_id;
	}

	public String getDesigner_name() {
		return designer_name;
	}

	public void setDesigner_name(String designer_name) {
		this.designer_name = designer_name;
	}

	public String getGst_in() {
		return gst_in;
	}

	public void setGst_in(String gst_in) {
		this.gst_in = gst_in;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

	
}
