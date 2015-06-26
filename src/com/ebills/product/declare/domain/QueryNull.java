package com.ebills.product.declare.domain;

import com.ebills.product.declare.Entity;


/**
 * 校验形成申报的数据是否为空
 * @author Hudan
 *
 */
public class QueryNull extends Entity {
	private int count ;
	private String nguid;
	private String wzbh;
	private String xType;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getNguid() {
		return nguid;
	}
	public void setNguid(String nguid) {
		this.nguid = nguid;
	}
	public String getWzbh() {
		return wzbh;
	}
	public void setWzbh(String wzbh) {
		this.wzbh = wzbh;
	}
	public String getxType() {
		return xType;
	}
	public void setxType(String xType) {
		this.xType = xType;
	}
}
