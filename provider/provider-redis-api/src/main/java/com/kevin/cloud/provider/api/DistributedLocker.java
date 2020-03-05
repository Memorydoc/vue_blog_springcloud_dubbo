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

    /**
     * 拿不到lock就不罢休，不然线程就一直block
     * @param lockKey
     * @return
     */
    RLock lock(String lockKey);

    /**
     * timeout为加锁时间，单位为秒
     * @param lockKey
     * @param timeout
     * @return
     */
    RLock lock(String lockKey, long timeout);

    /**
     * timeout为加锁时间，时间单位由unit确定
     * @param lockKey
     * @param unit
     * @param timeout
     * @return
     */
    RLock lock(String lockKey, TimeUnit unit, long timeout);

    /**
     * tryLock()，马上返回，拿到lock就返回true，不然返回false。
     * 带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false.
     * @param lockKey
     * @param unit
     * @param waitTime
     * @param leaseTime
     * @return
     */
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    /**
     * 释放锁
     * @param lockKey
     */
    void unlock(String lockKey);

    void unlock(RLock lock);
}