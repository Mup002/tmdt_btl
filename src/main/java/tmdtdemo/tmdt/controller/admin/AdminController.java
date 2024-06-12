package tmdtdemo.tmdt.controller.admin;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tmdtdemo.tmdt.dto.request.CartRequest;
import tmdtdemo.tmdt.dto.request.FlashOrderRequest;
import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.dto.request.UserRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;
import tmdtdemo.tmdt.dto.response.OrderDetailResponse;
import tmdtdemo.tmdt.entity.ProductSku;
import tmdtdemo.tmdt.entity.ProductSpu;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.service.AdminService;
import tmdtdemo.tmdt.utils.BaseResponse;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/index")
    public ResponseEntity<String> index(Principal principal){
        return ResponseEntity.ok("Welcome to admin page : " + principal.getName());
    }

    @PostMapping("/create/user")
    public ResponseEntity<BaseResponse> createUser(@RequestBody UserRequest request){
        return ResponseEntity.ok(adminService.createUser(request));
    }
    @PostMapping("/add/cart")
    public ResponseEntity<List<CartResponse>> addCart(@RequestBody CartRequest request,
                                                      @RequestParam Long userId){
        return ResponseEntity.ok(adminService.addCart(userId,request));
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(adminService.getAllUser());
    }
    @GetMapping("/getAllSpu")
    public ResponseEntity<List<ProductSpu>> getAllSpu(){
        return ResponseEntity.ok(adminService.getAllSpu());
    }
    @GetMapping("/getAllSkuBy/{idSpu}")
    public ResponseEntity<List<ProductSku>> getAllSku(@PathVariable("idSpu")Long idSpu){
        return ResponseEntity.ok(adminService.getAllSku(idSpu));
    }
    @PostMapping("/createFlashOrder")
    public ResponseEntity<String> createFlashOrder(@RequestBody FlashOrderRequest request){
        return ResponseEntity.ok(adminService.createFlashOrder(request));
    }

    @GetMapping("/status")
    public ResponseEntity<List<String>> getAllOrderStatus(){
        return ResponseEntity.ok(adminService.getAllStatus());
    }

    @PostMapping("/changOrderStatus")
    public ResponseEntity<BaseResponse> change(
            @RequestParam String orderCode,
            @RequestParam String status
    ){
        return ResponseEntity.ok(BaseResponse.builder()
                .code(HttpStatus.OK.toString())
                .message(adminService.changeOrderStatus(orderCode,status)).build());
    }
    @GetMapping("/allOrderDetails")
    public ResponseEntity<List<OrderDetailResponse>> getAllOrder(){
        return ResponseEntity.ok(adminService.allOrderDetails());
    }
}