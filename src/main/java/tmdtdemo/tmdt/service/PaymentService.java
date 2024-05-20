package tmdtdemo.tmdt.service;

import jakarta.servlet.http.HttpServletRequest;
import tmdtdemo.tmdt.dto.response.PaymentDTO;
import tmdtdemo.tmdt.utils.BaseResponse;

public interface PaymentService {
     PaymentDTO createVnPayPayment(HttpServletRequest request);
     BaseResponse paymentStatus(String status, String orderCode);
}
