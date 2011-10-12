package com.magicpwd._docs;

import java.util.Map;

/**
 * 一个标准的VDisk响应
 * 
 * <p/>如果发现任何bug或有任何意见建议,请发送邮件到 vdisk4j@wendal.net
 * <p/>项目主页: http://vdisk4j.googlecode.com
 * <p/>
 * <p/>我的博客: http://wendal.net
 * @author wendal(vdisk4j@wendal.net)
 */
public class VDiskResponse {

	/**
	 * 响应代码,除非等于0(Success),否则都是出错
	 */
	private int err_code = Integer.MAX_VALUE;
	
	/**
	 * 出错信息,有时候会是中文呢
	 */
	private String err_msg;
	
	/**
	 * 服务器当前的dologid
	 */
	private long dologid;
	
	/**
	 * 如果客户端发送的dologid已经过期,则这个数组的长度应该大于0
	 */
	private long[] dologdir;
	
	/**
	 * 响应所携带的信息
	 */
	private Map<String, Object> data;

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public int getErr_code() {
		return err_code;
	}

	public void setErr_code(int err_code) {
		this.err_code = err_code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public long getDologid() {
		return dologid;
	}

	public void setDologid(long dologid) {
		this.dologid = dologid;
	}

	public long[] getDologdir() {
		return dologdir;
	}

	public void setDologdir(long[] dologdir) {
		this.dologdir = dologdir;
	}
}
