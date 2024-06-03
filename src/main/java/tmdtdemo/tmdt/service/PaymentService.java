package tmdtdemo.tmdt.service;

import jakarta.servlet.http.HttpServletRequest;
import tmdtdemo.tmdt.utils.BaseResponse;

public interface PaymentService {
     String createVnPayPayment(String orderCode, String bankCode,String ipAddress, Long totalOrder);
//     String createVnPayPayment(HttpServletRequest request);
//     BaseResponse paymentStatus(String status, String orderCode);
}
