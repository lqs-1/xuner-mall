package com.lqs.mall.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.to.es.SkuEsModelTo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李奇凇
 * @date 2022年07月30日 下午10:38
 * @do 响应工具类
 */
public class  R extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    // 获取指定类型的数据
    public <T> T parseType(TypeReference<T> type, String paramName){
        // 从map中获取对应key的值，直接获取是一个Object
        Object originObject = this.get(paramName);
        // 将这个对象转换为字符串
        String objetString = JSON.toJSONString(originObject);
        // 在将字符串转换为指定对象
        T resultObject = JSON.parseObject(objetString, type);
        // 返回结果对象
        return resultObject;

    }

    public <T> T parseType(TypeReference<T> type){

        return this.parseType(type, Constant.RESPONSE_DATA_KEY);
    }

    public Integer parseCode(){
        return this.parseType(new TypeReference<Integer>(){}, Constant.RESPONSE_CODE_KEY);
    }

    public String parseMsg(){
        return this.parseType(new TypeReference<String>(){}, Constant.RESPONSE_MSG_KEY);
    }






    public R() {
        put("code", REnum.SUCCESS.getStatusCode());
        put("msg", REnum.SUCCESS.getStatusMsg());
    }


    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error() {
        R r = new R();
        r.put("code", REnum.FAIL.getStatusCode());
        r.put("msg", REnum.FAIL.getStatusMsg());
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.put("code", REnum.FAIL.getStatusCode());
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Integer code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }


    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R put(Object value){
        super.put("data", value);
        return this;
    }


    public static void main(String[] args) {
        SkuEsModelTo.Attr attr = new SkuEsModelTo.Attr();
        attr.setAttrId(1L);
        attr.setAttrValue("haha");
        attr.setAttrName("lqs");

        R ok = R.error().put("data", attr);

        R okk = new R();
        okk.put("data", "haha");

        System.out.println(ok.parseType(new TypeReference<SkuEsModelTo.Attr>(){}));
        System.out.println(ok.parseCode());
        System.out.println(ok.parseMsg());
    }
}
