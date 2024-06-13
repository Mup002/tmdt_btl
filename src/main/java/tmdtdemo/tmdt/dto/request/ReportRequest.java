package tmdtdemo.tmdt.dto.request;

import lombok.Data;

@Data
public class ReportRequest {
    private int day;
    private int month;
    private int year;

    private String username;
    private String date;

    private Long num_of_orders;
    private Long profit;
    private Long revenue;
}
