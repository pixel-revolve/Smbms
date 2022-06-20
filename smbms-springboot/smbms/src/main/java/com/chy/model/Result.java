package com.chy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    String msg;
    T data;
    Integer code;

    public static Result success() {
        Result result = new Result(ResultCode.SUCCESS);
        return result;
    }

    public static <T> Result success(T data){
        return new Result(ResultCode.SUCCESS,data);
    }

    public static <T> Result failure(String msg){
        return new Result(msg,null,9999);
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
        this.data = data;
    }


    public enum ResultCode{
        SUCCESS(200,"成功"),
        BAD_REQUEST(400,"BAD_REQUEST"),
        UNAUTHORIZED(401,"未认证"),
        NOT_FOUND(404,"未找到"),
        PWD_ERROR(300,"密码错误"),
        EXIST(403,"权限不足"),
        INTERNAL_SERVER_ERROR(500,"服务端错误"),
        SERVICE_UNAVAILABLE(503,"无服务"),
        ERROR(9999,"错误");

        private Integer code;

        private String message;


        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        ResultCode(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

    }


}
