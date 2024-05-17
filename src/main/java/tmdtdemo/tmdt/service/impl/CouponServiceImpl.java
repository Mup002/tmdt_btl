package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tmdtdemo.tmdt.dto.request.CouponRequest;
import tmdtdemo.tmdt.dto.request.ProductListRequest;
import tmdtdemo.tmdt.entity.Coupon;
import tmdtdemo.tmdt.entity.ProductSpu;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.exception.BaseException;
import tmdtdemo.tmdt.exception.ResourceNotFoundException;
import tmdtdemo.tmdt.repository.CouponRepo;
import tmdtdemo.tmdt.repository.ProductSpuRepo;
import tmdtdemo.tmdt.repository.UserRepository;
import tmdtdemo.tmdt.service.CouponService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepo couponRepo;
    private final ProductSpuRepo productSpuRepo;
    private final UserRepository userRepository;
    @Override
    public String createdCoupon(CouponRequest request) {
        Coupon coupon = new Coupon();
        coupon.setName(request.getName());
        coupon.setType(request.getType());
        coupon.setCode(request.getCode());
        coupon.setMin_order_need(request.getMin_order_need());
        coupon.setQuantity(request.getQuantity());
        coupon.setDescription(request.getDescription());
        coupon.setValue(request.getValue());
        coupon.setUser_use_max(request.getUser_use_max());
        coupon.setStartDate(request.getStartDate());
        coupon.setEndDate(request.getEndDate());
        couponRepo.save(coupon);
        return "done";
    }

    @Override
    public String appliesToProduct(ProductListRequest request, Integer couponId) {
        Coupon coupon = couponRepo.findCouponById(Long.valueOf(couponId));
        if(ObjectUtils.isEmpty(coupon)){
            throw new ResourceNotFoundException("Coupon not found with id : " + couponId);
        }
        List<ProductSpu> productSpuList = new ArrayList<>();
        for(Long id : request.getIds()){
            productSpuList.add(productSpuRepo.findProductSpuById(id));
        }
        coupon.setProductSpuList(productSpuList);
        couponRepo.save(coupon);
        return "done";
    }

    @Override
    public boolean checkCouponUsedByUser(Long couponId, Long userId) {
        User user = userRepository.findUserById(userId);
        Coupon coupon = couponRepo.findCouponById(couponId);
        return coupon.getUserList().contains(user);
    }

    @Override
    public String usedCoupon(Long couponId, Long userId) {
        User user = userRepository.findUserById(userId);
        Coupon coupon = couponRepo.findCouponById(couponId);
        coupon.setQuantity(coupon.getQuantity() - 1);
        coupon.setUserList(Collections.singletonList(user));
//        couponRepo.save(coupon);
        return "used";
    }
}
