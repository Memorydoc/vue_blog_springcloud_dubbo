package com.kevin.cloud.provider.api;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-23 22:30
 **/
public interface DistributedLocker {

    RLock lock(String lockKey);

    RLock lock(String lockKey, int timeout);

    RLock lock(String lockKey, TimeUnit unit, int timeout);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

    void setRedissonClient(RedissonClient redissonClient);
}