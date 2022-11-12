package com.divatt.user.serviceDTO;

public class AcceptDTO {

	private Boolean verifiedDesignCustomizationRequirements;
	private Boolean verifiedCustomizationSizes;

	public AcceptDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AcceptDTO(Boolean verifiedDesignCustomizationRequirements, Boolean verifiedCustomizationSizes) {
		super();
		this.verifiedDesignCustomizationRequirements = verifiedDesignCustomizationRequirements;
		this.verifiedCustomizationSizes = verifiedCustomizationSizes;
	}

	public Boolean getVerifiedDesignCustomizationRequirements() {
		return verifiedDesignCustomizationRequirements;
	}

	public void setVerifiedDesignCustomizationRequirements(Boolean verifiedDesignCustomizationRequirements) {
		this.verifiedDesignCustomizationRequirements = verifiedDesignCustomizationRequirements;
	}

	public Boolean getVerifiedCustomizationSizes() {
		return verifiedCustomizationSizes;
	}

	public void setVerifiedCustomizationSizes(Boolean verifiedCustomizationSizes) {
		this.verifiedCustomizationSizes = verifiedCustomizationSizes;
	}

	@Override
	public String toString() {
		return "AcceptDTO [verifiedDesignCustomizationRequirements=" + verifiedDesignCustomizationRequirements
				+ ", verifiedCustomizationSizes=" + verifiedCustomizationSizes + "]";
	}

}
