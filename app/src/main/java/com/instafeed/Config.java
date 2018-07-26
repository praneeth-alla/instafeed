package com.instafeed;

public class Config {

    public static final String AUTH_HOST = "https://insta23prod.auth.us-west-2.amazoncognito.com/login?response_type=tok\n" +
            "en&client_id=5khm2intordkd1jjr7rbborbfj&redirect_uri=https://www.23andme.com\n" +
            "/";
    public static final String BASE_URL = "https://kqlpe1bymk.execute-api.us-west-2.amazonaws.com/";

    public static final String LOG_OUT = "https://insta23prod.auth.us-west-2.amazoncognito.com/logout?response_type=token\n" +
            "&client_id=5khm2intordkd1jjr7rbborbfj&logout_uri=https://www.23andme.com/";
}
