package tmdtdemo.tmdt.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tmdtdemo.tmdt.dto.response.PaymentDTO;
import tmdtdemo.tmdt.service.PaymentService;
import tmdtdemo.tmdt.utils.BaseResponse;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ResponseEntity<BaseResponse> pay(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if(ip == null){
            ip =  request.getRemoteAddr();
        }
        return ResponseEntity.ok(BaseResponse.builder()
                .code(HttpStatus.OK.toString())
                .message(paymentService.createVnPayPayment(request.getParameter("orderCode")
                        ,request.getParameter("bankCode"),ip,Long.valueOf(request.getParameter("totalOrder"))).toString()).build());
//        return ResponseEntity.ok(BaseResponse.builder()
//                .code(HttpStatus.OK.toString())
//                .message(paymentService.createVnPayPayment(request).toString()).build());
    }
//    @GetMapping("/vn-pay-callback")
//    public ResponseEntity<BaseResponse> payCallbackHandler(HttpServletRequest request,
//                                                           @RequestParam String orderCode) {
//        String status = request.getParameter("vnp_ResponseCode");
//        return ResponseEntity.ok(paymentService.paymentStatus(status,orderCode));
//    }
}
//
/// if (status.equals("00")) {
//            return new ResponseEntity<>(HttpStatus.OK, "Success",
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST, "Failed", null);
//        }