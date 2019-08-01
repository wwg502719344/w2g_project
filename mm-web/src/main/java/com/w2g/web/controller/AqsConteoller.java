package com.w2g.web.controller;

import com.w2g.utils.ResponseData;
import com.w2g.web.utilEntity.AQSThread;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by W2G on 2019/7/23.
 */
@RestController
@RequestMapping(value="${w2g.version}/AQS")
@Validated
@Api(value="AQS测试")
public class AqsConteoller {

    @GetMapping("/A/lockTest")
    @ApiOperation(value="测试单机情况下通过lock解决高并发问题")
    public ResponseData test() {
        CyclicBarrier countDownLatch=new CyclicBarrier(5);
        for (int i=0;i<5;i++){
            new Thread(new AQSThread(countDownLatch),"THREAD-"+i).start();
        }
        return ResponseData.newSuccess();
    }
}
