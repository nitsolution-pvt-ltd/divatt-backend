package com.divatt.user.serviceDTO;

public class OrderItemStatusChange {

	private PackedDTO packedDTO;
	private OrdersDTO ordersDTO;
	private ShippedDTO shippedDTO;
	private DeliveryDTO deliveryDTO;

	public OrderItemStatusChange() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItemStatusChange(PackedDTO packedDTO, OrdersDTO ordersDTO, ShippedDTO shippedDTO,
			DeliveryDTO deliveryDTO) {
		super();
		this.packedDTO = packedDTO;
		this.ordersDTO = ordersDTO;
		this.shippedDTO = shippedDTO;
		this.deliveryDTO = deliveryDTO;
	}

	public PackedDTO getPackedDTO() {
		return packedDTO;
	}

	public void setPackedDTO(PackedDTO packedDTO) {
		this.packedDTO = packedDTO;
	}

	public OrdersDTO getOrdersDTO() {
		return ordersDTO;
	}

	public void setOrdersDTO(OrdersDTO ordersDTO) {
		this.ordersDTO = ordersDTO;
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
		return "OrderItemStatusChange [packedDTO=" + packedDTO + ", ordersDTO=" + ordersDTO + ", shippedDTO="
				+ shippedDTO + ", deliveryDTO=" + deliveryDTO + "]";
	}

}
