package com.zly.judy.plugin

import com.android.build.gradle.api.BaseVariant
import com.zly.judy.plugin.utils.AndroidHelper
import com.zly.judy.plugin.utils.MLog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * Judy Bridge apply插件
 * 1、根据变种构建代码解析与生成中间层接口源文件的任务，并注册至registerJavaGeneratingTask
 * 2、创建一个独立执行的任务组(judy)
 * Created by Zhuliya on 2018/8/10
 */
class JudyBridgePlugin implements Plugin<Project> {

    Project project
    /**
     * 生成的中间层接口源文件路径
     */
    def outBuildDir
    /**
     * 配置参数
     */
    private ConfigExtension configExtension

    @Override
    void apply(Project project) {
        this.project = project

        if (!AndroidHelper.hasLibraryModule(project)) {
            //插件配置必须在 Library Module 下 build.gradle 文件中
            MLog.e("The plug-in configuration must be in the build.gradle file under the Library Module")
            return
        }

        //构建输入中间层目录路径
        outBuildDir = "${project.buildDir}${File.separator}generated${File.separator}source${File.separator}judyBridge"
        //创建扩展配置
        configExtension = project.extensions.create("judyConfig", ConfigExtension)

        project.afterEvaluate {

            //设置日志输出
            MLog.setLogger(configExtension.logDebug)

            //创建生成中间层接口源文件Task
            Task generatorTask = createGeneratorTask(project)

            //创建一个独立执行的任务组(judy)
            Task task = project.task("generatorJudyBridge")
            task.setGroup("judy")
            //添加独立执行创建中间层接口Task
            task.dependsOn(generatorTask)
        }
    }

    /**
     * 创建生成中间层接口源文件Task
     * @param project
     * @param isExceNow 是否立即执行
     * @return
     */
    def createGeneratorTask(Project project) {

        Task generatorTask = project.tasks.create("executeGeneratorCode", GeneratorTask)
        generatorTask.outputs.dir(outBuildDir)

        //遍历变种
        AndroidHelper.forEachVariant(project) { BaseVariant variant ->

            if (configExtension.alwaysCompileJava) {
                //总是编译java文件
                def variantName = AndroidHelper.captureChar(variant.getName())
                Task compileJavaTask = project.tasks.getByName("compile${variantName}JavaWithJavac")
                compileJavaTask.outputs.upToDateWhen { false }
            }

            //注册Java生成任务，添加编译目录（build/generated/source/judyBridge）
            variant.registerJavaGeneratingTask(generatorTask,
                    generatorTask.outputs.files.files)
        }

        return generatorTask
    }

}