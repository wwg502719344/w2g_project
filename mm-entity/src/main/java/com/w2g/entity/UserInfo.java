/*
* CreateDate : 2018-04-13 17:43:37
* CreateBy   : vigo wu  
 */
package com.w2g.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity 
@Table( name = "user_info" ) 
public class UserInfo implements Serializable {

	@GeneratedValue(generator = "JDBC")
	@Id 
	@Column(name="id") 
	@ApiModelProperty(value="")
	private Integer id;

	@Column(name="name") 
	@ApiModelProperty(value="姓名") 
	private String name;

	@Column(name="create_date") 
	@ApiModelProperty(value="创建时间") 
	private Date createDate;

	@Column(name="address")
	@ApiModelProperty(value="地址")
	private String address;

	@Column(name="tel")
	@ApiModelProperty(value="地址")
	private String tel;

	@Column(name="redis_key")
	@ApiModelProperty(value="redis的key")
	private String redisKey;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return this.name;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

}