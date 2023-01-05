package com.divatt.admin.constant;

public enum RestTemplateConstant {
	  // FOR DEV
	USER_STATUS_INFORMATION("https://localhost:8082/dev/user/userStatusInformation"),
	DESIGNER_STATUS_INFORMATION("https://localhost:8083/dev/designer/designerStatusInformation"),
	DESIGNER_PRODUCTS_PRODUCT_LIST("https://localhost:8083/dev/designerProducts/productList/"),
	DESIGNER_PRODUCTS_APPROVAL_UPDATE("https://localhost:8083/dev/designerProducts/approvalUpdate/"),
	AUTH_PRESENT("https://localhost:8080/dev/auth/Present/"),
	AUTH_SEND_MAIL("https://localhost:8080/dev/auth/sendMail"),
	DESIGNER_BYID("https://localhost:8083/dev/designer/"),
	USER_URL("https://localhost:8082/dev/"),
	;
	
	  // FOR PROD
//	USER_STATUS_INFORMATION("https://localhost:9092/prod/user/userStatusInformation"),
//	DESIGNER_STATUS_INFORMATION("https://localhost:9094/prod/designer/designerStatusInformation"),
//	DESIGNER_PRODUCTS_PRODUCT_LIST("https://localhost:9094/prod/designerProducts/productList/"),
//	DESIGNER_PRODUCTS_APPROVAL_UPDATE("https://localhost:9094/prod/designerProducts/approvalUpdate/"),
//	AUTH_PRESENT("https://localhost:9091/prod/auth/Present/"),
//	AUTH_SEND_MAIL("https://65.1.190.195:9091/prod/auth/sendMail")
//	;
	private String message;
    private RestTemplateConstant(String message) {
		this.message = message;
	}
    public String getMessage() {
		return message;
	}
	
}
