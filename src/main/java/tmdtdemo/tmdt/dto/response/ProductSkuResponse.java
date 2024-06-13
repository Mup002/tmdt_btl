package tmdtdemo.tmdt.dto.response;

import lombok.Data;

@Data
public class ProductSkuResponse {
    private String color;
    private Long price;
    private Long skuId;
    private Long quantity;
    private String src;
}
