package tmdtdemo.tmdt.service;

import tmdtdemo.tmdt.dto.request.UserRequest;
import tmdtdemo.tmdt.dto.response.UserInfoDetailResponse;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.utils.BaseResponse;

import java.util.List;

public interface UserService {
    BaseResponse register(UserRequest request);
    User findUserByUsername(String username);
    String logout(String username);
    List<User> getAllUser();
    List<User> getAllUserRole();

    UserInfoDetailResponse info(String x_name, String username);

}
