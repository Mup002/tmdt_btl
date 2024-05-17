package tmdtdemo.tmdt.dto.response;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDetailsResponse {
    private Long id;
    private String name;
    private String description;
    private Double rate;
    private boolean status;
    private boolean available;
    private List<ProductSkuResponse> skuResponseList = new ArrayList<>();

}
