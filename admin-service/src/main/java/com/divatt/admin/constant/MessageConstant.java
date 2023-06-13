package com.divatt.admin.constant;

public enum MessageConstant {
	NO_DATA("No data found"), SUCCESS("Success"), ERROR("Error!!"), FAILED("Failed"), ID_NOT_EXIST("Id not exist :"),
	STATUS("Status"), SUCCESSFULLY("successfully"),
	ALL_CATEGORY_NOT_DELETED("All category not deleted successfully! It has subcategory"),

	COLOUR_ADDED("Colour added successfully"), COLOUR_NOT_ADDED("Colour not added"),
	COLOUR_UPDATED("Colour updated successfully"), COLOUR_NOT_UPDATED("Colour not updated"),
	COLOUR_INACTIVATED("Colour inActivated successfully"), COLOUR_ACTIVATED("Colour not inActivated"),
	COLOUR_DELETED("Colour deleted successfully"), COLOUR_NOT_DELETED("Colour not deleted"),
	COLOUR_NOT_FOUND("Colour Not Found"),

	BANNER_ALREADY_EXIST("Banner title already exist"), BANNER_ADDED("Banner added successfully"),
	BANNER_UPDATED("Banner updated successfully"), BANNER_NOT_UPDATED("Banner not updated"),
	FAILED_BANNER("Failed , banner is deleted"), BANNER_DELETED("Banner Deleted successfully"),
	BANNER_NOT_DELETED("Banner not deleted"), BANNER_NOT_FOUND("Banner not found!"), BANNER_INACTIVE("Banner inactive"),
	BANNER_ACTIVE("Banner active"),

	DESIGNER_CATEGORY_ADDED("designer category added successfully"),
	DESIGNER_CATEGORY_NOT_ADDED("designer category not added"),
	DESIGNER_CATEGORY_UPDATED("Designer category updated successfully"),
	DESIGNER_CATEGORY_NOT_UPDATED("Designerlevel not update, May not exist this level"),
	DESIGNER_LEVEL_DELETED("Designerlevels deleted successfully"), DESIGNER_LEVEL_NOT_EXIST("Designerlevels not exist"),

	CATEGORY_NOT_FOUND("Category not found!"), CATEGORY_ALREADY_EXIST("Category already exist!"),
	CATEGORY_ADDED("Category added succesfully"), CATEGORY_UPDATED("Category updated successfully"),
	CATEGORY_DELETE(
			"This category has been assigned a subcategory. Please delete the subcategory then you can delete this category."),
	CATEGORY_DELETED("Category deleted successfully"),

	HSN_CODE_ALREADY_USED("HsnCode already used"), HSN_CODE_ALREADY_EXIST("HsnCode already exist"),
	HSN_ADDED("Hsn added succesfully"), HSN_UPDATED("Hsn update succesfully"),
	HSN_ALREADY_DELETED("This hsnCode already delete"), HSN_CODE_NOT_EXIST("HsnCode not exist"),
	HSN_CODE_DELETED("Hsn delete succesfully"), HSN_ACTIVE("Hsn active successfully"),
	HSN_DEACTIVE("Hsn deactive successfully"),

	FILLUP_EXCEL("Please Fillup Excel"), FIELDS_ON_ROW(" Fields on row no "), AND_CELL_NO(" and cell no "),
	POSITION(" this Positon"), STATUS_UPDATED("Status Updated"), BAD_REQUEST("Bad Request"),
	PRODUCT_STATUS_UPDATED("Product status update successfully"),
	PRODUCT_AND_DESIGNER_ID_MISMATCH("ProductID and designerId are mismatched"),
	MEASUREMENT_ADDED("Measurement Added Successfully"), SUBCATEGORY_ALREADY_EXIST("SubCategory name allready exists"),
	MEASUREMENT_NOT_FOUND("Measurement data not found"), MEASUREMENT_STATUS_ACTIVE("Measurement status active"),
	MEASUREMENT_STATUS_DEACTIVE("Measurement status deactive"), MEASUREMENT_DELETED("Measurement deleted successfully"),
	MEASUREMENT_RECOVER("Measurement recover succesfully"), MEASUREMENT_UPDATED("Measurement updated"),
	NO_LIST_PERMISSION("Don't have list permission"), NO_GET_PERMISSION("Don't have get permission"),
	NO_CREATE_PERMISSION("Don't have create permission"), NO_UPDATE_PERMISSION("Don't have update permission"),
	NO_DELETE_PERMISSION("Don't have delete permission"), INTERNAL_SERVER_ERROR("Internal Server Error"),
	CHECK_ALL_FIELDS("Please check all input fields"), EMAIL_ALREADY_PRESENT("This email already present"),
	REGISTER_SUCCESSFULL("Successfully Registration"), WELCOME("Welcome "),
	ACCOUNT_CREATED_AND_LOGIN(" Your account created successfully. Please login your account by bellow credentials "),
	NAME("\n Username:-  "), PASSWORD("\n Password:-  "), SUB_ADMIN_ADDED("Sub admin added successfully"),
	SUB_ADMIN_DELETED("Subadmin deleted successfully"), CHECK("Check The Fields"),
	UPDATED_SUCCESSFULLY("Updated successfully"), DELETED_SUCCESSFULLY("Deleted successfully"),
	STATUS_CHANGED_SUCCESSFULLY("Status changed successfully"), ROLE_ALREADY_EXIST("This role Already exist"),
	ROLE_ADDED("Role added successfully"), ROLE_GIVEN_TO_ADMIN("Role is given to an admin"),
	ROLE_DELETED("Role deleted successfully"), PRODUCT_NOT_FOUND("Product not found!"),
	SPECIFICATION_ADDED("Specification added sucessfully"), SPECIFICATION_UPDATED("Specification Updated Sucessfully"),
	SPECIFICATION_DELETED("Specification deleted successfully"), INVALID_ID("Invalid Id"),
	CATEGORY_SPECIFICATION_NOT_FOUND("Category specification not found"),
	SPECIFICATION_ACTIVED("Specification actived successfully"),
	SPECIFICATION_DEACTIVED("Specification deactived successfully"), SUBCATEGORY_NOT_FOUND("Sub Category Not Found!"),
	SUBCATEGORY_ADDED("Subcategory added successfully"), SUBCATEGORY_UPDATED("Subcategory updated succesfully"),
	SUBCATEGORY_DELETED("Subcategory deleted successfully"),

	ACCOUNT_ADDED("Account data added successfully"), ACCOUNT_UPDATED("Account data updated successfully"),
	ACCOUNT_NOT_FOUND("Account data not found"), ORDER_ALREADY_EXIST("Account data already exist!"),
	ADMIN_ROLES("SADMIN"),DIVATT_CHARGES("Divatt Charges"),
	DATE_FORMAT("dd/MM/yyyy HH:mm:ss"), NO_ID_PRESENT("No Id is present"), PAYOUT_ADDED("Pay Out Id Added Successfully"),
    PAYMENT_STATUS_NOT_FOUND("No payment status is present"),PAYMENT_STATUS_UPDATE("RazorpayX payment status is updated"),
    PAY_OUT_ID_ADDED("Pay Out Id Added Successfully")
	;

	private String message;

	private MessageConstant(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
