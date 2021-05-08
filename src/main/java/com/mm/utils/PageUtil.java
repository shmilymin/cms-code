package com.mm.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author lwl
 */
public class PageUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 响应状态
	 */
	private int code = 0;
	/**
	 * 每页记录数
	 */
	private String msg = "";
	/**
	 * 总页数
	 */
	private long count;
	/**
	 * 列表数据
	 */
	private List<?> data;

	public PageUtil(long count, List<?> data) {
		this.count = count;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
}
