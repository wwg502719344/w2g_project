/*
 * ProjectName ：InfoPO
 * File_name   ：Constants.java
 * Type_name   ：Constants
 * Comment     ：本类定义了PO中数据库类型转换规则中的边界值，例如number(p,s),decimal(p,s),numeric(p,s)等数据库类型<br>
 * 在转换成java.lang.Integer,java.lang.Long,java.lang.Flaot和java.lang.Double时的p,s的取值。	    
 */
package com.w2g.utils;

/**
 * @author QiuMingJie
 *
 */
public interface Constants {
	int MinScale = 0;
	int MinPrecision = 0;
	/**
	 * precision小于等于IntegerMaxPrecision的number,decimal,numeric都会转换成java.lang.Integer.
	 */
	int IntegerMaxPrecision = 9;
	/**
	 * precision大于IntegerMaxPrecision同时小于等于IntegerMaxPrecision的number,decimal,numeric都会转换成java.lang.Long.
	 */
	int LongMinPrecision = 9;
	int LongMaxPrecision = 33;
	/**
	 * precision-scale 小于等于 FloatMaxPRS同时
	 * scale 小于 FloatMaxScale的number,decimal,numeric都会转换成java.lang.Float，
	 */
	int FloatMaxPRS = 8;
	int FloatMaxScale = 4;
	
	String MAKER_USERCODE ="UC000000001";  //创客对应角色userCode
	String SYS__USERCODE="UC000000002";    //系统管理员
	String ZHAO__USERCODE="UC000000003";   //招募经理
	String GUIHUA__USERCODE="UC000000004"; //创业规划师
	String KJO_USERCODE="UC000000005"; //空间经理对应角色userCode
	String KJ01E_USERCODE="UC000000006"; //空间管理员1对应角色userCode
	String KJ02E_USERCODE="UC000000007"; //空间管理员2对应角色userCode
	String INVESTOR_USERCODE="UC000000008"; //投资者对应角色userCode
	
	String ENTERPRISE_SIZE_FOR_REFUSE ="3" ;       //拒绝
	String ENTERPRISE_SIZE_FOR_NOENTERPRISE ="1";  //未认证
	String ENTERPRISE_SIZE_FOR_ENTERPRISEED ="2";  //已认证
	

	
	int USER_START_STATUS =1; //启用
	int USER_FORBID_STATUS = 2;// 禁用
	
	String DEFAULT_PASSWORD ="1111aaaa";  //数据库初始密码
	String  PRONECT_AUDITSTATUS_REFUSE="3";  //项目审核拒绝
	String  PRONECT_AUDITSTATUS_DEFAULT="1";//项目审核状态初始值
	String  PRONECT_AUDITSTATUS_SUCCESS="2";//项目审核通过
}
