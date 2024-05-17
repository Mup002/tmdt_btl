package tmdtdemo.tmdt.dto.response;

import lombok.Data;

@Data
public class ProductTypeResponse {
    private String type;
    private Long categories;
    private String spuCustom;
}
