package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tmdtdemo.tmdt.common.OrderStatus;
import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;
import tmdtdemo.tmdt.dto.response.OrderResponse;
import tmdtdemo.tmdt.entity.Coupon;
import tmdtdemo.tmdt.entity.OrderDetails;
import tmdtdemo.tmdt.entity.ProductSku;
import tmdtdemo.tmdt.entity.ProductSpu;
import tmdtdemo.tmdt.exception.BaseException;
import tmdtdemo.tmdt.exception.ResourceNotFoundException;
import tmdtdemo.tmdt.repository.*;
import tmdtdemo.tmdt.service.*;
import tmdtdemo.tmdt.utils.AppConstants;
import tmdtdemo.tmdt.utils.ChangeObject;
import tmdtdemo.tmdt.utils.HelperUtils;
import tmdtdemo.tmdt.utils.RandomCode;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BaseRedisService baseRedisService;
    private final CouponRepo couponRepo;
    private final UserRepository userRepository;
    private final CouponService couponService;
    private final ProductSkuRepo productSkuRepo;
    private final ProductService productService;
    private final ProductSpuRepo productSpuRepo;
    private final CartService cartService;

    @Override
    public String newOrder(String username, OrderRequest request) {
        String keyUser = HelperUtils.cartBuilderRedisKey(username);
        OrderDetails newOrder = new OrderDetails();
        if(baseRedisService.hashExists(keyUser,"cartData")){
            List<CartResponse> cartRedis = ChangeObject.jsonToListObject(baseRedisService.hashGet(keyUser,"cartData").toString(),CartResponse.class);

            List<ProductSku> productSkuList = new ArrayList<>();
            List<ProductSpu> productSpuList = new ArrayList<>();
            for (CartResponse cart : cartRedis) {
                ProductSpu spu = productSpuRepo.findProductSpuByName(cart.getProductSpu_name());
                ProductSku sku = productSkuRepo.findProductSkuByColorAndProductSpuId(cart.getProductSku_name(), spu.getId());
                if(cart.getQuantity() < sku.getQuantity()){
                    productService.quantityOreder(sku.getId(),cart.getQuantity());
                    productSkuList.add(sku);
                    if(!productSpuList.contains(spu)){
                        productSpuList.add(spu);
                    }

                }else{
                    throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST),"Khong du so luong san pham de thuc hien");
                }
            }
            newOrder.setProductSkus(productSkuList);
            newOrder.setProductSpus(productSpuList);
            //----Xu ly payment method --////

            /// truong hop tra sau / ship
//            if(request.getPayment_id()== 1){
//                newOrder.setStatus(OrderStatus.WAITING.toString());
//            }
            newOrder.setStatus(OrderStatus.DONE.toString());

            ///truong hop thanh toan online ////


            ///--updating........-----/////

            //----------- xu ly counpon---------////
            if(request.getCoupon_id() != null){
                Coupon coupon = couponRepo.findCouponById(request.getCoupon_id());
                if(coupon.isAvailable() &&
                        !couponService.checkCouponUsedByUser(request.getCoupon_id(),userRepository.findUserByUsername(username).getId())){
                    couponService.usedCoupon(request.getCoupon_id(),userRepository.findUserByUsername(username).getId());
                }else{
                    throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST),"Coupon not available");
                }
            }
            //-----------ket thuc xu ly counpon---------////


            newOrder.setTotal(request.getTotal());
            newOrder.setCode(RandomCode.genarateCodeForOrder());
            newOrder.setCreatedAt(new Date());
            if(!ObjectUtils.isEmpty(userRepository.findUserByUsername(username))){
                newOrder.setUser(userRepository.findUserByUsername(username));
            }else{
                throw new ResourceNotFoundException("Ko tim thay user voi : " + username);
            }

        }else{
            throw new ResourceNotFoundException("gio hang cua user : " + username + " ko ton tai!!");
        }

        orderRepository.save(newOrder);
        cartService.removeCart(username);
        return "done";
    }

    @Override
    public OrderResponse detailOrder(String username) {
        OrderResponse orderResponse = new OrderResponse();
        String keyCart = HelperUtils.cartBuilderRedisKey(username);
        List<CartResponse> cartResponseList = new ArrayList<>();
        if(baseRedisService.hashExistsKey(keyCart, AppConstants.cartPrefixKey)){
            cartResponseList = ChangeObject.jsonToListObject(baseRedisService.hashGet(keyCart,"cartData").toString(),CartResponse.class);
            orderResponse.setCartResponseList(cartResponseList);
        }
        return null;
    }
}
