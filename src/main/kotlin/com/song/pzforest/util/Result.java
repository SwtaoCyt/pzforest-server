package com.song.pzforest.util;


import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Result extends LinkedHashMap<String, Object> implements Serializable {
    public static final  long serialVersionUID=1l;
    public static final int  CODE_SUCCESS= 200;
    public static final int  CODE_ERROR = 500;

    public Result(int code, String msg, Object data){
        this.setCode(code);
        this.setMsg(msg);
        this.setData(data);
}

    public Integer getCode(){ return  (Integer) this.get("code");}
    public String getMsg(){return  (String) this.get("msg");}
    public Object getData(){return  this.get("data");}

    public Result setCode(int code){
        this.put("code",code);
        return this;
    }

    public Result set(String key,Object data){
        this.put(key,data);
        return this;
    }

    public Result setData(Object data){
        this.put("data",data);
        return this;
    }

    public Result setMsg(String msg){
        this.put("msg",msg);
        return this;
    }

    public Result setMap(Map<String, ?> map) {
        Iterator var2 = map.keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            this.put(key, map.get(key));
        }

        return this;
    }

    public static Result ok() {
        return new Result(200, "ok", (Object)null);
    }

    public static Result ok(String msg) {
        return new Result(200, msg, (Object)null);
    }

    public static Result code(int code) {
        return new Result(code, (String)null, (Object)null);
    }

    public static Result data(Object data) {
        return new Result(200, "ok", data);
    }

    public static Result error() {
        return new Result(500, "error", (Object)null);
    }

    public static Result error(String msg) {
        return new Result(500, msg, (Object)null);
    }

    public static Result get(int code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public String toString() {
        return "{\"code\": " + this.getCode() + ", \"msg\": \"" + this.getMsg() + "\", \"data\": \"" + this.getData() + "\"}";
    }
}
