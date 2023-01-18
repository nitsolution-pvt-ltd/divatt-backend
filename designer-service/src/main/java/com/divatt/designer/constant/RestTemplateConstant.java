package com.divatt.designer.constant;

public enum RestTemplateConstant {

	// FOR DEV
	USERORDER_GET_ORDER("https://localhost:8082/dev/userOrder/getOrder/"),
	USER_ORDER_UPDATE_ORDER("https://localhost:8082/dev/userOrder/updateOrder/"),
	GET_USER_ADDRESS("https://localhost:8082/dev/user/getUserAddress/"), USER("http://localhost:8082"),
	USER_ORDER_GET_ORDER("https://localhost:9095/dev/userOrder/getOrder/"),
	USER_ORDER_DESIGNER_ORDER_COUNT("https://localhost:8082/dev/userOrder/designerOrderCount/"),
	USER_ORDER_ORDER_DETAILS("https://localhost:8082/dev/userOrder/orderDetails/"),

	CATEGORY_VIEW("https://localhost:8084/dev/category/view/"),
	SUBCATEGORY_VIEW("https://localhost:8084/dev/subcategory/view/"),
	USER_FOLLOWEDUSERLIST("https://localhost:8082/dev/user/followedUserList/"),
	USER_GET_USER_ID("https://localhost:8082/dev/user/getUserId/"),
	INFO_USER("https://localhost:8080/dev/auth/info/USER/"), DEV_CATEGORY("https://localhost:9095/dev/category/"),
	CATEGORY_VIEWBY_NAME("https://localhost:9095/dev/category/viewByName/"),

	PRESENT_DESIGNER("https://localhost:8080/dev/auth/Present/DESIGNER/"),
	DESIGNER_REDIRECT("https://65.1.190.195:8083/dev/designer/redirect/"),
	AUTH_SEND_MAIL("https://65.1.190.195:8080/dev/auth/sendMail"), DESIGNER("https://dev.divatt.com/designer/"),
	USER_FOLLOWER_COUNT("https://localhost:9095/dev/user/followerCount/"),
	USER_FOLLOWED_DESIGNER("https://localhost:8082/dev/user/followedDesigner/"),
	ADMIN_EMAIL("developer.nitsolution@gmail.com"),

	ACCOUNT_ADD("https://localhost:8084/dev/account/add"),
	ACCOUNT_VIEW_BY_ID("https://localhost:8084/dev/account/view/"),
	ACCOUNT_UPDATE_BY_ID("https://localhost:8084/dev/account/update/"),
	ACCOUNT_LIST("https://localhost:8084/dev/account/list"),
	ADMIN_ROLE_NAME("https://localhost:8084/dev/admin/profile/getRoleName/"),
	USER_DESIGNER_DETAILS("https://localhost:8082/dev/user/getUserDesignerDetails/"),
	GET_ALL_CATEGORYDETAILS("https://localhost:8084/dev/category/getAllCategoryDetails"),
	DESIGNER_BY_ID("https://localhost:8083/dev/designer/");

	// FOR PROD
//	 USERORDER_GET_ORDER("https://localhost:9092/prod/userOrder/getOrder/"),
//	 USER_ORDER_UPDATE_ORDER("https://localhost:9092/prod/userOrder/updateOrder/"),
//	 GET_USER_ADDRESS("https://localhost:9092/prod/user/getUserAddress/"),
//	 USER("http://localhost:9092"),
//	 USER_ORDER_GET_ORDER("https://localhost:9090/prod/userOrder/getOrder/"),
//	 USER_ORDER_DESIGNER_ORDER_COUNT("https://localhost:9092/prod/userOrder/designerOrderCount/"),
//	 USER_ORDER_ORDER_DETAILS("https://localhost:9092/prod/userOrder/orderDetails/"),
//	 
//	 CATEGORY_VIEW("https://localhost:9093/prod/category/view/"),
//	 SUBCATEGORY_VIEW("https://localhost:9093/prod/subcategory/view/"),
//	 USER_FOLLOWEDUSERLIST("https://localhost:9092/prod/user/followedUserList/"),
//	 USER_GET_USER_ID("https://localhost:9092/prod/user/getUserId/"),
//	 INFO_USER("https://localhost:9091/prod/auth/info/USER/"),
//	 DEV_CATEGORY("https://localhost:9090/prod/category/"),
//	 CATEGORY_VIEWBY_NAME("https://localhost:9090/prod/category/viewByName/"),
//	 
//	 PRESENT_DESIGNER("https://localhost:9091/prod/auth/Present/DESIGNER/"),
//	 DESIGNER_REDIRECT("https://65.1.190.195:9094/prod/designer/redirect/"),
//	 AUTH_SEND_MAIL("https://65.1.190.195:9091/prod/auth/sendMail"),
//	 DESIGNER("https://dev.divatt.com/designer/"),
//	 USER_FOLLOWER_COUNT("https://localhost:9090/prod/user/followerCount/"),
//	 USER_FOLLOWED_DESIGNER("https://localhost:9092/prod/user/followedDesigner/"),
//	USER_DESIGNER_DETAILS("https://localhost:9092/prod/user/getUserDesignerDetails/"),
//	GET_ALL_CATEGORYDETAILS("https://localhost:8084/prod/category/getAllCategoryDetails");


	private String message;

	private RestTemplateConstant(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
