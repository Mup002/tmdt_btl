package tmdtdemo.tmdt.dto.request;

import lombok.Data;

@Data
public class OrderRequest {
    private Long total;
    private Long payment_id;
    private Long coupon_id;

//    {
//        "coupon_id" : 1,
//            "payment_id" : 1,
//            "total" : 37900000
//    }
}
