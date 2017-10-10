package com.fast.crawler.redis;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @Description: redis操作封装的工具类
 * @author zhouxi
 * @date 2017年9月5日 上午10:29:30
 *
 * @param <K>
 * @param <V>
 */
@Component
public class RedisClient<K, V> {
	
    @Autowired
    private RedisTemplate<K, V> redisTemplate;
    
    private static final long DEFAULT_EXPIRE_TIME = 60 * 48;   // 默认存活时间45分钟
    
    /**
     * 放入缓存服务器，默认存活48分钟
     *
     * @param key   缓存key值
     * @param value 缓存value值(object对象)
     */
    public void putExpireTime(K key, V value) {
    	putExpireTime(key, value, DEFAULT_EXPIRE_TIME);
    }
    
    /**
     * 放入缓存服务器,设置缓存时间
     *
     * @param key   缓存key值
     * @param value 缓存value值(object对象)
     */
    public void putExpireTime(K key, V value, long expireTime) {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
        valueOper.set(value);
        if(expireTime != -1) {
        	 redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }
    
    /**
     * 获取缓存服务器存储结果值
     *
     * @param key 缓存key值
     * @return 返回结果对象
     */
    public Object get(K key) {
        BoundValueOperations<K, V> boundValueOper = redisTemplate.boundValueOps(key);
        if (boundValueOper == null) {
            return null;
        }
        return boundValueOper.get();
    }

    /**
     * 以list的数据结构保存到redis中
     *
     * @param key
     * @param value
     */
    public void putListData(K key, V value) {
        ListOperations<K, V> boundValueOper = redisTemplate.opsForList();
        if(boundValueOper == null) {
            return;
        }
        boundValueOper.rightPush(key, value);
    }

    /**
     * 出队列
     *
     * @param key
     * @return
     */
    public Object getListOneData(K key) {
        ListOperations<K, V> boundlistOper = redisTemplate.opsForList();
        return boundlistOper.leftPop(key);
    }

    /**
     * 获取list的长度
     *
     * @param key
     * @return
     */
    public long getListSize(K key) {
        ListOperations<K, V> boundlistOper = redisTemplate.opsForList();
        return boundlistOper.size(key);
    }
}
