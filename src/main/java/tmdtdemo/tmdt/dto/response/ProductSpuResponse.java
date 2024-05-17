package tmdtdemo.tmdt.dto.response;

import lombok.Data;

@Data
public class ProductSpuResponse {
    private Long id;
    private String name;
    private String description;
    private Double rate;
    private boolean status;
    private boolean available;
    private String src;
    private Double price;
}
