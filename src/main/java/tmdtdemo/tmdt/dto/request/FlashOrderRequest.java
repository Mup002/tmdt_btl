package tmdtdemo.tmdt.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlashOrderRequest {
    private String username;
    private String createdAt;
    private Long total;
    private Long payment_id;
    private  List<CartRequest> carts = new ArrayList<>();
}
