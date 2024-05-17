package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tmdtdemo.tmdt.dto.request.UserRequest;
import tmdtdemo.tmdt.entity.Role;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.exception.BaseException;
import tmdtdemo.tmdt.exception.ResourceNotFoundException;
import tmdtdemo.tmdt.repository.RoleRepository;
import tmdtdemo.tmdt.repository.UserRepository;
import tmdtdemo.tmdt.service.BaseRedisService;
import tmdtdemo.tmdt.service.EmailSenderService;
import tmdtdemo.tmdt.service.RefreshTokenService;
import tmdtdemo.tmdt.service.UserService;
import tmdtdemo.tmdt.utils.BaseResponse;
import tmdtdemo.tmdt.utils.HelperUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final BaseRedisService baseRedisService;
    private final RefreshTokenService refreshTokenService;
    @Override
    public BaseResponse register(UserRequest request) {
        BaseResponse response = new BaseResponse();
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setDate(request.getDate());
        user.setGender(request.getGender());
        user.setRoles(Collections.singleton(roleRepository.findRoleByName(request.getRole())));
        try{
            userRepository.save(user);
            response.setCode(String.valueOf(HttpStatus.OK.value()));
            response.setMessage("Created account successfully");
            emailSenderService.sendSimpleEmail(
                    user.getEmail(),
                    "Chúc mừng, " + user.getUsername() + "đã tạo tài khoản thành công ",
                    "Created Account Succesfully!"
            );
//            emailSenderService.sendEmailWithAttachment(
//                    user.getEmail(),
//                    "Chúc mừng, " + user.getUsername() + "đã tạo tài khoản thành công ",
//                    "Created Account Succesfully!",
//                    "C:\\Users\\84981\\Pictures\\2021102220710.png"
//            );

        }catch (Exception e){
            response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
            response.setMessage("Service unavailable");
        }
        return response;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if(ObjectUtils.isEmpty(user)){
            throw new ResourceNotFoundException("User : "+ username + " not found, sth wrong");
        }
        return user;
    }
    @Override
    public String logout(String username) {
        String userKey = HelperUtils.accessKeyBuilderRedisKey(username);
        if(baseRedisService.get(userKey) != null){
            baseRedisService.delete(userKey);
            User user = userRepository.findUserByUsername(username);
            if(ObjectUtils.isEmpty(user)){
                throw new ResourceNotFoundException("username : " + username + " not found ");
            }else{
                refreshTokenService.disableToken(user.getId());
            }

            SecurityContextHolder.clearContext();
            return "logout successfully";
        }else{
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()),"user : " + username + " khong ton tai ");
        }

    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
