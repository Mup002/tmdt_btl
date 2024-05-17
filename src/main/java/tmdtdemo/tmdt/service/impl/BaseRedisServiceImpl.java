package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tmdtdemo.tmdt.service.BaseRedisService;


import java.util.*;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class BaseRedisServiceImpl implements BaseRedisService {
    private final RedisTemplate<String, Object>redisTemplate;
    private final HashOperations<String, String , Object> hashOperations;

    public BaseRedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void setTimeToLive(String key, long timeout) {
        redisTemplate.expire(key,timeout, TimeUnit.MINUTES);
    }

    @Override
    public void hashSet(String key, String field, Object value) {
        hashOperations.put(key, field, value);
    }

    @Override
    public boolean hashExists(String key, String field) {
        return hashOperations.hasKey(key,field);
    }

    @Override
    public boolean hashExistsKey(String key,String keyPrefix) {
        Set<String> listKey = redisTemplate.keys(keyPrefix + "*");
        log.info("... , {} ",listKey);
        if(listKey == null || listKey.isEmpty()){
            return false;
        }else{

            if(listKey.contains(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Map<String, Object> getField(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public Object hashGet(String key, String field) {
        return hashOperations.get(key,field);
    }

    @Override
    public List<Object> hashGetByFilePrefix(String key, String filePrefix) {
        List<Object> objects = new ArrayList<>();
        Map<String, Object> hashEntries = hashOperations.entries(key);
        for(Map.Entry<String, Object> entry : hashEntries.entrySet()){
            if(entry.getKey().startsWith(filePrefix)){
                objects.add(entry.getValue());
            }
        }
        return objects;
    }

    @Override
    public Set<String> getFieldPrefixes(String key) {
        return hashOperations.entries(key).keySet();
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delete(String key, String field) {
        hashOperations.delete(key,field);
    }

    @Override
    public void delete(String key, List<String> fields) {
       for(String field : fields){
           hashOperations.delete(key,fields);
       }
    }
}
