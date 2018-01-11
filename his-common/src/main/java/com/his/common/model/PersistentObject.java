package com.his.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 持久化基类
 * 
 * @author hybin
 */
public class PersistentObject implements Serializable {

	private static final long serialVersionUID = 1472145516693079043L;

	/** 主键ID */
	private String id = UUID.randomUUID().toString();
	private Date createDate;
	private String createUser;// 创建人
	private String createUserId;
	private Date updateDate;
	private String updateUser;// 修改人
	private Integer status;// '0' COMMENT '状态 0可用，1停用',
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 新增初始化数据
	 */
	public void  createPersistentObject() {
		this.id = UUID.randomUUID().toString();
		this.createDate = new Date();
		this.status=1;
	}
	/**
	 * 修改
	 */
	public void  updatePersistentObject() {
		this.updateDate = new Date();
	}
	
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * 覆盖原equals方法，只要对象类型相同并且主键相同，那么返回true，表示两个对象相等
	 * 
	 * @param Object
	 *            o
	 * @return boolean
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof PersistentObject)) {
			return false;
		}
		PersistentObject other = (PersistentObject) o;
		return id.equals(other.getId());
	}

	/**
	 * 覆盖原toString方法，打印更详细信息
	 * 
	 * @return String
	 */
	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]";
	}
}