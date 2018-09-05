package com.walkud.judy.plugin.parse;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.walkud.judy.plugin.ConfigExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 中间层文件的解析、生成接口
 * Created by Zhuliya on 2018/9/3
 */
public abstract class JudyParse {

    protected File sourceFile;//java源文件
    protected ConfigExtension configExtension;//JudyBridge配置
    protected CompilationUnit compilationUnit;
    protected TypeDeclaration<?> typeDeclaration;//类型声明

    public JudyParse(File sourceFile, ConfigExtension configExtension) throws IOException {
        this.sourceFile = sourceFile;
        this.configExtension = configExtension;

        FileInputStream in = new FileInputStream(sourceFile);
        // 解析Java源文件
        compilationUnit = JavaParser.parse(in);
    }

    /**
     * 解析JudyBridge注解源文件
     */
    public abstract void parse() throws IOException;

    /**
     * 生成中间层源码java文件
     *
     * @param file 生成中间层源码文件目录
     */
    public abstract void writeTo(File file) throws IOException;

    /**
     * 检查是否符合JudyBridge条件
     *
     * @return
     */
    /**
     * 检查是否符合JudyBridge条件
     *
     * @return
     */
    public boolean checkValid(String annotationName) {
        NodeList<TypeDeclaration<?>> types = compilationUnit.getTypes();
        for (TypeDeclaration<?> type : types) {
            for (Modifier modifier : type.getModifiers()) {
                if (Modifier.PUBLIC.equals(modifier)
                        && type.getAnnotationByName(annotationName).isPresent()) {
                    //类型声明中包含public && 包含JudyBridge注解
                    this.typeDeclaration = type;
                    return true;
                }
            }
        }
        return false;
    }

}
