package com.divatt.admin.DTO;

public class Payout {
	private Entity entity;

	public Payout() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payout(Entity entity) {
		super();
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		return "Payout [entity=" + entity + "]";
	}
	

}
