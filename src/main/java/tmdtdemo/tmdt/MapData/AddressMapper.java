package tmdtdemo.tmdt.MapData;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import tmdtdemo.tmdt.dto.request.AddressRequest;
import tmdtdemo.tmdt.dto.response.AddressResponse;
import tmdtdemo.tmdt.entity.Address;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.repository.UserRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
    @Mapping(source = "user.username", target = "username")
    AddressResponse addressToResponse(Address address);

    @Mapping(source = "user.username",target = "username")
    List<AddressResponse> addressToLstResponse(List<Address> addresses);
}
