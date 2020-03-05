package com.kevin.cloud.provider.service;

import java.util.concurrent.TimeUnit;

import com.kevin.cloud.provider.api.DistributedLocker;
import com.kevin.cloud.provider.api.RedissLockService;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

/**
 * @program: vue-blog-backend
 * @description: redis分布式锁帮助类
 * @author: kevin
 * @create: 2020-01-23 21:30
 **/
@Service(version = "1.0.0")
@Component
public class RedissLockServiceImpl implements RedissLockService {

    private DistributedLocker redissLock;


    @Override
    public void setLocker(DistributedLocker locker) {
        this.redissLock = locker;
    }

    /**
     * 加锁
     *
     * @param lockKey
     * @return
     */
    @Override
    public RLock lock(String lockKey) {
        return redissLock.lock(lockKey);
    }

    /**
     * 释放锁
     *
     * @param lockKey
     */
    @Override
    public void unlock(String lockKey) {
        redissLock.unlock(lockKey);
    }

    /**
     * 释放锁
     *
     * @param lock
     */
    @Override
    public void unlock(RLock lock) {
        redissLock.unlock(lock);
    }

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    @Override
    public RLock lock(String lockKey, int timeout) {
        return redissLock.lock(lockKey, timeout);
    }

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param unit    时间单位
     * @param timeout 超时时间
     */
    @Override
    public RLock lock(String lockKey, TimeUnit unit, int timeout) {
        return redissLock.lock(lockKey, unit, timeout);
    }

    /**
     * 尝试获取锁
     * @param lockKey
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, TimeUnit.SECONDS, waitTime, leaseTime);
    }

    /**
     * 尝试获取锁
     *
     * @param lockKey
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, unit, waitTime, leaseTime);
    }
}