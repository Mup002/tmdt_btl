package tmdtdemo.tmdt.config;


public class RequestURL {
    protected static  String[] UN_SECURED_URLs = {
            "/api/v1/account/**",
            "/test/redis/**",
            "/test/token/**",
            "/api/v1/product/**",
            "/api/v1/payment/vn-pay-callback",
            "/api/v1/search/**",
            "/api/v1/address/**",
            "/api/v1/report/**"

    };
     protected static String[] SECURED_URLS_ROLE_USER = {
            "/api/v1/cart/**",
            "/api/v1/user/index",
             "/api/v1/payment/pay",
             "/api/v1/order/**"

    };
    protected static   String[] SECURED_URLS_ROLE_ADMIN = {
            "/api/v1/admin/**",
            "/api/v1/coupon/admin/addProduct",
            "/api/v1/coupon/admin/create",
            "/api/v1/importData/**",
            "/api/v1/result/**",
            "/api/v1/order/getDetailOrder/**",
            "/api/v1/user/info/**"
    };
}
