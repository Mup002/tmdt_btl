package tmdtdemo.tmdt.service;


import tmdtdemo.tmdt.dto.response.DashboardResponseForMonth;

public interface DashboardService  {
    DashboardResponseForMonth getReportByMonth(int month, int year);
}
