package com.w2g.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.w2g.core.markerMapper.MapperFilter;
import com.w2g.emun.FilterType;
import com.w2g.entity.News;
import com.w2g.mapper.NewsMapper;
import com.w2g.utils.ResponseData;
import com.w2g.web.utilEntity.AQSThread;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by W2G on 2019/7/23.
 */
@RestController
@RequestMapping(value="${w2g.version}/AQS")
@Validated
@Api(value="AQS测试")
public class AqsConteoller {

    @Autowired
    private NewsMapper newsMapper;

    @GetMapping("/A/lockTest")
    @ApiOperation(value="测试单机情况下通过lock解决高并发问题")
    public ResponseData test() {

        News v1=new News();
        v1.setTitle("first thread");
        v1.setCreateDate(new Date());
        v1.setUpdateDate(new Date());
        v1.setUpdateNote("empty");
        newsMapper.insertSelective(v1);

        //删除之前测试数据
        MapperFilter mapperFilter=MapperFilter.custom(News.class)
                .addFilter("update_note = 'add' or update_note='up' ", FilterType.USR,null)
                .build();

        newsMapper.deleteByCondition(mapperFilter.getCondition());

        CyclicBarrier countDownLatch=new CyclicBarrier(5);
        for (int i=0;i<5;i++){
            new Thread(new AQSThread(countDownLatch),"THREAD-"+i).start();
        }
        return ResponseData.newSuccess("设置完毕，/B/selectLock查看结果");
    }

    @GetMapping("/B/selectLock")
    @ApiOperation(value="测试单机情况下通过lock解决高并发问题")
    public ResponseData selectLock() {

        MapperFilter mapperFilter=MapperFilter.custom(News.class)
                .addFilter("update_note = 'add' or update_note='up' ", FilterType.USR,null)
                .addOrderby("updateDate",false)
                .build();

        PageInfo<News> p=new PageInfo<>(newsMapper.selectByCondition(mapperFilter.getCondition()));
        return ResponseData.newSuccess(p);
    }
}
