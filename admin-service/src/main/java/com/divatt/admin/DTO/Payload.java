package com.divatt.admin.DTO;

public class Payload {
	private Payout payout;

	public Payload() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payload(Payout payout) {
		super();
		this.payout = payout;
	}

	public Payout getPayout() {
		return payout;
	}

	public void setPayout(Payout payout) {
		this.payout = payout;
	}

	@Override
	public String toString() {
		return "Payload [payout=" + payout + "]";
	}
	

}
