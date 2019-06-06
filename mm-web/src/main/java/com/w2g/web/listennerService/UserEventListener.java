package com.w2g.web.listennerService;

import com.w2g.entity.UserInfo;
import org.springframework.context.ApplicationEvent;

/**
 * Created by W2G on 2019/6/3.
 */
public class UserEventListener extends ApplicationEvent {

    private UserInfo userInfo;

    public UserEventListener(Object source,UserInfo userInfo) {
        super(source);
        this.userInfo=userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
