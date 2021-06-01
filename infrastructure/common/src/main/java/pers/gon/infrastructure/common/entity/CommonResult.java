package pers.gon.infrastructure.common.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult<T> {


    private static final int DEFAULT_FAIL_CODE = 1;
    private static final int DEFAULT_OK_CODE = 0;
    private static final String DEFAULT_OK_MSG = "请求成功";
    private static final String DEFAULT_FAIL_MSG = "请求失败";
    private String msg;
    private int code;
    private boolean success;
    T data;

    public static <T> CommonResult ok(){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(DEFAULT_OK_CODE);
        commonResult.setMsg(DEFAULT_OK_MSG);
        commonResult.setSuccess(true);
        return commonResult;
    }

    public static <T> CommonResult ok(T data){
        CommonResult commonResult = new CommonResult();
        commonResult.setData(data);
        commonResult.setCode(DEFAULT_OK_CODE);
        commonResult.setMsg(DEFAULT_OK_MSG);
        commonResult.setSuccess(true);
        return commonResult;
    }

    public static <T>CommonResult ok(String msg,T data){
        CommonResult commonResult = new CommonResult();
        commonResult.setData(data);
        commonResult.setMsg(msg);
        commonResult.setCode(DEFAULT_OK_CODE);
        commonResult.setSuccess(true);
        return commonResult;
    }

    public static CommonResult fail(){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(DEFAULT_FAIL_CODE);
        commonResult.setMsg(DEFAULT_FAIL_MSG);
        commonResult.setSuccess(false);
        return commonResult;
    }

    public static CommonResult fail(String msg){
        CommonResult commonResult = new CommonResult();
        commonResult.setMsg(msg);
        commonResult.setCode(DEFAULT_FAIL_CODE);
        commonResult.setSuccess(false);
        return commonResult;
    }


    public static CommonResult fail(String msg,int code){
        CommonResult commonResult = new CommonResult();
        commonResult.setMsg(msg);
        commonResult.setCode(code);
        commonResult.setSuccess(false);
        return commonResult;
    }

    public static <T> CommonResult build(String msg,int code,boolean success,T data){
        CommonResult commonResult = new CommonResult();
        commonResult.setData(data);
        commonResult.setMsg(msg);
        commonResult.setCode(code);
        commonResult.setSuccess(success);
        return commonResult;
    }

}
