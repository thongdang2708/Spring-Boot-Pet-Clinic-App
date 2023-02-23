package com.ltp.PetClinic.security;

public class SecurityConstants {
    public final static String registerPath = "/auth/register";
    public final static String logInPath = "/login";
    public final static int JWT_ACCESS_TOKEN_EXPIRATION = 7200 * 1000;
    public final static int JWT_REFRESH_TOKEN_EXPIRATION = 1 * 24 * 60 * 60 * 1000;
    public final static String JWT_SECRET_CODE = "LGHabcxyz2008";
    public final static String logoutPath = "/logout";
    public final static String fullOwnerPath = "/owner/**";
    public final static String getRefreshTokenPath = "/auth/token/refresh";
    public final static String registerAdminPath = "/auth/registerAdmin";
    public final static String petPath = "/pet/**";
    public final static String vetPath = "/vet/**";
    public final static String visitPath = "/visit/**";
}
