package tmdtdemo.tmdt.dto.request;

import lombok.Data;

@Data
public class AddressRequest {
    private String street;
    private String district;
    private String city;
    private String username;
}
