/*
 * RnrBase.java
 * Created by Half.Lee
 * Created at 2015年12月30日 下午3:51:06
 * Copyright (C) 2015, All rights reserved.
 */
package com.w2g.utils;

import javax.persistence.Transient;


import tk.mybatis.mapper.entity.IDynamicTableName;

/**
 * R&R管理的Entity生成的VO的父类
 * <li><b>ClassName :</b> RnrBase</li>
 * <li><b>Author :   </b> Half.Lee</li>
 * <li><b>Date :     </b> 2015年12月30日 下午3:51:06</li>
 * <style>*{color:#145b7d;}li{color:#00a6ac;list-style-type:square;}li b{color:#2585a6;}</style>
 */
@SuppressWarnings("serial")
public class RnrBase extends XO implements IDynamicTableName {
	//-------------------------------------------------------------------------------------------------------------------------
	private static ThreadLocal<String> TL_SUFFIX = new ThreadLocal<String>();

	public static void clearSuffix() {
		TL_SUFFIX.remove();
	}

	public static String getSuffix() {
		return TL_SUFFIX.get();
	}

	/**
	 * Query-By-Account<br>
	 * 开启Account纬度查询相关信息
	 */
	public static void openAccountMode() {
		TL_SUFFIX.set("Account");
	}

	/**
	 * Query-By-Partner<br>
	 * 开启Partner纬度查询相关信息
	 */
	public static void openPartnerMode() {
		TL_SUFFIX.set("Partner");
	}

	/*//---------------------------------------------------------------------------------
	@Column(name = "rnr_team_unit_code", insertable = false, updatable = false)
	private String rnrTeamUnitCode;

	@Column(name = "rnr_team_role_code", insertable = false, updatable = false)
	private String rnrTeamRoleCode;

	@Column(name = "rnr_executor_id", insertable = false, updatable = false)
	private String rnrExecutorId;

	@Column(name = "rnr_executor_uc", insertable = false, updatable = false)
	private String rnrExecutorUc;

	@Column(name = "rnr_item_id", insertable = false, updatable = false)
	private String rnrItemId;

	@Column(name = "rnr_item_code", insertable = false, updatable = false)
	private String rnrItemCode;

	@Column(name = "rnr_code_type", insertable = false, updatable = false)
	private String rnrCodeType;*/

	//---------------------------------------------------------------------------------
	@Transient
	private String dynamicTableName;

	/* 
	 * 当该方法返回值不为空时，就会使用返回值作为表名
	 */
	public String getDynamicTableName() {
		return dynamicTableName;
	}

	public void setDynamicTableName(String dynamicTableName) {
		this.dynamicTableName = dynamicTableName;
	}

	//---------------------------------------------------------------------------------
	public static void earse(Object bean) {
		Class<?> clz = bean.getClass();
		if (RnrBase.class.isAssignableFrom(clz)) {
			RnrBase base = (RnrBase) bean;
		/*	base.setRnrItemId(null);
			base.setRnrItemCode(null);
			base.setRnrCodeType(null);
			base.setRnrExecutorId(null);
			base.setRnrExecutorUc(null);
			base.setRnrTeamRoleCode(null);
			base.setRnrTeamUnitCode(null);*/
		}
	}
	//---------------------------------------------------------------------------------

	/*public String getRnrTeamUnitCode() {
		return rnrTeamUnitCode;
	}

	public void setRnrTeamUnitCode(String rnrTeamUnitCode) {
		this.rnrTeamUnitCode = rnrTeamUnitCode;
	}

	public String getRnrTeamRoleCode() {
		return rnrTeamRoleCode;
	}

	public void setRnrTeamRoleCode(String rnrTeamRoleCode) {
		this.rnrTeamRoleCode = rnrTeamRoleCode;
	}

	public String getRnrExecutorId() {
		return rnrExecutorId;
	}

	public void setRnrExecutorId(String rnrExecutorId) {
		this.rnrExecutorId = rnrExecutorId;
	}

	public String getRnrExecutorUc() {
		return rnrExecutorUc;
	}

	public void setRnrExecutorUc(String rnrExecutorUc) {
		this.rnrExecutorUc = rnrExecutorUc;
	}

	public String getRnrItemId() {
		return rnrItemId;
	}

	public void setRnrItemId(String rnrItemId) {
		this.rnrItemId = rnrItemId;
	}

	public String getRnrItemCode() {
		return rnrItemCode;
	}

	public void setRnrItemCode(String rnrItemCode) {
		this.rnrItemCode = rnrItemCode;
	}

	public String getRnrCodeType() {
		return rnrCodeType;
	}

	public void setRnrCodeType(String rnrCodeType) {
		this.rnrCodeType = rnrCodeType;
	}*/
}
