package com.zly.judy.api.annontations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bridge目标Class注解
 * 该注解无需手动配置，由judy-plugin插件生成中间层接口时自动配置
 * Created by Zhuliya on 2018/7/2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetClass {

    /**
     * 目标Class全路径名(如：com.zly.judy.annontations.TargetClass)
     *
     * @return 返回目标Class全路径名字符串
     */
    String value() default "";

}
