package com.divatt.user.constant;

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

	public static String DESIGNER_PRODUCT_VIEW = "designerProduct/view/";
	public static String USER_LOGIN_PRESENT = "auth/Present/USER/";
	public static String DESIGNER_DETAILS = "auth/info/DESIGNER/";
	public static String MAIL_SEND = "auth/sendMail";
	public static String WISHLIST_PRODUCTLIST = "designerProducts/getWishlistProductList";
	public static String CART_PRODUCTLIST = "designerProducts/getCartProductList";
	public static String DESIGNER_BYID = "designer/";
	public static String PRODUCT_LIST_USER = "designerProducts/productListUser";
	public static String DESIGNER_PRODUCT_LIST_USER = "designerProduct/getDesignerProductListUser?page=";
	public static String DESIGNER_PRODUCT = "designerProducts/productLists/";
	public static String CATEGORY_VIEW = "category/view/";
	public static String USER_DESIGNER_LIST = "designer/userDesignerList";
	public static String DESIGNER_USER = "designer/user/";
	public static String DESIGNER_PER_PRODUCT = "designerProduct/getPerDesignerProductUser/";
	public static String USER_BY_ID = "user/getUserId/";
	public static String DESIGNER_IDLIST = "designer/designerIdList";
	public static String ADMIN_ROLE_NAME = "admin/profile/getRoleName/";
	public static String USER_DESIGNER_POP_LIST = "designer/userDesignerPopList";
//	public static String USER_REDIRECT = "https://dev.divatt.com/dev/user/redirect/";
//	public static String DESIGNER = DESIGNER_SERVICE;

}
