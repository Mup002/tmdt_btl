package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import tmdtdemo.tmdt.dto.request.AddressRequest;
import tmdtdemo.tmdt.dto.response.AddressResponse;
import tmdtdemo.tmdt.entity.Address;
import tmdtdemo.tmdt.repository.AddressRepository;
import tmdtdemo.tmdt.service.AddressService;
import tmdtdemo.tmdt.service.UserService;
import tmdtdemo.tmdt.MapData.AddressMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
//    private final AddressMapper addressMapper;
    @Override
    @Transactional
    public String addNew(AddressRequest request) {
        Address address = new Address();
        address.setCity(request.getCity());
        address.setDistrict(request.getDistrict());
        address.setStreet(request.getStreet());
        address.setUser(userService.findUserByUsername(request.getUsername()));
        addressRepository.save(address);
//        Address address = AddressMapper.INSTANCE.requestToAddress(request);
//        addressRepository.save(address);
        return "done";
    }

    @Override
    public List<AddressResponse> loadAddressByUser(String username) {
        return AddressMapper.INSTANCE.addressToLstResponse(addressRepository.findAddressByUserUsername(username));
    }

    @Override
    public Long addIfDontExist(AddressRequest request) {
        if(!ObjectUtils.isEmpty(addressRepository.findAddressByCityAndDistrictAndStreetAndUserUsername(
                request.getCity(),
                request.getDistrict(),
                request.getStreet(),
                request.getUsername()
        ))){
            return addressRepository.findAddressByCityAndDistrictAndStreetAndUserUsername(
                    request.getCity(),
                    request.getDistrict(),
                    request.getStreet(),
                    request.getUsername()
            ).getId();
        }
        addNew(request);
        return addressRepository.findAddressByCityAndDistrictAndStreetAndUserUsername(
                request.getCity(),
                request.getDistrict(),
                request.getStreet(),
                request.getUsername()
        ).getId();
    }
}
