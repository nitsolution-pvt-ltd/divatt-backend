package com.divatt.designer.entity.profile;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import nonapi.io.github.classgraph.json.Id;

@Document(collection = "tbl_designer_personal_info")
public class DesignerPersonalInfoEntity {
	
	@Id
	private Long id;
	
	@NotNull
	@Field(name = "designer_id") private Long designerId;
	
	@NotNull
	@Field(name = "bank_details") private BankDetails bankDetails;
	
	@NotNull
	@Field(name = "documents") private DesignerDocuments designerDocuments;

	public DesignerPersonalInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerPersonalInfoEntity(Long id, @NotNull Long designerId, @NotNull BankDetails bankDetails,
			@NotNull DesignerDocuments designerDocuments) {
		super();
		this.id = id;
		this.designerId = designerId;
		this.bankDetails = bankDetails;
		this.designerDocuments = designerDocuments;
	}

	@Override
	public String toString() {
		return "DesignerPersonalInfoEntity [id=" + id + ", designerId=" + designerId + ", bankDetails=" + bankDetails
				+ ", designerDocuments=" + designerDocuments + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDesignerId() {
		return designerId;
	}

	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}

	public BankDetails getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}

	public DesignerDocuments getDesignerDocuments() {
		return designerDocuments;
	}

	public void setDesignerDocuments(DesignerDocuments designerDocuments) {
		this.designerDocuments = designerDocuments;
	}
	
	

}
