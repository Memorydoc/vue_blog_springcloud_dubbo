/*
package com.kevin.cloud.service.manage;

import java.lang.annotation.*;

*/
/*
*
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.service.manage
 * @ClassName: EsJobConfig
 * @Author: kevin
 * @Description: 分布式任务注解
 * @Date: 2020/2/11 19:15
 * @Version: 1.0

*//*


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EsJobConfig {
    String cron() default "";
    int shardingTotalCount() default 1;
    String shardingItemParameters() default  "1=A";
    String listener() default  "defaultElasticJobListener";
}
*/
