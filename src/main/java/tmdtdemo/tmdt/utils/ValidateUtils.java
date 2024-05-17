package tmdtdemo.tmdt.utils;

import lombok.Builder;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
public class ValidateUtils {
    private Object value;
    private boolean required;
    private Integer maxLength;
    private Integer minLength;
    private String fieldName;
    private String regex;
    private boolean onlyNumber;
    private boolean isInteger;
    private boolean isValidEmail;
    private Long max;
    private Long min;
    private final String ONLY_NUMBER = "[0-9]+";
    private final String EMAIL = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";

    public Map validate(){
        Map<String, String> errors = new HashMap<>();
        //check field is required
        if(required && ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(fieldName)){
            errors.put(fieldName,fieldName + "is required");
        }

        //check min max length of field name
        if(!ObjectUtils.isEmpty(minLength)
        && !ObjectUtils.isEmpty(value)
        && value.toString().length() < minLength.intValue()
        && !ObjectUtils.isEmpty(fieldName)){
            errors.put(fieldName, fieldName + " must be has " + minLength + " characters");
        }

        // check field name is only number
        if(onlyNumber && !ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(fieldName)){
            Pattern patternOnlyNumber = Pattern.compile(ONLY_NUMBER);
            Matcher matcher = patternOnlyNumber.matcher(value.toString());
            if(!matcher.matches()){
                errors.put(fieldName, fieldName + " must contain only number");
            }
        }

        // check field name is integer
        if(isInteger && !ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(fieldName)){
            try{
                Integer.parseInt(value.toString());
            }catch (Exception e){
                errors.put(fieldName, fieldName + " must is integer number");
            }
        }

        // check mail is valid
        if(!ObjectUtils.isEmpty(fieldName) && !ObjectUtils.isEmpty(value)
        && isValidEmail){
            Pattern pattern = Pattern.compile(EMAIL);
            Matcher matcher = pattern.matcher(value.toString());

            if(!matcher.matches()){
                errors.put(fieldName, fieldName + " must be a valid email");
            }
        }

        // check max min of value
        if(!ObjectUtils.isEmpty(max)
                && !ObjectUtils.isEmpty(min)
        && !ObjectUtils.isEmpty(value)
                && !ObjectUtils.isEmpty(fieldName)){
            try{
                Long valueCheck = Long.valueOf(value.toString());
                if(valueCheck < min || valueCheck > max){
                    errors.put(fieldName, fieldName + " must range from " + min + " to " + max);
                }
            }catch (Exception e){
                errors.put(fieldName, fieldName +" is valid data type");
            }
        }

        return errors;
    }

}
