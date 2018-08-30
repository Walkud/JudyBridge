# JudyBridge

## 项目介绍

Android 一个简单、低学习成本的模块化库。

## 原理

模块化开发通常情况都会有一个基础库，相关模块都会依赖基础库。正是利用这种特性，通过自定义 Gradle Plugin 实现apt功能，生成各业务模块对外暴露的抽象类（ 接口 ）至基础库下（ 路径：build/generated/source/judyBridge/ ），
业务模块通过动态代理、反射调用对应的具体实现，达到跨模块通信的功能。原理和实现代码都非常简单，源码加上注解也只有10+个类，学习相对简单。


自定义Gradle Plugin apt功能简述：
1. 对根路径下的所有模块源码目录（ AndroidStudio 标准目录结构的src下 ）进行递归遍历 java 源文件。
2. 解析该类是否包含指定注解,如果包含则解析并在指定路径下（ 路径：build/generated/source/judyBridge/ ）生成抽象类源文件，否则跳过该文件。


## 使用说明

待完善