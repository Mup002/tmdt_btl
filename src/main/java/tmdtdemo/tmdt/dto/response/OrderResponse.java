package tmdtdemo.tmdt.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {
    private UserDetailResponse userDetailResponse;
    private String createdDate;
    private List<CartResponse> cartResponseList = new ArrayList<>();
    private Long quantity;
    private CouponOrderResponse couponOrderResponse;
    private String payment_status;
    private String status;
}
