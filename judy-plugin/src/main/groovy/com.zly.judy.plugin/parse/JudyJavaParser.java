package com.zly.judy.plugin.parse;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.zly.judy.plugin.ConfigExtension;
import com.zly.judy.plugin.utils.TypeUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 * 使用https://github.com/javaparser/javaparser库进行java源文件解析
 * Created by Zhuliya on 2018/9/3
 */
public class JudyJavaParser extends JudyParse {

    /**
     * 有效的类型集合，用于判断是否移除无效的导入类型
     */
    private Set<String> typeSets = new HashSet<>();

    public JudyJavaParser(File file, ConfigExtension configExtension) throws IOException {
        super(file, configExtension);
        typeSets.add(TypeUtil.TARGET_CLASS.reflectionName());
    }

    @Override
    public void parse() {

        //处理注解
        parseAnnotation();

        //处理Body声明
        parseBodyDeclaration();

        //将类设置为接口
        ((ClassOrInterfaceDeclaration) typeDeclaration).setInterface(true);
        //修改类名称
        typeDeclaration.setName(typeDeclaration.getNameAsString() + configExtension.getProxyFileSuffix());

        //处理包声明
        parsePackageDeclaration();

        //清理无效的导入声明
        clearImportDeclaration();
    }

    /**
     * 处理类注解
     */
    private void parseAnnotation() {
        //修改注解
        ImportDeclaration importDeclaration = new ImportDeclaration(TypeUtil.TARGET_CLASS.reflectionName(), false, false);
        compilationUnit.getImports().add(importDeclaration);
        NodeList<AnnotationExpr> annotationExprs = typeDeclaration.getAnnotations();
        //清空所有无用注解
        annotationExprs.clear();

        //构建目标注解参数
        String typeName = typeDeclaration.getNameAsString();
        Optional<PackageDeclaration> pdOpt = compilationUnit.getPackageDeclaration();
        if (pdOpt.isPresent()) {
            //如果源文件存在包声明，则替换为配置包声明
            PackageDeclaration packageDeclaration = pdOpt.get();
            //构建类全名
            typeName = packageDeclaration.getNameAsString() + "." + typeDeclaration.getNameAsString();
        }

        //添加目标类注解
        Name smaName = new Name(TypeUtil.TARGET_CLASS.simpleName());
        SingleMemberAnnotationExpr singleMemberAnnotationExpr = new SingleMemberAnnotationExpr(smaName, new StringLiteralExpr(typeName));
        annotationExprs.add(singleMemberAnnotationExpr);
    }

    /**
     * 处理Body声明(属性、方法)
     */
    private void parseBodyDeclaration() {
        //遍历所有Body声明
        NodeList<BodyDeclaration<?>> nodeList = typeDeclaration.getMembers();
        Iterator<BodyDeclaration<?>> iterator = nodeList.iterator();
        while (iterator.hasNext()) {
            BodyDeclaration<?> bodyDeclaration = iterator.next();
            if (bodyDeclaration instanceof MethodDeclaration) {
                MethodDeclaration methodDeclaration = (MethodDeclaration) bodyDeclaration;
                EnumSet<Modifier> enumSet = methodDeclaration.getModifiers();

                if (enumSet.contains(Modifier.PUBLIC) && !enumSet.contains(Modifier.STATIC)) {
                    //处理公开非静态方法
                    parseMethodDeclaration(methodDeclaration);
                } else {
                    //移除无用方法
                    iterator.remove();
                }
            } else {
                //非方法则移除
                iterator.remove();
            }
        }
    }

    /**
     * 处理方法
     *
     * @param methodDeclaration
     */
    private void parseMethodDeclaration(MethodDeclaration methodDeclaration) {

        //记录参数类型
        NodeList<Parameter> nodeList = methodDeclaration.getParameters();
        for (Parameter parameter : nodeList) {
            typeSets.add(parameter.getTypeAsString());
        }

        //记录返回类型
        typeSets.add(methodDeclaration.getTypeAsString());

        methodDeclaration.getModifiers().clear();
        methodDeclaration.removeBody();
    }

    /**
     * 处理包声明
     */
    private void parsePackageDeclaration() {
        Name packageName = new Name(configExtension.getPackageName());
        PackageDeclaration packageDeclaration = compilationUnit.getPackageDeclaration().orElse(new PackageDeclaration());
        packageDeclaration.setName(packageName);
        //修改包名
        compilationUnit.setPackageDeclaration(packageDeclaration);
    }

    /**
     * 清理无效的导入声明
     */
    private void clearImportDeclaration() {
        NodeList<ImportDeclaration> nodeList = compilationUnit.getImports();
        Iterator<ImportDeclaration> iterator = nodeList.iterator();

        while (iterator.hasNext()) {
            ImportDeclaration importDeclaration = iterator.next();

            if (importDeclaration.isStatic() || importDeclaration.isAsterisk()) {
                //移除静态导入和星号导入
                iterator.remove();
            } else {
                String name = importDeclaration.getNameAsString();

                if (!isVaildType(name)) {
                    //移除无效导入声明
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 检查是否属于有效类型
     *
     * @param name
     * @return
     */
    private boolean isVaildType(String name) {

        for (String typeSet : typeSets) {
            if (TypeUtil.isValidType(typeSet, name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 生成文件
     *
     * @param file 生成中间层源码文件目录
     */
    @Override
    public void writeTo(File file) {
        //构建生成文件路径
        String packagePath = configExtension.getPackageName().replace("\\.", File.separator);//包路径
        String fileName = typeDeclaration.getNameAsString() + ".java";//文件名称
        Path path = Paths.get(file.getAbsolutePath(), packagePath, fileName);
        //生成代理接口
        compilationUnit.setStorage(path);
        compilationUnit.getStorage().get().save();
    }
}
