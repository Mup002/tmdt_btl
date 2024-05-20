package tmdtdemo.tmdt.dto.response;


import lombok.Data;

@Data
public  class PaymentDTO {
    private String code;
    private String message;
    private String paymentUrl;
}
