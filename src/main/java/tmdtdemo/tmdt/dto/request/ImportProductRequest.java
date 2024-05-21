package tmdtdemo.tmdt.dto.request;

import lombok.Data;

@Data
public class ImportProductRequest {
    private Long spuId;
    private Long skuId;
    private Long quantity;
    private Long price;
}
