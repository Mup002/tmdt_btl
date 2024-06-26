package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tmdtdemo.tmdt.MapData.OrderDetailMapper;
import tmdtdemo.tmdt.common.OrderStatus;
import tmdtdemo.tmdt.common.PaymentStatus;
import tmdtdemo.tmdt.dto.request.CartRequest;
import tmdtdemo.tmdt.dto.request.FlashOrderRequest;
import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.dto.request.UserRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;
import tmdtdemo.tmdt.dto.response.OrderDetailResponse;
import tmdtdemo.tmdt.entity.*;
import tmdtdemo.tmdt.exception.BaseException;
import tmdtdemo.tmdt.exception.ResourceNotFoundException;
import tmdtdemo.tmdt.repository.*;
import tmdtdemo.tmdt.service.*;
import tmdtdemo.tmdt.utils.AppConstants;
import tmdtdemo.tmdt.utils.BaseResponse;
import tmdtdemo.tmdt.utils.DateFormat;
import tmdtdemo.tmdt.utils.RandomCode;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final ProductService productService;
    private final PaymentMethodRepo paymentMethodRepo;
    private final ProductSpuRepo productSpuRepo;
    private final ProductSkuRepo productSkuRepo;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final ShippingDetailsRepo shippingDetailsRepo;
    private final AddressService addressService;
    private final CarrierRepository carrierRepository;
    private final OrderSkuRepo orderSkuRepo;

    @Override
    public BaseResponse createUser(UserRequest request) {
        return userService.register(request);
    }

    @Override
    public List<CartResponse> addCart(Long userID, CartRequest request) {
        return cartService.addCart(request,userRepository.findUserById(userID).getUsername());
    }
    @Override
    public List<User> getAllUser() {
        return userService.getAllUserRole();
    }

    @Override
    public List<ProductSpu> getAllSpu() {
        return productService.getAllProductSpu();
    }

    @Override
    public List<ProductSku> getAllSku(Long idSpu) {
        return productService.getAllProductSku(idSpu);
    }

    @Override
    @Transactional
    public String createFlashOrder(FlashOrderRequest request) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setUser(userService.findUserByUsername(request.getUsername()));
//        orderDetails.setCode(RandomCode.genarateCodeForOrder());
        orderDetails.setCode(request.getOrdercode());
        orderDetails.setPaymentMethod(paymentMethodRepo.findPaymentMethodById(request.getPayment_id()));
        orderDetails.setTotal(request.getTotal());
        orderDetails.setStatus(OrderStatus.DONE.toString());
        orderDetails.setPayment_status(PaymentStatus.DONE.toString());
        orderDetails.setCreatedAt(DateFormat.convertStringToDate(request.getCreatedAt()));
        List<ProductSku> productSkuList = new ArrayList<>();
        List<ProductSpu> productSpuList = new ArrayList<>();
        for (CartRequest cart : request.getCarts()) {
            ProductSpu spu = productSpuRepo.findProductSpuById(cart.getIdSpu());
            ProductSku sku = productSkuRepo.findProductSkuById(cart.getIdSku());
            if(cart.getQuantity() < sku.getQuantity()){
                productService.quantityOreder(sku.getId(),cart.getQuantity());
                productSkuList.add(sku);
                if(!productSpuList.contains(spu)){
                    productSpuList.add(spu);
                }

            }else{
                throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST),"Khong du so luong san pham de thuc hien " + sku.getId());
            }
        }

        orderDetails.setAddress(addressRepository.findAddressById(addressService.addIfDontExist(request.getAddressRequest())));

        orderDetails.setProductSkus(productSkuList);
        orderDetails.setProductSpus(productSpuList);
        orderRepository.save(orderDetails);

        //----luu so luong san pham ban---////
        for(CartRequest cart : request.getCarts()){
            OrderSku orderSku = new OrderSku();
            orderSku.setQuantity(cart.getQuantity());
            ProductSpu spu = productSpuRepo.findProductSpuById(cart.getIdSpu());
            ProductSku sku = productSkuRepo.findProductSkuById(cart.getIdSku());
            orderSku.setOrderDetails(orderDetails);
            orderSku.setProductSku(sku);
            orderSkuRepo.save(orderSku);
        }

        ///------ket thuc luu so luon--------/////
        ShippingDetails shippingDetails = new ShippingDetails();
        shippingDetails.setCarrier(carrierRepository.findCarrierByCarrier(request.getShippingRequest().getCarrier()));
        shippingDetails.setOrderDetails(orderDetails);
        shippingDetails.setCreatedAt(new Date());
        shippingDetails.setFee_ship(request.getShippingRequest().getFee_ship());
        shippingDetails.setExpected(request.getShippingRequest().getExpected());
        shippingDetails.setPhone(request.getShippingRequest().getPhone());
        shippingDetails.setStatus("DONE");
        shippingDetails.setTotal_bill(request.getShippingRequest().getTotal_bill());
        shippingDetails.setCode(request.getShippingRequest().getCode());
        shippingDetails.setService(request.getShippingRequest().getService());
        shippingDetailsRepo.save(shippingDetails);


        return "done";
    }

    @Override
    public String changeOrderStatus(String orderCode, String status) {
        if(Arrays.stream(OrderStatus.values()).anyMatch(
                os -> os.name().equalsIgnoreCase(status)
        )){
            OrderDetails orderDetails = orderRepository.findOrderDetailsByCode(orderCode);
            orderDetails.setStatus(status);
            return "done";
        }else{
            throw new ResourceNotFoundException("status not found");
        }

    }

    @Override
    public List<String> getAllStatus() {
        List<String > result = new ArrayList<>();
        for(OrderStatus s : OrderStatus.values()){
            result.add(s.toString());
        }
        return result;
    }

    @Override
    public List<OrderDetailResponse> allOrderDetails() {
        return OrderDetailMapper.INSTANCE.orderToLstDetailsResponse(orderRepository.findAll());
    }

    @Override
    public String updateProductPrice(Long skuId, Long new_price) {
        return productService.updatePrice(skuId, new_price);
    }
}
