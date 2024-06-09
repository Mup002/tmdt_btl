package tmdtdemo.tmdt.service;

import tmdtdemo.tmdt.dto.request.CartRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> addCart(CartRequest cartRequest,String username);
    List<CartResponse> getCartIfExits(String username);
    String removeCart(String username);
    List<CartResponse> removeItem(String username, Long skuId, Long spuId);
}
