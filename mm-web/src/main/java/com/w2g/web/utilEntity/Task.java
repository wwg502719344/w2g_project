package com.w2g.web.utilEntity;

import com.w2g.core.markerMapper.MapperFilter;
import com.w2g.emun.FilterType;
import com.w2g.entity.UserInfo;
import com.w2g.mapper.UserInfoMapper;
import com.w2g.utils.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by W2G on 2018/10/13.
 * 异步计算类，重写call方法
 */
public class Task implements Callable<String> {

    /*@Autowired
    private UserInfoMapper userInfoMapper;*/



    @Override
    public String call() throws Exception {

        UserInfoMapper userInfoMapper = (UserInfoMapper) ApplicationContextProvider.getBean("userInfoMapper");//使用工具手动注入注入对象信息

        MapperFilter mapperFilter=MapperFilter.custom(UserInfo.class)
                .addFilter("id", FilterType.EQ,"1")
                .build();

        List<UserInfo> uList= userInfoMapper.selectByCondition(mapperFilter.getCondition());
        UserInfo newByTask=uList.get(0);

        Thread.sleep(10000);
        String a=newByTask.getName();
        return a;
    }
}
