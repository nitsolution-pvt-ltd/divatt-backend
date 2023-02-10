package com.divatt.auth.constant;

import org.springframework.beans.factory.annotation.Value;

public class RestTemplateConstants {
	
	
	@Value("${DESIGNER}")
	private static String DESIGNER_SERVICE;

	@Value("${AUTH}")
	private static String AUTH_SERVICE;

	@Value("${ADMIN}")
	private static String ADMIN_SERVICE;

	@Value("${USER}")
	private static String USER_SERVICE;
	
	@Value("${DESIGNER_RESET_PASSWORD_LINK}")
	private static String DESIGNER_RESET_PASSWORD_LINK_LIVE;

	@Value("${USER_RESET_PASSWORD_LINK}")
	private static String USER_RESET_PASSWORD_LINK_LIVE;
	
	@Value("${ADMIN_RESET_PASSWORD_LINK}")
	private static String ADMIN_RESET_PASSWORD_LINK_LIVE;
	
	
	
	public static String USER_LOGIN_ADD="user/add";
//	public static String ADMIN_RESET_PASSWORD_LINK=ADMIN_RESET_PASSWORD_LINK_LIVE;
//	public static String SEND_EMAIL=AUTH_SERVICE+"auth/sendMail";
//	public static String DESIGNER_RESET_PASSWORD_LINK=DESIGNER_RESET_PASSWORD_LINK_LIVE;
//	public static String USER_RESET_PASSWORD_LINK=USER_RESET_PASSWORD_LINK_LIVE;
	public static String MAIL_SEND="auth/sendMail";
	
}
