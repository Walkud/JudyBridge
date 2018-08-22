package com.walkud.judy.annontations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Api目标Class注解
 * Created by Zhuliya on 2018/7/2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface TargetClass {

    /**
     * 目标Class全路径名(如：com.walkud.judy.annontations.TargetClass)
     *
     * @return
     */
    String value() default "";

}
