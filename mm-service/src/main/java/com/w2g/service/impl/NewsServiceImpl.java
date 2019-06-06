package com.w2g.service.impl;

import com.w2g.entity.UserInfo;
import com.w2g.service.NewsService;
//import listennerService.UserEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by W2G on 2019/6/3.
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    ApplicationContext applicationContext;


    @Override
    public void testPublishEventByNews(UserInfo userInfo) {
        //applicationContext.publishEvent(new UserEventListener(this,userInfo));
    }
}
