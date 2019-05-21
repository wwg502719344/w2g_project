package com.w2g.service;

import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Set;

/**
 * Created by W2G on 2018/6/4.
 */
public interface RedisService {

        /**
         * string操作，常规设置key－value
         * @param key
         * @param value
         */
        void strSet(String key, String value);

        /**
         * string操作，常规通过key获取value
         * @param key
         * @return
         */
        String setGet(String key);

        /**
         * list操作，尾部追加数据
         * @param key
         * @param value
         */
        void listAppend(String key, String value);

        /**
         * list操作，获取说有数据
         */
        List<String> listGetAll(String key);

        /**
         * 指定键值在redis中是否存在
         * @param key
         * @return
         */
        boolean exists(String key);


        void setList(String a, Object list);

        List get(String key);

        Set<String> getLikeList(String key);

        ScanResult scanParams(String key);

        ScanResult insertSscan(String key);

    void postMember(Integer[] blogsIds);

    void testTransaction() throws InterruptedException;

}


