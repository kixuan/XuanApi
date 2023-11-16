# XuanAPI开放平台

## 项目概述

 本项目是一个面向开发者的 API 平台，提供 API
接口供开发者调用。用户通过注册登录，可以开通接口调用权限，并可以浏览和调用接口。每次调用都会进行统计，用户可以根据统计数据进行分析和优化。管理员可以发布接口、下线接口、接入接口，并可视化接口的调用情况和数据。本项目侧重于后端，涉及多种编程技巧和架构设计层面的知识。

## 项目介绍

 一个提供 API 接口供开发者调用的平台：
​ 管理员可以发布接口，同时统计分析各接口的调用情况，用户可以注册登录并开通接口调用权限，浏览接口以及在线进行调试，并可以使用
SDK 轻松地在代码中调用接口。该项目前端简单，后端丰富，涵盖编程技巧和架构设计等多个技术领域。

 实现功能：

1. 主页：浏览接口
2. 管理员：接口管理
3. 用户：在线调试
4. 使用自己开发的客户端SDK，一行代码调用接口

包含内容： API 签名认证、网关、RPC、分布式等必学知识

## 第一期

1. 初始化前端、后端
    1. 直接ant.design拉下来，注意不要用dev模式运行
    2. 项目瘦身（注意）
    2.

## 报错

1. 前端运行dev的时候报错显示`'cross-env' 不是内部或外部命令，也不是可运行的程序`

   好像是yarn没安装成功，重装了就好了

2. 运行`i18n-remove`报错 exit code 1.

   去github的issue上找：[antdesign官方的开源地址](https://github.com/ant-design/ant-design-pro)





列一下第二节的思路

1.  新建一个xuanapi-interface项目
   1. 版本2.7.12
   2. 注解选择SpringWeb、Lombok、Spring Boot DevTools
2. 创建模拟接口
   1. model层建user类
   2. controller层建NameController
      1. 注意三种请求接口方式：Get、Post（url+json传参）
   3. 重构application.yml
   4. 测试一下

我们现在是通过url去测试接口的，但是真正给用户用的时候肯定不是这种方式，而是选择在后端调用第三方api，目前HTTP 调用方式：HttpClient、RestTemplate
、第三方库（OKHTTP、Hutool）

3. 开发调用接口
   1. 加Hutool依赖
   2. 建client层，重写三种请求接口方式
   3. 测试一下
4. api签名认证
   1. user表加ak、sk，重新生成代码、重新加一条数据
   2. 在client传入ak、sk字段
   3. 在main中传入ak、sk数据
   4. 在controller校验
   5. 测试一下
   6. 优化：**防止重放请求**
      1. 建个utils层搞加密算法
      2. client多传时间戳、随机数和加密后的sk
      3. controller页改变检验方式

现在已经实现了调用接口，但是很麻烦欸，所以我们达成jar包搞成sdk，这样开发者只需要关心调用哪些接口传那些参数

5. 新建xuanapi-client-sdk
   1. Lombok、Spring Configuration Processor
   2. pom.xml删掉bulid
   3. 把client、model、controlle复制过来
   4. 创建client配置类，传入ak、sk，新建bean
   5. 新建META-INF
      1. spring.factories
      2. 把client配置类指定为配置类
      3. maven install，按小闪电退出test
      4. sdk放进interface项目
   6. 测试一下



第三节思路：

1. 发布/下线接口
   1. 判断接口使用可以调用（模拟）
   2. InterfaceInfoStatusEnum
   3. 编写online和offonline
2. 前端查看接口需要改一下（有Bug）
3. register新增ak、sk
4. 测试在线调用接口
5. 接口信息里面没有请求参数、、、、、重新生成改表
6. 4.1有点点没看懂、、、、、



mark一下是怎么找到这两个bug的

1. 首先是前端分页接口报404错误，发现add接口同样出错，这个时候是先去怀疑了前端哪里出错了（因为上次就是前端的一个参数写错了，但是前端我也看不懂捏，是从url的一个个参数入手的，先是token，发现没错，直接访问接口不行，但是后端接口文档运行又是可以的，进一步怀疑url，发现是前后端的路径不一样、、、一个是interface，一个是interfaceInfo、、、、

2. 改了上面的问题后分页可以显示，但是add接口还是出错，显示validInterfaceInfo方法的时候name为null，往上一步就是BeanUtils.copyProperties，果然是参数传错了（悲
