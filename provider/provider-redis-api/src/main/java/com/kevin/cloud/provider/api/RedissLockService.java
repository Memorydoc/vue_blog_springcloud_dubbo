package com.kevin.cloud.provider.api;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-23 22:20
 **/
public interface RedissLockService {
    public  void setLocker(DistributedLocker locker);

    /**
     * 加锁
     * @param lockKey
     * @return
     */
    public  RLock lock(String lockKey) ;

    /**
     * 释放锁
     * @param lockKey
     */
    public  void unlock(String lockKey) ;

    /**
     * 释放锁
     * @param lock
     */
    public  void unlock(RLock lock);

    /**
     * 带超时的锁
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    public  RLock lock(String lockKey, int timeout) ;

    /**
     * 带超时的锁
     * @param lockKey
     * @param unit 时间单位
     * @param timeout 超时时间
     */
    public  RLock lock(String lockKey, TimeUnit unit , int timeout);

    /**
     * 尝试获取锁
     * @param lockKey
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public  boolean tryLock(String lockKey, int waitTime, int leaseTime);

    /**
     * 尝试获取锁
     * @param lockKey
     * @param unit 时间单位
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public  boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);
}
