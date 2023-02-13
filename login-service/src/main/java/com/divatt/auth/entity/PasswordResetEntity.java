package com.divatt.auth.entity;

import java.util.Date;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tbl_password_reset")
public class PasswordResetEntity {
	@Transient
	public static final String SEQUENCE_NAME = "tbl_password_reset";

	private Integer id;
	private Object user_id;
	private String prtoken;
	private String user_type;
	private Date created_on;
	private String status;
	private String email;
	private String link;
	public PasswordResetEntity() {
		super();
	}
	
	public PasswordResetEntity(Integer id, Object user_id, String prtoken, String user_type, Date created_on,
			String status, String email, String link) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.prtoken = prtoken;
		this.user_type = user_type;
		this.created_on = created_on;
		this.status = status;
		this.email = email;
		this.link = link;
	}

	
	@Override
	public String toString() {
		return "PasswordResetEntity [id=" + id + ", user_id=" + user_id + ", prtoken=" + prtoken + ", user_type="
				+ user_type + ", created_on=" + created_on + ", status=" + status + ", email=" + email + ", link="
				+ link + "]";
	}

	public Integer getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Object getUser_id() {
		return user_id;
	}
	public void setUser_id(Object user_id) {
		this.user_id = user_id;
	}
	public String getPrtoken() {
		return prtoken;
	}
	public void setPrtoken(String prtoken) {
		this.prtoken = prtoken;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public Date getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
