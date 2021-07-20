package pers.gon.infrastructure.common.entity;


import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.crypto.hash.Hash;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CommonResult<T>  extends HashMap {


    private static final int DEFAULT_FAIL_CODE = 1;
    private static final int DEFAULT_OK_CODE = 0;
    private static final String DEFAULT_OK_MSG = "请求成功";
    private static final String DEFAULT_FAIL_MSG = "请求失败";
    private String msg;
    private int code;
    private boolean success;
    T data;

    public <T> CommonResult add(Object k,Object v){
        this.put(k,v);
        return this;
    }

    public static <T> CommonResult ok(){
        CommonResult commonResult = new CommonResult();
        commonResult.put("code",DEFAULT_OK_CODE);
        commonResult.put("msg",DEFAULT_OK_MSG);
        commonResult.put("success",true);
        return commonResult;
    }

    public static <T> CommonResult ok(T data){
        CommonResult commonResult = new CommonResult();
        commonResult.put("code",DEFAULT_OK_CODE);
        commonResult.put("msg",DEFAULT_OK_MSG);
        commonResult.put("success",true);
        commonResult.put("data",data);
        return commonResult;
    }

    public static <T>CommonResult ok(String msg,T data){
        CommonResult commonResult = new CommonResult();
        commonResult.put("code",DEFAULT_OK_CODE);
        commonResult.put("msg",msg);
        commonResult.put("success",true);
        commonResult.put("data",data);
        return commonResult;
    }

    public static CommonResult fail(){
        CommonResult commonResult = new CommonResult();
        commonResult.put("code",DEFAULT_FAIL_CODE);
        commonResult.put("msg",DEFAULT_FAIL_MSG);
        commonResult.put("success",false);
        return commonResult;
    }

    public static CommonResult fail(String msg){
        CommonResult commonResult = new CommonResult();
        commonResult.put("code",DEFAULT_FAIL_CODE);
        commonResult.put("msg",msg);
        commonResult.put("success",false);
        return commonResult;
    }


    public static CommonResult fail(String msg,int code){
        CommonResult commonResult = new CommonResult();
        commonResult.put("code",code);
        commonResult.put("msg",msg);
        commonResult.put("success",false);
        return commonResult;
    }

    public static <T> CommonResult build(String msg,int code,boolean success,T data){
        CommonResult commonResult = new CommonResult();
        commonResult.put("code",code);
        commonResult.put("msg",msg);
        commonResult.put("success",success);
        commonResult.put("data",data);
        return commonResult;
    }

}
