package com.divatt.user.constant;

public enum MessageConstant {

	// MEASUREMENT & USER CONTROLLER OR SERVICES

	MEASUREMENT_ADDED("Measurement added successfully"), MEASUREMENT_ALREADY_ADDED("Measurement already added"),
	MEASUREMENT_UPDATED_SUCESS("Measurement updated successfully"),
	MEASUREMENT_DELETED_SUCESSFULLY("Measurement deleted successfully"), SUCCESS("Success"),
	MEASUREMENT_NOT_FOUND("Measurement not found"), REMINDER_MAIL("Reminder Mail"),
	CHECK_FIELDS("Please check input fields"), EMAIL_ALREADY_PRESENT("Email already present"),
	SUCCESSFUL_REGISTRATION("Successfully Registration"), WELLCOME(" Welcome "),
	REGISTERED_SUCESSFULLY(" you have been register successfully."),
	ACTIVATE_LINK("Please active your account by clicking the bellow link "),
	VERIFY_DETAILS_SOON(" . We will verify your details and come back to you soon."), USER_NOT_FOUND("User not found"),
	UPDATED_SUCCESSFULLY("Updated successfully"), NO_ADDRESS_FOUND("No address found"),
	PRIMARY_ADDRESS_NOT_DELETED("Primary address can't delete"), ADDRESS_DELETED("Address deleted successfully"),
	ADDRESS_ADDED("Address added successfully"), ID_NOT_FOUND("Id not found"),
	ADDRESS_UPDATED("Address updated successfully"), ADDRESS_SET_PRIMARY("This address has been set as primary"),
	WISHLIST_ALREADY_EXIST("Wishlist already exist"), WISHLIST_ADDED_SUCESSFULLY("Wishlist added succesfully"),
	WISHLIST_REMOVED("Wishlist removed succesfully"), PRODUCT_NOT_EXIST("Product not exist!"),
	WISHLIST_NOT_FOUND("Wishlist not found!"), PRODUCT_ALREDY_CART("Product already added to the cart."),
	CART_ADDED("Cart added succesfully"), PRODUCT_NOT_FOUND_IN_CART("Product not found in the cart."),
	CART_UPDATED_SUCESSFULLY("Cart updated succesfully"), CART_NOT_EXIST("Cart not exist!"),
	CART_REMOVED("Cart removed succesfully"), PRODUCT_NOT_FOUND("Product not found"),
	REVIEWED_EXIST("Reviewed already exist!"), REVIEWED_ADDED("Reviewed added succesfully"),
	REVIEW_NOT_EXIST("Reviewed not exist!"), REVIEW_UPDATE("Reviewed updated succesfully"),
	REVIEW_STATUS("Reviewed status "), SUCCESSFULLY(" successfully"), REVIEW_REMOVED("Reviewed removed succesfully"),
	DESIGNER_FOLLOWING("Designer following successfully"), DESIGNER_UNFOLLOWING("Designer unfollowing successfully"),
	ERROR("Error!!"), CART_DATA_DELETED("Cart data deleted successfully"), INVOICE_NOT_FOUND("Invoice not found!"),

	// ORDER & PAYMENT CONTROLLER

	UNAUTHORIZED("Unauthorized"), ORDER_PLACED("Order placed successfully"), ORDER_SUMMARY("Order summary"),
	ORDER_CREATED(" Your order created successfully. "), PDF_GENERATED("PDF generated successfully"),
	ORDER_DELIVERED("Order Already delivered"), ORDER_CANCEL("Order cancelled successfully"),
	ORDER_ID_EXIST("OrderId Already Exist"), PAYMENT_NOT_FOUND("Payment not found!"),
	ORDER_NOT_FOUND("Order not found"), ORDER_STATUS_UPDATED("Order status updated"),
	TRACKING_UPDATED("Tracking updated successfully"),
	SOMETHING_WENT_WRONG("Something went to wrong! from order related"),
	PRODUCT_ALREADY_CANCEL("Product already cancelled "), INVOICE_EXIST("Invoice already exist!"),
	INVOICE_ADDED("Invoice added succesfully"), INVOICE_UPDATED("Invoice updated succesfully"),
	CANCELATION_REQUEST("Cancelation request send successfully"),
	ORDER_CANCEL_FROM_DESIGNER("Order cnacelled from designer side"),
	ORDER_CANCEL_REQUEST_REJECTED("Order cancelation request rejected successfully"),
	YOU_CANNOT_SKIP_STATUS("You Can't Skip Any Status"), PRODUCT_STATUS("The Product is Already "),
	ADMIN_CANNOT_CHANGE_STATUS_NOW("Admin Can't Change this Status Now"), ITEM_STATUS_CHANGE("Item Status Changed "),
	TO(" to "), ORDER_CANCELATION_REJECTED("Order cancelation rejected"), RANDOM_STRING("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
	RANDOM_STRING_INT("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"), RANDOM_INT("1234567890"),
	DATE_FORMAT_TYPE("dd/MM/yyyy HH:mm:ss"), DATA_TYPE_FORMAT("dd/MM/yyyy"),
	PRODUCT_PLACED("Your product has been ordered by ");

	private String message;

	MessageConstant(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
