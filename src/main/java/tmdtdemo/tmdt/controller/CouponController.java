package tmdtdemo.tmdt.controller;

import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import tmdtdemo.tmdt.dto.request.CouponRequest;
import tmdtdemo.tmdt.dto.request.ProductListRequest;
import tmdtdemo.tmdt.service.CouponService;
import tmdtdemo.tmdt.utils.BaseResponse;
import tmdtdemo.tmdt.utils.ValidateObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    @PostMapping("/admin/create")
    public ResponseEntity<Object> created(@RequestBody CouponRequest couponRequest){
        Map<String , String> errors = ValidateObject.validateCouponRequest(couponRequest);new HashMap<>();
        if(!ObjectUtils.isEmpty(errors)){
            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }
        String rs = couponService.createdCoupon(couponRequest);
        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.CREATED.toString())
                .message("Created coupon : " + rs).build();
        return ResponseEntity.ok(baseResponse);

    }

    @PatchMapping("/admin/addProduct")
    public ResponseEntity<Object> addProduct(@RequestBody ProductListRequest request, @RequestParam Integer couponId){
        String rs = couponService.appliesToProduct(request,couponId);
        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.toString())
                .message("Added successfully").build();

        return ResponseEntity.ok(baseResponse);

    }
}
