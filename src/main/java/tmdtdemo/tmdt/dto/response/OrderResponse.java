package tmdtdemo.tmdt.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {
    private UserDetailResponse userDetailResponse;
    private String createdDate;
    private List<CartResponse> cartResponseList = new ArrayList<>();
    private Double quantity;
    private CouponOrderResponse couponOrderResponse;
    private boolean payment_status;
}
