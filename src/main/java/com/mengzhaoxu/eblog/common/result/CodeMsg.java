package com.mengzhaoxu.eblog.common.result;

public class CodeMsg {

	private int status;
	private String msg;

	//通用的错误码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SUCCESS_REG = new CodeMsg(0, "注册成功");
	public static CodeMsg SUCCESS_LOG = new CodeMsg(0, "登录成功");

	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
	//登录模块 5002XX
	public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
	public static CodeMsg VERIFY_ERROR = new CodeMsg(500213, "验证码错误");
	public static CodeMsg USERNAME_EXIST = new CodeMsg(500214, "用户名已经被注册过");
	public static CodeMsg EMAIL_EXIST = new CodeMsg(500214, "邮箱已经被注册过");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "两次输入不一致");
	public static CodeMsg LOGIN_ERROR = new CodeMsg(500215, "账号或密码格式错误");
	public static CodeMsg USERNAME_ISNULL = new CodeMsg(500215, "昵称不能为空");
	public static CodeMsg USERAVATAR_ISNULL = new CodeMsg(500215, "頭像不能为空");

	//商品模块 5003XX
	public static CodeMsg COUNT_ISNULL = new CodeMsg(500301, "库存不足");



	//订单模块 5004XX

	//秒杀模块 5005XX
	public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500, "商品已经秒杀完毕");
	public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500501, "不能重复秒杀");





	private CodeMsg( ) {
	}

	private CodeMsg(int status, String msg ) {
		this.status = status;
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public CodeMsg fillArgs(Object... args) {
		int code = this.status;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}

	@Override
	public String toString() {
		return "CodeMsg [code=" + status + ", msg=" + msg + "]";
	}


}
