package tmdtdemo.tmdt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class ChangeObject {
    public static String  objectToJson(Object object)  {
        ObjectMapper objectMapper  = new ObjectMapper();
        String json = null;
        try{
            json =  objectMapper.writeValueAsString(object);
        }catch ( JsonProcessingException e){
           e.printStackTrace();
        }
        return json;
    }

    public static <T> String listObjectToJson(List<T>list){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try{
            json = objectMapper.writeValueAsString(list);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return json;
    }

    public static <T> List<T> jsonToListObject(String json, Class<T> valueType){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class,valueType);
            return objectMapper.readValue(json,type);
        }catch(JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }
}
