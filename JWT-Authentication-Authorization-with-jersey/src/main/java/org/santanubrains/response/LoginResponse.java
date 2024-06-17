package org.santanubrains.response;

public class LoginResponse {

	private Integer success;
	private String message;
	private String accessToken;

	public LoginResponse() {
		super();

	}

	public LoginResponse(Integer success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public LoginResponse(Integer success, String message, String accessToken) {
		super();
		this.success = success;
		this.message = message;
		this.accessToken = accessToken;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
