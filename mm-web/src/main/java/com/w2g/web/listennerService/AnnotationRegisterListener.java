package com.w2g.web.listennerService;

import com.w2g.entity.UserInfo;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by W2G on 2019/6/3.
 * 实现监听处理程序
 */
@Component
public class AnnotationRegisterListener {


    @EventListener
    public void onApplicationEvent(UserEventListener userEventListener) {
        UserInfo u=userEventListener.getUserInfo();
        System.out.println("user name is "+u.getName());
    }

}
