package tmdtdemo.tmdt.utils;

import tmdtdemo.tmdt.dto.request.CouponRequest;
import tmdtdemo.tmdt.dto.request.UserRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateObject {
    public static Map<String, String> validateRegisterRequest(UserRequest dto){
        Map<String, String> errors = new HashMap<>();

        errors.putAll(ValidateUtils.builder()
                .fieldName("username")
                .value(dto.getUsername())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("password")
                .value(dto.getPassword())
                .required(true)
                        .minLength(0)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("email")
                .value(dto.getEmail())
                .required(true)
                .isValidEmail(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("gender")
                .value(dto.getGender())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("date")
                .value(dto.getGender())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("role")
                .value(dto.getGender())
                .required(true)
                .build().validate());

        return errors;
    }

    public static Map<String,String> validateCouponRequest(CouponRequest request){
        Map<String , String> errors = new HashMap<>();

        errors.putAll(ValidateUtils.builder()
                .fieldName("name")
                        .value(request.getName())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("code")
                        .value(request.getCode())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("type")
                        .value(request.getValue())
                        .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("user_use_max")
                        .value(request.getUser_use_max())
                .required(true)
                .isInteger(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("min_order_need")
                        .value(request.getMin_order_need())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("value")
                        .value(request.getValue())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("quantity")
                        .value(request.getQuantity())
                        .required(true)
                .build().validate());

        return errors;
    }
}
