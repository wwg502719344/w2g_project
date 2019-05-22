package com.w2g.service.impl;

import com.w2g.entity.News;
import com.w2g.mapper.NewsMapper;
import com.w2g.service.RedisService;
import com.w2g.utils.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Transaction;

import java.util.*;

/**
 * Created by W2G on 2018/6/2.
 */
@Service
public class RedisServiceImpl implements RedisService {

    private RedisPool redisPool = RedisPool.getInstance();

    private Jedis newJedis=redisPool.getJedis();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private NewsMapper newsMapper;

    /**
     * 取出资源
     * @return
     */
    private Jedis borrowJedis(){
        if(null!=redisPool){
            return redisPool.getJedis();
        }

        return null;
    }

    /**
     * 归还资源
     * @param jedis
     */
    private void returnJedis(Jedis jedis){
        if(null!=jedis && null!=redisPool){
            redisPool.returnResource(jedis);
        }
    }

    @Override
    public void strSet(String key, String value) {
        Jedis jedis = borrowJedis();
        if(null!=jedis){
            jedis.set(key, value);
        }
        returnJedis(jedis);
    }

    @Override
    public String setGet(String key) {
        Jedis jedis = borrowJedis();
        if(null!=jedis){
            String value = jedis.get(key);
            returnJedis(jedis);
            return value;
        }
        return null;
    }

    @Override
    public void listAppend(String key, String value) {
        Jedis jedis = borrowJedis();

        if(null!=jedis){
            jedis.rpush(key, value);
            returnJedis(jedis);
        }
    }

    @Override
    public List<String> listGetAll(String key) {
        List<String> list = null;

        Jedis jedis = borrowJedis();

        if(null!=jedis){
            list = jedis.lrange(key, 0, -1);
            returnJedis(jedis);
        }

        return null==list ? new ArrayList() : list;
    }

    @Override
    public boolean exists(String key) {
        Jedis jedis = borrowJedis();

        if(null!=jedis){
            boolean exists = jedis.exists(key);
            returnJedis(jedis);
            return exists;
        }

        return false;
    }


    /**
     * 设置redislist
     * @param key
     * @param value
     */
    @Override
    public void setList(String key, Object value) {

        //设置redis中的list
        List obj = (List) value;
        stringRedisTemplate.opsForList().leftPushAll(key,obj);
    }

    /**
     * 获取List类型的redis数据
     * @param key
     * @return
     */
    public List get(String key)
    {
        Jedis jedis = borrowJedis();
        List value = jedis.lrange(key, 0, -1);

        return value;
    }

    /**
     * 模糊匹配查询
     * @param key
     * @return
     */
    @Override
    public Set<String> getLikeList(String key) {
        Jedis jedis = borrowJedis();
        Set<String> set = jedis.keys("w*");

        /*for ( String keys : set) {
            System.out.println(keys);
        }*/

        return set;
    }

    /**
     * 新增sscan类型的key-value
     * @param key
     * @return
     */
    @Override
    public ScanResult insertSscan(String key) {
        Jedis jedis = borrowJedis();
        for (int i=0;i<50;i++){
            String a="ww"+i;
            jedis.sadd(key,a);
        }

        return null;
    }

    /**
     * 根据scanParams进行模糊匹配
     * @param key
     */
    @Override
    public ScanResult scanParams(String key) {
        //Linux命令 sscan testList123 0 match w*
        ScanParams scanParams = new ScanParams();
        scanParams.match("*1*");
        scanParams.count(100);
        ScanResult a=borrowJedis().sscan(key,0,scanParams);
        return a;
    }

    @Override
    public void postMember(Integer[] blogsIds) {
       // newJedis
        for (int i=0;i<blogsIds.length;i++){
            newJedis.sadd("twolist",blogsIds[i].toString());
        }
        Map map=new HashMap<>();
        map.put("1","1");
        map.put("2","2");
        //添加命令->HMSET forelist '1' '2'
        //获取存入的集合数据->hget forelist 1
        newJedis.hmset("forelist",map);
    }

    /**
     * 测试redis事务
     */
    @Override
    public void testTransaction(){
        boolean resultValue = false;
        try {

            //调用redis事务方法
            resultValue = transMethod(10);
            if (resultValue==true){
                News news=new News();
                news.setTitle("tt");
                newsMapper.insert(news);
                newJedis.set("title", "300");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result is " + resultValue);

        int title = Integer.parseInt(newJedis.get("title"));
        int title2 = Integer.parseInt(newJedis.get("title2"));

        System.out.printf("title: %d, title2: %d\n", title, title2);
    }

    public  boolean transMethod(int amtToSubtract) throws InterruptedException {

        int var1;  // 变量值
        boolean isfalse=false;

        //为变量设置初始值，如果在事务启动后这个值被改变了，则事务判断失效
        newJedis.set("title", "100");

        //此处执行监控命令，监控title变量
        newJedis.watch("title", "title2");

        //jedis获取redis当中的变量值
        var1 = Integer.parseInt(newJedis.get("title"));

//        if (balance < amtToSubtract) {
//            newJedis.unwatch();  // 放弃所有被监控的键
//            System.out.println("Insufficient balance");
//            return false;
//        }

        Transaction transaction = newJedis.multi();

        //transaction.set("title","200");//此处注掉，list为0，如果在此期间var1被修改，则事务失效
        //Thread.sleep(5000);  // 在外部修改 title 值
        System.out.println("stop");//此处方便我打断点对redis数据进行修改来验证事务

        // list为null说明事务执行失败
        List<Object> list = transaction.exec();

        if (list!=null){//如果事务执行成功
            isfalse=true;
        }
        return isfalse;
    }
}




