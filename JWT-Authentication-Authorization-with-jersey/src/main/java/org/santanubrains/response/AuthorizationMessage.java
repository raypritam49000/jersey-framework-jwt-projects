package org.santanubrains.response;

public class AuthorizationMessage {
	private int statusCode;
	private String message;
	private String detail;

	public AuthorizationMessage() {
		super();
	}

	public AuthorizationMessage(int statusCode, String message, String detail) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.detail = detail;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
