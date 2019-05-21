package com.w2g.service.impl;

import com.w2g.entity.DelayQueues;
import com.w2g.entity.DelayedElement;
import com.w2g.mapper.DelayQueuesMapper;
import com.w2g.service.DelayService;
import com.w2g.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.concurrent.DelayQueue;

/**
 * Created by W2G on 2018/11/28.
 */
public class DelayServiceImpl implements DelayService {

    @Autowired
    private DelayQueuesMapper delayQueueMapper;

    @Override
    public DelayQueue<DelayedElement> dealwith(int time, String updateNote, int sId) {
        ResponseData responseData=ResponseData.getInstance();

        DelayQueues delay=new DelayQueues();
        delay.setServiceId(sId);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + time);

        delay.setDelayDate(calendar.getTime());
        delay.setVer("0");

        delayQueueMapper.insert(delay);

        /**
         * 1和2可以单独抽出来写共通
         */
        //1
        DelayQueue<DelayedElement> delayQueue=new DelayQueue<DelayedElement>();

        return delayQueue;
    }

    private void producers(DelayQueue delayQueue, DelayQueues delay, int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DelayedElement element = new DelayedElement(time*1000,delay);
                delayQueue.offer(element);
            }
        }).start();
    }
}
