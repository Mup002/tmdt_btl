package tmdtdemo.tmdt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import tmdtdemo.tmdt.dto.request.UserRequest;
import tmdtdemo.tmdt.service.UserService;
import tmdtdemo.tmdt.utils.ValidateObject;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor

public class AccountController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRequest dto){
        Map<String, String> errorsValidate = ValidateObject.validateRegisterRequest(dto);
        if(!ObjectUtils.isEmpty(errorsValidate)){
            return new ResponseEntity<>(errorsValidate,HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userService.register(dto));
    }
    @PostMapping("/logout/{username}")
    public ResponseEntity<String> logout(@PathVariable("username")String username){
        String response= userService.logout(username);
        return ResponseEntity.ok(response);
    }
}
