package com.kevin.cloud.service.manage;

import com.alibaba.nacos.client.utils.StringUtils;
import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.service.manage
 * @ClassName: RegisterJob
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/12 1:08
 * @Version: 1.0
 */
@Component
@Slf4j
public class RegisterJob implements ApplicationContextAware, InitializingBean {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 等所有的bean初始化之后，注册任务
     * */
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,Object> registerJobs = applicationContext.getBeansWithAnnotation(TaskJob.class);
        for(Map.Entry<String,Object> entry : registerJobs.entrySet()){
            try {
                Object object = entry.getValue();
                if(! (object instanceof ElasticJob)){
                    throw new ClassCastException("["+object.getClass().getName() + "] The class type is not com.dangdang.ddframe.job.api.ElasticJob");
                }
                TaskJob taskJob = AnnotationUtils.findAnnotation(object.getClass(),TaskJob.class);
                SpringJobScheduler springJobScheduler = new SpringJobScheduler(
                        (ElasticJob) object,
                        zookeeperRegistryCenter,
                        getJobConfiguration(taskJob,object)
                );
                springJobScheduler.init();
            }catch (Exception e){
                log.error("注册任务异常 ",e);
            }
        }
    }
    /**
     * 配置job任务
     * @param taskJob
     * @param object
     * @return
     */
    private LiteJobConfiguration getJobConfiguration(TaskJob taskJob, Object object) {

        Optional.ofNullable(taskJob.jobName()).orElseThrow(() -> new IllegalArgumentException("The jobName cannot be null !"));
        Optional.ofNullable(taskJob.cron()).orElseThrow(() -> new IllegalArgumentException("The cron cannot be null !"));
        Optional.ofNullable(taskJob.desc()).orElseThrow(() -> new IllegalArgumentException("The desc cannot be null !"));

        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(
                JobCoreConfiguration
                        .newBuilder(taskJob.jobName(), taskJob.cron(),taskJob.shardingTotalCount())
                        .shardingItemParameters(StringUtils.isEmpty(taskJob.shardingItemParameters()) ? null : taskJob.shardingItemParameters())
                        .description(taskJob.desc())
                        .failover(taskJob.failover())
                        .jobParameter(StringUtils.isEmpty(taskJob.jobParameter()) ? null : taskJob.jobParameter())
                        .build(),
                object.getClass().getName());

        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
                .newBuilder(simpleJobConfiguration).overwrite(taskJob.overwrite())
                .monitorExecution(true)
                .build();
        return liteJobConfiguration;
    }

}