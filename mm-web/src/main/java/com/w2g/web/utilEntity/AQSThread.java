package com.w2g.web.utilEntity;

import com.w2g.core.markerMapper.MapperFilter;
import com.w2g.emun.FilterType;
import com.w2g.entity.News;
import com.w2g.mapper.NewsMapper;
import com.w2g.utils.ApplicationContextProvider;
import com.w2g.utils.RedisPool;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by W2G on 2019/5/20.
 * redis分布式锁实现
 * 获取锁的线程返回true，未获取锁的线程返回false
 */
public class AQSThread implements Runnable {
    private CyclicBarrier cyclicBarrier;
    static final Lock lock = new ReentrantLock();

    public AQSThread(CyclicBarrier c){
        cyclicBarrier=c;
    }

    @Override
    public void run() {
        NewsMapper newsMapper = (NewsMapper) ApplicationContextProvider.getBean("newsMapper");//使用工具手动注入注入对象信息
            try {
                cyclicBarrier.await();
                lock.lock();
                MapperFilter mapperFilter=MapperFilter.custom(News.class)
                        .addFilter("title", FilterType.EQ,"tt")
                        .build();

                List<News> n=newsMapper.selectByCondition(mapperFilter.getCondition());

                if (!n.isEmpty()){
                    News v=new News();
                    v.setTitle("asd");
                    v.setId(n.get(0).getId());
                    newsMapper.updateByPrimaryKeySelective(v);
                }else{
                    News v1=new News();
                    v1.setTitle("tts");
                    newsMapper.insertSelective(v1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                lock.unlock();
            }
    }
}
