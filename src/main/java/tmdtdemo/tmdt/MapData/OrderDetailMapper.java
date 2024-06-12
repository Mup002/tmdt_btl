package tmdtdemo.tmdt.MapData;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tmdtdemo.tmdt.dto.response.OrderDetailResponse;
import tmdtdemo.tmdt.entity.OrderDetails;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);
    @Mapping(source = "user.username" ,target = "username")
    OrderDetailResponse orderToDetailResponse(OrderDetails orderDetails);
    @Mapping(source = "user.username", target = "username")
    List<OrderDetailResponse> orderToLstDetailsResponse(List<OrderDetails> orderDetailsList);
}
