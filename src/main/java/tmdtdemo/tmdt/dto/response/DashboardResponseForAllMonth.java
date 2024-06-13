package tmdtdemo.tmdt.dto.response;

import lombok.Data;
import org.apache.commons.math3.stat.descriptive.summary.Product;

import java.util.HashMap;
import java.util.Map;

@Data
public class DashboardResponseForAllMonth {
    private Map<String, Long> most_user = new HashMap<>();
    private Map<String, Long> most_product = new HashMap<>();
}
