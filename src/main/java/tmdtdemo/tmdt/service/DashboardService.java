package tmdtdemo.tmdt.service;


import tmdtdemo.tmdt.dto.response.DashboardResponseForDay;
import tmdtdemo.tmdt.dto.response.DashboardResponseForMonth;

import java.util.Date;

public interface DashboardService  {
    DashboardResponseForMonth getReportByMonth(int month, int year);
    DashboardResponseForDay getReportByDay(Date dateFrom, Date dateTo);
}
