package com.divatt.designer.constant;

public enum MessageConstant {
	SUCCESS("Success"),
    ORDER_STATUS_UPDATED("Order status updated"),
    ORDER_ITEM_STATUS_ALREADY_IN("OrderItemStatus already in"),
    ORDER_ITEM_STATUS_UPDATED_CHANGE("OrderItemStatus updated Change"),
    TO("to"),
    ERROR("Error"),
    PRODUCT_ADDED_SUCCESSFULLY("Product added successfully"),
    PRODUCT_NOT_FOUND("Product not found"),
    PRODUCT_ALREADY_ADDED("Product already added"),
    DESIGNER_DOCUMENT_IS_NOT_APPROVE("Designer doucument is not appoved"),
    DESIGNER_ID_DOES_NOT_EXIST("Designerid does not exist"),
    BAD_REQUEST("Bad request"),
    STATUS_INACTIVATED("Status inactive successfully"),
    STATUS_ACTIVATED("Status active successfully"),
    PRODUCT_UPDATED("Product updated successfully"),
    DESIGNER_ID_CHANGE("Designer id can to be change"),
    ALREADY_DELETED("Product already deleted"),
    DELETED("Deleted successfully"),
    PRODUCT_APPROVED("Product approved successfully"),
    STOCK_CLEARED("Stock cleared successfully"),
    DESIGNER_INFORMED("Designer informed successfully"),
    STOCK_RECOVER("Stock recovered"),
    PRODUCT_CHAT_DATA_ADD("Product chart data added susccessfully"),
    NAME_ALREADY_EXIST("Product name already exists"),
    PRODUCT_CHAT_DATA_UPDATE("Product chart data successfully updated"),
    PROFILE_NOT_COMPLETED("This designer profile is not completed"),
    REGISTERED("A verification link has been sent to your email account"),
    BOUTIQUE_NAME("This boutique name already exists"),
    DETAILS_NOT_FOUND("Designer details not found"),
    UPDATED("Updated successfully"),
    CHECK_FIELDS("Please check the fields"),
    USER_NOT_FOUND("User not found"),
    DESIGNER_STATUS_CHANGE("Designer current status Changed"),
    PROFILE_IMAGE_UPDATED("Profile image updated sucessfully"),
    PROFILE_DELETE("Profile deleted sucessfuly"),
    DESIGNER_NOT_FOUND("Designer not found"),
    ACCOUNT_ADDED_MESSAGE("Account added sucessfully"),
    UNAUTHORIZED("Unauthorized"),
    ACCOUNT_UPDATED_MESSAGE("Account updated successfully"),
    ACCOUNT_NOT_FOUND("Account data not found"),
    ADMIN_ROLES("SADMIN"),
    POP("pop"),
    UNCHECKED("unchecked"),
    PRODUCT_IS_OUT_OF_STOCK("Product is already out of stock"),
    DISTANCE("100000")
    ;
	private String message;

	private MessageConstant(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
