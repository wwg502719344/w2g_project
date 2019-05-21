/*
* CreateDate : 2018-04-17 15:38:32
* CreateBy   : vigo wu  
 */
package com.w2g.entity;


import javax.persistence.Entity; 
import javax.persistence.Table; 
import javax.persistence.GeneratedValue; 
import javax.persistence.Id; 
import javax.persistence.Column; 
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@Entity 
@Table( name = "news" ) 
public class News {

	@Id 
	@Column(name="id") 
	@ApiModelProperty(value="") 
	private Integer id;

	@Column(name="title")
	@ApiModelProperty(value="新闻标题")
	private String title;

	@Column(name="create_date")
	@ApiModelProperty(value="创建时间")
	private Date createDate;

	@Column(name="update_date")
	@ApiModelProperty(value="更新时间")
	private Date updateDate;

	@Column(name="update_note")
	@ApiModelProperty(value="更新内容")
	private String updateNote;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateNote() {
		return updateNote;
	}

	public void setUpdateNote(String updateNote) {
		this.updateNote = updateNote;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return this.id;
	}

}