package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tmdtdemo.tmdt.dto.request.CartRequest;
import tmdtdemo.tmdt.dto.response.CartResponse;
import tmdtdemo.tmdt.entity.Image;
import tmdtdemo.tmdt.entity.User;
import tmdtdemo.tmdt.exception.ResourceNotFoundException;
import tmdtdemo.tmdt.repository.ImageRepository;
import tmdtdemo.tmdt.repository.ProductSkuRepo;
import tmdtdemo.tmdt.repository.ProductSpuRepo;
import tmdtdemo.tmdt.repository.UserRepository;
import tmdtdemo.tmdt.service.BaseRedisService;
import tmdtdemo.tmdt.service.CartService;
import tmdtdemo.tmdt.utils.ChangeObject;
import tmdtdemo.tmdt.utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final BaseRedisService baseRedisService;
    private final UserRepository userRepository;
    private final ProductSkuRepo productSkuRepo;
    private final ProductSpuRepo productSpuRepo;
    private final ImageRepository imageRepository;
    @Override
    public List<CartResponse> addCart(CartRequest cartRequest ,String username) {
        List<CartResponse> cartList = new ArrayList<>();
        User user = userRepository.findUserByUsername(username);
        String keyCart = HelperUtils.cartBuilderRedisKey(user.getUsername());
        CartResponse response = new CartResponse();
        response.setPrice(cartRequest.getPrice());
        response.setQuantity(cartRequest.getQuantity());
        response.setProductSku_name(productSkuRepo.findProductSkuById(cartRequest.getIdSku()).getColor());
        response.setProductSpu_name(productSpuRepo.findProductSpuById(cartRequest.getIdSpu()).getName());
        response.setIdSku(cartRequest.getIdSku());
        response.setIdSpu(cartRequest.getIdSpu());
        Image image = imageRepository.findImageBySpuIdAndSkuId(
                productSpuRepo.findProductSpuById(cartRequest.getIdSpu()).getId(),
                productSkuRepo.findProductSkuById(cartRequest.getIdSku()).getId());
        if(ObjectUtils.isEmpty(image)){
            response.setSrc(null);
        }else{
            response.setSrc(image.getSrc());
        }

        if(!baseRedisService.hashExists(keyCart,"cartData")){
            cartList.add(response);
        }else{
            cartList = ChangeObject.jsonToListObject(baseRedisService.hashGet(keyCart,"cartData").toString(),CartResponse.class);
            int check = 0;
            for(CartResponse cartResponse : cartList){
                if(cartResponse.getIdSpu() == response.getIdSpu() && cartResponse.getIdSku() == response.getIdSku()){
                    cartResponse.setQuantity(cartResponse.getQuantity() + response.getQuantity());
                    check = 1;
                    break;
                }
            }
            if(check == 0){
                cartList.add(response);
            }

        }
        baseRedisService.hashSet(keyCart,"cartData", ChangeObject.listObjectToJson(cartList));
        baseRedisService.setTimeToLive(keyCart,5*60*60);
        return cartList;
    }

    @Override
    public List<CartResponse> getCartIfExits(String username) {
        User user = userRepository.findUserByUsername(username);
        String keyCart = HelperUtils.cartBuilderRedisKey(user.getUsername());
        List<CartResponse> responses = new ArrayList<>();
        if(baseRedisService.hashExists(keyCart,"cartData")){
            responses =  ChangeObject.jsonToListObject(baseRedisService.hashGet(keyCart,"cartData").toString(),CartResponse.class);
        }else{
            throw new ResourceNotFoundException("Khong tim thay gio hang cua " + username);
        }
        return responses ;
    }

    @Override
    public String removeCart(String username) {
        User user = userRepository.findUserByUsername(username);
        String keyCart = HelperUtils.cartBuilderRedisKey(user.getUsername());
        baseRedisService.delete(keyCart,"cartData");
        return "done";
    }

    @Override
    public List<CartResponse> removeItem(String username, Long skuId, Long spuId) {
        User user = userRepository.findUserByUsername(username);
        String keyCart = HelperUtils.cartBuilderRedisKey(username);
        List<CartResponse> responses = new ArrayList<>();
        if(baseRedisService.hashExists(keyCart,"cartData")){
            responses =  ChangeObject.jsonToListObject(baseRedisService.hashGet(keyCart,"cartData").toString(),CartResponse.class);
            for(CartResponse cartResponse : responses){
                if(cartResponse.getIdSku() == skuId && cartResponse.getIdSpu() == spuId){
                    responses.remove(cartResponse);
                    break;
                }
            }
            baseRedisService.hashSet(keyCart,"cartData", ChangeObject.listObjectToJson(responses));
            baseRedisService.setTimeToLive(keyCart,5*60*60);
        }else{
            throw new ResourceNotFoundException("Khong tim thay gio hang cua " + username);
        }
        return responses ;
    }
}
