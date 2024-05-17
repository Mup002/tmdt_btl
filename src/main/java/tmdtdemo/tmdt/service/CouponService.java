package tmdtdemo.tmdt.service;

import tmdtdemo.tmdt.dto.request.CouponRequest;
import tmdtdemo.tmdt.dto.request.ProductListRequest;
import tmdtdemo.tmdt.dto.response.CouponResponse;

import java.util.List;

public interface CouponService {
    String  createdCoupon(CouponRequest request);
    String appliesToProduct(ProductListRequest request, Integer couponId);
    boolean checkCouponUsedByUser(Long couponId, Long userId);
    String usedCoupon(Long couponId, Long userId);
}
