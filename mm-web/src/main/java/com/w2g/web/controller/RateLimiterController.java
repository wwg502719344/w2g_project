package com.w2g.web.controller;

import com.google.common.util.concurrent.RateLimiter;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by W2G on 2019/8/20.
 */
@RestController
@RequestMapping(value="${w2g.version}/RateLimiter")
@Validated
@Api(value="限流算法测试")
public class RateLimiterController {


}
