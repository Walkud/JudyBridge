package com.walkud.judy.plugin

import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * Judy Bridge apply插件
 * 1、根据变种构建代码解析与生成代理类源文件的任务，并注册至registerJavaGeneratingTask
 * 2、创建一个独立执行的任务组(judy)
 * Created by Zhuliya on 2018/8/10
 */
class JudyBridgePlugin implements Plugin<Project> {

    Project project
    /**
     * 生成的代理类源文件路径
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
            MLog.e("插件配置必须在Library Module下build.gradle文件中")
            return
        }

        outBuildDir = "${project.buildDir}${File.separator}generated${File.separator}source${File.separator}judyBridge"
        configExtension = project.extensions.create("judyConfig", ConfigExtension)

        //设置日志输出
        MLog.setLogger(project.getLogger(), configExtension.logDebug)

        MLog.d("Judy生成源码路径：${outBuildDir}")

        project.afterEvaluate {

            //创建一个独立执行的任务组(judy)
            Task task = project.task("generatorJudyBridge")
            task.setGroup("judy")

            createGeneratorTask(project, task)
        }
    }

    /**
     * 创建生成代理类源文件Task
     * @param project
     * @param isExceNow 是否立即执行
     * @return
     */
    def createGeneratorTask(Project project, Task task) {

        Task compile = null

        //遍历变种
        AndroidHelper.forEachVariant(project) { BaseVariant variant ->
            //创建一个生成任务
            String variantName = AndroidHelper.captureChar(variant.name)
            compile = project.tasks.create("judy${variantName}", GeneratorTask)
            //为任务创建一个输入属性
            compile.inputs.property 'package', variant.applicationId
            //为任务创建一个输出文件 这里是outBuildDir
            compile.outputs.dir(outBuildDir)

            //设置一个固定的输入文件,触发GeneratorTask执行run，理论上build.gradle必定存在
            File inputFile = new File(project.projectDir, "build.gradle")
            compile.inputs.file(inputFile)

            variant.registerJavaGeneratingTask(compile,
                    compile.outputs.files.files)
        }

        if (compile != null) {
            task.dependsOn(compile)
        }
    }

}