package com.w2g.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.w2g.core.BaseController;
import com.w2g.core.markerMapper.MapperFilter;
import com.w2g.emun.FilterType;
import com.w2g.entity.UserInfo;
import com.w2g.mapper.UserInfoMapper;
import com.w2g.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="${w2g.version}/userLogin")
@Validated
@Api(value="测试1")
public class AdminLoginController extends BaseController {

	/*@Resource(name = "simpleServiceImpl")
	private SimpleServiceImpl simpleService;*/

	@Autowired
	private UserInfoMapper userInfoMapper;

	@GetMapping("/adminUserLogin")
	@ApiOperation(value="测试1")
	public ResponseData adminUserLogin (
			@ApiParam(value="name",required = true)@RequestParam String name
			) throws Exception {

		MapperFilter mapperFilter=MapperFilter.custom(UserInfo.class)
				.addFilter("id",FilterType.EQ,1)
				.build();

		PageHelper.startPage(1,1);
		PageInfo<UserInfo> p=new PageInfo<>(userInfoMapper.selectByCondition(mapperFilter.getCondition()));


		ResponseData responseData=ResponseData.getInstance();
		Map map=new HashMap<>();
		
		map.put("1","2");
		responseData.setEntity(map);
		return responseData;
	}

}
