package com.divatt.user.serviceDTO;

public class OrderItemStatusChange {

	private PackedDTO packedDTO;
	private AcceptDTO acceptDTO;
	private ShippedDTO shippedDTO;
	private DeliveryDTO deliveryDTO;

	public OrderItemStatusChange() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItemStatusChange(PackedDTO packedDTO, AcceptDTO acceptDTO, ShippedDTO shippedDTO,
			DeliveryDTO deliveryDTO) {
		super();
		this.packedDTO = packedDTO;
		this.acceptDTO = acceptDTO;
		this.shippedDTO = shippedDTO;
		this.deliveryDTO = deliveryDTO;
	}

	public PackedDTO getPackedDTO() {
		return packedDTO;
	}

	public void setPackedDTO(PackedDTO packedDTO) {
		this.packedDTO = packedDTO;
	}

	public AcceptDTO getAcceptDTO() {
		return acceptDTO;
	}

	public void setAcceptDTO(AcceptDTO acceptDTO) {
		this.acceptDTO = acceptDTO;
	}

	public ShippedDTO getShippedDTO() {
		return shippedDTO;
	}

	public void setShippedDTO(ShippedDTO shippedDTO) {
		this.shippedDTO = shippedDTO;
	}

	public DeliveryDTO getDeliveryDTO() {
		return deliveryDTO;
	}

	public void setDeliveryDTO(DeliveryDTO deliveryDTO) {
		this.deliveryDTO = deliveryDTO;
	}

	@Override
	public String toString() {
		return "OrderItemStatusChange [packedDTO=" + packedDTO + ", acceptDTO=" + acceptDTO + ", shippedDTO="
				+ shippedDTO + ", deliveryDTO=" + deliveryDTO + "]";
	}

}
