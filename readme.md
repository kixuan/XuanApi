# XuanAPI开放平台

## 项目概述

​ 本项目是一个面向开发者的 API 平台，提供 API
接口供开发者调用。用户通过注册登录，可以开通接口调用权限，并可以浏览和调用接口。每次调用都会进行统计，用户可以根据统计数据进行分析和优化。管理员可以发布接口、下线接口、接入接口，并可视化接口的调用情况和数据。本项目侧重于后端，涉及多种编程技巧和架构设计层面的知识。

## 项目介绍

​ 一个提供 API 接口供开发者调用的平台：
​ 管理员可以发布接口，同时统计分析各接口的调用情况，用户可以注册登录并开通接口调用权限，浏览接口以及在线进行调试，并可以使用
SDK 轻松地在代码中调用接口。该项目前端简单，后端丰富，涵盖编程技巧和架构设计等多个技术领域。

​ 实现功能：

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