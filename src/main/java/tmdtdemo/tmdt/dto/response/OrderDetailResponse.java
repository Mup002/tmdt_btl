package tmdtdemo.tmdt.dto.response;


import lombok.Data;

import java.util.Date;

@Data
public class OrderDetailResponse {
    private String code;
    private String status;
    private String payment_status;
    private Long total;
    private String username;
    private Date createdAt;

}
