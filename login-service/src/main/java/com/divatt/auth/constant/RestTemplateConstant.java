package com.divatt.auth.constant;

public enum RestTemplateConstant {
	// dev
	USER_LOGIN_ADD("https://localhost:8082/dev/user/add"),
	ADMIN_RESET_PASSWORD_LINK("https://dev.divatt.com/admin/auth/reset-password/"),
	SEND_EMAIL("https://localhost:8080/dev/auth/sendMail"),
	DESIGNER_RESET_PASSWORD_LINK("https://dev.divatt.com/designer/reset-password/"),
	USER_RESET_PASSWORD_LINK("https://dev.divatt.com/divatt/forgetpassword/");

	// prod
//	USER_LOGIN_ADD("https://localhost:9092/dev/user/add"),
//	ADMIN_RESET_PASSWORD_LINK("https://dev.divatt.com/admin/auth/reset-password/"),
//	SEND_EMAIL("https://localhost:9091/dev/auth/sendMail"),
//	DESIGNER_RESET_PASSWORD_LINK("https://dev.divatt.com/designer/reset-password/"),
//	USER_RESET_PASSWORD_LINK("https://dev.divatt.com/divatt/forgetpassword/");

	private String link;

	RestTemplateConstant(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}
}
