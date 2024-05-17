package tmdtdemo.tmdt.dto.request;

import lombok.Data;

@Data
public class CartRequest {
    private Long idSku;
    private Long idSpu;
    private Long quantity;
    private Double price;
}
