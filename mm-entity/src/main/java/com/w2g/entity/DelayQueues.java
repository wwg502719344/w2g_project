/*
* CreateDate : 2018-11-28 13:25:11
* CreateBy   : vigo wu  
 */
package com.w2g.entity;

import java.util.Date;

import javax.persistence.Entity; 
import javax.persistence.Table; 
import javax.persistence.GeneratedValue; 
import javax.persistence.Id; 
import javax.persistence.Column; 
import io.swagger.annotations.ApiModelProperty; 
@Entity 
@Table( name = "delay_queues" )
public class DelayQueues {

	@GeneratedValue(generator = "JDBC") 
	@Id 
	@Column(name="id") 
	@ApiModelProperty(value="") 
	private Integer id;

	@Column(name="service_id") 
	@ApiModelProperty(value="服务id") 
	private Integer serviceId;

	@Column(name="column_name") 
	@ApiModelProperty(value="所属数据表") 
	private String columnName;

	@Column(name="delay_date") 
	@ApiModelProperty(value="到期时间") 
	private Date delayDate;

	@Column(name="ver") 
	@ApiModelProperty(value="版本号") 
	private String ver;


	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setServiceId(Integer serviceId){
		this.serviceId=serviceId;
	}

	public Integer getServiceId(){
		return this.serviceId;
	}

	public void setColumnName(String columnName){
		this.columnName=columnName;
	}

	public String getColumnName(){
		return this.columnName;
	}

	public void setDelayDate(Date delayDate){
		this.delayDate=delayDate;
	}

	public Date getDelayDate(){
		return this.delayDate;
	}

	public void setVer(String ver){
		this.ver=ver;
	}

	public String getVer(){
		return this.ver;
	}

}