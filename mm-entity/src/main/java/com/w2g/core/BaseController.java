package com.w2g.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller共通
 */
public class BaseController {

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	protected HttpServletResponse response;

	/** 登录用户ID */
	protected Integer loginUserId;

	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}

	public Integer getLoginUserId() {
		return loginUserId;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
