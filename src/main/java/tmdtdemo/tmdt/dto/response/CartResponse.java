package tmdtdemo.tmdt.dto.response;

import lombok.Data;

@Data
public class CartResponse {
    private String productSpu_name;
    private String productSku_name;
    private String src;
    private Long quantity;
    private Long price;

}
