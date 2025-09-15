package com.system.vetcare.controller.constants;

public final class AuthenticationUrl {

	public static final String ABSOLUTE_API_PATH = "/";
	public static final String SECURITY_API_PATH = "/security-api/v1";
	public static final String USER_REGISTRATION = "/registration";
	public static final String USER_LOGIN = "/login";
	public static final String USER_LOGOUT = "/logout";
	public static final String REFRESH_JWT_TOKEN = "/refresh";
	public static final String VALIDATE_EMAIL = "/validate_email";

	private AuthenticationUrl() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

}