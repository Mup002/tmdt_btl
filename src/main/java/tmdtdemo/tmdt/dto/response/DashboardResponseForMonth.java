package tmdtdemo.tmdt.dto.response;

import lombok.Data;


@Data
public class DashboardResponseForMonth {
    private Long amount_order;
    private String status_1;
    private Long revenue;
    private String status_2;
    private String percent;
    private String status_3;
}
