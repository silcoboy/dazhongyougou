package com.pine.base.exception;

import com.pine.base.result.ResultJson;

import lombok.Getter;

/** 
 */
@Getter
public class CustomException extends RuntimeException{
    private ResultJson resultJson;

    public CustomException(ResultJson resultJson) {
        this.resultJson = resultJson;
    }
}