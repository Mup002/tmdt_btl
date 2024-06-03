package tmdtdemo.tmdt.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class ShippingRequest {
    private String carrier;
//    private Date createdAt;
    private String expected;
    private String code;
    private String status;
    private Long total_bill;
    private Long fee_ship;
    private String phone;
    private String service;
}
