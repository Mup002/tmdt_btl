package tmdtdemo.tmdt.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DashboardResponseForDay {
    private Long product_quantity_by_day;
    private List<Long> skuIDS = new ArrayList<>();
    private Map<String,Long> type_product_by_day = new HashMap<>();
    private Map<String,Long> status_order_by_day = new HashMap<>();

    private Map<Long, Long> revenue_by_day = new HashMap<>();
    private Map<Long, Long> profit_by_day = new HashMap<>();
}
