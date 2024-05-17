package tmdtdemo.tmdt.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class CouponRequest {
    private String name;
    private String description;
    private String code;
    private Long user_use_max;
    private String type;
    private Date startDate;
    private Date endDate;
    private Double min_order_need;
    private String value;
    private Long quantity;
}
