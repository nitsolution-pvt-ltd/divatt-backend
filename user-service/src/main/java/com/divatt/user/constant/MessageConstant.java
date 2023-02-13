package com.divatt.user.constant;

public enum MessageConstant {

	// MEASUREMENT & USER CONTROLLER OR SERVICES

	MEASUREMENT_ADDED("Measurement added successfully"), MEASUREMENT_ALREADY_ADDED("Measurement already added"),
	MEASUREMENT_UPDATED_SUCESS("Measurement updated successfully"),
	MEASUREMENT_DELETED_SUCESSFULLY("Measurement deleted successfully"), SUCCESS("Success"),
	MEASUREMENT_NOT_FOUND("Measurement not found"), REMINDER_MAIL("Reminder Mail"),
	CHECK_FIELDS("Please check input fields"), EMAIL_ALREADY_PRESENT("Email already present"),
	SUCCESSFUL_REGISTRATION("Successfully Registration"), WELLCOME(" Welcome "),
	REGISTERED_SUCESSFULLY(" You have been register successfully. "),
	ACTIVATE_LINK("Please active your account by clicking the bellow link "),
	VERIFY_DETAILS_SOON(" . We will verify your details and come back to you soon."), USER_NOT_FOUND("User not found"),
	UPDATED_SUCCESSFULLY("Updated successfully"), NO_ADDRESS_FOUND("No address found"),
	PRIMARY_ADDRESS_NOT_DELETED("Primary address can't delete"), ADDRESS_DELETED("Address deleted successfully"),
	ADDRESS_ADDED("Address added successfully"), ID_NOT_FOUND("Id not found"),
	ADDRESS_UPDATED("Address updated successfully"), ADDRESS_SET_PRIMARY("This address has been set as primary"),
	WISHLIST_ALREADY_EXIST("Wishlist already exists"), WISHLIST_ADDED_SUCESSFULLY("Wishlist added succesfully"),
	WISHLIST_REMOVED("Wishlist removed succesfully"), PRODUCT_NOT_EXIST("Product not exist!"),
	WISHLIST_NOT_FOUND("Wishlist not found!"), PRODUCT_ALREDY_CART("Product already added to the cart."),
	CART_ADDED("Cart added succesfully"), PRODUCT_NOT_FOUND_IN_CART("Product not found in the cart."),
	CART_UPDATED_SUCESSFULLY("Cart updated succesfully"), CART_NOT_EXIST("Cart not exists!"),
	CART_REMOVED("Cart removed succesfully"), PRODUCT_NOT_FOUND("Product not found"),
	REVIEWED_EXIST("Reviewed already exist!"), REVIEWED_ADDED("Reviewed added succesfully"),
	REVIEW_NOT_EXIST("Reviewed not exists!"), REVIEW_UPDATE("Reviewed updated succesfully"),
	REVIEW_STATUS("Reviewed status "), SUCCESSFULLY(" successfully"), REVIEW_REMOVED("Reviewed removed succesfully"),
	DESIGNER_FOLLOWING("Following designer"), DESIGNER_UNFOLLOWING("Unfollowing designer"), ERROR("Error"),
	CART_DATA_DELETED("Cart data deleted successfully"), INVOICE_NOT_FOUND("Invoice not found"),
	PRODUCT_OUT_STOCK("Product is out of stock"),

	// ORDER & PAYMENT CONTROLLER

	UNAUTHORIZED("Unauthorized"), ORDER_PLACED("Order placed successfully"), ORDER_SUMMARY("Order successfully placed "),
	ORDER_CREATED(" Your order created successfully. "), PDF_GENERATED("PDF generated successfully"),
	ORDER_DELIVERED("Order already delivered"), ORDER_CANCEL("Order cancelled successfully"),
	ORDER_REFUND_REQUEST("Refund request submitted successfully"), ORDER_REFUND_APPROVED("Refund successfully"),
	ORDER_REFUND_REJECTED("Refund request rejected"), ORDER_ID_EXIST("OrderId already exists"),
	PAYMENT_NOT_FOUND("Payment not found!"), ORDER_NOT_FOUND("Order not found"),
	ORDER_STATUS_UPDATED("Order status updated"), TRACKING_UPDATED("Tracking updated successfully"),
	SOMETHING_WENT_WRONG("Something went to wrong! from order related"),
	PRODUCT_ALREADY_CANCEL("Product is already cancelled "), INVOICE_EXIST("Invoice already exists!"),
	BAD_REQUEST("Bad request"), INVOICE_ADDED("Invoice added succesfully"),
	INVOICE_UPDATED("Invoice updated succesfully"), CANCELATION_REQUEST("Cancelation request send successfully"),
	ORDER_CANCEL_FROM_DESIGNER("Order cnacelled from designer side"),
	ORDER_CANCEL_REQUEST_REJECTED("Order cancelation request rejected successfully"),
	YOU_CANNOT_SKIP_STATUS("You can't skip any status"), PRODUCT_STATUS("The product is already "),
	ADMIN_CANNOT_CHANGE_STATUS_NOW("Admin can't change this Status Now"), ITEM_STATUS_CHANGE("Item status changed "),
	TO(" to "), ORDER_CANCELATION_REJECTED("Order cancelation rejected"), RANDOM_STRING("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
	RANDOM_STRING_INT("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"), RANDOM_INT("1234567890"),
	DATE_FORMAT_TYPE("dd/MM/yyyy"), DATA_TYPE_FORMAT("dd/MM/yyyy"),
	PRODUCT_ALREADY_DELIVERED("The product is already delivered"), PRODUCT_PLACED("Your product has been ordered by "),
	ADMIN_ROLES("SADMIN"), PLEASE_FILL_UP_REQUIRED_FIELDS("Please fill up the required fields"),
	INVALID_TOKEN("Invalid token"),ORDER_RECEIVED("Order received"),
	PRODUCT_SHIPPED_SUCCESSFULLY("Product shipped successfully");

	private String message;

	MessageConstant(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
