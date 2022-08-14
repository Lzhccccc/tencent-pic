package com.lzh.demo.exception;

/**
 * 与GlobalExceptionHandler配合  返回Response
 * BaseJSONException   前端收到此response 会  进行弹窗报错
 * HttpCode 固定为400
 *
 * Response的值
 *      -boolean success;     固定为false
 *      -String code;         为BaseJSONException中的错误码
 *      -String message;      为BaseJSONException中的错误信息
 *      -T data;              为null
 */
public class BaseJSONException extends RuntimeException {
    private ErrorCodes error;
    private String[] replaces;

    public BaseJSONException(ErrorCodes error, String... replaces) {
        this.error = error;
        this.replaces = replaces;
    }

    public BaseJSONException(ErrorCodes error) {
        this.error = error;
    }

    public String[] getReplaces() {
        return this.replaces;
    }

    public ErrorCodes getError() {
        return this.error;
    }

    @Override
    public String getMessage() {
        String msg = error.getMsg();
        if(replaces != null && replaces.length > 0){
            msg = String.format(msg, replaces);
        }
        return msg;
    }
}