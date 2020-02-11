package com.kevin.cloud.service.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.provider.joblistener
 * @ClassName: DefaultElasticJobListener
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/11 14:50
 * @Version: 1.0
 */
@Component
public class DefaultElasticJobListener implements ElasticJobListener {
    private static final Logger logger = LoggerFactory.getLogger(DefaultElasticJobListener.class);

    private long beginTime = 0;
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        beginTime = System.currentTimeMillis();

        logger.info("===>{} JOB BEGIN TIME: {} <===",shardingContexts.getJobName());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();
        logger.info("===>{} JOB END TIME: {},TOTAL CAST: {} <===",shardingContexts.getJobName(), endTime - beginTime);
    }
}