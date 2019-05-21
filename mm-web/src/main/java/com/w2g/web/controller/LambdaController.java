package com.w2g.web.controller;

import com.w2g.service.LambdaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by W2G on 2018/6/14.
 */
@RestController
@RequestMapping(value="${w2g.version}/lambda")
@Validated
@Api(value="lambda测试")
public class LambdaController {

    @Autowired
    private LambdaService lambdaService;


}
