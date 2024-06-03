package tmdtdemo.tmdt.service;

import tmdtdemo.tmdt.dto.request.AddressRequest;
import tmdtdemo.tmdt.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    String addNew(AddressRequest request);
    List<AddressResponse> loadAddressByUser(String username);
    Long addIfDontExist(AddressRequest request);

}
