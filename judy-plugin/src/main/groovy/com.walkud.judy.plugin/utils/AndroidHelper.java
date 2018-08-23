package com.walkud.judy.plugin.utils;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.BaseExtension;
import com.android.build.gradle.LibraryExtension;
import com.android.build.gradle.api.BaseVariant;

import org.gradle.api.Action;
import org.gradle.api.Project;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 辅助工具类
 * Created by Zhuliya on 2018/8/10
 */
public class AndroidHelper {

    public static String getSeparator() {
        return "\\".equals(File.separator) ? "\\\\" : File.separator;
    }


    /**
     * 过滤Src下特定目录
     */
    public static boolean filterSrcDir(File file) {
        String reg = "^.*" + getSeparator() + "(androidTest|test|debug)$";
        String path = file.getPath();
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }

    /**
     * 过滤Src下资源文件目录
     *
     * @param file
     * @return
     */
    public static boolean filterResDir(File file) {
        String reg = "^.*src" + getSeparator() + "[a-zA-Z0-9]+" + getSeparator() + "res$";
        String path = file.getPath();
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }

    /**
     * 获取Android 扩展配置
     *
     * @param project
     * @return
     */
    public static BaseExtension getAndroidExtension(Project project) {
        return (BaseExtension) project.getExtensions().getByName("android");
    }

    /**
     * 是否为Android Library模块
     *
     * @param project
     * @return
     */
    public static boolean hasLibraryModule(Project project) {
        BaseExtension extension = getAndroidExtension(project);
        return extension instanceof LibraryExtension;
    }

    /**
     * 遍历指定Android变种
     *
     * @param project
     * @param action
     */
    public static void forEachVariant(Project project, Action<BaseVariant> action) {
        BaseExtension extension = getAndroidExtension(project);
        if (extension instanceof AppExtension) {
            ((AppExtension) extension).getApplicationVariants().all(action);
        } else if (extension instanceof LibraryExtension) {
            ((LibraryExtension) extension).getLibraryVariants().all(action);
        }
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String captureChar(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);

    }
}
