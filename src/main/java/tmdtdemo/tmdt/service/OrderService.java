package tmdtdemo.tmdt.service;

import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.dto.response.OrderResponse;

public interface OrderService {
    String newOrder(String username, OrderRequest request);
    OrderResponse detailOrder(String username);
}
