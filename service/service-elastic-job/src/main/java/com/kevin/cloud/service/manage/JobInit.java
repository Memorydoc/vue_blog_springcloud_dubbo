/*
package com.kevin.cloud.service.manage;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.kevin.cloud.commons.utils.CommonUtils;
import com.kevin.cloud.service.SpringContextServiceUtil;
import com.kevin.cloud.service.job.KevinCloudJob;
import com.kevin.cloud.service.job.SimpleJobDemo;
import com.kevin.cloud.service.listener.DefaultElasticJobListener;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;

*/
/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.service.manage
 * @ClassName: JobInit
 * @Author: kevin
 * @Description: 分布式任务初始化
 * @Date: 2020/2/11 19:19
 * @Version: 1.0*//*



// 说明： 这个注册任务的类可以使用  只不过RegisterJob 更好一点

@Component
@DependsOn("springContextServiceUtil") // 重点:  @PostConstruct 注解会在 ApplicationContextAware 实现类之前执行
public class JobInit {
    private static final String JOBPACKAGE = "com.kevin.cloud.service.job";
    @Autowired
    private ZookeeperRegistryCenter regCenter;
    @Resource
    SimpleJobDemo simpleJobDemo;
    @Resource
    DefaultElasticJobListener defaultElasticJobListener;


    @PostConstruct
    public void jobInit() {
        System.out.println("初始化 job");
        Reflections f = new Reflections(JOBPACKAGE);
        Set<Class<?>> set = f.getTypesAnnotatedWith(EsJobConfig.class);
        for (Class<?> c : set) {
            Object bean = null;
            try {
                bean = c.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            EsJobConfig annotation = c.getAnnotation(EsJobConfig.class);
            KevinCloudJob kevinCloudJob = (KevinCloudJob)SpringContextServiceUtil.getBean(CommonUtils.toLowerCaseFirstOne(c.getSimpleName()));
            ElasticJobListener elasticJobListener = (ElasticJobListener)SpringContextServiceUtil.getBean(annotation.listener());
            jobScheduler(
                    kevinCloudJob,
                    annotation.cron(),
                    annotation.shardingTotalCount(),
                    annotation.shardingItemParameters(),
                    elasticJobListener
            );
        }
    }

*/
/*
*
     * 配置任务详细信息
     *
     * @param jobClass
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     * @return
*//*




    private LiteJobConfiguration getLiteJobConfiguration(Class<? extends KevinCloudJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters).build()
                , jobClass.getCanonicalName())
        ).overwrite(true).build();
    }
    public void jobScheduler(KevinCloudJob simpleJob,
                                     String cron,
                                     int shardingTotalCount,
                                     final String shardingItemParameters,
                                     ElasticJobListener elasticJobListener) {
        SpringJobScheduler springJobScheduler = new SpringJobScheduler(simpleJob, regCenter,
                getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters),
                elasticJobListener);
        springJobScheduler.init();
    }


}
*/
