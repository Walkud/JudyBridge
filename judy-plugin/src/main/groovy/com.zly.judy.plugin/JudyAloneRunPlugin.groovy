package com.zly.judy.plugin

import com.android.build.gradle.BaseExtension
import com.zly.judy.plugin.utils.AndroidHelper
import org.gradle.api.Plugin
import org.gradle.api.Project

class JudyAloneRunPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        //先加载local.properties文件
        Properties localProperties = new Properties()
        try {
            def localFile = project.rootProject.file('local.properties')
            if (localFile != null && localFile.exists()) {
                localProperties.load(localFile.newDataInputStream())
            }
        } catch (Exception ignored) {
            println("local.properties not found")
        }

        def ext = project.extensions

        //检查是否为主模块
        def isMainApp = project.pluginManager.hasPlugin('com.android.application')

        if (!isMainApp) {

            //当前模块是否独立运行
            def appPlugin = "true" == localProperties.getProperty(project.name)
            ext.appPlugin = appPlugin

            Map<String, String> pluginMap = new HashMap<>()
            //根据本地属性文件配置设置android插件类型
            if (appPlugin) {
                //独立模式
                pluginMap.put("plugin", "com.android.application")
            } else {
                //集成模式
                pluginMap.put("plugin", "com.android.library")
            }
            project.apply(pluginMap)


            BaseExtension baseExtension = AndroidHelper.getAndroidExtension(project)
            def debug = baseExtension.sourceSets.getByName("debug")

            if (appPlugin) {
                //配置独立运行模式下代码及资源路径等
                debug.setRoot('src/aloneRun')
                project.afterEvaluate {
                    createAloneRunFile(project)
                }
            } else {
                //解决当转换使用独立模块运行后再切换为集成模式后，运行app会出现异常
                project.afterEvaluate {
                    project.tasks.getByName("compileDebugJavaWithJavac") {
                        outputs.upToDateWhen {
                            //根据是否存在独立标记来返回是否使用缓存
                            return !deleteAloneRunFile(project)
                        }
                    }
                }
            }
        }

        /**
         * 主项目依赖组件模块
         * @param dependMode 依赖模式 ，默认为implementation 或 api
         * @param moduleName 模块名称 ，例如 : 'bundle_login'
         */
        ext.dependModule = { dependMode = null, moduleName ->

            //查找组件模块
            def componentProject = project.rootProject.subprojects.find {
                it.name == moduleName
            }

            if (componentProject == null) {
                throw new IllegalArgumentException("主项目依赖错误，请检查依赖模块名称是否正确和根目录settings.gradle配置文件中是否include该模块：${moduleName}")
            }

            //判断依赖组件模块当前是否为独立运行模式
            def appPlugin = "true" == localProperties.getProperty(componentProject.name)
            println("appPlugin:${appPlugin},project:${project.name}")

            if (appPlugin) {
                //组件如果为独立运行，则跳过对该组件依赖
                println("${componentProject.name} 模块为独立运行组件")
                return
            }

            //判断依赖模式
            def isNewGragle = (project.gradle.gradleVersion as float) >= 4.1F
            if (dependMode == null) {
                dependMode = isNewGragle ? 'implementation' : 'compile'
            }
            println("project:${project.getName()} -> ${dependMode} ${componentProject.name}")

            //添加依赖
            project.dependencies.add(dependMode, componentProject)
        }
    }

/**
 * 创建独立运行临时标记文件，用于解决当转换使用独立模块运行后再切换为集成模式后，运行app会出现异常
 * 原因是由于切换后compileDebugJavaWithJavac 还是存在缓存(缓存中存在独立运行的class)，任务被标记为UP-TO-DATE，导致运行app时找不到独立模块的class
 * @return
 */
    static def createAloneRunFile(Project project) {
        File dirFile = new File("${project.buildDir}${File.separator}tmp")
        if (!dirFile.exists()) {
            dirFile.mkdirs()
        }

        File aloneRunFlagFile = new File(dirFile, "aloneRun")
        if (!aloneRunFlagFile.exists()) {
            aloneRunFlagFile.createNewFile()
        }
    }

/**
 * 删除独立运行临时标记文件
 * @return true：存在独立运行临时标记，false：不存在独立运行标记
 */
    static boolean deleteAloneRunFile(Project project) {
        File dirFile = new File("${project.buildDir}${File.separator}tmp", "aloneRun")
        if (dirFile.exists()) {
            dirFile.delete()
            return true
        }
        return false
    }
}