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
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "payment_status", target = "payment_status")
    OrderDetailResponse orderToDetailResponse(OrderDetails orderDetails);
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "payment_status", target = "payment_status")
    List<OrderDetailResponse> orderToLstDetailsResponse(List<OrderDetails> orderDetailsList);
}
