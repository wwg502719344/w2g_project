package com.w2g.web.controller;

import com.w2g.entity.News;
import com.w2g.entity.UserInfo;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by W2G on 2018/8/20.
 * 封装队列元素，实现Delayed接口
 */
class DelayedElements implements Delayed {

    private final long delay; //延迟时间
    private final long expire;  //到期时间
    private final News news;   //数据
    private final long now; //创建时间

    public DelayedElements(long delay, News news) {
        this.delay = delay;
        this.news = news;
        expire = System.currentTimeMillis() + delay;    //到期时间 = 当前时间+延迟时间
        now = System.currentTimeMillis();
    }

    public long getDelay() {
        return delay;
    }

    public long getExpire() {
        return expire;
    }

    public News getNews() {
        return news;
    }

    public long getNow() {
        return now;
    }

    /**
     * 获得延迟时间   用过期时间-当前时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    /**
     * 队列中内部比较排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) -o.getDelay(TimeUnit.MILLISECONDS));
    }
}
