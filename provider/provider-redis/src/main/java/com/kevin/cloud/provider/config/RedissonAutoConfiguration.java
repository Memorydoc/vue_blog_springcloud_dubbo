package com.kevin.cloud.provider.config;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-23 21:27
 **/

import com.kevin.cloud.provider.api.DistributedLocker;
import com.kevin.cloud.provider.lock.RedissonDistributedLocker;
import com.kevin.cloud.provider.service.RedissLockServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@ConditionalOnClass(Config.class)
public class RedissonAutoConfiguration {

    @Autowired
    private RedissonProperties redisProperties;

    /**
     * 哨兵模式自动装配
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.master-name")
    RedissonClient redissonSentinel() {
        System.out.println("sentinelredis Properties:" + redisProperties.getSentinelAddresses());
        Config config = new Config();
        String[] nodes = redisProperties.getSentinelAddresses();
        List<String> newNodes = new ArrayList(nodes.length);
        Arrays.stream(nodes).forEach((index) -> newNodes.add(
                index.startsWith("redis://") ? index : "redis://" + index));

        SentinelServersConfig serverConfig = config.useSentinelServers()
                .addSentinelAddress(newNodes.toArray(new String[0]))
                .setMasterName(redisProperties.getMasterName())
                .setReadMode(ReadMode.SLAVE)
                .setFailedAttempts(redisProperties.getFailMax())
                .setTimeout(redisProperties.getTimeout())
                .setMasterConnectionPoolSize(redisProperties.getPoolSize())
                .setSlaveConnectionPoolSize(redisProperties.getPoolSize());

        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serverConfig.setPassword(redisProperties.getPassword());
        }

        return Redisson.create(config);
    }

    /**
     * 单机模式自动装配
     * @return
     */
   /* @Bean
    @ConditionalOnProperty(name="redisson.address")
    RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redssionProperties.getAddress())
                .setTimeout(redssionProperties.getTimeout())
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());

        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }

        return Redisson.create(config);
    }*/

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     *
     * @return
     */
    @Autowired
    RedissLockServiceImpl redissLockService;

    @Bean
    DistributedLocker distributedLocker(RedissonClient redissonClient) {
        DistributedLocker locker = new RedissonDistributedLocker();
        locker.setRedissonClient(redissonClient);
        redissLockService.setLocker(locker);
        return locker;
    }

}