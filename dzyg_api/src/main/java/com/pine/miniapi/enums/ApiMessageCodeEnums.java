package com.pine.miniapi.enums;

/***
 * 
* Description: 错误码
* version:
* @author 
* @date
 */
public enum ApiMessageCodeEnums {

	NO_ERROR(0, "操作成功"), 
	ERROR_SYSTEM(-1001, "您的网络服务繁忙，请稍后重试!"),
	BUSSINESS_EXCEPTOIN(-1002, "业务异常"),
	TOKEN_ERROR(-1003,"请先授权"),
	;
	 
	
	
	private int code;

	private String message;

	private ApiMessageCodeEnums(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
