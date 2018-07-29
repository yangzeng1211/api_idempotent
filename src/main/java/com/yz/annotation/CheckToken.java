package com.yz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解决接口幂等性问题,支持网络延迟和表单提交
 * Created by yz on 2018/7/29.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckToken {
    // 区分请求来源
    String type();
}
