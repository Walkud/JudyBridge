package com.zly.judy.plugin.utils;

import com.squareup.javapoet.ClassName;

/**
 * 类型 ClassName 工具类
 * Created by Zhuliya on 2018/8/10
 */
public class TypeUtil {

    /**
     * 生成代理接口的被中间层接口的目标注解类型，详见judy-api
     */
    public static final ClassName TARGET_CLASS = ClassName.get("com.zly.judy.api.annontations", "TargetClass");
    /**
     * 扫描服务类标识注解类型，详见judy-api
     */
    public static final ClassName JUDY_BRIDGE = ClassName.get("com.zly.judy.api.annontations", "JudyBridge");
    /**
     * 保持源码接口类型,详见judy-api
     */
    public static final ClassName KEEP_SOURCE = ClassName.get("com.zly.judy.api", "KeepSource");

    /**
     * 判断是否为有效Type
     *
     * @param typeSet 有效类型
     * @param name    导入类型
     * @return 返回导入的类型是否有效，true：有效，false：无效
     */
    public static boolean isValidType(String typeSet, String name) {
        int index = typeSet.indexOf("<");
        if (index > -1) {
            //包含泛型，递归处理泛型
            String type = typeSet.substring(0, index);
            if (isVaild(type, name)) {
                //如果类型匹配，则直接返回，无需再处理后续泛型
                return true;
            } else {

                type = typeSet.substring(index + 1, typeSet.length() - 1);

                if (!type.contains("<") && type.contains("?")) {
                    //去掉通配符
                    type = releaseWildcard(type);
                }

                //递归
                return isValidType(type, name);
            }
        }

        return isVaild(typeSet, name);
    }

    /**
     * 验证类型是否匹配
     * <p>
     * 判断：有效类型包含 . 则全匹配或截取再后缀匹配 否则直接后缀匹配
     *
     * @param typeSet 有效类型
     * @param name    导入类型
     * @return
     */
    private static boolean isVaild(String typeSet, String name) {
        if (typeSet.contains(".")) {
            String prefixType = typeSet.substring(0, typeSet.lastIndexOf("."));

            return name.equals(typeSet) || name.endsWith("." + prefixType);
        }

        return name.endsWith("." + typeSet);
    }

    /**
     * 去掉通配符
     *
     * @param type
     * @return
     */
    private static String releaseWildcard(String type) {
        return type.replaceAll("\\?", "")
                .replaceAll(" extends ", "")
                .replaceAll(" super ", "")
                .trim();
    }

}
