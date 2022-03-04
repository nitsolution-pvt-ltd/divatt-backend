package com.ecom.ecomMongo.jwt;

/**
 * @author aryansh
 *
 */
public class JwtProperties 
{
	    public static final String SECRET = "EcomSecretForJWTGeneration";
	    public static final int EXPIRATION_TIME = 864_000_000;
	    public static final String TOKEN_PREFIX = "Bearer ";
	    public static final String HEADER_STRING = "Authorization";
}
