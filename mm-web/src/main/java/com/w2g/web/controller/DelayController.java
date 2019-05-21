package com.w2g.web.controller;

import com.w2g.core.markerMapper.MapperFilter;
import com.w2g.emun.FilterType;
import com.w2g.entity.DelayQueues;
import com.w2g.entity.News;
import com.w2g.entity.DelayedElement;
import com.w2g.mapper.DelayQueuesMapper;
import com.w2g.mapper.NewsMapper;
import com.w2g.mapper.UserInfoMapper;
import com.w2g.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.DelayQueue;


/**
 * Created by W2G on 2018/8/20.
 *
 */
@RestController
@RequestMapping(value="${w2g.version}/delayQueue")
@Validated
@Api(value="delayQueue测试")
public class DelayController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private DelayQueuesMapper delayQueueMapper;

    /*@Autowired
    private DelayService delayService;*/

    @GetMapping("/delayPush")
    @ApiOperation(value="实现延迟队列的推送问题")
    public ResponseData adminUserLogin (
            @ApiParam(value="推送时间",required = true)@RequestParam long time,
            @ApiParam(value="更新内容",required = true)@RequestParam String updateNote,
            @ApiParam(value="新闻id",required = true)@RequestParam int nId
    ) throws Exception {
        ResponseData responseData=ResponseData.getInstance();

        MapperFilter mapperFilter1=MapperFilter.custom(News.class)
                .addFilter("id", FilterType.EQ,nId)
                .build();

        List<News> newsList=newsMapper.selectByCondition(mapperFilter1.getCondition());

        News news=newsList.get(0);
        DelayQueue<DelayedElements> delayQueue=new DelayQueue<DelayedElements>();
        news.setCreateDate(new Date());
        newsMapper.updateByPrimaryKeySelective(news);
        producer(delayQueue,news,time);

        consumer(delayQueue,updateNote);

        return responseData;
    }

    private void producer(DelayQueue delayQueue, News news, long time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DelayedElements element = new DelayedElements(time*1000,news);
                delayQueue.offer(element);
            }
        }).start();
    }

    private void consumer(DelayQueue<DelayedElements> delayQueue,String updateNote) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    DelayedElements element = null;
                    try {
                        element =  delayQueue.take();

                        News news=element.getNews();
                        news.setUpdateDate(new Date());
                        news.setUpdateNote(updateNote);
                        newsMapper.updateByPrimaryKeySelective(news);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(System.currentTimeMillis()+"---"+element);
                }
            }
        }).start();
    }













    /**
     * delayQueue共通写法
     */
    @GetMapping("/delayPushByCommon")
    @ApiOperation(value="实现延迟队列的推送问题")
    public ResponseData delayPushByCommon (
            @ApiParam(value="推送时间",required = true)@RequestParam int time,
            @ApiParam(value="更新内容",required = true)@RequestParam String updateNote,
            @ApiParam(value="业务类型",required = true)@RequestParam String columnType,
            @ApiParam(value="业务id",required = true)@RequestParam int sId
    ) throws Exception {
        ResponseData responseData=ResponseData.getInstance();

        //调用共通方法
        //DelayQueue<DelayedElement> delayQueue=delayService.dealwith(time,updateNote,sId);
        //consumers(delayQueue,updateNote);

        return responseData;
    }



    private void consumers(DelayQueue<DelayedElement> delay, String updateNote) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    DelayedElement elements = null;
                    try {
                        elements =delay.take();

                        //获取所需元素
                        DelayQueues delayQueue=elements.getDelayQueue();

                        //使用数据库乐观锁

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }







}
