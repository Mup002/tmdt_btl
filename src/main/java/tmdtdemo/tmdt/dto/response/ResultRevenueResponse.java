package tmdtdemo.tmdt.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ResultRevenueResponse {
    private Long id;
    private Long cost;
    private Long revenue;
    private Long profit;
    private Date createdAt;
    private String username;
}
