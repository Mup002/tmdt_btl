package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import tmdtdemo.tmdt.entity.DailyRevenue;
import tmdtdemo.tmdt.entity.OrderDetails;
import tmdtdemo.tmdt.entity.ProductSku;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.repository.DailyRevenueRepo;
import tmdtdemo.tmdt.repository.OrderRepository;
import tmdtdemo.tmdt.repository.ProductSkuRepo;
import tmdtdemo.tmdt.repository.ProductSpuRepo;
import tmdtdemo.tmdt.service.DailyRevenueService;
import tmdtdemo.tmdt.service.UserService;
import tmdtdemo.tmdt.utils.DateFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyRevenueServiceImpl implements DailyRevenueService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ProductSpuRepo productSpuRepo;
    private final ProductSkuRepo productSkuRepo;
    private final DailyRevenueRepo dailyRevenueRepo ;
    @Override
    @Transactional
    public String caculateDailyRevenueForMonth(int month, int year, String username) {
        User user = userService.findUserByUsername(username);

        int daysOfMonth = LocalDate.of(year,month,1).lengthOfMonth();
        for(int i = 1 ; i<=daysOfMonth ;i++){
            DailyRevenue dailyRevenue = new DailyRevenue();
            StringBuilder builder = new StringBuilder();
            builder.append(year);
            builder.append("-");
            if (month < 10) {
                builder.append("0");
            }
            builder.append(month);
            builder.append("-");
            if (i < 10) {
                builder.append("0");
            }
            builder.append(i);
            String createAt = builder.toString();
            List<OrderDetails> ods = orderRepository.findAllOrderByDay(DateFormat.convertStringToDateQuery(createAt));
            if(ObjectUtils.isEmpty(ods)){
                log.error("ods null with : {}",DateFormat.convertStringToDateQuery(createAt));
            }
            Long revenue = Long.valueOf(0);
            if(orderRepository.sumTotalByDay(i,month,year) != null){
                revenue = orderRepository.sumTotalByDay(i,month,year);
            }

            dailyRevenue.setRevenue(revenue);
            dailyRevenue.setUser(user);
            Long cost = Long.valueOf(0);
            for(OrderDetails od : ods){
                List<ProductSku> skus = od.getProductSkus();
                if(ObjectUtils.isEmpty(skus)){
                    log.error("skus null with : {}",i);
                }
                for(ProductSku sku : skus){
                    cost += sku.getCost();
                }
            }
            dailyRevenue.setCost(cost);
            dailyRevenue.setProfit(revenue - cost);
            dailyRevenue.setResultDate(DateFormat.convertStringToDateQuery(builder.toString()));
            dailyRevenueRepo.save(dailyRevenue);
        }
        return "done";
    }
}
