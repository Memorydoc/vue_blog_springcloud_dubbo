# kevin-cloud-dubbo2.0

#### 介绍
是使用springcloud-alibab-dubbo  集成  Rocket、OSS、gateway、sentinel、nacos 与一体的三层架构开发框架， 后续还会集成 Solo搜索引擎
分布式事务框架 等等
# 软件架构
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
    
#### provider-elasticsearch  搜索引擎模块
    提供了索引初始化方法 和 动态匹配索引方法， 使用spring-data，
    提供全文查找、分页、 高亮字段 等一系列功能，使用 tracntionclient 查询，
    如果需要使用spring-data 的方法，代码中也有提供部分代码， 使用spring-data方法查询可以使用
    spring提供的api，可以自己定制findByxxx方法， 通过实现 <code>ElasticsearchRepository</code>接口
    
       


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
