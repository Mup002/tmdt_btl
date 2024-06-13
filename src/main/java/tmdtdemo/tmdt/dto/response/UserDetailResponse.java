package tmdtdemo.tmdt.dto.response;

import lombok.Data;

@Data
public class UserDetailResponse {
    private String phone;
    private String username;
    private String city;
    private String street;
    private String district;
}
