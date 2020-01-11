# kevin-cloud-dubbo2.0

#### 介绍
{**以下是码云平台说明，您可以替换此简介**
码云是 OSCHINA 推出的基于 Git 的代码托管平台（同时支持 SVN）。专为开发者提供稳定、高效、安全的云端软件开发协作平台
无论是个人、团队、或是企业，都能够用码云实现代码托管、项目管理、协作开发。企业项目请看 [https://gitee.com/enterprises](https://gitee.com/enterprises)}

#### 软件架构
#### 通过springcloud 与 Dubbo结合， 对内RPC

##### cloud 模块
    包括平台公共服务， 消息服务， OSS上传服务
##### configuration 
    统一配置管理
##### gateway  统一网关服务
##### provider 模块
    Dubbo 服务提供者， Dao层数据库资源管理
##### service 模块
    springcloud feign服务， 可以通过调用provider 服务，也可以 feign 之间进行服务调用， 实现
    springcloud 和duboo结合的架构    
   
##### 统一的依赖管理模块 dependencies
    进行统一 <code>Maven</code> 管理， 版本控制
##### common模块
    包括公共 dto、 utils、公用的provider        


#### 安装教程
    mvn clean package

#### 使用说明





#### 参与贡献


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
