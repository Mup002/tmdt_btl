package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tmdtdemo.tmdt.common.OrderStatus;
import tmdtdemo.tmdt.dto.request.CartRequest;
import tmdtdemo.tmdt.dto.request.FlashOrderRequest;
import tmdtdemo.tmdt.dto.request.OrderRequest;
import tmdtdemo.tmdt.dto.request.UserRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;
import tmdtdemo.tmdt.entity.OrderDetails;
import tmdtdemo.tmdt.entity.ProductSku;
import tmdtdemo.tmdt.entity.ProductSpu;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.exception.BaseException;
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

    @Override
    public BaseResponse createUser(UserRequest request) {
        return userService.register(request);
    }

    @Override
    public List<CartResponse> addCart(Long userID, CartRequest request) {
        return cartService.addCart(request,userRepository.findUserById(userID).getUsername());
    }
    @Override
    public String createOrder(String username, OrderRequest request) {
//        return orderService.newOrder(username,request);
        return null;
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
        orderDetails.setCode(RandomCode.genarateCodeForOrder());
        orderDetails.setPaymentMethod(paymentMethodRepo.findPaymentMethodById(request.getPayment_id()));
        orderDetails.setTotal(request.getTotal());
        orderDetails.setStatus(OrderStatus.DONE.toString());
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
        orderDetails.setProductSkus(productSkuList);
        orderDetails.setProductSpus(productSpuList);
        orderRepository.save(orderDetails);
        return "done";
    }
}
