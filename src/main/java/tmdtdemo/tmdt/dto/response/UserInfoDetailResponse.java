package tmdtdemo.tmdt.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfoDetailResponse {
    private String username;
    private List<String> role_name = new ArrayList<>();
    private Long userId;
    private Long total_all;
    private String email;
    private String sdt;
    private List<AddressResponse> addressResponseList = new ArrayList<>();
    private List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
}
