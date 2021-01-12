/*
 * 项目名称:gdjs-plus
 * 类名称:DataSource.java
 * 包名称:com.gdjs.datasources.annotation
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    GDJS      初版完成
 *
 * Copyright (c) 2019-2019 GDJS
 */
package com.kingdee.datasources.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 *
 * @author GDJS
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default "";
}
