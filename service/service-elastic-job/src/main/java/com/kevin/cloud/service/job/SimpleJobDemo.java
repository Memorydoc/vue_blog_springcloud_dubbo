package com.kevin.cloud.service.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kevin.cloud.service.manage.TaskJob;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.provider.job
 * @ClassName: SimpleJobDemo
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/11 14:52
 * @Version: 1.0
 */
@TaskJob(jobName = "EjobA",cron = "0/5 * * * * ? ",desc = "EjobA任务")
public class SimpleJobDemo implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(String.format("------Thread ID: %s, 任务总分片数: %s, " +
                        "当前分片项: %s.当前参数: %s," +
                        "当前任务名: %s.当前任务参数: %s"
                ,
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()
        ));
    }
}