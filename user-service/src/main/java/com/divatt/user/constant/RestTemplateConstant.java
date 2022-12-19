package com.divatt.user.constant;

public enum RestTemplateConstant {

	// dev

	DESIGNER_PRODUCT_VIEW("https://localhost:8083/dev/designerProduct/view/"),
	USER_LOGIN_PRESENT("https://localhost:8080/dev/auth/Present/USER/"),
	DESIGNER_DETAILS("https://localhost:8080/dev/auth/info/DESIGNER/"),
	MAIL_SEND("https://localhost:8080/dev/auth/sendMail"),
	WISHLIST_PRODUCTLIST("https://localhost:8083/dev/designerProducts/getWishlistProductList"),
	CART_PRODUCTLIST("https://localhost:8083/dev/designerProducts/getCartProductList"),
	DESIGNER_BYID("https://localhost:8083/dev/designer/"),
	PRODUCT_LIST_USER("https://localhost:8083/dev/designerProducts/productListUser"),
	DESIGNER_PRODUCT_LIST_USER("https://localhost:8083/dev/designerProduct/getDesignerProductListUser?page="),
	DESIGNER_PRODUCT("https://localhost:8083/dev/designerProducts/productList/"),
	CATEGORY_VIEW("https://localhost:8084/dev/category/view/"),
	USER_DESIGNER_LIST("https://localhost:8083/dev/designer/userDesignerList"),
	DESIGNER_USER("https://localhost:8083/dev/designer/user/"),
	DESIGNER_PER_PRODUCT("https://localhost:8083/dev/designerProduct/getPerDesignerProductUser/"),
	USER_BY_ID("https://localhost:8082/dev/user/getUserId/"),
	DESIGNER_IDLIST("https://localhost:8083/dev/designer/designerIdList");

//prod

//	DESIGNER_PRODUCT_VIEW("https://localhost:9094/prod/designerProduct/view/"),
//	NO_REPLY_MAIL("no-reply@nitsolution.in"), USER_LOGIN_PRESENT("https://localhost:9091/prod/auth/Present/USER/"),
//	DESIGNER_DETAILS("https://localhost:9091/prod/auth/info/DESIGNER/"),
//	MAIL_SEND("https://localhost:9091/prod/auth/sendMail"),
//	WISHLIST_PRODUCTLIST("https://localhost:9094/prod/designerProducts/getWishlistProductList"),
//	CART_PRODUCTLIST("https://localhost:9094/prod/designerProducts/getCartProductList"),
//	DESIGNER_BYID("https://localhost:9094/prod/designer/"),
//	PRODUCT_LIST_USER("https://localhost:9094/prod/designerProducts/productListUser"),
//	DESIGNER_PRODUCT_LIST_USER("https://localhost:9094/prod/designerProduct/getDesignerProductListUser?page="),
//	DESIGNER_PRODUCT("https://localhost:9094/prod/designerProducts/productList/"),
//	CATEGORY_VIEW("https://localhost:9093/prod/category/view/"),
//	USER_DESIGNER_LIST("https://localhost:9094/prod/designer/userDesignerList"),
//	DESIGNER_USER("https://localhost:9094/prod/designer/user/"),
//	DESIGNER_PER_PRODUCT("https://localhost:9094/prod/designerProduct/getPerDesignerProductUser/"),
//	USER_BY_ID("https://localhost:9092/prod/user/getUserId/"),
//	DESIGNER_IDLIST("https://localhost:9094/prod/designer/designerIdList");

	private String link;

	RestTemplateConstant(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

}
