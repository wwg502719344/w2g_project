package com.w2g.web.utilEntity;

import com.w2g.entity.News;
import com.w2g.mapper.NewsMapper;
import com.w2g.utils.ApplicationContextProvider;
import com.w2g.utils.RedisPool;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by W2G on 2019/5/20.
 * redis分布式锁实现
 * 获取锁的线程返回true，未获取锁的线程返回false
 */
public class WorkThread implements Runnable {
    private CyclicBarrier cyclicBarrier;

    private RedisPool redisPool = RedisPool.getInstance();


    private Jedis newJedis=redisPool.getJedis();

    public WorkThread(CyclicBarrier c){
        cyclicBarrier=c;
    }

    @Override
    public void run() {
            try {
                NewsMapper newsMapper = (NewsMapper) ApplicationContextProvider.getBean("newsMapper");//使用工具手动注入注入对象信息
                cyclicBarrier.await();
                boolean isLock=DistributedLock.tryLock(redisPool,"1", UUID.randomUUID().toString(),11);
                if (isLock){
                    News news=new News();
                    String name=Thread.currentThread().getName();
                    news.setTitle(name);
                    news.setCreateDate(new Date());
                    newsMapper.insert(news);
                }else{
                    News news=new News();
                    news.setTitle("notget");
                    news.setCreateDate(new Date());
                    newsMapper.insert(news);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
