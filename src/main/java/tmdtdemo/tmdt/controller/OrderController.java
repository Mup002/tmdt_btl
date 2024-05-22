package tmdtdemo.tmdt.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.service.OrderService;
import tmdtdemo.tmdt.utils.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/new")
    public ResponseEntity<BaseResponse> newOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(
                BaseResponse.builder()
                        .code(HttpStatus.OK.toString())
                        .message(orderService.newOrder(httpServletRequest.getHeader("x-client-username"),orderRequest )).build()
        );
    }
}
