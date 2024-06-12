package tmdtdemo.tmdt.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tmdtdemo.tmdt.common.OrderStatus;
import tmdtdemo.tmdt.common.PaymentStatus;
import tmdtdemo.tmdt.config.payment.VNPAYConfig;
import tmdtdemo.tmdt.dto.response.PaymentDTO;
import tmdtdemo.tmdt.entity.OrderDetails;
import tmdtdemo.tmdt.exception.BaseException;
import tmdtdemo.tmdt.exception.ResourceNotFoundException;
import tmdtdemo.tmdt.repository.OrderRepository;
import tmdtdemo.tmdt.service.OrderService;
import tmdtdemo.tmdt.service.PaymentService;
import tmdtdemo.tmdt.utils.BaseResponse;
import tmdtdemo.tmdt.utils.VNPayUtil;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final VNPAYConfig vnPayConfig;
    private final OrderRepository orderRepository;
    @Override
    public String createVnPayPayment(String orderCode, String bankCode, String ipAddress, Long totalOrder) {
//        if(!orderService.getOrderCodeExits(orderCode)){
//            throw new ResourceNotFoundException("This order not found");
//        }else{
//            OrderDetails orderDetails = orderService.getOrderByCode(orderCode);
//
//        }

        long amount = totalOrder * 100;
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(orderCode);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr",ipAddress);
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return paymentUrl;
    }

    @Override
    public BaseResponse paymentStatus(String status, String orderCode) {
        if(status.equals("00")) {
            OrderDetails orderDetails = orderRepository.findOrderDetailsByCode(orderCode);
            orderDetails.setPayment_status(PaymentStatus.DONE.toString());
            orderRepository.save(orderDetails);
            return  BaseResponse
                   .builder()
                    .code(HttpStatus.OK.toString())
                   .message("Payment " + orderCode +" successfully" ).build();
       }
        return BaseResponse
                .builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message("Payment failed").build();
   }
}
