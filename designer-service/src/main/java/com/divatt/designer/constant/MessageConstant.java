package com.divatt.designer.constant;

public enum MessageConstant {
    SUCCESS("Success"),
    ORDER_STATUS_UPDATED("Order status updated"),
    ORDER_ITEM_STATUS_ALREADY_IN("OrderItemStatus already in"),
    ORDER_ITEM_STATUS_UPDATED_CHANGE("OrderItemStatus updated Change"),
    TO("to"),
    ERROR("Error!!"),
    PRODUCT_ADDED_SUCCESSFULLY("Product added successfully"),
    PRODUCT_NOT_FOUND("Product not found!"),
    PRODUCT_ALREADY_ADDED("Product already added"),
    DESIGNER_DOCUMENT_IS_NOT_APPROVE("Designer doucument is not appoved"),
    DESIGNER_ID_DOES_NOT_EXIST("Designerid does not exist!!"),
    BAD_REQUEST("Bad request"),
    STATUS_INACTIVATED("Status Inactive successfully"),
    STATUS_ACTIVATED("Status Active successfully"),
    PRODUCT_UPDATED("Product updated successfully"),
    DESIGNER_ID_CHANGE("Designer id can to be change"),
    ALREADY_DELETED("Product allready deleted"),
    DELETED("Deleted successfully"),
    PRODUCT_APPROVED("Product approved"),
    STOCK_CLEARED("Stock cleared successfully"),
    DESIGNER_INFORMED("Designer informed successfully"),
    STOCK_RECOVER("Stock recovered"),
    PRODUCT_CHAT_DATA_ADD("Product chat data Added susccessfully"),
    NAME_ALREADY_EXIST("Product name already exist"),
    PRODUCT_CHAT_DATA_UPDATE("Product chart data successfully updated"),
    PROFILE_NOT_COMPLETED("This designer profile is not completed"),
    REGISTERED("Registered successfully"),
    BOUTIQUE_NAME("This Boutique Name allready present!"),
    DETAILS_NOT_FOUND("Designer details not found"),
    UPDATED("Updated successfully"),
    CHECK_FIELDS("Please check the fields"),
    USER_NOT_FOUND("User not found"),
    DESIGNER_STATUS_CHANGE("Designer current status Changed"),
    PROFILE_IMAGE_UPDATED("Profile Image Updated Sucessfully"),
    PROFILE_DELETE("Profile deleted Sucessfuly")
    ;
	private String message;

	private MessageConstant(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
