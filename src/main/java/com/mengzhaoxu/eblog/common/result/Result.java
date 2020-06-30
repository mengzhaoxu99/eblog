package com.mengzhaoxu.eblog.common.result;


import lombok.Data;

@Data
public class Result<T> {

	private int status;
	private String msg;
	private T data;
	private String action;

	/**
	 *  成功时候的调用
	 * */

	public static  <T> Result<T> success(T data){
		return new Result<T>(data);
	}

	public static  <T> Result<T> success(CodeMsg codeMsg){
		return new Result<T>(codeMsg);
	}

	/**
	 *  失败时候的调用
	 * */
	public static  <T> Result<T> error(CodeMsg codeMsg){
		return new Result<T>(codeMsg);
	}
	public static  <T> Result<T> error(String errMsg){
		return new Result<T>(-1,errMsg);
	}


	private Result(T data) {
		this.data = data;
	}

	private Result(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	private Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.status = codeMsg.getStatus();
			this.msg = codeMsg.getMsg();
		}
	}

	public Result action(String action){
		this.action=action;
		return this;
	}


//	public int getStatus() {
//		return status;
//	}
//	public void setStatus(int status) {
//		this.status = status;
//	}
//	public String getMsg() {
//		return msg;
//	}
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
//	public T getData() {
//		return data;
//	}
//	public void setData(T data) {
//		this.data = data;
//	}
}
