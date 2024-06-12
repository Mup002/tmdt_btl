package tmdtdemo.tmdt.service;

import tmdtdemo.tmdt.dto.request.CartRequest;
import tmdtdemo.tmdt.dto.request.FlashOrderRequest;
import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.dto.request.UserRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;
import tmdtdemo.tmdt.dto.response.OrderDetailResponse;
import tmdtdemo.tmdt.entity.OrderDetails;
import tmdtdemo.tmdt.entity.ProductSku;
import tmdtdemo.tmdt.entity.ProductSpu;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.utils.BaseResponse;

import java.util.List;

public interface AdminService {
    BaseResponse createUser(UserRequest request);
    List<CartResponse> addCart(Long userID , CartRequest request);

    List<User> getAllUser();
    List<ProductSpu> getAllSpu();
    List<ProductSku> getAllSku(Long idSpu);

    String createFlashOrder(FlashOrderRequest request);
    String changeOrderStatus(String orderCode, String status);

    List<String> getAllStatus();
    List<OrderDetailResponse> allOrderDetails();
}
