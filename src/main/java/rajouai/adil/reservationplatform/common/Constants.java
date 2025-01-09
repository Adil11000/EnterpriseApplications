package rajouai.adil.reservationplatform.common;

public class Constants {

    // White list of routes that do not require authentication,
    // in array form for `SecurityConfig.java`
    public static final String[] AUTH_WHITELIST = {
            // Frontend/Static resources
            "/",
            "/index.html",
            "/static/**",
            "/assets/**",
            "/favicon.ico",
            "/vite.svg",
            // Auth routes
            "/api/v1/auth/signin",
            "/api/v1/auth/signup",
    };

    public static final String SESSION_ATTRIBUTE_CART = "cart";
    public static final String SESSION_ATTRIBUTE_USER = "user";

}
