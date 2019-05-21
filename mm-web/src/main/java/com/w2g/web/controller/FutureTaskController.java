package com.w2g.web.controller;

import com.w2g.core.markerMapper.MapperFilter;
import com.w2g.emun.FilterType;
import com.w2g.entity.UserInfo;
import com.w2g.mapper.NewsMapper;
import com.w2g.mapper.UserInfoMapper;
import com.w2g.utils.ResponseData;
import com.w2g.web.utilEntity.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * Created by W2G on 2018/10/13.
 */
@RestController
@RequestMapping(value="${w2g.version}/futureTask")
@Validated
@Api(value="futureTask测试")
public class FutureTaskController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private NewsMapper newsMapper;

    @GetMapping("/computationFutureTask")
    @ApiOperation(value = "实现多线程异步计算")
    public String adminUserLogin(
    ) throws Exception {
        ResponseData responseData = ResponseData.getInstance();

        Task task=new Task();
        FutureTask futureTask=new FutureTask(task);
        Thread thread =new Thread(futureTask);
        thread.setName("thread1");
        thread.start();


        Thread.sleep(5000);
        String a= (String) futureTask.get();
        return a;
    }
}