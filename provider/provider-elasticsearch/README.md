# 这是搜索引擎服务

> 两种操作es的方法都用到了， 只是为了试验哪一种更加好用，后面会继续使用总结出来最好用的供大家参考

> 使用spring-data 的查询方式已经注释， 查询使用的是client的工具， 创建索引和创建mapping 使用的是spring-data
> 注解的方式， 因为注解创建所以比较方便，具体使用spring-data 进行查询的方法，可以参考，还可以通过实现
<code>ElasticsearchRepository</code> 编写自定义的findByxxx 方法， 实现灵活的查询功能