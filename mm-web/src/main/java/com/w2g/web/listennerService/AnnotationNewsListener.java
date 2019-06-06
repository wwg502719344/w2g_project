package com.w2g.web.listennerService;

import com.w2g.entity.UserInfo;
import com.w2g.service.NewsService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by W2G on 2019/6/3.
 * 实现监听处理程序
 */
@Component
public class AnnotationNewsListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return aClass== UserEventListener.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        return aClass == NewsService.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        UserEventListener userEventListener=(UserEventListener)applicationEvent;
        UserInfo u=userEventListener.getUserInfo();
        System.out.println("news name is "+u.getName());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
