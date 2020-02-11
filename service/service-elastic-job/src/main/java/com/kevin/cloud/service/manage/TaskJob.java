package com.kevin.cloud.service.manage;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.service.manage
 * @ClassName: TaskJob
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/12 1:10
 * @Version: 1.0
 */

/**
 * 重点
 * 里添加了org.springframework.stereotype.Component
 * 为了可以在ApplicationContext中获取当前任务的bean和任务配置
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component // 这里配置了 在 applicationContext  就可以获取
public @interface TaskJob {

    @NotNull
    String jobName();

    @NotNull
    String cron();

    @NotNull
    String desc();

    boolean failover() default false;

    String shardingItemParameters() default "1=A";  // 分片参数

    String jobParameter() default ""; // 任务参数

    boolean overwrite() default true;

    int shardingTotalCount() default 1; // 分片总数
}
