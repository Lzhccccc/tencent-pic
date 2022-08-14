package com.lzh.demo.model;


import com.lzh.demo.exception.BaseJSONException;
import com.lzh.demo.exception.ErrorCodes;

/**
 * Created by xie.gc on 14-9-9.
 */
public class Response<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 构建成功结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(T data){
        Response<T> result = new Response<>();
        result.setData(data);
        result.setCode("1000");
        result.setSuccess(true);
        return result;
    }
    public static <T> Response<T> success(T data,String msg){
        Response<T> result = new Response<>();
        result.setData(data);
        result.setCode("1000");
        result.setSuccess(true);
        result.setMessage(msg);
        return result;
    }
    public static <T> Response<T> error(String errorCode, String error){
        Response<T> result = new Response<>();
        result.setSuccess(false);
        result.setCode(errorCode);
        result.setMessage(error);
        return  result;
    }

    public static <T> Response<T> error(BaseJSONException e){
        Response<T> result = new Response<>();
        result.setSuccess(false);
        result.setCode(Integer.toString(e.getError().getCode()));
        result.setMessage(e.getMessage());
        return  result;
    }
    public static <T> Response<T> error(ErrorCodes errorCodes, String...replaces){
        Response<T> result = new Response<>();
        result.setSuccess(false);
        result.setCode(Integer.toString(errorCodes.getCode()));
        String msg = Response.assembleMsg(errorCodes, replaces);
        result.setMessage(msg);
        return  result;
    }

    public static String assembleMsg(ErrorCodes errorCodes,String...replaces){
        String msg = errorCodes.getMsg();
        if(replaces != null && replaces.length > 0){
            msg = String.format(msg, replaces);
        }
        return msg;
    }


    /**
     * 设置成功消息，并不返回任何值
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(){
        Response<T> result = new Response<>();
        result.setSuccess(true);
        result.setCode("1000");
        return result;
    }
}