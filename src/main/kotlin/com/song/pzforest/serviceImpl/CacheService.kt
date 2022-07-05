package com.song.pzforest.serviceImpl;


import cn.hutool.json.JSONUtil
import cn.hutool.json.JSONUtil.toJsonStr
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.mysql.cj.util.StringUtils
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit
import mu.KotlinLogging;
import org.junit.Test
import org.junit.runners.Parameterized

/*
redis 工具类
 */
@Component
public class CacheService {


    private val logger = KotlinLogging.logger {}
    @Autowired
    lateinit var redisTemplate: StringRedisTemplate;

    val DEFAULT_KEY_PREFIX=""
    val EXPIRE_TIME=6;
    val EXPIRE_TIME_TYPE=TimeUnit.HOURS

    /**
     * 保存数据到缓存
     */
    fun <K,V>add(key:K,value:V){
        try {
            if(value!=null)
            {

                redisTemplate.opsForValue().set(DEFAULT_KEY_PREFIX+key,JSON.toJSONString(value),
                    6,TimeUnit.HOURS)
            }
        }catch (e:Exception)
        {
            logger.debug(e.message,e)
            throw  RuntimeException("数据缓存至redis失败");
        }

    }


    /**
     * 以字符形式获取元素
     */
    fun <K> get(key:K): String? {
        var value:String
        try{

          value= redisTemplate.opsForValue().get(DEFAULT_KEY_PREFIX + key).toString()
            if (value.equals("null"))
            {
                return null;
            }
        }catch (e:Exception){
            logger.debug(e.message,e)
            throw  RuntimeException("从redis缓存中获取缓存数据失败");
        }

        return value
    }

    /**
     * 以对象形式获取元素
     */
    fun <K,V> getObject(key:K,clazz:Class<V>): V? {
        var value: String? = this.get(key);
        var result: V? =null;
        if(!StringUtils.isNullOrEmpty(value)){
            result=JSONObject.parseObject(value,clazz)
        }
        return result


    }


    /**
     * 保存数据到缓存，带有过期时间
     */

    fun <K,V>add(key:K,value:V,timeout:Long,unit: TimeUnit){
        try {
            if(value!=null)
            {
                redisTemplate.opsForValue().set(DEFAULT_KEY_PREFIX+key, JSONUtil.toJsonStr(value),timeout, unit)
            }
        }catch (e:Exception)
        {
            logger.debug { e }
            throw  RuntimeException("数据缓存至redis失败");
        }

    }



}
