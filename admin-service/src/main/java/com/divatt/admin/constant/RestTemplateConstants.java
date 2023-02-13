package com.divatt.admin.constant;

import org.springframework.beans.factory.annotation.Value;

public class RestTemplateConstants {
	
	@Value("${DESIGNER}")
	private static String DESIGNER_SERVICE;

	@Value("${AUTH}")
	private static String AUTH_SERVICE;

	
	
	public static String USER_STATUS_INFORMATION="user/userStatusInformation";
	public static String DESIGNER_STATUS_INFORMATION="designer/designerStatusInformation";
	public static String DESIGNER_PRODUCTS_PRODUCT_LIST=DESIGNER_SERVICE+"designerProducts/productLists/";
	public static String DESIGNER_PRODUCTS_APPROVAL_UPDATE="designerProducts/approvalUpdate/";
	public static String AUTH_PRESENT=AUTH_SERVICE+"auth/Present/";
	public static String AUTH_SEND_MAIL=AUTH_SERVICE+"auth/sendMail";
	public static String DESIGNER_BYID=DESIGNER_SERVICE+"designer/";
	public static String PRODUCT_LIST="designerProducts/productListUser";

}
