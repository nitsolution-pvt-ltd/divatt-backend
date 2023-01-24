package com.divatt.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForceReturnOnDTO {

	@JsonProperty("ForceReturnOnDTO")
	public ForceReturnOn forceReturnOn;
	public boolean returnAcceptable;
	
	
	public ForceReturnOn getForceReturnOn() {
		return forceReturnOn;
	}
	public void setForceReturnOn(ForceReturnOn forceReturnOn) {
		this.forceReturnOn = forceReturnOn;
	}
	public boolean isReturnAcceptable() {
		return returnAcceptable;
	}
	public void setReturnAcceptable(boolean returnAcceptable) {
		this.returnAcceptable = returnAcceptable;
	}
	
	

}
