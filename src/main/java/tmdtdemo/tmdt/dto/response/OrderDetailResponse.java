package tmdtdemo.tmdt.dto.response;

import lombok.Data;

@Data
public class OrderDetailResponse {
    private String code;
    private String paymentStatus;
    private String orderStatus;
    private Long total;
    private String username;

}
