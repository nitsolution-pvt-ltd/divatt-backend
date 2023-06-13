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
	public static String MAIL_SEND = "auth/sendMail";
	public static String PRESENT_AUTH= "auth/Present/";
	public static final String DESIGNER_ID = "designer/{id}";
    public static final String RAZORPAYX_CONTACTS_URL="https://api.razorpay.com/v1/contacts";
	public static final String DESIGNER_IDD = "designer/";
	public static final String DESIGNER_PROFILE_UPDATE = "designer/profile/update";
	public static final String RAZORPAYX_FUNDS_URL="https://api.razorpay.com/v1/fund_accounts";
	public static final String RAZORPAYX_GET_CONTACTS_BY_ID ="https://api.razorpay.com/v1/contacts/";
	public static final String RAZORPAYX_GET_CONTACTS_ALL ="https://api.razorpay.com/v1/contacts";
	public static final String RAZORPAYX_GET_FUNDS_ALL="https://api.razorpay.com/v1/fund_accounts";
	public static final String RAZORPAYX_GET_FUNDS_BY_ID ="https://api.razorpay.com/v1/fund_accounts/";
	public static final String RAZORPAYX_POST_FUNDS_URL="designer/postFundsAccount";
	public static final String RAZORPAYX_GET_FUNDS_URL="payOutDetails/getFundsListById";
	public static final String DESIGNER_POST_CONTS_URL="/designer/postContacts";
	public static final String RAZORPAYX_GET_CONTACTS_URL ="https://api.razorpay.com/v1/contacts";
	public static final String RAZORPAYX_PAYOUT_URL="https://api.razorpay.com/v1/payouts";
	public static final String RAZORPAYX_GET_PAYOUT_BY_ID_URL="https://api.razorpay.com/v1/payouts/";
	public static final String RAZORPAYX_GET_ALL_PAYOUT_URL="https://api.razorpay.com/v1/payouts";

}
