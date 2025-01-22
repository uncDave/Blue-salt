package com.blusalt.blusalt.security;

public class SecurityConstants {

	public static final String SECRET = "QIIFxsM1FajEOcZnQK3aMuIyfBfdzfaUkwN0nLnEqo7jWRgNlFghbbcy3zHnfFPq";
//	public static final BigInteger EXPIRATION_TIME = BigInteger.valueOf(new BigInteger(String.valueOf(Long.MAX_VALUE)));
	public static final Long EXPIRATION_TIME = 3_000_000_000L;

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/signup/**";
	public static final String LOGIN_URL = "/auth/login";
	public static final String FORGOT_PASSWORD = "/forgot-password";

	public static final String RESET_PASSWORD = "/reset-password";
	public static final int TOKEN_BEGIN_INDEX = 7;


    public static final int PAGE_SIZE = 10;
}
