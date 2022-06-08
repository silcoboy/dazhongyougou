package com.pine.miniapi.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单状态
 */
public enum OrdStatusEnum {
	pay(1,"待支付"),
	init(2,"待发货"),
	ing(3,"待取货"),
	ok(4,"已提货");
	
	
	private int code;
	private String value;
	OrdStatusEnum(int code, String value){
		this.code = code;
		this.value = value; 
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
	public static  List<Map<String,Object>> getList(){
		List<Map<String,Object>> list=new ArrayList<>();
		for (OrdStatusEnum infoEnum : OrdStatusEnum.values()) {
			Map<String,Object> map=new HashMap<>();
			map.put("code", infoEnum.getCode());
			map.put("value", infoEnum.getValue());
			list.add(map);
	    }
		return list;
	}
	public static  List<Map<String,Object>> getAllList(){
		List<Map<String,Object>> list=new ArrayList<>();
		Map<String,Object> bxmap=new HashMap<>();
		bxmap.put("code", null);
		bxmap.put("value", "全部");
		list.add(bxmap);
		
		for (OrdStatusEnum infoEnum : OrdStatusEnum.values()) {
			Map<String,Object> map=new HashMap<>();
			map.put("code", infoEnum.getCode());
			map.put("value", infoEnum.getValue());
			list.add(map);
	    }
		return list;
	}
 
	public static OrdStatusEnum getText(int value) {
        for (OrdStatusEnum infoEnum : OrdStatusEnum.values()) {
            if (value == infoEnum.getCode()) {
                return infoEnum;
            }
        }
        return null;
    }
}
