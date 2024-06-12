package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import tmdtdemo.tmdt.common.OrderStatus;
import tmdtdemo.tmdt.common.PaymentStatus;
import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;
import tmdtdemo.tmdt.dto.response.OrderResponse;
import tmdtdemo.tmdt.entity.*;
import tmdtdemo.tmdt.exception.BaseException;
import tmdtdemo.tmdt.exception.ResourceNotFoundException;
import tmdtdemo.tmdt.repository.*;
import tmdtdemo.tmdt.service.*;
import tmdtdemo.tmdt.utils.*;

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
    private final PaymentMethodRepo paymentMethodRepo;
    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final PaymentService paymentService;
    private final CarrierRepository carrierRepository;
    private final ShippingDetailsRepo shippingDetailsRepo;

    @Override
    @Transactional
    public String newOrder(String username, OrderRequest request,String ipAddress) {
        // tra ve mac dinh
        String result = "done";

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

            //-----------check dia chi---------////

            newOrder.setAddress(addressRepository.findAddressById(addressService.addIfDontExist(request.getAddressRequest())));

            //------------ket thuc check dia chi------///

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

//            String orderCode = RandomCode.genarateCodeForOrder();
//            newOrder.setCode(orderCode);
            newOrder.setCode(request.getOrdercode());
            newOrder.setCreatedAt(new Date());
            if(!ObjectUtils.isEmpty(userRepository.findUserByUsername(username))){
                newOrder.setUser(userRepository.findUserByUsername(username));
            }else{
                throw new ResourceNotFoundException("Ko tim thay user voi : " + username);
            }
            //----Xu ly payment method --////

            //--- set trang thai mac dinh don hang---///
            newOrder.setStatus(OrderStatus.WAITING.toString());
            ///------ket thuc set trang thai--------///

            /// truong hop tra sau / ship
            newOrder.setPaymentMethod(paymentMethodRepo.findPaymentMethodById(request.getPayment_id()));
            if(request.getPayment_id()== 1){
               newOrder.setPayment_status(PaymentStatus.WAITING.toString());
            }else if(request.getPayment_id() == 2){
                result = paymentService.createVnPayPayment(request.getOrdercode(),
                        "NCB",
                        ipAddress,
                        request.getShippingRequest().getTotal_bill());
                newOrder.setPayment_status(PaymentStatus.WAITING.toString());
            }
            orderRepository.save(newOrder);
            //--- xu ly van chuyen--------//

            if(ObjectUtils.isEmpty(carrierRepository.findCarrierByCarrier(request.getShippingRequest().getCarrier()))){
                throw new ResourceNotFoundException("Khong tim thay carrirer");
            }
            ShippingDetails shippingDetails = new ShippingDetails();
            shippingDetails.setCarrier(carrierRepository.findCarrierByShortname(request.getShippingRequest().getCarrier()));
            shippingDetails.setOrderDetails(newOrder);
            shippingDetails.setCreatedAt(new Date());
            shippingDetails.setFee_ship(request.getShippingRequest().getFee_ship());
            shippingDetails.setExpected(request.getShippingRequest().getExpected());
            shippingDetails.setPhone(request.getShippingRequest().getPhone());
            shippingDetails.setStatus("Đơn mới");
            shippingDetails.setTotal_bill(request.getShippingRequest().getTotal_bill());
            shippingDetails.setCode(request.getShippingRequest().getCode());
            shippingDetails.setService(request.getShippingRequest().getService());
            shippingDetailsRepo.save(shippingDetails);
//            shippingDetails.setCode(RandomCode.);
            ///--------------------------////
            cartService.removeCart(username);
            return result;
        }else{
            throw new ResourceNotFoundException("gio hang cua user : " + username + " ko ton tai!!");
        }
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

    @Override
    public boolean getOrderCodeExits(String code) {
        return !ObjectUtils.isEmpty(orderRepository.findOrderDetailsByCode(code)) ;
    }

    @Override
    public OrderDetails getOrderByCode(String code) {
        return orderRepository.findOrderDetailsByCode(code);
    }

    @Override
    public String changePaymentStatus(String code) {
        OrderDetails orderDetails = orderRepository.findOrderDetailsByCode(code);
        orderDetails.setPayment_status(PaymentStatus.DONE.toString());
        orderRepository.save(orderDetails);
        return "done";
    }
}
