package tmdtdemo.tmdt.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tmdtdemo.tmdt.dto.request.CartRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;
import tmdtdemo.tmdt.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<List<CartResponse>> addCart(@RequestBody CartRequest request, HttpServletRequest httpServletRequest){
        List<CartResponse> responses = cartService.addCart(request, httpServletRequest.getHeader("x-client-username"));
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/getCart")
    public ResponseEntity<List<CartResponse>> getCart(HttpServletRequest httpServletRequest){
        List<CartResponse> responses = cartService.getCartIfExits(httpServletRequest.getHeader("x-client-username"));
        return ResponseEntity.ok(responses);
    }
}
