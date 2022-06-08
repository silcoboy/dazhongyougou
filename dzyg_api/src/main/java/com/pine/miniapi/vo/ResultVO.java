package com.pine.miniapi.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.pine.miniapi.enums.ApiMessageCodeEnums;

/** 
 */
public class ResultVO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 是否成功 */
	private boolean success;

	/** 编码 */
	private Integer code;

	/** 消息 */
	private String msg;

	/** 服务时间 */
	private long serverTime;

	/** 数据 */
	private T data;

	/** 额外数据 */
	private Map<String, Object> extra;

	
	public ResultVO(){
		this.success(null);
	}
	
	public ResultVO<T> addExtraIfTrue(boolean bool, String key, Object value) {
		if (bool) {
			addExtra(key, value);
		}
		return this;
	}

	public ResultVO<T> addExtra(String key, Object value) {
		extra = extra == null ? new HashMap<>(16) : extra;
		extra.put(key, value);
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public ResultVO<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public ResultVO<T> setCode(Integer code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public ResultVO<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public ResultVO<T> setData(T data) {
		this.data = data;
		return this;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public ResultVO<T> setExtra(Map<String, Object> extra) {
		this.extra = extra;
		return this;
	}

	public ResultVO<T> success() {
		return this.success(null);
	}

	public ResultVO<T> failure(ApiMessageCodeEnums resultCodeEnum) {
		return this.setSuccess(false).setServerTime(System.currentTimeMillis()).setCode(resultCodeEnum.getCode())
				.setMsg(resultCodeEnum.getMessage());
	}

	public ResultVO<T> failure(String msg) {
		return this.setSuccess(false).setServerTime(System.currentTimeMillis())
				.setCode(ApiMessageCodeEnums.BUSSINESS_EXCEPTOIN.getCode()).setMsg(msg);
	}

	public ResultVO<T> success(T obj) {
		return this
				// 为null统一给前端{}
				.setData(obj).setSuccess(true).setServerTime(System.currentTimeMillis())
				.setCode(ApiMessageCodeEnums.NO_ERROR.getCode()).setMsg(ApiMessageCodeEnums.NO_ERROR.getMessage());
	}

	public long getServerTime() {
		return serverTime;
	}

	public ResultVO<T> setServerTime(long serverTime) {
		this.serverTime = serverTime;
		return this;
	}

}
