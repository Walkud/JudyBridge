# JudyBridge

## 项目介绍

Android 一个轻量、自由、低学习成本的模块化库。


## 使用说明


1、在根目录build.gradle构建脚本中添加插件依赖：


```
buildscript {
  repositories {
  	maven{
   		url "https://dl.bintray.com/zly/maven"
    }
    jcenter()
   }
   
  dependencies {
  	classpath classpath 'com.walkud.judy:judy-plugin:0.1.1'
  }
}


```

2、在基础库模块中buidle.gradle构建脚本中添加依赖：

```
apply plugin: 'judy.bridge'

dependencies {
	api 'com.walkud.judy:judy-api:0.1.0'
}

```

3、在各业务模块中(AS标准目录结构)创建服务xxx类,在该类上添加@JudyBridge注解，例如：

```
@JudyBridge
public class LoginJudy{

	public boolean isLogin() {……}
	
	……
}

```

4、在Terminal执行如下命令(生成中间层抽象类，每次对服务类有任何改动，重新执行命令即可，无需重新build项目)：

```
//mac
./gradlew generatorJudyBridge

//windows
gradlew generatorJudyBridge


```

PS：也可以在AndroidStudio 右侧边栏gradle(Gradle projects) ---> [基础库名称] ---> Tasks ---> judy ---> 双击generatorJudyBridge


5、调用：

```

Judy.getBridge(LoginJudyBridge.class).isLogin();


```

PS：详细使用示例请参考代码


## 原理

模块化开发通常情况都会有一个基础库，相关模块都会依赖基础库。正是利用这种特性，通过自定义 Gradle Plugin 实现apt功能，生成各业务模块服务的中间层抽象类（ 接口 ）至基础库下（ 路径：build/generated/source/judyBridge/ ），
业务模块通过动态代理、反射调用对应的具体实现，达到跨模块通信的功能。原理和实现代码都非常简单，源码加上注解也只有10+个类，学习相对简单。


自定义Gradle Plugin apt功能简述：
1. 对根路径下的所有模块源码目录（ AndroidStudio 标准目录结构的src下 ）进行递归遍历 java 源文件。
2. 解析该类是否包含指定注解,如果包含则解析并在指定路径下（ 路径：build/generated/source/judyBridge/ ）生成中间层抽象类源文件，否则跳过该文件。


## 混淆
待完善

## License

```
MIT License

Copyright (c) 2018 Zhu LiYa

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
