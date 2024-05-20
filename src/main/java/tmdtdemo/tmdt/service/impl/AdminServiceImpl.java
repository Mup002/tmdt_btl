package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tmdtdemo.tmdt.dto.request.CartRequest;
import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.dto.request.UserRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;
import tmdtdemo.tmdt.entity.Role;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.repository.RoleRepository;
import tmdtdemo.tmdt.repository.UserRepository;
import tmdtdemo.tmdt.service.AdminService;
import tmdtdemo.tmdt.service.CartService;
import tmdtdemo.tmdt.service.OrderService;
import tmdtdemo.tmdt.service.UserService;
import tmdtdemo.tmdt.utils.BaseResponse;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderService orderService;

    @Override
    public BaseResponse createUser(UserRequest request) {
        return userService.register(request);
    }

    @Override
    public List<CartResponse> addCart(Long userID, CartRequest request) {
        return cartService.addCart(request,userRepository.findUserById(userID).getUsername());
    }
    @Override
    public String createOrder(String username, OrderRequest request) {
        return orderService.newOrder(username,request);
    }

    @Override
    public String createFlashOrder(String username, OrderRequest request, List<CartRequest> carts) {

        return null;
    }
}
