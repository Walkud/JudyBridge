package com.zly.judy.plugin

import com.android.build.gradle.BaseExtension
import com.zly.judy.plugin.utils.AndroidHelper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException

/**
 * 各业务模块独立运行插件
 */
class JudyAloneRunPlugin implements Plugin<Project> {

    static final String SHARE_USER_ID = "SHARE_USER_ID"
    Properties localProperties

    @Override
    void apply(Project project) {

        //加载本地属性文件
        loadLocalProperties(project)

        //独立运行配置
        aloneRunConfiguration(project)

        //添加宿主模块依赖扩展
        addDependModule(project)
    }

    /**
     * 加载本地属性文件
     * @return
     */
    def loadLocalProperties(Project project) {
        localProperties = new Properties()
        try {
            def localFile = project.rootProject.file('local.properties')
            if (localFile != null && localFile.exists()) {
                localProperties.load(localFile.newDataInputStream())
            } else {
                //未找到本地属性文件
                throw new StopExecutionException("local.properties file not found by root path.")
            }
        } catch (Exception ignored) {
            //加载本地属性文件异常
            throw new StopExecutionException("Error loading local.properties file.")
        }
    }

    /**
     * 独立运行配置
     * @param project
     * @return
     */
    def aloneRunConfiguration(Project project) {

        //检查是否为主模块
        def isMainApp = project.pluginManager.hasPlugin('com.android.application')

        if (!isMainApp) {

            //当前模块是否独立运行
            def aloneRun = "true" == localProperties.getProperty(project.name)
            project.extensions.aloneRun = aloneRun

            Map<String, String> pluginMap = new HashMap<>()
            //根据本地属性文件配置设置android插件类型
            if (aloneRun) {
                //独立模式
                pluginMap.put("plugin", "com.android.application")
            } else {
                //集成模式
                pluginMap.put("plugin", "com.android.library")
            }
            project.apply(pluginMap)


            BaseExtension baseExtension = AndroidHelper.getAndroidExtension(project)
            def debugSourceSet = baseExtension.sourceSets.getByName("debug")

            if (aloneRun) {

                //添加独立运行默认的applicationId，如果需要自定义，请在配置文件中重写即可（参照业务模块B的gradle文件）
                baseExtension.defaultConfig.applicationId = "com.judy.${project.name.toLowerCase()}"

                //添加ShareUserId配置
                addShareUserIdConfiguration(baseExtension.defaultConfig.manifestPlaceholders)

                //配置独立运行模式下代码及资源路径等
                debugSourceSet.setRoot('src/aloneRun')
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
        } else {
            //在debug buildType中添加shareUserId配置
            BaseExtension baseExtension = AndroidHelper.getAndroidExtension(project)
            def debugBuildType = baseExtension.buildTypes.getByName("debug")

            //添加defaultConfig shareUserId空串配置
            baseExtension.defaultConfig.manifestPlaceholders.put(SHARE_USER_ID, "")
            //添加debug buildType shareUserId配置
            addShareUserIdConfiguration(debugBuildType.manifestPlaceholders)
        }
    }

    /**
     * 添加ShareUserId配置
     * @param manifestPlaceholders
     * @return
     */
    def addShareUserIdConfiguration(Map<String, Object> manifestPlaceholders) {
        if (localProperties.containsKey(SHARE_USER_ID)) {
            String shareUserId = localProperties.getProperty(SHARE_USER_ID)
            if (!shareUserId) {
                throw new IllegalArgumentException("The ${SHARE_USER_ID} attribute in local.properties file cannot be empty.")
            }

            manifestPlaceholders.put(SHARE_USER_ID, shareUserId)
        }
    }

    /**
     * 添加宿主模块依赖扩展
     * @param project
     * @return
     */
    private addDependModule(Project project) {
        /**
         * 宿主app依赖各业务模块
         * @param dependMode 依赖模式 ，默认为implementation 或 compile
         * @param moduleName 模块名称 ，例如 : 'bundle_login'
         */
        project.extensions.dependModule = { dependMode = null, moduleName ->

            //查找组件模块
            def componentProject = project.rootProject.subprojects.find {
                it.name == moduleName
            }

            if (componentProject == null) {
                throw new IllegalArgumentException("主项目依赖错误，请检查依赖模块名称是否正确和根目录settings.gradle配置文件中是否include该模块：${moduleName}")
            }

            //判断依赖组件模块当前是否为独立运行模式
            def aloneRun = "true" == localProperties.getProperty(componentProject.name)
            if (aloneRun) {
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