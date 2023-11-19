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

### 报错

1. 前端运行dev的时候报错显示`'cross-env' 不是内部或外部命令，也不是可运行的程序`

   好像是yarn没安装成功，重装了就好了

2. 运行`i18n-remove`报错 exit code 1.

   去github的issue上找：[antdesign官方的开源地址](https://github.com/ant-design/ant-design-pro)



## 第二期

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
   2. 在client传入ak、sk字段，请求头传到后端去
   3. 在main中传入ak、sk数据
   4. 在controller校验
   5. 测试一下
      1. 两个都debug运行才能断点成功
   6. 优化：**防止重放请求**
      1. 建个utils层搞加密算法
      2. client多传时间戳、随机数和加密后的sk
      3. controller页改变检验方式

现在已经实现了调用接口，但是很麻烦欸，所以我们打包搞成sdk，这样开发者只需要关心调用哪些接口传那些参数

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



## 第三期

第三节思路：

1. 发布/下线接口
   1. 判断接口使用可以调用（模拟）
   2. InterfaceInfoStatusEnum
   3. 编写online和offonline
2. 前端查看接口需要改一下（有Bug）
3. register新增ak、sk注入
4. 测试真实数据在线调用接口
5. 接口信息里面没有请求参数、、、、、重新生成改表

好啦现在停一下，理一下现在的逻辑

我们目前有三个项目：前端项目(刚刚写的点击调用)、接口平台的后端项目backend、以及提供给开发者的模拟接口项目sdk。

6. 在线调用接口（注意要运行interface接口）

mark一下是怎么找到这两个bug的

1. 首先是前端分页接口报404错误，发现add接口同样出错，这个时候是先去怀疑了前端哪里出错了（因为上次就是前端的一个参数写错了，但是前端我也看不懂捏，是从url的一个个参数入手的，先是token，发现没错，直接访问接口不行，但是后端接口文档运行又是可以的，进一步怀疑url，发现是前后端的路径不一样、、、一个是interface，一个是interfaceInfo、、、、

2. 改了上面的问题后分页可以显示，但是add接口还是出错，显示validInterfaceInfo方法的时候name为null，往上一步就是BeanUtils.copyProperties，果然是参数传错了（悲



## 第四期

1. 调用次数统计
   1. 就是加个表啦
2. 网关知识点讲解（阅读官方文档）



网关的作用：

1. 路由：起到转发的作用，比如有接口 A 和接口 B，网关会记录这些信息，根据用户访问的地址和参数，转发请求到对应的接口（服务器 / 集群）。
   /a => 接口A
   /b => 接口B
   参考文档：[The After Route Predicate Factory](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-after-route-predicate-factory)。
2. 负载均衡：在路由的基础上。
   /c => 服务 A / 集群 A（随机转发到其中的某一个机器）
   uri 从固定地址改成 lb:xxxx
3. 统一处理跨域：网关统一处理跨域，不用在每个项目里单独处理。
   参考文档：[Global CORS Configuration](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#global-cors-configuration)。
4. 发布控制：灰度发布，比如上线新接口，先给新接口分配 20% 的流量，老接口 80%，再慢慢调整比重。
   参考文档：[The Weight Route Predicate Factory](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-weight-route-predicate-factory)。
5. 流量染色：给请求（流量）添加一些标识，一般是设置请求头中，添加新的请求头。
   参考文档：[TheAddRequestHeaderGatewayFilterFactory](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-addrequestheader-gatewayfilter-factory)。
6. 全局染色：[Default Filters](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#default-filters)。
7. 统一接口保护：
   1. 限制请求：[requestheadersize-gatewayfilter-factory](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#requestheadersize-gatewayfilter-factory)。
   2. 信息脱敏：[the-removerequestheader-gatewayfilter-factory](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-removerequestheader-gatewayfilter-factory)。
   3. 降级（熔断）：[fallback-headers](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#fallback-headers)。
   4. 限流：[the-requestratelimiter-gatewayfilter-factory](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-requestratelimiter-gatewayfilter-factory)。
   5. 超时时间：[http-timeouts-configuration](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#http-timeouts-configuration)。
   6. 重试（业务保护）：[the-retry-gatewayfilter-factory](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-retry-gatewayfilter-factory)。
8. 统一业务处理：把一些每个项目中都要做的通用逻辑放到上层（网关），统一处理，比如本项目的次数统计。
9. 统一鉴权：判断用户是否有权限进行操作，无论访问什么接口，我都统一去判断权限，不用重复写。
10. 访问控制：黑白名单，比如限制 DDOS IP。
11. 统一日志：统一的请求、响应信息记录。
12. 统一文档：将下游项目的文档进行聚合，在一个页面统一查看。
    建议用：[knife4j 文档](https://doc.xiaominfo.com/docs/middleware-sources/aggregation-introduction)。



阅读SpringCloudGateWay官方文档

1. 编程式和参数式
2. 路由规则
   ![image-20231120001655524](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231120001655524.png)

![image-20231120012417130](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20231120012417130.png)

## 第五期

## 第六期

## 第七期





## point

上传自己的starter到中央maven仓库中