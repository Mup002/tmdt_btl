package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tmdtdemo.tmdt.entity.ResultRevenue;
import tmdtdemo.tmdt.repository.ImportHistoryRepo;
import tmdtdemo.tmdt.repository.OrderRepository;
import tmdtdemo.tmdt.repository.ResultRevenueRepo;
import tmdtdemo.tmdt.service.ResultRevenueService;
import tmdtdemo.tmdt.service.UserService;
import tmdtdemo.tmdt.utils.AppConstants;
import tmdtdemo.tmdt.utils.DateFormat;

@Service
@RequiredArgsConstructor
public class ResultRevenueServiceImpl implements ResultRevenueService {
    private final ResultRevenueRepo resultRevenueRepo;
    private final ImportHistoryRepo importHistoryRepo;
    private final OrderRepository orderRepository;
    private final UserService userService;
    @Override
    public String caculateResultFor(int day, int month, int year, String username) {
        ResultRevenue result = new ResultRevenue();
        if(String.valueOf(month) != null && String.valueOf(year) != null){
            Long cost = importHistoryRepo.sumTotalPriceByMonthAndYear(month,year);
            Long revenue = orderRepository.sumTotalByMonthAndYearAndStatus(month, year);
            Long profit = revenue - cost;

            result.setCost(cost);
            result.setRevenue(revenue);
            result.setProfit(profit);
            result.setUser(userService.findUserByUsername(username));

            if(revenue > cost){
                result.setNote("Red");
            }else{
                result.setNote("Green");
            }
            StringBuilder str = new StringBuilder();
            str.append("23:59 ");
            String strDay = String.valueOf(day);
            if(strDay.length() == 1){
                str.append("0");
            }
            str.append(strDay);
            str.append("/");
            String strMonth = String.valueOf(month);
            if(strMonth.length() == 1){
                str.append("0");
            }
            str.append(strMonth);
            str.append("/");

            str.append(String.valueOf(year));
            result.setResultDate(DateFormat.convertStringToDate(str.toString()));
        }else{

        }
        resultRevenueRepo.save(result);
        return "done";
    }
}
