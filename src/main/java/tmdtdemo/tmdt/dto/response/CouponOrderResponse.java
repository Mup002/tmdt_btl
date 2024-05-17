package tmdtdemo.tmdt.dto.response;

import lombok.Data;


@Data
public class CouponOrderResponse {
    private Double coupon_value;
    private String coupon_code;
    private String coupon_type;
    private Long id;
}
