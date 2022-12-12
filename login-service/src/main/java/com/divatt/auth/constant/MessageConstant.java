package com.divatt.auth.constant;

public enum MessageConstant {
	MAIL_SENT_SUCESS("Mail sent successfully"), CHECKPASSWORD("Please check your password"),
	SOCIAL_LOGIN_NOT_MATCH("Your social loginType don't match , please try with another"),
	BADCREDENTIAL("Bad credentials"), ACCOUNT_DEACTIVE("This account has been deactive"),
	LOGIN_SUCESSFULL("Login successful"),
	ACCOUNT_INACTIVE("You got a mail. please go to that mail and activate your account"),
	PROFILE_REJECTED("Your profile is rejected."),
	PROFILE_DELETED("Your profile has been deleted and no need to take into profile."),
	EMAIL_NOT_FOUND("Email not found"), NODATAFOUND("No data found"),
	USERNAME_NOT_FOUND("Oops! No such user account found"), DATANOTSAVE("Data not save! try again"),
	FORGET_PASSWORDBODY(
			"We are sending you this email because you requested a password reset. You can change your Password by clicking the button below."),
	RESET_DIVATT_PASSWORD("Reset Divatt Password"), SUCESS("SUCCESS"),
	PASSWORD_CHANGED("Password changed successfully"), OLD_PASSWORD_NOT_VALID("Old password is not valid"),
	TOKEN_NOT_VALID("Token not valid"), URL_NOT_VALID("This URL is not valid"),
	LINKEXPIRED("This link has been expierd"), PASSWORD_GENERATE_SUCESS("Password generate successfully"),WAIT_FOR_APPROVAL("Waiting for Approval");

	private String message;

	MessageConstant(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
