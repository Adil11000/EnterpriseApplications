package rajouai.adil.reservationplatform.security;

import java.util.Arrays;
import java.util.List;

public class UrlConstants {

    public static final String[] AUTH_WHITELIST_ARRAY = {
            "/",
            "/index.html",
            "/static/**",
            "/assets/**",
            "/favicon.ico",
            "/vite.svg",
            "/api/v1/auth/signin",
            "/api/v1/auth/signup",
    };

    public static final List<String> AUTH_WHITELIST = Arrays.asList(AUTH_WHITELIST_ARRAY);

}
