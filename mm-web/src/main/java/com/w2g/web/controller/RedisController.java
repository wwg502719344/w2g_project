package com.w2g.web.controller;

import com.w2g.entity.UserInfo;
import com.w2g.service.RedisService;
import com.w2g.utils.ResponseData;
import com.w2g.web.utilEntity.WorkThread;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by W2G on 2018/6/4.
 */
@RestController
@RequestMapping(value="${w2g.version}/redis")
@Validated
@Api(value="redis缓存测试")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/syn/testRedisSyn")
    @ApiOperation(value="redis测试-redis实现分布式锁")
    public ResponseData testRedisSyn(
    ) throws InterruptedException {
        CyclicBarrier countDownLatch=new CyclicBarrier(5);
        for (int i=0;i<5;i++){
            new Thread(new WorkThread(countDownLatch),"thread"+i).start();
        }
        return ResponseData.newSuccess();
    }



    @PostMapping("/transaction/testTransaction")
    @ApiOperation(value="redis测试-redis事务实现")
    public ResponseData testTransaction(
    ) throws InterruptedException {
        int type=2;
        redisService.testTransaction(type);
        return ResponseData.newSuccess();
    }

    @PostMapping("/transaction/testTransaction2")
    @ApiOperation(value="redis测试2-redis事务实现-睡眠15秒",notes = "验证redis事务是不是同一时间只能执行一个客户端的事务")
    public ResponseData testTransaction2(
    ) throws InterruptedException {
        int type=1;
        redisService.testTransaction(type);
        return ResponseData.newSuccess();
    }

    @GetMapping("/A/strSet")
    @ApiOperation(value="redis测试-string操作，常规设置key－value")
    public ResponseData test(
            @ApiParam(value="key",required = true )@RequestParam String key,
            @ApiParam(value="value",required = true)@RequestParam String value
    ) {
        ResponseData responseData=ResponseData.getInstance();
        redisService.strSet(key, value);
        return ResponseData.newSuccess();
    }

    @GetMapping("/B/setList")
    @ApiOperation(value="redis测试-设置redis中list的存入")
    public ResponseData setList(
            @ApiParam(value="key",required = true)@RequestParam String key
    ) {
        ArrayList list=new ArrayList();
        for (int i=0;i<5;i++){
            String a=String.valueOf(i);
            list.add(a);
        }
        //LPUSH runoobkey mysql
        redisService.setList(key,list);

        return ResponseData.newSuccess();
    }

    @GetMapping("/C/getList")
    @ApiOperation(value="redis测试-获取redis中指定key的list数据")
    public ResponseData getList(
            @ApiParam(value="key",required = true)@RequestParam String key
            //general
    ) {
        String getredis="";

        //如果获取的是List类型
        //LPUSH runoobkey mysql
        List object=redisService.get(key);
        if(object !=null) {
            for (int i=0;i<object.size();i++){
                String a = (String) object.get(i);
                getredis+=a+"+";
            }
        }

        return ResponseData.newSuccess(getredis);
    }

    @GetMapping("/D/getLikeList")
    @ApiOperation(value="redis测试-获取redis中模糊key的数据")
    public ResponseData getLikeList(
            @ApiParam(value="key",required = true)@RequestParam String key
    ) {
        Set<String> object=redisService.getLikeList(key);
        List alist=new ArrayList();
        for ( String keys : object) {
            alist.add(keys);
        }
        return ResponseData.newSuccess(alist);
    }

    @GetMapping("/E/insertSscan")
    @ApiOperation(value="redis测试-sscan实现模糊查询-新增相应的数据")
    public ResponseData getLikeListBySscan1(
            @ApiParam(value="key",required = true)@RequestParam String key
    ) {

        redisService.insertSscan(key);

        return ResponseData.newSuccess();
    }

    @GetMapping("/F/getLikeListBySscan")
    @ApiOperation(value="redis测试-scan实现模糊查询")
    public ResponseData getLikeListBySscan(
            @ApiParam(value="key",required = true)@RequestParam String key
    ) {

        ScanResult a=redisService.scanParams(key);
        List alist=a.getResult();
        return ResponseData.newSuccess(alist);
    }

    @PostMapping("/G/postMember")
    @ApiOperation(value="redis测试-redis添加集合元素")
    public ResponseData postMember(
            @ApiParam(value="key",required = true)@RequestBody Integer[] blogsIds
    ) {

        redisService.postMember(blogsIds);
        return ResponseData.newSuccess();
    }

    @PostMapping("/syn/testRedisHmset")
    @ApiOperation(value="redis测试-redis实现实体存值")
    public ResponseData testRedisHmset(
            @ApiParam("用户信息") @Validated @RequestBody UserInfo userInfo
    ) throws InterruptedException {
        redisService.testRedisHmset(userInfo);
        return ResponseData.newSuccess();
    }

    @GetMapping("/getRedisHmset")
    @ApiOperation(value="redis测试-redis获取实体取值")
    public ResponseData getRedisHmset(
            @ApiParam(value="key",required = true)@RequestParam String key
    ) throws InterruptedException {
        Object userInfo=redisService.getRedisHmset(key);
        return ResponseData.newSuccess(userInfo);
    }
}
