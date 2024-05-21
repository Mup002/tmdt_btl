package tmdtdemo.tmdt.config;


public class RequestURL {
    protected static  String[] UN_SECURED_URLs = {
            "/api/v1/account/**",
            "/test/redis/**",
            "/test/token/**",
            "/api/v1/room/**",
            "/api/v1/product/**",
            "/api/v1/payment/vn-pay-callback"
    };
     protected static String[] SECURED_URLS_ROLE_USER = {
            "/api/v1/cart/**",
            "/api/v1/comment/cmt",
            "/api/v1/user/index",
             "/api/v1/payment/pay"

    };
    protected static   String[] SECURED_URLS_ROLE_ADMIN = {
            "/api/v1/admin/**",
            "/api/v1/coupon/admin/addProduct",
            "/api/v1/coupon/admin/create",
            "/api/v1/importData/**"
    };
}
