package tmdtdemo.tmdt.dto.response;

import lombok.Data;

@Data
public class ShippingResponse {

    private String carrier_name;
    private String code;
    private Long fee_ship;
    private Long total_bill;
    private String service;
    private String status;

}
