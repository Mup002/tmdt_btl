package tmdtdemo.tmdt.dto.response;

import lombok.Data;

@Data
public class ProductResponse {
    private Long stt;
    private String product_name;
    private Long quantity;
    private Long revenue;
}
