package tmdtdemo.tmdt.service;

import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.dto.response.OrderResponse;
import tmdtdemo.tmdt.entity.OrderDetails;

import java.util.List;

public interface OrderService {
    String newOrder(String username, OrderRequest request,String ipAdress);
//    List<OrderResponse> detailOrder(String username);
    boolean getOrderCodeExits(String code);
    OrderDetails getOrderByCode(String code);
    String changePaymentStatus(String code);
    OrderResponse detailOrder(String code, String x_name);
}
