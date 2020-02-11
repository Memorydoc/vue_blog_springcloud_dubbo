package com.kevin.cloud.service.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.provider.config
 * @ClassName: ElasticRegCenterConfig
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/11 14:54
 * @Version: 1.0
 */
@Configuration
public class ElasticRegCenterConfig {
    /**
     * 配置zookeeper
     * @param serverList
     * @param namespace
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(
            @Value("${regCenter.serverList}") final String serverList,
            @Value("${regCenter.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }
}

