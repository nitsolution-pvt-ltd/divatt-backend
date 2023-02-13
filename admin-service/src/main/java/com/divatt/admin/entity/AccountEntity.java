package com.divatt.admin.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "tbl_accounts")
public class AccountEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_accounts";
	
	@Id
	@Field("_id")
	public Integer id;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd hh:mm:ss")
    public String datetime;
	public String filter_date;
    public ServiceCharge service_charge;
    public AdminDetails admin_details;
    public DesignerDetails designer_details;
    public List<OrderDetails> order_details;
    public List<GovtCharge> govt_charge;
    public List<DesignerReturnAmount> designer_return_amount;
	
    public AccountEntity() {
		super();
	}

	public AccountEntity(Integer id, String datetime, String filter_date, ServiceCharge service_charge,
			AdminDetails admin_details, DesignerDetails designer_details, List<OrderDetails> order_details,
			List<GovtCharge> govt_charge, List<DesignerReturnAmount> designer_return_amount) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.filter_date = filter_date;
		this.service_charge = service_charge;
		this.admin_details = admin_details;
		this.designer_details = designer_details;
		this.order_details = order_details;
		this.govt_charge = govt_charge;
		this.designer_return_amount = designer_return_amount;
	}

	@Override
	public String toString() {
		return "AccountEntity [id=" + id + ", datetime=" + datetime + ", filter_date=" + filter_date
				+ ", service_charge=" + service_charge + ", admin_details=" + admin_details + ", designer_details="
				+ designer_details + ", order_details=" + order_details + ", govt_charge=" + govt_charge
				+ ", designer_return_amount=" + designer_return_amount + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getFilter_date() {
		return filter_date;
	}

	public void setFilter_date(String filter_date) {
		this.filter_date = filter_date;
	}

	public ServiceCharge getService_charge() {
		return service_charge;
	}

	public void setService_charge(ServiceCharge service_charge) {
		this.service_charge = service_charge;
	}

	public AdminDetails getAdmin_details() {
		return admin_details;
	}

	public void setAdmin_details(AdminDetails admin_details) {
		this.admin_details = admin_details;
	}

	public DesignerDetails getDesigner_details() {
		return designer_details;
	}

	public void setDesigner_details(DesignerDetails designer_details) {
		this.designer_details = designer_details;
	}

	public List<OrderDetails> getOrder_details() {
		return order_details;
	}

	public void setOrder_details(List<OrderDetails> order_details) {
		this.order_details = order_details;
	}

	public List<GovtCharge> getGovt_charge() {
		return govt_charge;
	}

	public void setGovt_charge(List<GovtCharge> govt_charge) {
		this.govt_charge = govt_charge;
	}

	public List<DesignerReturnAmount> getDesigner_return_amount() {
		return designer_return_amount;
	}

	public void setDesigner_return_amount(List<DesignerReturnAmount> designer_return_amount) {
		this.designer_return_amount = designer_return_amount;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	
    

}
