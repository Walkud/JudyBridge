package com.walkud.judy.plugin

import com.walkud.judy.plugin.model.ModuleParse
import org.eclipse.jdt.core.dom.*
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

/**
 * 代码解析与生成代理类源文件的任务
 * Created by Zhuliya on 2018/8/10
 */
class GeneratorTask extends DefaultTask {

    def packageName
    def outDir
    def packageId
    ConfigExtension configExtension
    def fileSuffix//自定义过滤后缀规则

    GeneratorTask() {
        outputs.upToDateWhen { false }

        configExtension = (ConfigExtension) project.extensions.getByName("judyConfig")

        if (configExtension.parseFileSuffix) {
            //判断是否存在自定义过滤规则
            fileSuffix = configExtension.parseFileSuffix + ".java"
        }
    }

    @TaskAction
    def run() {
        //获取传入的属性包名
        packageName = inputs.getProperties().'package'
        packageId = "${packageName}.judy"
        //创建输出目录,如果存在则删除重新创建
        outDir = outputs.files.singleFile

        MLog.d("输出路径:${outDir.getAbsolutePath()}")

        outDir.deleteDir()
        outDir.mkdirs()

        //遍历输入文件
        Set<File> inputDirs = getInputPaths(project)
        inputDirs.each {
            eachInputFile(it)
        }
    }

    /**
     * 递归遍历输入文件
     * @param file
     * @return
     */
    def eachInputFile(File file) {

        if (AndroidHelper.filterResDir(file)) {
            //过滤资源文件目录
            return
        }

        file.listFiles().each {
            if (it.isDirectory()) {
                eachInputFile(it)
            } else if (it.file && isVaildFile(it)) {
                //AST分析
                def parser = ASTParser.newParser(AST.JLS8) as ASTParser //initialize
                parser.setKind(ASTParser.K_COMPILATION_UNIT)     //to parse compilation unit
                parser.setSource(it.text.toCharArray())
                //content is a string which stores the java source
                parser.setResolveBindings(true)
                CompilationUnit result = (CompilationUnit) parser.createAST(null)
                //获取类名
                List types = result.types()

                if (types.isEmpty()) {
                    return
                }

                //获取注解信息
                TypeDeclaration typeDec = (TypeDeclaration) types.get(0)
                List modifiers = typeDec.modifiers()

                modifiers.each { modifier ->
                    if (modifier instanceof MarkerAnnotation) {
                        //判断指定注解
                        MarkerAnnotation annotation = (MarkerAnnotation) modifier
                        if ("ModuleApi" == annotation.typeName.toString()) {
                            ModuleParse moduleParse = new ModuleParse(result, configExtension)
                            moduleParse.writeTo(outDir, packageId)
                        }
                    }
                }


            }
        }

    }

    /**
     * 判断是否过滤该文件
     * @param file
     * @return
     */
    def isVaildFile(File file) {
        if (fileSuffix) {//判断自定义规则
            return file.name.endsWith(fileSuffix)
        }
        return file.name.endsWith("java")
    }

    /**
     * 获取注解java文件解析路径
     * @param project
     * @return
     */
    Set<File> getInputPaths(Project project) {
        Set<File> inputDirs = new HashSet<>()

        project.rootProject.subprojects.each { pt ->

            if (pt.name == project.name) {
                //过滤掉公共库模块
                return
            }

            String path = pt.projectDir.getAbsolutePath()
            def absolutePackageDir = "${path}${File.separator}src${File.separator}"

            MLog.d("注解java文件解析路径： ${absolutePackageDir}")

            def activityPackage = new File(absolutePackageDir)

            if (activityPackage.exists()) {
                activityPackage.eachFile {
                    //过滤src下特定目录
                    if (!AndroidHelper.filterSrcDir(it)) {
                        inputDirs.add(it)
                    }
                }
            }
        }

        return inputDirs
    }

}