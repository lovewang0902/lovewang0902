package com.mybatis.example.domain;

import java.util.Date;

public class WaybillNo {
	
	private Integer id;
	private String waybillNo;
	public int count;
	public String waybillFormat;
	public String label;
	public Date createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getWaybillFormat() {
		return waybillFormat;
	}
	public void setWaybillFormat(String waybillFormat) {
		this.waybillFormat = waybillFormat;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}