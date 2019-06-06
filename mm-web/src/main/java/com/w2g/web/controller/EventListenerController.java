package com.w2g.web.controller;

import com.w2g.entity.UserInfo;
import com.w2g.service.NewsService;
import com.w2g.service.UserInfoService;
import com.w2g.web.listennerService.UserEventListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by W2G on 2019/6/3.
 * 实现事件监听方法
 * https://www.jianshu.com/p/4359dd4b36a6
 */
@RestController
@RequestMapping(value="${w2g.version}/redis")
@Validated
@Api(value="实现事件监听方法")
public class EventListenerController {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private NewsService newsService;

    @PostMapping("/publish")
    @ApiOperation(value="事件发布",notes = "控制台观察输出")
    public String testRedisSyn(
    ) throws InterruptedException {
        UserInfo user=new UserInfo();
        user.setName("test user");
        userInfoService.testPublishEventByUser(user);
        UserInfo user2=new UserInfo();
        user2.setName("test user2");
        newsService.testPublishEventByNews(user2);
        //调用ApplicationContext完成事件发布
        applicationContext.publishEvent(new UserEventListener(this,user));
        return "事件发布成功";
    }
}
