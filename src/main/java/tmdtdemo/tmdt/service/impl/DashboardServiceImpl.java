package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tmdtdemo.tmdt.dto.response.DashboardResponseForMonth;
import tmdtdemo.tmdt.entity.ResultRevenue;
import tmdtdemo.tmdt.repository.OrderRepository;
import tmdtdemo.tmdt.repository.ResultRevenueRepo;
import tmdtdemo.tmdt.service.DashboardService;

import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {
    private final ResultRevenueRepo resultRevenueRepo;
    private final OrderRepository orderRepository;
    @Override
    public DashboardResponseForMonth getReportByMonth(int month, int year) {
        DashboardResponseForMonth response_current = new DashboardResponseForMonth();
        //////////////////-----------------xu ly tong don hang---------------------//////////////////
        DecimalFormat df = new DecimalFormat("#.##");
        // so luon trong thang 6
        Long order6 = orderRepository.countOrdersByMonth(month,year);
        response_current.setAmount_order(order6);
        // sp luong trong thang 5
        Long order5 = orderRepository.countOrdersByMonth(month-1, year);
        log.info("order5 = {}",order5);
        // so sanh
        if(order6 > order5){
            Double percent_order =  ((double)order5 / (double)order6) * 100;
            log.info("percent_order >  = {}",percent_order);
            StringBuilder bdorder = new StringBuilder();
            bdorder.append("Tăng ");
            bdorder.append(df.format(percent_order));
            bdorder.append(" % so với tháng trước");
            response_current.setStatus_1(bdorder.toString());
        }else{
            Double percent_order =  ((double)order6 / (double)order5) * 100;
            log.info("percent_order < = {}",percent_order);
            StringBuilder bdorder = new StringBuilder();
            bdorder.append("Giảm ");
            bdorder.append(df.format(percent_order));
            bdorder.append(" % so với tháng trước");
            response_current.setStatus_1(bdorder.toString());
        }
        //////////////////----------------- ket thuc xu ly tong don hang---------------------//////////////////


        //////////////////-----------------xu ly doanh thu don hang---------------------//////////////////

        // xu ly thang 6
        ResultRevenue revenue_current = resultRevenueRepo.findResultRevenueByResultMonth(month,year);

        response_current.setRevenue(revenue_current.getRevenue());
        DecimalFormat df1 = new DecimalFormat("#.##");
        Double percent1 = (double) revenue_current.getProfit() / (double) revenue_current.getRevenue();
        response_current.setPercent(df1.format(percent1));


        // xu ly thang 5
        ResultRevenue revenue_pass = resultRevenueRepo.findResultRevenueByResultMonth(month-1,year);
        DecimalFormat df2 = new DecimalFormat("#.##");
        Double percent2 = (double) revenue_pass.getProfit() / (double) revenue_pass.getRevenue();

        // so sanh
        if(revenue_current.getRevenue() > revenue_pass.getRevenue()){
            DecimalFormat dfss = new DecimalFormat("#.##");
            Double ss =  ((double)revenue_pass.getRevenue() / (double)revenue_current.getRevenue()) * 100;
            StringBuilder builder = new StringBuilder();
            builder.append("Tăng ");
            builder.append(df.format(ss));
            builder.append(" % so với tháng trước");
            response_current.setStatus_2(builder.toString());
        }else{
            Double ss =  ((double)revenue_current.getRevenue() / (double)revenue_pass.getRevenue()) * 100;
            StringBuilder builder = new StringBuilder();
            builder.append("Giảm ");
            builder.append(df.format(ss));
            builder.append(" % so với tháng trước");
            response_current.setStatus_2(builder.toString());

        }

        //////////////////----------------- ket thucxu ly doanh thu don hang---------------------//////////////////

        if(percent1 > percent2){
            StringBuilder builder = new StringBuilder();
            builder.append("Tăng ");
            builder.append((percent1 - percent2) * 100);
            builder.append(" % so với tháng trước");
            response_current.setStatus_3(builder.toString());
        }else if(percent2 > percent1){
            StringBuilder builder = new StringBuilder();
            builder.append("Giảm ");
            builder.append((percent2 - percent1) * 100);
            builder.append(" % so với tháng trước");
            response_current.setStatus_3(builder.toString());
        }else{
            response_current.setStatus_3(null);
        }
        return response_current;
    }
}