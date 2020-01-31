package com.kevin.cloud.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * @program: vue-blog-backend
 * @description: 平台公共工具类
 * @author: kevin
 * @create: 2020-01-23 11:55
 **/
public class CommonUtils {

    /**
     * 将对象转成JsonObject 对象
     * @param o 转换对象
     * @param serializerFeature  默认值策略
     * @return 转换后数据
     *
     */
    public static JSONObject beanToJSONObject(Object o,  SerializerFeature serializerFeature) {
        return JSONObject.parseObject(JSON.toJSONString(o, serializerFeature));
    }

    /** 策略攻略
     * SerializerFeature.PrettyFormat:格式化输出
     * SerializerFeature.WriteMapNullValue:是否输出值为null的字段,默认为false
     * SerializerFeature.DisableCircularReferenceDetect:消除循环引用
     * SerializerFeature.WriteNullStringAsEmpty:将为null的字段值显示为""
     * WriteNullListAsEmpty：List字段如果为null,输出为[],而非null
     * WriteNullNumberAsZero：数值字段如果为null,输出为0,而非null
     * WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
     * SkipTransientField：如果是true，类中的Get方法对应的Field是transient，序列化时将会被忽略。默认为true
     * SortField：按字段名称排序后输出。默认为false
     * WriteDateUseDateFormat：全局修改日期格式,默认为false。JSON.DEFFAULT_DATE_FORMAT = “yyyy-MM-dd”;JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
     * BeanToArray：将对象转为array输出
     * QuoteFieldNames：输出key时是否使用双引号,默认为true
     * UseSingleQuotes：输出key时使用单引号而不是双引号,默认为false（经测试，这里的key是指所有的输出结果，而非key/value的key,而是key,和value都使用单引号或双引号输出）
     * @param o
     * @return
     */

    public static JSONObject beanToJSONObject(Object o) {
        return JSONObject.parseObject(JSON.toJSONString(o, SerializerFeature.WriteNullNumberAsZero));
    }


    /**
     * 获取当前系统时间 年月日时分秒
     */




}
