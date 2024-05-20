package tmdtdemo.tmdt.service;

import tmdtdemo.tmdt.dto.request.CartRequest;
import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.dto.request.UserRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;
import tmdtdemo.tmdt.utils.BaseResponse;

import java.util.List;

public interface AdminService {
    BaseResponse createUser(UserRequest request);
    List<CartResponse> addCart(Long userID , CartRequest request);
    String createOrder(String username ,OrderRequest request);
    String createFlashOrder(String username, OrderRequest request, List<CartRequest> carts);
}
