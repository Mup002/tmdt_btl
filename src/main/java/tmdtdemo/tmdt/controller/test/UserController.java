package tmdtdemo.tmdt.controller.test;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tmdtdemo.tmdt.dto.response.UserInfoDetailResponse;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/index")
    public ResponseEntity<String> index(Principal principal){
        return ResponseEntity.ok("Welcome to user page : " + principal.getName());
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll(){
        List<User> res = userService.getAllUser();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/info")
    public ResponseEntity<UserInfoDetailResponse> info(
            @RequestParam String username,
            HttpServletRequest servletRequest){
        String header = null;
        if(servletRequest.getHeader("x-client-username") == null){
            header = servletRequest.getHeader("x-admin-username");
        }else{
            header = servletRequest.getHeader("x-client-username");
        }
        return ResponseEntity.ok(userService.info(header,username));
    }
}