package com.walkud.judy.plugin.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

/**
 * 类型 ClassName 工具类
 * Created by Zhuliya on 2018/8/10
 */
public class TypeUtil {

    /**
     * 生成代理接口的被抽象类的目标注解类型
     */
    public static final ClassName TARGET_CLASS = ClassName.get("com.walkud.judy.annontations", "TargetClass");
    /**
     * 生成代理接口的方法 Return 返回类型
     */
    public static final ClassName JUDY_RESULT_CLASS = ClassName.get("com.walkud.judy.api", "JudyResult");

    /**
     * 根据类型名称转换为对应的TypeName对象
     *
     * @param typeName
     * @return
     */
    public static TypeName convertTypeName(String typeName) {
        if ("void".equals(typeName)) {
            return TypeName.VOID.box();
        } else if ("boolean".equals(typeName)) {
            return TypeName.BOOLEAN.box();
        } else if ("byte".equals(typeName)) {
            return TypeName.BYTE.box();
        } else if ("short".equals(typeName)) {
            return TypeName.SHORT.box();
        } else if ("int".equals(typeName)) {
            return TypeName.INT.box();
        } else if ("long".equals(typeName)) {
            return TypeName.LONG.box();
        } else if ("char".equals(typeName)) {
            return TypeName.CHAR.box();
        } else if ("float".equals(typeName)) {
            return TypeName.FLOAT.box();
        } else if ("double".equals(typeName)) {
            return TypeName.DOUBLE.box();
        }
        return ClassName.get("", typeName);
    }

}
