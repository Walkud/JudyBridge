package com.walkud.judy.plugin.model;


import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.walkud.judy.plugin.ConfigExtension;
import com.walkud.judy.plugin.utils.TypeUtil;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Modifier;

/**
 * 模块注解解析与抽象类生成
 * 主要功能如下：
 * 1、解析JudyBridge java源文件
 * 2、根据解析信息，构建对应抽象类内容生成对象(TypeSpec)
 * 3、在指定路径下生成抽象类文件
 * Created by Zhuliya on 2018/7/6
 */
public class ModuleParse {

    private TypeSpec typeSpec;
    private HashMap<String, ClassName> importTypeReg = new HashMap<>();

    public ModuleParse(CompilationUnit cu, ConfigExtension configExtension) {
        //获取类名
        List types = cu.types();

        parseImportType(cu);

        //构建全类名
        TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
        String className = cu.getPackage().getName() + "." + typeDec.getName();

        //构建类注解信息
        AnnotationSpec annotationSpec = AnnotationSpec.builder(TypeUtil.TARGET_CLASS)
                .addMember("value", "$S", className)
                .build();

        //构建类信息
        TypeSpec.Builder typeSpecBuilder = TypeSpec.interfaceBuilder(typeDec.getName() + configExtension.getProxyFileSuffix())
                .addAnnotation(annotationSpec)
                .addModifiers(Modifier.PUBLIC);

        for (MethodDeclaration methodDeclaration : typeDec.getMethods()) {

            //创建方法Spec
            MethodSpec.Builder methodBuilder = createMethodSpec(methodDeclaration);

            //构建参数
            for (Object o : methodDeclaration.parameters()) {
                SingleVariableDeclaration dec = (SingleVariableDeclaration) o;
                String typeStr = dec.getType().toString();
                TypeName typeName = getTypeName(typeStr, false);

                ParameterSpec spec = ParameterSpec.builder(typeName, dec.getName().toString())
                        .build();
                methodBuilder.addParameter(spec);
            }

            typeSpecBuilder.addMethod(methodBuilder.build());

        }


        typeSpec = typeSpecBuilder.build();
    }

    /**
     * 根据import类型生成对应的参数类型，由于eclipse-astparser无法获取参数类型ClassName,所以使用一下方法生成
     *
     * @param result
     */
    private void parseImportType(CompilationUnit result) {
        List imports = result.imports();
        for (Object anImport : imports) {
            if (anImport instanceof ImportDeclaration) {
                //遍历所有导入类，根据类型名称生成对应的ClassName
                ImportDeclaration astNode = (ImportDeclaration) anImport;
                String fqName = astNode.getName().getFullyQualifiedName();
                int index = fqName.lastIndexOf(".");
                if (index != -1) {
                    String typeReg = fqName.substring(index + 1);
                    importTypeReg.put(typeReg, ClassName.bestGuess(fqName));
                }
            }
        }
    }

    /**
     * 根据类型名称获取类型TypeName对象
     *
     * @param typeStr   类型名称
     * @param isConvert 是否将基本类型装箱为引用类型
     * @return
     */
    private TypeName getTypeName(String typeStr, boolean isConvert) {
        //获取导入类型的TypeName
        TypeName typeName = importTypeReg.get(typeStr);

        if (typeName == null) {
            //导入类型未找到,使用类型名称生成TypeName
            if (isConvert) {
                //需要装箱转换(可用于泛型)
                typeName = TypeUtil.convertTypeName(typeStr);
            } else {
                //直接使用类型名称生成
                typeName = ClassName.get("", typeStr);
            }

        }
        return typeName;
    }


    /**
     * 创建方法Spec 返回值、修饰符、方法名称
     *
     * @param methodDeclaration
     * @return
     */
    private MethodSpec.Builder createMethodSpec(MethodDeclaration methodDeclaration) {

        String returnTypeName = methodDeclaration.getReturnType2().toString();
        //生成泛型返回类型
        TypeName typeName = getTypeName(returnTypeName, true);
        //暂时不需要再包一层，增加开发工作量且意义不大，如果需要，开发者可自行添加
//        TypeName retrunType = ParameterizedTypeName.get(TypeUtil.JUDY_RESULT_CLASS, typeName);

        //构建方法返回值、修饰符、方法名称
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodDeclaration.getName().toString())
                .returns(typeName)//返回类型
                .addModifiers(Modifier.PUBLIC)//设置为公开
                .addModifiers(Modifier.ABSTRACT);//设置为抽象

        return methodBuilder;
    }

    /**
     * 生成文件
     *
     * @param file        文件
     * @param packageName 包名
     * @throws IOException
     */
    public void writeTo(File file, String packageName) throws IOException {
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
        javaFile.writeTo(file);
    }
}
